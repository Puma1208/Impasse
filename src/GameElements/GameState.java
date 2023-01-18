package GameElements;

public class GameState {

    static Board wholeBoard;
    private Board board;
    final Player current;

    public GameState(Player current)  {
        try {
            this.board = (Board) wholeBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        this.current = current;
    }

    public void setPlayer(PlayerType player){

    }

    public Board getBoard() {
        return board;
    }

    /**
     * To use before initialising 1st GameState object
     * @param board which is the board that is changing throughout the game
     */
    public static void setBoard(Board board){
        wholeBoard = board;
    }








}
