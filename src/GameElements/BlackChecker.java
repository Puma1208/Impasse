package GameElements;

import java.awt.*;
import java.util.ArrayList;

public class BlackChecker extends Checker{

    static Color color = Color.BLACK;
    public BlackChecker(Player player, Cell cell, Board board) {
        super(player, cell, board);
    }
    public Color getColor(){    return this.color;}

    // For black checkers -> can slide from top rows with higher index
    // to rows with lower index
//    @Override
//    public void move(Cell cell){
//
//    }
//    @Override
//    public void doSlide(Cell cell){
//        if(canSlide(cell)){
//            setPosition(cell);
//        }else{
//            System.out.println("Can't go from cell " + this.position.getID() + " to cell " + cell.getID() +
//                    " for player " + color);
//        }
//        // Notify if got to last row
////        if(canCrown()){
////            System.out.println("can crown the checker at " + position.getID());
////            doCrown(cell);
////            this.board.notifyToCrown(this);
////
////        }
//
//    }

    @Override
    public boolean canSlideToNextDiagonal(){
        if(position.getRow()==1){
            return false;
        }
        if(position.getColumn()==board.getSize()){
            return board.getCell(position.getRow()-1, position.getColumn()-1).isOccupied();
        }
        if(position.getColumn()==board.getSize()){
            return board.getCell(position.getRow()-1, position.getColumn()+1).isOccupied();
        }
        return board.getCell(position.getRow()-1, position.getColumn()+1).isOccupied()
                || board.getCell(position.getRow()-1, position.getColumn()-1).isOccupied();
    }

//
//    public boolean canSlide(Cell goal){
//        // Get next checker on diagonal
//        return this.position!=goal && onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row-1, updateCol(position.column, goal.column)), goal);
//    }



    public boolean onCorrectDiagonal(Cell goal){
        return (position.row-goal.row == Math.abs(goal.column-position.column));
    }
    public boolean noCheckerBetween(Cell current, Cell goal){
        if(current.isOccupied()){
            return false;
        }
        current = board.getCell(current.row, current.column);
        if(current.getRow()==goal.getRow() || current.getColumn()==goal.getColumn()){
            return (current.equals(goal) && !current.isOccupied());
        }
        int column = updateCol(current.column, goal.column);
        int row = current.row-1;
        return noCheckerBetween(board.getCell(row, column), goal);
    }

    // Tranposing to the current checker
    public void doTransposeOn(Checker checker){
        doTranspose(checker.getPosition());
    }
//    @Override
//    public void doTranspose(Cell cell){
//        if(canTranspose(cell)){
//            this.player.removeStack(this.stack);
//            this.position.setUnoccupied();
//            this.position.setOccupation(this.stack.bottomChecker);
//            this.stack.bottomChecker.removeFromStack();
//            StackCheckers stackAfterTranspose = new StackCheckers(board, cell.getOccupying(), this, cell);
//            this.player.addStack(stackAfterTranspose);
//            if(stackAfterTranspose.canBearOff()){
//                stackAfterTranspose.doBearOff();
//            }
//        }
//        else{
//            System.out.println("Can't transpose from " + this.position.getID() + " to " + cell.getID());
//
//        }
//
//    }

    // Only using when current is the top checker
    @Override
    public boolean canTranspose(Cell cell){
        if(isTopChecker() && cell.getOccupyingStack()==null
                && cell.occupying!=null
                && areSameColor(cell.occupying)){
            return (cell.row-position.row==Math.abs(position.column-cell.column))
                        && (cell.row-position.row==1);
        }
        System.out.println("Current is not the top checker of a stack");
        return false;
    }

    // ------------------------------------------- crowning
    @Override
    public boolean canCrown(){
        return this.position.row==1 && this.stack==null;
    }
    // Checker that is crowning toCrown
//    @Override
//    public void doCrown(Cell toCrown){
//        StackCheckers stack = new StackCheckers(board, this.player.checkersToCrown.get(0), this);
//        this.player.stacks.add(stack);
//        this.player.checkersToCrown.remove(0);
//    }

//
//    @Override
//    public void crown(){
//        canCrown = true;
//        player.shouldCrown(this.position);
////        if(player.playingCheckers.size()>1){
//            // Need to choose checker to crown
////                this.board
//
//    }

    // Admissible to be a candidate to be on top of a stack
    public boolean isCandidateCrowning(){
        return (stack==null);
    }

    public void chooseToCrown(){
        // get input from player - GUI - or AI
    }


    /*
     The right methods for plays
  */
    @Override
    public boolean canSlide(Cell cell) {
        return false;
    }

    @Override
    public void slide(Cell cell) {

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

    @Override
    public Cell getNextLeft(Cell current){
        if(current.row <=1 || current.column>=board.getSize()){
            return null;
        }
        return board.getCell(current.row-1, current.column+1);
    }

    @Override
    public Cell getNextRight(Cell current){
        if(current.row <=1 || current.column<=1){
            return null;
        }
        return board.getCell(current.row-1, current.column-1);
    }



}
