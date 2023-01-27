package GUI;

import GameElements.PlayerType;

public class ImpasseGame {
    public static void main(String[] args)  {

        new GameFrame(8, PlayerType.AI, PlayerType.HUMAN);
    }
}
