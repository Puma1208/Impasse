package GameElements;

import java.awt.*;

public class HumanPlayer extends Player{


    public HumanPlayer(PlayerType type, Color color) {
        super(type, color);
    }

    @Override
    public void notifySelectPiece(Piece piece) {
        board.setNoBasicMove();
        board.notTranspose();
        board.setCellsPossible(piece.getPossibleSlide());
        board.setCellsTranspose(piece.getPossibleTranspose());
        if(this.selectedPiece!=null) {

            if(this.selectedPiece instanceof StackCheckers && piece instanceof Checker
                    && this.selectedPiece.canTranspose(((Checker) piece).position)) {
                this.selectedPiece.transpose(((Checker) piece).position);
            }else{
                this.selectedPiece = piece;
//                System.out.println("Selected piece by the player " + this.selectedPiece.getPosition().getID());
            }
        }
        else{
            if(shouldImpasse()){
                piece.impasse();
            }
            else{
                this.selectedPiece = piece;
//                System.out.println("Selected piece by the player " + this.selectedPiece.getPosition().getID());

            }
        }
    }

    @Override
    public void notifySelectedCell(Cell cell) {
        super.notifySelectedCell(cell);
    }

    @Override
    public void makeMove() {
        super.makeMove();
    }
}
