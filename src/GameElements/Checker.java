package GameElements;
import java.awt.*;
import java.awt.Color;

public abstract class Checker {//implements Piece{

    Player player;
    Color color;
    Cell position;
    static Board board;
    StackCheckers stack;
    boolean selectedToCrown;

    public Checker(Player player, Cell cell, Board board){
        this.player = player;
        this.player.addChecker(this);
        this.color = this.player.getColor();
        setPosition(cell);
        this.board = board;
        this.stack = null;
        this.selectedToCrown = false;
    }
    public Board getBoard(){    return board; }

    public Color getColor() {
        return color;
    }
    public Player getPlayer(){  return this.player; }

    public Cell getPosition(){
        return this.position;
    }
    // Set a new position
    public void setPosition(Cell cell){
        // Check if it is allowed - different method
        if(this.position!=null){
            this.position.setUnoccupied();
        }
        this.position = cell;
        this.position.setOccupation(this);
//        if(canCrown){
//            System.out.println("can crown the checker at " + position.getID());
//            doCrown(cell);
//        }
    }
    public boolean areSameColor(Checker checker){   return this.getColor()==checker.getColor(); }

    public boolean isTopChecker(){
        if(this.position.getOccupyingStack()==null){
            return false;
        }
        return this.position.getOccupyingStack().equals(this.stack)
                && this.stack!=null
                && this.stack.topChecker.equals(this);
    }

    public boolean canSlideToNextDiagonal(){
        return false;
    }
    public void doSlide(Cell cell){
//        canCrown();
    }
    // Transposing current to checker
    public boolean canTransposeOn(Checker checker){
        return checker.stack==null && canTranspose(checker.getPosition());
    }
    public boolean canTranspose(Cell cell){  return false;}
    public void doTransposeOn(Checker checker){}
    public void doTranspose(Cell cell){}

    public boolean canCrown(){
        return false;
    }
    public boolean canBeCrowning(){
        return this.stack==null && !this.player.checkersToCrown.contains(this);
    }

    public void selectedToCrown(){ this.selectedToCrown=true;}










    // TODO
    public StackCheckers createStack(Checker checker, Cell cell){
        return new StackCheckers(board, this, checker, cell);
    }
    public void removeFromStack(){
        this.stack = null;
    }
    // TODO
    public void putOnStack(Checker checker, Cell cell){
        checker.createStack(checker, cell);
    }
    // Should be used in checking for single and stack
    public static boolean onDiagonal(Cell start, Cell goal){
        // If difference between row and column between the 2 is the same
        return (Math.abs(start.row-goal.row)==Math.abs(start.column-goal.column));
    }

    // GUI - the checker draws itself
    public void drawSelf(Graphics g, int unitSize){
        g.setColor(color);
//        int[] pos = position.getPosition();
//        g.drawOval(pos[0], pos[1], unitSize, unitSize);
    }



    // Checker reaches the opposite
    public void move(){
        // Check that the goal cell is not the current
        // See if available to move diagonally or crown
        // Check for black or white
        // If white can only move from row with smaller index to row with higher index
        // If black
    }
    // Check if can put a checker from a stack to the current
    public void slide(Board board, Cell goalCell){
        // check if can slide diagonally
        // no checkers in between current and goal
    }

    public void slideCorrectPosition(){

    }
//    // Check if in diagonal
//    public boolean isInDiagonal(Cell goalCell){
//        if(Math.abs(goalCell.column-this.position.column) != Math.abs(goalCell.row-this.position.row)){
//            System.out.println("The goal cell " + goalCell.getID() + "is not in the same diagonal as the current position " + this.getPosition().getID());
//        }
//        return (Math.abs(goalCell.column-this.position.column) == Math.abs(goalCell.row-this.position.row));
//    }

    // Checks if there is a checker between the current position and the goal position
    public boolean checkerInPath(){
        return false;
    }



    public void doCrown(Checker toCrown){
        this.position.setUnoccupied();
        StackCheckers stack = new StackCheckers(board, toCrown, this);
        this.player.addStack(stack);
        this.player.checkersToCrown.remove(0);
        this.selectedToCrown = false;
    }

    public void doImpasse(){
        if(this.stack==null){
            this.player.removeChecker(this);
            this.position.setUnoccupied();
        }
        else{
            this.player.removeStack(this.stack);
            this.position.setUnoccupied();
            this.position.setOccupation(this.stack.bottomChecker);
            this.stack.bottomChecker.removeFromStack();
        }
    }

    // Used in methods where checking whether sliding is possible
    // !!! Not updating the column variable of the object !!!
    public static int updateCol(int currentCol, int goalCol){
        if(currentCol < goalCol){
            return currentCol+1;
        }
        if(currentCol > goalCol){
            return currentCol-1;
        }
        return currentCol;
    }
    public void putOnStack(StackCheckers stackCheckers) {
        this.position = stackCheckers.position;
        this.stack = stackCheckers;
    }

}


