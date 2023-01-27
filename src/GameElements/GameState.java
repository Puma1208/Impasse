package GameElements;

import Minimax.GameTree;
import Minimax.Node;

import java.util.ArrayList;

public class GameState {

    static Board wholeBoard;
    private Board board;
    Player current;

    /**
     * Move performed to arrive at this state
     */
    public Move move;

    public Checker toCrown;
    public Checker crowning;

    public Checker impasse;


    GameState parent = null;
    private double value;
    private ArrayList<GameState> children = new ArrayList<>();


    public GameState(Player current)  {
        this.board = wholeBoard;
        this.board.gameState = this;
        this.current = current;
    }

    /**
     * Used for simulation
     * @param current
     * @param board
     */
    public GameState(Player current, Board board)  {
        try {
            this.board = board.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        this.current = this.board.players[current.getPlayerIndex()];
        this.board.gameState = this;
        this.board.play = new Simulation(this, this.board, this.current);
    }

    public void setPlayer(PlayerType player){

    }

    public Board getBoard() {
        return board;
    }

    /**
     * To use before initialising 1st GameState object
     * @param board which is the board that is changing throughout the game
     */
    public void setBoard(Board board){
        board.gameState = this;
    }

    public static void setStartingBoard(Board board){
        wholeBoard = board;
    }


    public void setValue(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void addChild(GameState child){
        children.add(child);
        child.setParent(this);
    }

    public void setParent(GameState parent) {
        this.parent = parent;
    }

    public ArrayList<GameState> getChildren() {
        return children;
    }

    public GameState getParent() {
        return parent;
    }

    public boolean isLeaf(){
        return children.isEmpty();
    }

    /**
     * Remove the siblings that should be visited next by the current parrent
     * @param child
     */
    public void removeChildrenAfter(GameState child){
        if(children.contains(child)){
            children.subList(children.indexOf(child)+1, children.size()).clear();
        }
    }

    /**
     * Setting the move parameter to reach the current state from the parent
     * @param move
     */
    public void setMove(Move move){
        this.move = move;
    }

    public void setCrown(Checker toCrown, Checker crowning){
        this.toCrown = toCrown;
        this.crowning = crowning;
    }

//    public void setImpasse(){
//        this.impasse =
//    }
}
