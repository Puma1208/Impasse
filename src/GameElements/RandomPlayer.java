package GameElements;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomPlayer extends Player{

    RandomPlayer(Color color){
        super(PlayerType.RANDOM, color);

    }


    /**
     * 1) Check if need to impasse
     * 2) If not make basic move
     * 3) Check if a checker needs to be crowned + if can be crowned now
     */
    @Override
    public void makeMove() {
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
