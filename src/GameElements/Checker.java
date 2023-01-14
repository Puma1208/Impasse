package GameElements;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

public abstract class Checker implements Piece{

    Player player;
    Color color;
    Cell position;
    static Board board;
    StackCheckers stack;
    boolean mustCrown;
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

    public void setPosition(Cell cell){
        // Check if it is allowed - different method
        if(this.position!=null){
            this.position.setUnoccupied();
        }
        this.position = cell;
        this.position.setOccupation(this);
    }

    public void putOnStack(StackCheckers stackCheckers) {
        this.position = stackCheckers.position;
        this.stack = stackCheckers;
    }

    /**
     *
        @return The right methods for plays
     */
    @Override
    public boolean canImpasse() {
        return player.shouldImpasse();
    }

    @Override
    public void impasse() {
        this.player.removeChecker(this);
        if(this.stack==null){
            this.position.setUnoccupied();
        }
        //TODO - if stack not null -> error

    }

    @Override
    public void slide(Cell cell) {
        if(canSlide(cell)){
            setPosition(cell);
        }
    }

    @Override
    public boolean canTranspose(Cell cell){
        return false;
    }

    @Override
    public void transpose(Cell cell) {
        return;
    }

    /**
     * Must be called on a stack - error if called on a checker
     * TODO check if better to return a null object or an empty array list
     * @return
     */
    @Override
    public ArrayList<Cell> getPossibleTranspose() {
        return null;
    }


    /**
     *  Used to check after slide
     *                      transpose
     *                      impasse
     *  if the current checker should be crowned
     * @return
     */
    public boolean mustCrown(){
        return this.stack==null;
    }

    /**
     *
     * @param checker
     */
    public void crown(Checker checker){
        if(canCrownWith(checker)){
            new StackCheckers(board, this, checker);
            this.mustCrown = false;
            checker.selectedToCrown = false;
        }
    }

    /**
     * Check    if both are of the same color
     *          checker is not in a stack
     *
     * @param checker
     * @return if can crown with the current checker
     */
    public boolean canCrownWith(Checker checker){
        return checker.color==this.color && checker.stack==null;
    }

    /**
     *
     * @param checker is the one to be crowned by the current checker
     */
    public void selectedToCrown(Checker checker){
        checker.crown(this);
    }


}


