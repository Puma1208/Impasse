package GameElements;

import Minimax.GameTree;
import Minimax.Node;

import java.util.ArrayList;

public class GameState {

    static Board wholeBoard;
    private Board board;
    Player current;


    GameState parent = null;
    private int value;
    private ArrayList<GameState> children = new ArrayList<>();


    public GameState(Player current)  {
        this.board = wholeBoard;
        this.board.gameState = this;
        this.current = current;
    }

    public GameState(Player current, Board board)  {
        try {
            board.gameState = this;
//            System.out.println("            play " + board.play);
            this.board = board.clone();
            this.board.gameState = this;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        this.current = this.board.players[current.getPlayerIndex()];
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


    public void setValue(int value){
        this.value = value;
    }

    public int getValue() {
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
}
