package GameElements;

import java.awt.*;

public class StackCheckers implements Piece{

    Player player;
    Color color;
    static Board board;
    Cell position;
    Checker bottomChecker;
    Checker topChecker;


    public StackCheckers(Board board, Checker bottomChecker, Checker topChecker, Cell cell){
        if(bottomChecker.getPlayer().equals(topChecker.getPlayer())){
            this.board = board;
            this.player = bottomChecker.getPlayer();
//            System.out.println("setting player " + player.getPlayerIndex());
//            this.player.addStack(this);
            this.bottomChecker = bottomChecker;
            this.topChecker = topChecker;
            this.position = cell;
            this.bottomChecker.putOnStack(this);
            this.topChecker.putOnStack(this);
            this.color = bottomChecker.getColor();
            setPosition(position);
        }

    }

    public StackCheckers(Board board, Checker bottomChecker, Checker topChecker){
        new StackCheckers(board, bottomChecker, topChecker, bottomChecker.getPosition());
    }
    public Color getColor(){    return color; }
    public Player getPlayer(){   return player;}
    public Cell getPosition(){  return position; }
    public void setPosition(Cell cell){
        if(this.position!=null){
            this.position.setUnoccupied();
        }
        this.position = cell;
        this.bottomChecker.setPosition(cell);
//        this.topChecker.position = position;
        this.position.setOccupyingStack(this);
    }
    public void updatePos(){
//        setPosition(bottomChecker.position);
        this.position.removeCurrentStack();
        this.position = bottomChecker.position;
    }

    public Checker getBottomChecker(){  return bottomChecker; }
    public Checker getTopChecker(){  return topChecker; }


    // Check if from current position can move to the cell toMove
//    public void move(Cell cell){
//        // Slide
//        doSlide(cell);
//        doTranspose(cell);
//        if(canSlide(cell)){
//            setPosition(cell);
//        }else{
//            System.out.println("Stack can't go from cell " + position.getID() + " to cell " + cell.getID() +
//                    " for player " + color);
//        }
//        // Checking if another cell where a checker is to transpose
//        // If first click on stack then on checker that is not a stack -> transpose
//        // Check if transpose allowed
//
//    }

    public void doSlide(Cell cell){
        if(canSlide(cell)){
            setPosition(cell);
        }else{
            System.out.println("Stack can't go from cell " + position.getID() + " to cell " + cell.getID() +
                    " for player " + color);
        }
        if(canBearOff()){
            this.doBearOff();
        }
    }

    public void removeTopFromBoard(){
        this.player.removeTopCheckerFromBoard(this);
    }

    // Use to replace previous stack
//    public void doTranspose(Cell cell){
//        if(canTranpose(cell) && board.players[this.player.indexFromColor()].stacks.contains(this)){
//            board.players[this.player.indexFromColor()].addStack(new StackCheckers(board, cell.getOccupying(), this.topChecker));
//            this.bottomChecker.stack = null;
//            this.position.setOccupation(bottomChecker);
//            this.position.removeStackCurrentStack();
//            board.players[this.player.indexFromColor()].removeStack(this);
//
//        }
//        else{
//            System.out.println("Stack can't transpose " + position.getID() + " to cell " + cell.getID() +
//                    " for player " + color);
//        }
//    }

