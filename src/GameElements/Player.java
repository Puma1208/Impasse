package GameElements;
import com.sun.jdi.StackFrame;

import java.awt.Color;
import java.util.ArrayList;

// To change
public class Player {
    //White, Black;

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
    public int getPlayerIndex(){    return playerIndex; }
    public Color getColor(){ return this.color; }
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
    public void removeStack(StackCheckers stack) { this.stacks.remove(stack);}


    /*
        The Basic Moves + Impasse
     */
    public void slide(Checker checker, Cell to){
        // Check if checker belongs to player
        if(checker.getPlayer().equals(this)){
            checker.doSlide(to);
            if(checker.canCrown()){
                // To implement
                // Notify board
//                checker.doCrown();
                checker.getBoard().playerNotifyCrown();
            }
        }else{
            System.out.println("The checker doesn't belong to the current player");
        }
    }
//    public void slide(StackCheckers stack, Cell to){
//        // Check if stack belongs to player
//        if(stack.getPlayer().equals(this)){
//            stack.doSlide(to);
//            if(stack.canBearOff()){
//                stack.doBearOff();
//                // Notify board
//                this.stacks.remove(stack);
//                // Notify board?
//                // Check if after bear-off can crown
//            }
//        }else{
//            System.out.println("The checker doesn't belong to the current player");
//        }
//
//    }
//    public void transpose(StackCheckers stack, Checker checker){
//        // Check if stack and checker belongs to player
//        if(stack.getPlayer().equals(this) && checker.getPlayer().equals(this)
//                && stack.topChecker.canTransposeOn(checker)){
//            stack.getTopChecker().doTransposeOn(checker);
//        }else{
//            System.out.println("The checker or/and the stack doesn't belong to the current player");
//        }
//
//    }
    public void impasse(Checker checker){
        // Check if checker belongs to player
        if(checker.getPlayer().equals(this)){
            // Check if can do, or should do impasse
            checker.doImpasse();
        }else{
            System.out.println("The checker doesn't belong to the current player");
        }

    }
    public void impasse(StackCheckers stack){
        // Check if stack belongs to player
        if(stack.getPlayer().equals(this)){
            stack.topChecker.doImpasse();
        }else{
            System.out.println("The stack doesn't belong to the current player");
        }

    }

    public boolean shouldImpasse(){
        for(Checker checker:playingCheckers){
            if(checker.getPossibleSlide().size()==0){
                return false;
            }
        }
        for(StackCheckers stack:stacks){
            if(stack.getPossibleSlide().size()==0 || stack.getPossibleTranspose().size()>0){
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


}
