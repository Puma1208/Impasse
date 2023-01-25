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


    /**
     * 1) Check if need to impasse
     * 2) If not make basic move
     * 3) Check if a checker needs to be crowned + if can be crowned now
     */
    @Override
    public void makeMove() {
        // TODO sort out how to deal with crown and random player
        super.makeMove();
        ArrayList<Move> moves = getMoves();
        if(moves.size()==0){
            ArrayList<Piece> impasse = getImpasse();
            if(impasse.size()>0){
                Random random = new Random();
                int randomIndex = random.nextInt(impasse.size());
                impasse.get(randomIndex).impasse();
            }
        }else{
            Random rand = new Random();
            int randomIndex = rand.nextInt(moves.size());
            notifySelectPiece(moves.get(randomIndex).piece);
            notifySelectedCell(moves.get(randomIndex).cell);
        }
        ArrayList<Checker> toCrown = getCheckersToCrown();
        ArrayList<Checker> singles = getSingleCheckers();
        if(toCrown.size()>0 && singles.size()>1){
            this.board.crownMode = true;
            Checker[] pairCrown = getPairCrown(toCrown, singles);
            notifySelectPiece(pairCrown[0]);
            notifySelectedCell(pairCrown[1].position);
        }

    }


}
