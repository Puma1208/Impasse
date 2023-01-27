package GameElements;

import java.awt.event.MouseEvent;


public class Move {

    Piece piece;
    Cell cell;
    Move(Piece piece, Cell cell){
        this.piece = piece;
        this.cell = cell;
    }
}
