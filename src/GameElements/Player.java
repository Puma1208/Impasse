package GameElements;
import java.awt.Color;
import java.util.*;

public abstract class Player {


    static Board board;
    PlayerType type;
    int score = 0;
    Color color;
    // Among checkers that are in the stacks -> consider only stack to move and not individual checkers
    public ArrayList<Checker> playingCheckers = new ArrayList<>();
    ArrayList<StackCheckers> stacks = new ArrayList<>();
    public ArrayList<Checker> checkersToCrown = new ArrayList<>();

    boolean shouldCrown;
    private final int playerIndex;

    private Piece selectedPiece;
    private Cell selectedCell;

    public boolean moved;

    public Player(PlayerType type, Color color){
        this.type = type;
        this.color = color;
        this.shouldCrown = false;
        this.selectedPiece = null;
        this.selectedCell = null;
        moved = false;
        playerIndex = indexFromColor();
    }

    public static void setBoard(Board b){
        board = b;
    }
    public static Player createPlayer(PlayerType type, Color color){
        if(type==PlayerType.RANDOM){
            return new RandomPlayer(color);
        }
        else return new HumanPlayer(type, color);
    }

    public int indexFromColor(){
        if(this.color==Color.WHITE){
            return 0;
        }
        return 1;
    }

    public int getPlayerIndex(){
        return playerIndex;
    }

    public Color getColor(){
        return this.color;
    }

    public void removeChecker(Checker checker){
        if(this.playingCheckers.contains(checker)){
            playingCheckers.remove(checker);
        }
    }
    public void removeTopCheckerFromBoard(StackCheckers stack){
        removeChecker(stack.topChecker);
        this.stacks.remove(stack);
    }
    public void addChecker(Checker checker){
        this.playingCheckers.add(checker);
    }

    public void addStack(StackCheckers stack){
        this.stacks.add(stack);
        stack.setPlayer(this);
    }
    public void removeStack(StackCheckers stack) {
        removeChecker(stack.bottomChecker);
        removeChecker(stack.topChecker);
        this.stacks.remove(stack);
    }


    public void removeTopChecker(StackCheckers stack){
        removeChecker(stack.topChecker);
        this.stacks.remove(stack);
    }



    public boolean shouldImpasse(){
        for(Checker checker:playingCheckers){
            if(checker.stack==null && checker.getPossibleSlide().size()>0){
//                System.out.println("checker at " + checker.position.getID() + " can slide to");
                for(Cell c: checker.getPossibleSlide()){
                    System.out.println("    " + c.getID());
                }
                return false;
            }
        }
        for(StackCheckers stack:stacks){
            if(stack.getPossibleSlide().size()>0 || stack.getPossibleTranspose().size()>0){
//                System.out.println("stack at " + stack.position.getID() + " can slide to");
                for(Cell c: stack.getPossibleSlide()){
//                    System.out.println("    " + c.getID());
                }
                for(Cell c: stack.getPossibleTranspose()){
//                    System.out.println("    transpose to " + c.getID());
                }
                return false;

            }
        }
        return true;
    }


    /**
     * The checkers should not be on a stack
     * @return the list of all the possible checkers that could be used to crown
     */
    public ArrayList<Checker> getSingleCheckers(){
        ArrayList<Checker> singles = new ArrayList<>();
        for(Checker c: playingCheckers){
            if(c.stack==null){
                singles.add(c);
            }
        }
        return singles;
    }

    /**
     * Return all the checkers the player should crown from previous turns
     * @return
     */
    public ArrayList<Checker> getCheckersToCrown(){
        ArrayList<Checker> singlesToCrown = new ArrayList<>();
        for(Checker c: getSingleCheckers()){
            if(c.mustCrown && c.mustCrown()){
                singlesToCrown.add(c);
            }
        }
        return singlesToCrown;
    }

    /**
     * Either chosen    for later slide
     *                  for transposing now
     *                  for impasse now
     * SelectedPiece null       -> Impasse
     *                          -> SelectedPiece is piece
     * SelectedPiece Checker    -> Checker:     switch
     *                          -> Stack:       switch
     * SelectedPiece Stack      -> Checker:     transpose or switch
     *                          -> Stack:       switch
     *
     * @param piece
     */
    public void notifySelectPiece(Piece piece) {
        if(this.selectedPiece!=null){
            if(this.selectedPiece instanceof StackCheckers && piece instanceof Checker) {
                this.selectedPiece.transpose(((Checker) piece).position);
            }else{
                this.selectedPiece = piece;
            }
        }else{
            if(shouldImpasse()){
                piece.impasse();
            }
            else{
                this.selectedPiece = piece;
            }
        }
    }

    /**
     * Empty cell   to slide now
     * @param cell
     */
    public void notifySelectedCell(Cell cell){
        this.selectedCell = cell;
        if(this.selectedPiece !=null){
            selectedPiece.slide(cell);
        }
    }
    public void notifyForCrowning() {

    }



    /**
     * Make a dictionary to store
     *      Which piece is selected
     *      What move can it make - next cell
     */
    public HashMap<Piece, ArrayList<Cell>> getBasicMoves(){
        HashMap<Piece, ArrayList<Cell>> dictionary = new HashMap<>();
        for(Piece p: this.playingCheckers){
            ArrayList<Cell> possiblePos = new ArrayList<>();
            ArrayList<Cell> slides = p.getPossibleSlide();
            for(Cell slide: slides){
                possiblePos.add(slide);
            }
            dictionary.put(p, possiblePos);

        }
        for(Piece p: this.stacks){
            ArrayList<Cell> possiblePos = new ArrayList<>();
            ArrayList<Cell> slides = p.getPossibleSlide();
            for(Cell slide: slides){
                possiblePos.add(slide);
            }
            ArrayList<Cell> transposes = p.getPossibleTranspose();
            for(Cell transpose: transposes){
                possiblePos.add(transpose);
            }
            dictionary.put(p, possiblePos);
        }
        return dictionary;

    }

    /**
     * After the player has decided to
     *      impasse - because no choice
     *      slide a single
     *      slide a double
     *      transpose
     * he can have other moves available
     */
    public void execute(Piece p, Cell goal){
        // Check for impasse
        if(p.getPosition()==goal){
            p.impasse();
            if(p.getPlayer().getCheckersToCrown().size()>0){
                notifyForCrowning();
                GamePlay gamePlay = p.getBoard().gamePlay;
            }
        }
        // Check if stack   -> slide
        //                  -> transpose
        //          checker -> slide
        else
        {
            if(p instanceof StackCheckers){
                if(p.canTranspose(goal)){
                    p.transpose(goal);
                }
                else if (p.canSlide(goal)){
                    p.slide(goal);
                }
            }else if(p instanceof Checker){
                if(p.canSlide(goal)){
                    p.slide(goal);
                }
            }
        }

        // If current was stack and became single -> must be crowned
        // After bear-off or impasse can crown if still exist by current if became single


    }


    /**
     * Make a basic move by choosing a piece and if needed (not impasse) an empty cell
     */
    public void makeMove() {

    }

    public void deSelectedPiece(){
        this.selectedPiece = null;
    }
}
