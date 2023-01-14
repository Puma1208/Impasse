package GameElements;

public class Move {

    Piece piece;
    Cell goal;


    /**
     * Either use this method or kind of dictionary
     * @param piece
     * @param goal
     */
    public Move(Piece piece, Cell goal){
        this.piece = piece;
        this.goal = goal;
    }
}
