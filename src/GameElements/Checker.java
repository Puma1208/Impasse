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

    public Checker(Player player, Cell cell, Board board){
        this.player = player;
        this.player.addChecker(this);
        this.color = this.player.getColor();
        setPosition(cell);
        this.board = board;
        this.stack = null;

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
            System.out.println("IMPASSE checker at " + this.position.getID());
            notifyMoved();
        }
        //TODO - if stack not null -> error

    }

    @Override
    public void slide(Cell cell) {
        if(canSlide(cell)){
            System.out.println("SLIDE from " + this.position.getID() + " to " + cell.getID() );
            setPosition(cell);
            notifyMoved();
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
        return new ArrayList<>();
    }


    /**
     *  Used to check after slide
     *                      transpose
     *                      impasse
     *  if the current checker should be crowned
     * @return
     */
    @Override
    public boolean mustCrown(){
        return this.stack==null;
    }


    /**
     * The current piece - must only be a checker - will crown the parameter
     * @param checker
     */
    @Override
    public void crown(Checker checker) {
        if(checker.canCrownWith(this)){
            this.position.setUnoccupied();
            new StackCheckers(board, checker, this);
            this.getBoard().crownMode = false;
            notifyCrowned();
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
        return checker.color==this.color && checker.stack==null && this!=checker;
    }


    /**
     * To put in a method when  a slide
     * a transpose
     * an impasse
     * was performed
     */
    @Override
    public void notifyMoved()  {
        this.player.deSelectedPiece();
        board.play.playerMoved();
    }

    public void notifyCrowned(){
        board.play.playerFinishTurn();
    }

}


