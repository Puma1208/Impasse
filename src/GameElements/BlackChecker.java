package GameElements;

import java.awt.*;

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
    @Override
    public void doSlide(Cell cell){
        if(canSlide(cell)){
            setPosition(cell);
        }else{
            System.out.println("Can't go from cell " + this.position.getID() + " to cell " + cell.getID() +
                    " for player " + color);
        }
        // Notify if got to last row
        if(canCrown()){
            System.out.println("can crown the checker at " + position.getID());
            crown();
        }
    }
    public boolean canSlide(Cell goal){
        // Get next checker on diagonal
        return onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row-1, updateCol(position.column, goal.column)), goal);
    }
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

    @Override
    public void doTranspose(Cell cell){
//        System.out.println("Transposing from " + this.position.getID() + " to " + cell.getID());
        if(canTranspose(cell)){
            this.player.removeStack(this.stack);


            this.position.setUnoccupied();
            this.position.setOccupation(this.stack.bottomChecker);
//            this.position.removeCurrentStack();
            this.stack.bottomChecker.removeFromStack();
//            this.stack.bottomChecker.stack = null;
            StackCheckers stackAfterTranspose = new StackCheckers(board, cell.getOccupying(), this);
//            cell.setOccupyingStack(stackAfterTranspose);
            this.player.addStack(stackAfterTranspose);
//            this.board.
//            this.stack.removeFromBoard();
//            this.position = cell;
//            this.stack = null;
        }
        else{
            System.out.println("Can't transpose from " + this.position.getID() + " to " + cell.getID());

        }

    }

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
//    @Override
//    public boolean canCrown(){
//        return this.position.row==1 && this.stack==null;
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
}
