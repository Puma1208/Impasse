package GameElements;

import GUI.GameFrame;
import Minimax.AlphaBeta;
import Minimax.GameTree;
import Minimax.Tree;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayer extends Player{

    GameTree tree;

    public AIPlayer(Color color) {
        super(PlayerType.AI, color);
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
    }

    public void simulation(){
        this.tree = new GameTree(this.board.gameState);
        simulateGame(2, this.tree.root);
        AlphaBeta.alphaBetaFailHard(this.tree.root, 2,
                (int) Double.NEGATIVE_INFINITY,  (int) Double.POSITIVE_INFINITY, this.getPlayerIndex());

        double maxVal = 0;
        Move toDo = null;
        GameState winning = null;
        for(GameState g: this.tree.root.getChildren()){
            if(g.getValue()>maxVal){
                toDo = g.move;
                maxVal = g.getValue();
                winning = g;
            }
        }
        notifySelectPiece(toDo.piece);
        notifySelectedCell(toDo.cell);
        if(winning!=null && winning.toCrown!=null && winning.crowning!=null){
            notifySelectPiece(winning.toCrown);
            notifySelectedCell(winning.crowning.position);
        }
    }



    /**
     *
     * @param depth
     * @param current the current state to which append further moves
     */
    @Override
    public void simulateGame(int depth, GameState current) {
        if(depth>0){
            ArrayList<Move> moves = getMoves();
            if(moves.size()==0){
                ArrayList<Piece> impasse = getImpasse();
                for(Piece toImpasse:impasse){
                    GameState newState = new GameState(this, this.board);
                    newState.getBoard().getCell(toImpasse.getPosition()).getOccupyingPiece().impasse();
//                    newState.setMove(m);

                    current.addChild(newState);
                    EvaluationFunction.evaluateState(newState, newState.current);
                    newState.getBoard().play.current.simulateGame(depth-1, newState);
                }
            }
            for(Move m: moves){
                GameState newState = new GameState(this, this.board);
                newState.current.notifySelectPiece(newState.getBoard().getCell(m.piece.getPosition()).getOccupyingPiece());
                newState.current.notifySelectedCell(newState.getBoard().getCell(m.cell));

                ArrayList<Checker> toCrown = newState.current.getCheckersToCrown();
                ArrayList<Checker> singles = newState.current.getSingleCheckers();
                if(toCrown.size()>0 && singles.size()>1){
                    newState.getBoard().crownMode = true;
                    Checker[] pairCrown = getPairCrown(toCrown, singles);
                    newState.current.notifySelectPiece(pairCrown[0]);
                    newState.current.notifySelectedCell(pairCrown[1].position);
                    newState.setCrown(pairCrown[0], pairCrown[1]);
                }

                newState.setMove(m);
                current.addChild(newState);
                EvaluationFunction.evaluateState(newState, newState.current);
                newState.getBoard().play.current.simulateGame(depth-1, newState);
            }

        }

    }


    @Override
    public void makeMove() {
        super.makeMove();
        ArrayList<Move> moves = getMoves();

        if(moves.size()==0){

        }
        simulation();
    }
}
