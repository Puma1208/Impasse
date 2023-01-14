package GameElements;
import com.sun.jdi.StackFrame;

import java.awt.Color;
import java.nio.charset.CharacterCodingException;
import java.util.*;

// To change
public class Player {

    PlayerType type;
    int score = 0;
    Color color;
    // Among checkers that are in the stacks -> consider only stack to move and not individual checkers
    public ArrayList<Checker> playingCheckers = new ArrayList<>();
    ArrayList<StackCheckers> stacks = new ArrayList<>();
    public ArrayList<Checker> checkersToCrown = new ArrayList<>();

    boolean shouldCrown;
    private final int playerIndex;


    public Player(PlayerType type, Color color){
        this.type = type;
        this.color = color;
        this.shouldCrown = false;
        playerIndex = indexFromColor();
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
                System.out.println("checker at " + checker.position.getID() + " can slide to");
                for(Cell c: checker.getPossibleSlide()){
                    System.out.println("    " + c.getID());
                }
                return false;
            }
        }
        for(StackCheckers stack:stacks){
            if(stack.getPossibleSlide().size()>0 || stack.getPossibleTranspose().size()>0){
                System.out.println("stack at " + stack.position.getID() + " can slide to");
                for(Cell c: stack.getPossibleSlide()){
                    System.out.println("    " + c.getID());
                }
                for(Cell c: stack.getPossibleTranspose()){
                    System.out.println("    transpose to " + c.getID());
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
     *
     * @return all the checkers and stacks that can make basic moevs
     */
    public ArrayList<Piece> getAllBasicMoves(){
        ArrayList<Piece> pieces = new ArrayList<>();
        for(Checker c: playingCheckers){
            if(c.stack==null && c.getPossibleSlide().size()>0){
                pieces.add(c);
            }
        }
        for(StackCheckers s: stacks){
            if(s.getPossibleSlide().size()>0 || s.getPossibleTranspose().size()>0){
                pieces.add(s);
            }
        }
        return pieces;
    }

    /**
     *
     * @return all the checkers and stacks that can impasse
     */
    //TODO is it even useful?
    public ArrayList<Piece> getAllImpasse(){
        ArrayList<Piece> toImpasse = new ArrayList<>();
        if(getAllBasicMoves().size()==0){
            for(Checker c: playingCheckers){
                if(c.stack==null){
                    toImpasse.add(c);
                }
            }
            for(StackCheckers s: stacks){
                toImpasse.add(s);
            }
        }
        return toImpasse;
    }


    public void notifyPlayer() {
    }

    public void notifyForCrowning() {

    }


    /**
     * Could do in 2 ways
     *      1) Take randomly a piece then select a random move
     *      2) From all the possible moves select 1
     */
    public void makeMove(){
        ArrayList<Piece> pieces = getAllBasicMoves();
        if(pieces.size()==0){
            //Impasse a random piece
        }else{

            // Get all the possible moves
            HashMap<Piece, ArrayList<Cell>> possibleMoves = getBasicMoves(pieces);
            for (Map.Entry<Piece, ArrayList<Cell>> entry : possibleMoves.entrySet()) {
                Piece key = entry.getKey();
                ArrayList<Cell> value = entry.getValue();
                for(Cell c: value){
                    System.out.println(key.getPosition().getID() + " : " + c.getID());

                }
            }



//            // Randomly make a piece and make a move
            Random rand = new Random();
            int randomPiece = rand.nextInt(pieces.size());
            Piece chosen = pieces.get(randomPiece);

//            if(chosen instanceof StackCheckers){
//                // Differentiate if transpose or slide
//            }else if(chosen instanceof Checker){
//                ArrayList<Cell> possibleSlides = chosen.getPossibleSlide();
//                chosen.getPossibleSlide()
//            }
        }

    }

    /**
     * Make a dictionary to store
     *      Which piece is selected
     *      What move can it make - next cell
     */
    public HashMap<Piece, ArrayList<Cell>> getBasicMoves(ArrayList<Piece> pieces){
        // TODO dictionary
        HashMap<Piece, ArrayList<Cell>> dictionary = new HashMap<>();
        for(Piece p: pieces){
            ArrayList<Cell> slides = p.getPossibleSlide();
            dictionary.put(p, slides);
            if(p instanceof StackCheckers){
                ArrayList<Cell> transposes = p.getPossibleTranspose();
                for(Cell transpose: transposes){
                    dictionary.get(p).add(transpose);

                }

            }

        }
        return dictionary;

    }
}
