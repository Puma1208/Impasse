package GameElements;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomPlayer extends Player{

    RandomPlayer(Color color){
        super(PlayerType.AI, color);

    }

    @Override
    public void notifyPlayer() {
        super.notifyPlayer();
        makeMove();
    }




}
