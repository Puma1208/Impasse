package GameElements;

import java.util.ArrayList;

/** Extended by stack and checker
 *
 */
public interface Piece {

    Player getPlayer();

    Board getBoard();

    Cell getPosition();

    boolean canImpasse();

    void impasse();

    boolean canSlide(Cell cell);

    void slide(Cell cell);

    boolean canTranspose(Cell cell);

    void transpose(Cell cell);

    /**
     * @return all the possible cell positions the current piece can be in
     */
    ArrayList<Cell> getPossibleSlide();

    /**
     * @return 0, 1 or 2 possible Cells to which the piece can transpose
     */
    ArrayList<Cell> getPossibleTranspose();

    /**
     * @return The next cell to the left of the player
     */
    Cell getNextLeft(Cell current);

    /**
     * @return The next cell to the right of the player
     */
    Cell getNextRight(Cell current);

    /**
     * @return if a checker - ! not stack can be crowned
     */
    boolean mustCrown();

    /**
     * To put in a method when  a slide
     *                          a transpose
     *                          an impasse
     *  was performed
     */
    void notifyMoved() ;

    /**
     * The current piece - must only be a checker - will crown the parameter
     * @param checker
     */
    void crown(Checker checker);
}