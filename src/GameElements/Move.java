package GameElements;

import java.awt.event.MouseEvent;

/**
 * Attempt class to have ArrayList for possible moves of player
 * mainly used to deal with random entries
 */
public class Move {

    Piece piece;
    Cell cell;
    Move(Piece piece, Cell cell){
        this.piece = piece;
        this.cell = cell;
    }
}
