package GUI;

import GameElements.GamePlay;
import GameElements.Play;
import GameElements.PlayerType;

import java.util.Random;

public class ImpasseGame {
    public static void main(String[] args)  {

//        new Play(8, PlayerType.RANDOM, PlayerType.RANDOM);
        new GameFrame(4, PlayerType.RANDOM, PlayerType.RANDOM);
        int n = 5;
    }
}
