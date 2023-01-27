package Minimax;

import GameElements.GameState;

public class GameTree {

    public GameState root;


    public GameTree(GameState currentState){
        root = currentState;
    }

    public void getBestChild(){
        for(GameState s: root.getChildren()){

        }
    }
}