    public boolean canSlide(Cell goal){
        if(this.bottomChecker.getColor()==Color.WHITE){
            return onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row-1, Checker.updateCol(position.column, goal.column)), goal);
        }else{
            return onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row+1, Checker.updateCol(position.column, goal.column)), goal);
        }
    }

    public boolean onCorrectDiagonal(Cell goal){
        if(this.bottomChecker.getColor()==Color.WHITE){
            return (position.row-goal.row == Math.abs(goal.column-position.column));
        }else{
            // For black stacks
            return (goal.row-position.row == Math.abs(goal.column-position.column));
        }
    }
    public boolean noCheckerBetween(Cell current, Cell goal){
        if(current.isOccupied()){
            return false;
        }
        if(this.bottomChecker.getColor()==Color.WHITE){
            current = board.getCell(current.row, current.column);
            if(current.getRow()==goal.getRow() || current.getColumn()==goal.getColumn()){
                return (current.equals(goal) && !current.isOccupied());
            }
            int column = Checker.updateCol(current.column, goal.column);
            int row = current.row-1;
            return noCheckerBetween(board.getCell(row, column), goal);
        }else{
            // For black stacks
            if(current.getRow()==goal.getRow() || current.getColumn()==goal.getColumn()){
                return (current.equals(goal)) && !current.isOccupied();
            }
            return noCheckerBetween(board.getCell(current.row+1, Checker.updateCol(current.column, goal.column)), goal);
        }
    }

    public void doBearOff(){
        this.position.removeCurrentStack();
//        this.board.notifyToBearOff(this);
    }

    public boolean canBearOff(){
        // Index = 0 -> white player
        if(this.player.getPlayerIndex()==0){
            return this.position.row==1;
        }
        return this.position.row==board.getSize();
    }

    public void setPlayer(Player player) {
        if(this.player==null){
            this.player = player;
        }
    }

    public void doImpasse(){
        if(canImpasse()){
            this.player.removeChecker(topChecker);
            this.position.setOccupation(this.bottomChecker);
            this.player.removeStack(this);
        }
    }
    // Checks only if both checkers are not null NOT IF OTHER MOVES ARE NOT AVAILABLE
    public boolean canImpasse(){
        return this.bottomChecker!=null && this.topChecker!=null;
    }

    public boolean canSlideToNextDiagonal(){
        // WHITE
        if(this.player.getPlayerIndex()==0) {
            if(position.getRow()==board.getSize()){
                return false;
            }
            if(position.getColumn()==board.getSize()){
                return board.getCell(position.getRow()+1, position.getColumn()-1).isOccupied();
            }
            if(position.getColumn()==board.getSize()){
                return board.getCell(position.getRow()+1, position.getColumn()+1).isOccupied();
            }
            return board.getCell(position.getRow()+1, position.getColumn()+1).isOccupied()
                    || board.getCell(position.getRow()+1, position.getColumn()-1).isOccupied();

        }
        else
        if (position.getRow() == 1) {
            return false;
        }
        if (position.getColumn() == board.getSize()) {
            return !board.getCell(position.getRow() - 1, position.getColumn() - 1).isOccupied();
        }
        if (position.getColumn() == board.getSize()) {
            return !board.getCell(position.getRow() - 1, position.getColumn() + 1).isOccupied();
        }
        return !board.getCell(position.getRow() - 1, position.getColumn() + 1).isOccupied()
                || !board.getCell(position.getRow() - 1, position.getColumn() - 1).isOccupied();

    }

    public boolean canTransposeNext(){
        if(this.player.getPlayerIndex()==0){
            // WHITE
            if(board.getCell(position.row-1, position.column-1).getOccupying()!=null
                    && board.getCell(position.row-1, position.column-1).getOccupying().getColor()==this.color){
                return true;
            }
            return (board.getCell(position.row-1, position.column-1).getOccupying()!=null
                    && board.getCell(position.row-1, position.column+1).getOccupying().getColor()==this.color);
        }else{
            // BLACK
            if(board.getCell(position.row+1, position.column-1).getOccupying()!=null
                    && board.getCell(position.row+1, position.column-1).getOccupying().getColor()==this.color){
                return true;
            }
            return (board.getCell(position.row+1, position.column-1).getOccupying()!=null
                    && board.getCell(position.row+1, position.column+1).getOccupying().getColor()==this.color);
        }
    }
//    public boolean canTranpose(Cell cell){
//        if(cell.getOccupying()!= null
//                && cell.getOccupyingStack()==null
//                && cell.getOccupying().getColor().equals(this.color)){
//            // Check if cell on next cell on the diagonal
//            // Check if adjacent
//            // Check if checker on nearer row to player
//            // Adjacent -> diff between rows=diff columns=1 and on nearer row
//
//            // White -> checker should be one row less than stack
//            if(color==Color.WHITE){
//                return ((position.row-cell.row==Math.abs(position.column-cell.column))
//                && (position.row-cell.row==1));
//            }
//            // Black -> checker should be one row more than stack
//            if(color==Color.BLACK){
//                return ((cell.row-position.row==Math.abs(position.column-cell.column))
//                        && (cell.row-position.row==1));
//            }
//        }
//        return false;
//    }

}
