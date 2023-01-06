package GameElements;

import java.awt.*;
import java.util.ArrayList;

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

//    public void doSlide(Cell cell){
//        if(canSlide(cell)){
//            setPosition(cell);
//        }else{
//            System.out.println("Stack can't go from cell " + position.getID() + " to cell " + cell.getID() +
//                    " for player " + color);
//        }
//        if(canBearOff()){
//            this.doBearOff();
//        }
//    }

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
//
//    public boolean canSlide(Cell goal){
//        if(this.bottomChecker.getColor()==Color.WHITE){
//            return onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row-1, Checker.updateCol(position.column, goal.column)), goal);
//        }else{
//            return onCorrectDiagonal(goal) && noCheckerBetween(board.getCell(position.row+1, Checker.updateCol(position.column, goal.column)), goal);
//        }
//    }

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

//    public void doImpasse(){
//        if(canImpasse()){
//            this.player.removeChecker(topChecker);
//            this.position.setOccupation(this.bottomChecker);
//            this.player.removeStack(this);
//        }
//    }


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


    /*
        The right methods for plays
     */
    @Override
    public boolean canImpasse() {
        // Checks only if both checkers are not null AND IF OTHER MOVES ARE NOT AVAILABLE
        return player.shouldImpasse() && this.bottomChecker!=null && this.topChecker!=null;
    }

    @Override
    public void impasse() {
        this.player.removeChecker(topChecker);
        this.position.occupyingStack = null;
        this.position.setOccupation(this.bottomChecker);
        this.bottomChecker.stack = null;
        this.player.removeStack(this);
    }

    @Override
    public boolean canSlide(Cell cell) {
        return false;
    }

    @Override
    public void slide(Cell cell) {

    }

    /**
     *
     * @param cell to which check the transpose from the current stack
     * @return if can tranpose on cel that is difference 1 on the diagonal
     *          and the cell has 1 checker
     */
    @Override
    public boolean canTranspose(Cell cell) {
        boolean correctRow = false;
        if(this.color==Color.WHITE){
            correctRow = this.position.row-cell.row==1;
        }
        if(this.color==Color.BLACK){
            correctRow = cell.row-this.position.row==1;

        }
        return correctRow
                && Math.abs(this.position.column-cell.column)==1
                && cell.occupyingStack==null && cell.occupying!=null
                && cell.occupying.color==this.color;
    }

    @Override
    public void transpose(Cell cell) {
        this.player.removeStack(this);
        StackCheckers newStack = new StackCheckers(board, cell.getOccupying(), this.topChecker);
        this.player.addStack(newStack);

        this.position.setUnoccupied();
        this.position.setOccupation(this.bottomChecker);

    }

    /**
     * @return all the possible cell positions the current piece can be in
     */
    @Override
    public ArrayList<Cell> getPossibleSlide() {

        return null;
    }

    /**
     * Must return 0, 1 or 2 possible cells where the current stack can transpose
     * POV: current player
     * @return
     */
    @Override
    public ArrayList<Cell> getPossibleTranspose() {
        ArrayList<Cell> transposeList = new ArrayList<>();

        if(this.player.color == Color.WHITE){
            Cell left = board.getCell(this.position.row-1, this.position.column-1);
            Cell right = board.getCell(this.position.row-1, this.position.column+1);
            if(this.position.column!=1 && canTranspose(right)){
                transposeList.add(right);
            }
            if(this.position.column!=8 && canTranspose(left)){
                transposeList.add(left);
            }
        }
        if(this.player.color == Color.BLACK){
            Cell left = board.getCell(this.position.row+1, this.position.column+1);
            Cell right = board.getCell(this.position.row+1, this.position.column-1);
            if(this.position.column!=1 && canTranspose(left)){
                transposeList.add(left);
            }
            if(this.position.column!=8 && canTranspose(right)){
                transposeList.add(right);
            }
        }
        return transposeList;
    }

    /**
     * POV: the player  -> White Row-- and Col++
     *                  -> Black Row++ and Col--
     * @return The next cell to the left of the player
     */
    @Override
    public Cell getNextLeft(Cell current) {
        if(this.color==Color.WHITE){
            if(current.row <=1 || current.column>=board.getSize()){
                return null;
            }
            return board.getCell(current.row-1, current.column+1);
        }
        if(this.color==Color.BLACK){
            if(current.row>=board.getSize() || current.column<=1){
                return null;
            }
            return board.getCell(current.row+1, current.column-1);
        }
        return null;
    }

    /**
     * POV: the player  -> White Row-- and Col--
     *                  -> Black Row++ and Col++
     * @return The next cell to the right of the player
     */
    @Override
    public Cell getNextRight(Cell current) {
        if(this.color==Color.WHITE){
            if(current.row <=1 || current.column<=1){
                return null;
            }
            return board.getCell(current.row-1, current.column-1);
        }
        if(this.color==Color.BLACK){
            if(current.row>=board.getSize() || current.column>=board.getSize()){
                return null;
            }
            return board.getCell(current.row+1, current.column+1);
        }
        return null;
    }

}
