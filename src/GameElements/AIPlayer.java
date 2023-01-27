package GameElements;

import GUI.GameFrame;
import Minimax.GameTree;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayer extends Player{

    GameTree tree;

    public AIPlayer(Color color) {
        super(PlayerType.AI, color);
    }
    @Override
    public void makeMove() {
        super.makeMove();
        ArrayList<Move> moves = getMoves();
        simulateMoves(moves);
        System.out.println("TREE " + tree.root.getChildren().size());
        for(GameState s: tree.root.getChildren()){
//            s.getBoard().printBoard();
            System.out.println();

        }
//        for(Move move: moves){
//            GameState newState = new GameState(this);
//            newState.current.notifySelectPiece(move.piece);
//            newState.current.notifySelectedCell(move.cell);
//        }
    }


    public void simulateMoves(ArrayList<Move> moves){
        this.tree = new GameTree(this.board.gameState);

        for(Move move: moves){
            GameState newState = new GameState(this, this.board);
            newState.current.notifySelectPiece(newState.getBoard().getCell(move.piece.getPosition()).getOccupyingPiece());
            newState.current.notifySelectedCell(newState.getBoard().getCell(move.cell));

            tree.root.addChild(newState);
            EvaluationFunction.evaluateState(newState, newState.current);
            new GameFrame(newState.getBoard(), "Move:" + move.piece.getPosition().getID() + " to " + move.cell.getID() +" value=" + newState.getValue());

        }

        System.out.println("done");

    }


}
