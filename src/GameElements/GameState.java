package GameElements;

public class GameState {

    Player[] players;
    Board board;

    public GameState(Board board){
        this.board = board;
        this.players = board.players;
    }




}
