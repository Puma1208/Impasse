package GameElements;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomPlayer extends Player{

    RandomPlayer(Color color){
        super(PlayerType.AI, color);

    }

//    @Override
//    public void makeMove() {
////        System.out.println("Random player making a move");
//        HashMap<Piece, ArrayList<Cell>> basicMoves = super.getBasicMoves();
//        basicMoves.entrySet().removeIf(entry -> entry.getValue().isEmpty());
//
//        if(basicMoves.size()==0){
//            //Impasse a random piece
//        }else{
//
//            // Get all the possible moves
//            Set<Map.Entry<Piece, ArrayList<Cell>>> entrySet = basicMoves.entrySet();
//
//            List<Map.Entry<Piece, ArrayList<Cell>>> entryList = new ArrayList<>(entrySet);
//
//            for(Map.Entry<Piece, ArrayList<Cell>> e: entryList){
//                for(Cell c: e.getValue()){
//                    System.out.println("In list " + e.getKey().getPosition().getID() + " " + c.getID());
//
//                }
//            }
//
//
//            Random rand = new Random();
//            int randomIndex = rand.nextInt(entryList.size());
////            System.out.println("SIZE OF LIST =" + entryList.size());
//
//            Map.Entry<Piece, ArrayList<Cell>> randomEntry = entryList.get(randomIndex);
////
////            basicMoves.forEach((key, value) -> {
////                System.out.println("Key: " + key + " at " + key.getPosition().getID() + ", Value: " + value.size());
////            });
//
//            // choose the random piece
//            notifySelectPiece(randomEntry.getKey());
//            notifySelectedCell(randomEntry.getValue().get(0));
////            System.out.println("random player moving " + randomEntry.getKey().getPosition() + " " + randomEntry.getKey().getPosition().getID());
//        }
//    }


    @Override
    public void makeMove() {
        super.makeMove();
        ArrayList<Move> moves = getMoves();
//        for(Move m: moves){
//            System.out.println("Piece " + m.piece.getPosition().getID() + " to " + m.cell.getID());
//        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(moves.size());
        notifySelectPiece(moves.get(randomIndex).piece);
        notifySelectedCell(moves.get(randomIndex).cell);


    }
}
