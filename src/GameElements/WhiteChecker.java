package GameElements;

import java.awt.*;
import java.util.ArrayList;

public class WhiteChecker extends Checker{

    static Color color = Color.WHITE;

    // used to check if can crown this checker -> still single and in the fursest row
    private boolean canCrown;
    public WhiteChecker(Player player, Cell cell, Board board) {
        super(player, cell, board);
    }

    public Color getColor(){    return this.color; }
//    public Player getPlayer(){  return super.getPlayer(); }
//    public Cell getPosition(){  return super.getPosition();}
//    public void setPosition(Cell cell){
//        super.setPosition(cell);
//    }
//    public void putInStack(StackCheckers stack){
//        this.stack = stack;
//    }
//    @Override
//    public void move(Cell cell){
//
//    }

//    @Override
//    public void doSlide(Cell cell){
//        System.out.println("sliding current to cell " + cell.getID());
//        if(canSlide(cell)){
//            setPosition(cell);
//        }else{
//            System.out.println("Can't go from cell " + position.getID() + " to cell " + cell.getID() +
//                    " for player " + color);
//        }
////        if(canCrown()){
////            this.board.notifyToCrown(this);
//////            if(canBearOff()){
//////
//////            }
////        }
////    }
//    public boolean canSlide(Cell goal) {
//        return this.position!=goal && onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row+1, updateCol(position.column, goal.column)), goal);
//    }
    public boolean onCorrectDiagonal(Cell goal){
        return (goal.row-position.row == Math.abs(goal.column-position.column));
    }

    public boolean noCheckerBetween(Cell current, Cell goal){
        if(current.isOccupied()){
            return false;
        }
        if(current.getRow()==goal.getRow() || current.getColumn()==goal.getColumn()){
            return (current.equals(goal)) && !current.isOccupied();
        }
        return noCheckerBetween(board.getCell(current.row+1, updateCol(current.column, goal.column)), goal);
    }

//    @Override
//    public void doTranspose(Cell cell){
//        if(canTranspose(cell)){
//            this.player.removeStack(this.stack);
//            this.position.setUnoccupied();
////            this.position.setOccupation(this.stack.bottomChecker);
//            this.position.setOccupation(this);
//            this.stack.bottomChecker.removeFromStack();
//            StackCheckers stackAfterTranspose = new StackCheckers(board, cell.getOccupying(), this, cell);
//            this.player.addStack(stackAfterTranspose);
//
//            // CAN CROWN
//            if(this.canCrown()){
//                this.canCrown = true;
//            }
//            // CAN BEAR OFF
//            if(stackAfterTranspose.canBearOff()){
//                stackAfterTranspose.doBearOff();
//            }
//        }
//        else{
//            System.out.println("Can't transpose from " + this.position.getID() + " to " + cell.getID());
//        }
//    }


    // Only using when current is the top checker
    @Override
    public boolean canTranspose(Cell cell){
        if(isTopChecker() && cell.getOccupyingStack()==null
                && cell.occupying!=null
                && areSameColor(cell.occupying)){
            return (position.row-cell.row==Math.abs(position.column-cell.column))
                    && (position.row-cell.row==1);
        }
        System.out.println("Current is not the top checker of a stack or not the selected stack is not of the same player");
        return false;
    }

    // Checker that is crowning toCrown
//    @Override
//    public void doCrown(Cell toCrown){
//        StackCheckers stack = new StackCheckers(board, this.player.checkersToCrown.get(0), this);
//        this.player.stacks.add(stack);
//        this.player.checkersToCrown.remove(0);
//    }
//    @Override
//    public void crown(){
//        canCrown = true;
//        player.shouldCrown(this.position);
//
//    }

    //
    // Putting one checker on another
    // Can occur after a slide or transpose or impasse > MUST IMMEDIATELY crown!
    public void crowning(WhiteChecker checker){
//        if(this.crownAllowedOn() && checker.crowningAllowed() && this.player.canCrown){
//            // Creating a new stack for the player
//            StackCheckers stack = new StackCheckers(this, checker, this.position);
//            this.player.addStack(stack);
////            return true;
//        }
//        return false;
    }
    // Maybe a method for the stack
    // Double in the nearest row - so smallest row for white
    // MUST IMMEDIATELY bear off! or remove top checker of that double from the board while
    // still current turn
    public void canBearOff(){

    }

    /*
     The right methods for plays
  */
    @Override
    public boolean canSlide(Cell cell) {
        return getPossibleSlide().contains(cell);
    }

    @Override
    public void slide(Cell cell) {
        super.slide(cell);
        if(mustCrown()){
            this.mustCrown = true;
        }
    }


    /**
     * @return all the possible cell positions the current piece can be in
     */
    @Override
    public ArrayList<Cell> getPossibleSlide() {
        Cell current = this.position;
        ArrayList<Cell> cells = new ArrayList<>();
        // Slides to the left
        while(getNextLeft(current)!=null && !getNextLeft(current).isOccupied()) {
            cells.add(getNextLeft(current));
            current = getNextLeft(current);

        }
        current = this.position;
        // Slides to the right
        while(getNextRight(current)!=null && !getNextRight(current).isOccupied()) {
            cells.add(getNextRight(current));
            current = getNextRight(current);

        }
        return cells;
    }


    /**
     * @return The next cell to the left of the player
     */
    @Override
    public Cell getNextLeft(Cell current) {
        if(current.row>=board.getSize() || current.column<=1){
            return null;
        }
        return board.getCell(current.row+1, current.column-1);
    }

    /**
     * @return The next cell to the right of the player
     */
    @Override
    public Cell getNextRight(Cell current) {
        if(current.row>=board.getSize() || current.column>=board.getSize()){
            return null;
        }
        return board.getCell(current.row+1, current.column+1);
    }

    @Override
    public boolean mustCrown() {
        return super.mustCrown() && this.position.row==board.getSize();
    }

    @Override
    public boolean canCrownWith(Checker checker) {
        return super.canCrownWith(checker) && mustCrown();
    }
}
