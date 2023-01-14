package GameElements;

public class GameState {

    final Board board;
    final Player current;

    public GameState(Board board, Player current) throws CloneNotSupportedException {
        this.board = (Board) board.clone();
        this.current = current;
    }

    public void setPlayer(PlayerType player){

    }

    public void stgShouldHappen(){
        current.notifyPlayer();
        if(this.board.crowningCanHappen){
            current.notifyForCrowning();
        }
    }






}
