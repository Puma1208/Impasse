package GameElements;

public class EvaluationFunction {

    public static double evaluateState(GameState state, Player current){
        double good = 0;
        for(Player player:state.getBoard().players){
            if(player == current){
                double checkersFar = 0;
                for(Checker c: player.playingCheckers){
//                    if(chec)
                }
//                good += player.playingCheckers
            }
        }
        return 0;
    }
}
