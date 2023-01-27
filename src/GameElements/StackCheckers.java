package GameElements;

import java.awt.*;
import java.util.ArrayList;

public class StackCheckers implements Piece{

    Player player;
    Color color;
    Board board;
    Cell position;
    Checker bottomChecker;
    Checker topChecker;


    public StackCheckers(Board board, Checker bottomChecker, Checker topChecker, Cell cell){
        if(bottomChecker.getPlayer().equals(topChecker.getPlayer())){
            this.board = board;
            this.player = bottomChecker.getPlayer();
            this.player.addStack(this);
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

    @Override
    public Board getBoard() {
        return board;
    }

    public Cell getPosition(){  return position; }

    public void setPosition(Cell cell){
        if(this.position!=null){
            this.position.setUnoccupied();
        }
        this.position = cell;
        this.position.setOccupyingStack(this);
    }

    public Checker getBottomChecker(){
        return bottomChecker;
    }

    public Checker getTopChecker(){
        return topChecker;
    }

    public void setPlayer(Player player) {
        if(this.player==null){
            this.player = player;
        }
    }

    @Override
    public boolean canImpasse() {
        // Checks only if both checkers are not null AND IF OTHER MOVES ARE NOT AVAILABLE
        return player.shouldImpasse() && this.bottomChecker!=null && this.topChecker!=null;
    }

    /**
     * COULD CHECK TO CROWN CURRENT
     */
    @Override
    public void impasse() {
        // Maybe no need to check if impasse to not waste time?
        if(canImpasse()){
            this.position.occupyingStack = null;
            this.position.setOccupation(this.bottomChecker);
            this.bottomChecker.stack = null;
            this.player.removeTopChecker(this);
            System.out.println("IMPASSE Stack at " + this.position.getID());
            notifyMoved();
        }
        else{
            System.out.println("Can't impasse stack at " + this.position.getID());
        }

    }

    @Override
    public boolean canSlide(Cell cell) {
        return getPossibleSlide().contains(cell);
    }


    /**
     * Check if can slide
     *      When slide allowed -> check if can bear-off
     *      COULD CHECK TO CROWN CURRENT
     * @param cell
     */
    @Override
    public void slide(Cell cell) {
        if(canSlide(cell)){
            System.out.println("SLIDE from " + this.position.getID() + " to " + cell.getID() );
            setPosition(cell);
            bearOff();
            notifyMoved();
        }else{
            System.out.println("Can't slide stack at " + this.position.getID() +"  to " + cell.getID() + "_" + cell);
        }

    }

    /**
     *
     * @param cell to which check the transpose from the current stack
     * @return if can transpose on cel that is difference 1 on the diagonal
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

    /**
     * COULD CHECK TO CROWN CURRENT
     * @param cell
     */
    @Override
    public void transpose(Cell cell) {
        if(canTranspose(cell)){
            System.out.println("TRANSPOSING from " + this.position.getID() + " to " + cell.getID());
            this.bottomChecker.stack = null;
            this.player.stacks.remove(this);
            new StackCheckers(board, cell.getOccupying(), this.topChecker);
            this.position.setUnoccupied();
            this.position.setOccupation(this.bottomChecker);
            cell.getOccupyingStack().bearOff();
            notifyMoved();
        }
        else{
            System.out.println("Can't transpose stack at " + this.position.getID());
        }
    }

    /**
     * @return all the possible cell positions the current piece can be in
     */
    @Override
    public ArrayList<Cell> getPossibleSlide() {
        ArrayList<Cell> slideList = new ArrayList<>();
        Cell current = this.position;
        while(getNextLeft(current)!=null && !getNextLeft(current).isOccupied()) {
            slideList.add(getNextLeft(current));
            current = getNextLeft(current);

        }
        current = this.position;
        // Slides to the right
        while(getNextRight(current)!=null && !getNextRight(current).isOccupied()) {
            slideList.add(getNextRight(current));
            current = getNextRight(current);

        }
        return slideList;
    }

    /**
     * Must return 0, 1 or 2 possible cells where the current stack can transpose
     * POV: current player
     * @return
     */
    @Override
    public ArrayList<Cell> getPossibleTranspose() {
        ArrayList<Cell> transposeList = new ArrayList<>();
        if(player.color == Color.WHITE && position.row>1){
            if(position.column<board.getSize()){
                Cell right = board.getCell(position.row-1, position.column+1);
                if(canTranspose(right)){
                    transposeList.add(right);
                }
            }
            if(position.column>1){
                Cell left = board.getCell(position.row-1, position.column-1);
                if(canTranspose(left)){
                    transposeList.add(left);

                }
            }


        }
        if(this.player.color == Color.BLACK && position.row< board.getSize()){
            if(this.position.column<board.getSize()){
                Cell left = board.getCell(this.position.row+1, this.position.column+1);
                if(canTranspose(left)){
                    transposeList.add(left);

                }
            }
            if(this.position.column>1){
                Cell right = board.getCell(this.position.row+1, this.position.column-1);
                if(canTranspose(right)) {
                    transposeList.add(right);

                }
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

    /**
     * @return if a checker - ! not stack can be crowned
     */
    @Override
    public boolean mustCrown() {
        return false;
    }


    /**
     * Check if the current stack is in the nearest row
     * with respect to the current player
     *
     * @return if the top checker of the current stack can be removed
     */
    public boolean canBearOff(){
        return (this.color==Color.WHITE && this.position.row==1) ||
                (this.color==Color.BLACK && this.position.row==board.getSize());
    }

    /**
     * Remove stack - from cell, player, bottom checker
     * Remove top checker from player
     * Must NOT crown since in nearest row
     * BUT is available to crown
     */
    public void bearOff(){
        if(canBearOff()){
            System.out.println("BEAR-OFF stack at " + this.position.getID());
            this.position.setUnoccupied();
            this.position.setOccupation(this.bottomChecker);
            this.bottomChecker.stack = null;
            this.player.removeTopChecker(this);

        }
        else{
            System.out.println("    Can't bear-off stack at " + this.position.getID());
        }
    }

    /**
     * To put in a method when  a slide
     * a transpose
     * an impasse
     * was performed
     */
    @Override
    public void notifyMoved() {
        this.player.deSelectedPiece();
        board.play.playerMoved();
    }

    /**
     * The current piece - must only be a checker - will crown the parameter
     *
     * @param checker
     */
    @Override
    public void crown(Checker checker) {

    }


}
