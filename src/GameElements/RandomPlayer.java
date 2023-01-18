package GameElements;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomPlayer extends Player{

    RandomPlayer(Color color){
        super(PlayerType.AI, color);

    }



    @Override
    public void makeMove() {
        HashMap<Piece, ArrayList<Cell>> basicMoves = super.getBasicMoves();
        if(basicMoves.size()==0){
            //Impasse a random piece
        }else{

            // Get all the possible moves
            Set<Map.Entry<Piece, ArrayList<Cell>>> entrySet = basicMoves.entrySet();

            List<Map.Entry<Piece, ArrayList<Cell>>> entryList = new ArrayList<>(entrySet);

            Random rand = new Random();
            int randomIndex = rand.nextInt(entryList.size());

            Map.Entry<Piece, ArrayList<Cell>> randomEntry = entryList.get(randomIndex);

            basicMoves.forEach((key, value) -> {
                System.out.println("Key: " + key + " at " + key.getPosition().getID() + ", Value: " + value);
            });

            // choose the random piece
            notifySelectPiece(randomEntry.getKey());

        }
    }

}
