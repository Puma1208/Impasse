package GameElements;

import java.util.ArrayList;

public class State {
    private Board board;
    // Current player -> 0 is WHITE
    //                -> 1 is BLACK
    private final int currentPlayer;
    double evaluationValue;

    State(Board board, int currentPlayer){
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard(){    return this.board; }

    public void evaluateState(){
        ArrayList<Checker> whiteCheckers = board.players[0].playingCheckers;
        ArrayList<Checker> blackCheckers = board.players[1].playingCheckers;
        ArrayList<StackCheckers> whiteStacks = board.players[0].stacks;
        ArrayList<StackCheckers> blackStacks = board.players[1].stacks;
        evaluationValue = 0;
    }
}
