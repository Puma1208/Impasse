package GameElements;

import java.util.ArrayList;

public class Play {

    Board board;

    ArrayList<GameState> states;

    public Play(int size, PlayerType type1, PlayerType type2){
        this.board = new Board(this, size, type1, type2);
        this.states = new ArrayList<>();
    }




}
