package GameElements;

public class EvaluationFunction {

    public static void evaluateState(GameState state, Player current){
        double good = 0;
        double bad = 0;
        
        for(Player player:state.getBoard().players){
            if(player == current){
                for(Checker c: player.playingCheckers){
                    if(c.stack == null){
                        good += c.getCellsFromFurthestRow();
                    }
                }
//                good += player.playingCheckers
            }
            else{
                for(Checker c: player.playingCheckers){
                    if(c.stack == null){
                        bad += c.getCellsFromFurthestRow();
                    }
                }
            }
        }
        state.setValue(good);
    }
}
