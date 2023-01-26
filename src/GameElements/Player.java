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

    public Piece selectedPiece;
    public Cell selectedCell;

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
            if(checker.stack==null){
                if(checker.getPossibleSlide().size()>0){
                    return false;
                }
            }
        }
        for(StackCheckers stack:stacks){
            if(stack.getPossibleSlide().size()>0 || stack.getPossibleTranspose().size()>0){
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
            if(c.mustCrown()){
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

        if(board.crownMode){
            System.out.println("CROWN MODE - choose checker to crown");
            if(piece instanceof Checker && piece.mustCrown()){
                System.out.println("Checker selected for crown " + piece.getPosition().getID());
                this.selectedPiece = piece;

            }else{
                System.out.println("The selected piece is not a checker - not candidate for crowning");
            }
        }else{
            if(this.selectedPiece!=null
                    && this.selectedPiece instanceof StackCheckers && piece instanceof Checker
                    && this.selectedPiece.canTranspose(((Checker) piece).position)) {
//            System.out.println("ready for transpose");
                this.selectedPiece.transpose(((Checker) piece).position);

            }else{
                if(shouldImpasse()){
//                System.out.println("ready for impasse");

                    piece.impasse();
                }
                else{
//                System.out.println("Selected piece");
                    this.selectedPiece = piece;
                }
            }
        }
    }

    /**
     * No selected piece before         Set selectedCell cell
     * Already selected piece before    Checker         Slide if empty
     *                                                  Change to newly selected piece
     *                                  StackCheckers   Slide if empty
     *                                                  Transpose if next selected is checker
     *                                                  Change to newly selected piece
     * @param cell
     */
    public void notifySelectedCell(Cell cell){
        if(board.crownMode){
            System.out.println("CROWN MODE - choose the checker to be crowning the current");
            if(cell.getOccupying() == null && selectedPiece!=null){
                System.out.println("Can't crown " + selectedPiece.getPosition().getID() + " with empty cell");
            }else{
                if(selectedPiece instanceof Checker){
                    System.out.println("CROWN " + selectedPiece.getPosition().getID() + " with " + cell.getOccupying().position.getID());
                    cell.getOccupying().crown((Checker) selectedPiece);
                }
                else if(selectedPiece!=null){
                    System.out.println("Can't crown the stack at " + selectedPiece.getPosition().getID());
                }
            }
            selectedPiece = null;
        }
        else{
            if(selectedPiece==null){
                if(cell.isOccupied()){
                    notifySelectPiece(cell.getOccupyingPiece());
                }
            }else{
                this.selectedCell = cell;
                if(this.selectedPiece instanceof Checker){
                    if(!cell.isOccupied()){
                        selectedPiece.slide(cell);
                    }else{
                        notifySelectPiece(cell.getOccupyingPiece());
                    }
                }
                if(this.selectedPiece instanceof StackCheckers){
                    if(!cell.isOccupied()){
                        selectedPiece.slide(cell);
                    }
                    else if(selectedPiece.canTranspose(cell)){
                        selectedPiece.transpose(cell);
                    }
                    else{
                        notifySelectPiece(cell.getOccupyingStack());

                    }
                }
            }
        }
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
     * Make a dictionary to store
     *      Which piece is selected
     *      What move can it make - next cell
     */
    public ArrayList<Move> getMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        for(Checker p: this.playingCheckers){
            if(p.stack == null){
                for(Cell slide: p.getPossibleSlide()){
                    moves.add(new Move(p, slide));
                }
            }
        }
        for(Piece p: this.stacks){
            for(Cell slide: p.getPossibleSlide()){
                moves.add(new Move(p, slide));
            }
            for(Cell transpose: p.getPossibleTranspose()){
                moves.add(new Move(p, transpose));
            }
        }
        return moves;

    }

    /**
     * Make a basic move by choosing a piece and if needed (not impasse) an empty cell
     */
    public void makeMove() {

    }

    public void deSelectedPiece(){
        this.selectedPiece = null;
        this.selectedCell = null;
    }

    public ArrayList<Piece> getImpasse(){
        ArrayList<Piece> canImpasse = new ArrayList<>();
        if(shouldImpasse()){
            for(StackCheckers stack: stacks){
                canImpasse.add(stack);
            }
            for(Checker checker: playingCheckers){
                if(checker.stack==null){
                    canImpasse.add(checker);
                }
            }
        }
        return canImpasse;
    }

    /**
     *
     * @param toCrown list of checker that need to be crowned
     * @param singles list of single checkers that can be used for crown
     * @return checker[0] the checker to crown and checker[1] the single checker used to crown
     */
    public Checker[] getPairCrown(ArrayList<Checker> toCrown, ArrayList<Checker> singles){
        if(toCrown.size()==1 && singles.size()==1 && toCrown.get(0)==singles.get(0)){
            System.out.println("Impossible to crown a checker with itself");
            return null;
        }
        Random rand = new Random();
        int randomIndexCrown = rand.nextInt(toCrown.size());
        Random rand2;
        int randomIndexToCrown;
        do{
            rand2 = new Random();
            randomIndexToCrown = rand2.nextInt(singles.size());
        }while(!toCrown.get(randomIndexCrown).canCrownWith(singles.get(randomIndexToCrown)));
        Checker[] pair = {toCrown.get(randomIndexCrown), singles.get(randomIndexToCrown)};
        return pair;
    }
}
