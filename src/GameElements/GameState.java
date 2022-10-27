package GameElements;

public class GameState {

    Player[] players;
    final Board board;
    Player current;

    public GameState(Board board, Player current){
        this.board = board;
        this.players = board.players;
        this.current = current;
    }

    public void setPlayer(PlayerType player){

    }






}
