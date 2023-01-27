package GameElements;

public class EvaluationFunction {

    public static void evaluateState(GameState state, Player current){
        double ownAdvantage = 0;
        double opponentAdvantage = 0;
        double advantageMaterial = 0;
        for(Player player:state.getBoard().players){
            if(player == current){
                for(Checker c: player.playingCheckers){
                    if(c.stack == null){
                        ownAdvantage += c.getCellsFromFurthestRow();
                    }
                }
            }
            else{
                advantageMaterial = player.playingCheckers.size() + 2*player.stacks.size()
                        - current.playingCheckers.size() - 2*current.stacks.size();

                for(Checker c: player.playingCheckers){
                    if(c.stack == null){
                        opponentAdvantage += c.getCellsFromFurthestRow();
                    }
                }
            }
        }
        state.setValue(advantageMaterial - opponentAdvantage + ownAdvantage);
    }
}
