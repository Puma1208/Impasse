package GUI;

import GameElements.PlayerType;

public class ImpasseGame {
    public static void main(String[] args)  {

//        new Play(8, PlayerType.RANDOM, PlayerType.RANDOM);
        new GameFrame(8, PlayerType.AI, PlayerType.HUMAN);
        int n = 5;
    }
}
