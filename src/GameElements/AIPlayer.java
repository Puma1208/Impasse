package GameElements;

import Minimax.GameTree;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayer extends Player{


    public AIPlayer(Color color) {
        super(PlayerType.AI, color);
    }

    @Override
    public void makeMove() {
        super.makeMove();
        ArrayList<Move> moves = getMoves();
        simulateMoves(moves);
//        for(Move move: moves){
//            GameState newState = new GameState(this);
//            newState.current.notifySelectPiece(move.piece);
//            newState.current.notifySelectedCell(move.cell);
//        }
    }


    public void simulateMoves(ArrayList<Move> moves){
        GameTree tree = new GameTree(this.board.gameState);
        for(Move move: moves){

            GameState newState = new GameState(this, this.board);
//            newState.getBoard().play.addGameState(newState);
//            System.out.println(newState.getBoard().players[0] + " "+newState.getBoard().players[1]);
//            System.out.println(board.players[0] + " " + board.players[1]);
            newState.current.notifySelectPiece(move.piece);
            newState.current.notifySelectedCell(move.cell);
            tree.root.addChild(newState);

        }
//        tree.

    }


}
