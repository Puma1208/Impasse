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
        super.notifySelectPiece(piece);
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
