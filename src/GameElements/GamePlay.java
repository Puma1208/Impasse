package GameElements;

import java.util.ArrayList;

public class GamePlay {

    ArrayList<GameState> gameStates = new ArrayList<>();


    public GamePlay(GameState ininitalState){
        gameStates.add(ininitalState);
    }
    public void addState(GameState state){
        gameStates.add(state);
    }
    public void makingAction(Board currentBoard){
        gameStates.add(new GameState(currentBoard));
    }
}
