package GameElements;
import com.sun.jdi.StackFrame;

import java.awt.Color;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Stack;

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

    /*
            Bear-off
            Crown
     */
    public void checkerOffBoard(Checker checker){
        this.playingCheckers.remove(checker);
    }
    public void shouldCrown(Cell cell){
        if(cell.getOccupyingStack()==null &&
                cell.getOccupying()!=null &&
                cell.getOccupying().getPlayer().equals(this)){
            checkersToCrown.add(cell.getOccupying());
            this.shouldCrown = true;
        }
        else{
            System.out.println("Player can't crown");
        }
    }
    // Checks whether there are available checkers other than current to crown
    // For loop to see if there are not in stack :,(
//    public boolean canCrown(){
//        for(Checker playingChecker: playingCheckers){
//            if(!checkersToCrown.contains(playingChecker) && playingChecker.stack==null){
//                return true;
//            }
//        }
//        return false;
////        return this.playingCheckers.size()>
//    }
    public void crowning(Cell cellToCrown, Checker checker){
        if(cellToCrown.getOccupyingStack()!=null && cellToCrown.getOccupying()!=null
                && cellToCrown.getOccupying().getPlayer().equals(this)
                && checker.getPlayer().equals(this)){
            checkersToCrown.remove(cellToCrown.getOccupyingStack().bottomChecker);
            this.stacks.add(new StackCheckers(cellToCrown.getOccupyingStack().bottomChecker.getBoard(),
                    cellToCrown.getOccupyingStack().bottomChecker, checker));
            this.shouldCrown = false;
        }
        else{
            System.out.println("Can perform the crowning");
        }
    }

    // Exist available moves
//    public boolean availableMoves(){
////        if(stacks.size()==0 && playingCheckers.size()==0){
////            System.out.println("Player "+ this.playerIndex + "won!");
////        }
//        for(StackCheckers stack:stacks){
//            if(stack)
//        }
//        return false;
//    }

    /**
     * The checkers should not be on a stack
     * @return the list of all the possible checkers that could be used to crown
     */
    public ArrayList<Checker> getSingleChecker(){
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
        for(Checker c: getSingleChecker()){
            if(c.mustCrown){
                singlesToCrown.add(c);
            }
        }
        return singlesToCrown;
    }


}
