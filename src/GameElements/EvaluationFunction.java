package GameElements;

public class EvaluationFunction {

    public static void evaluateState(GameState state, Player current){
        double good = 0;
        double bad = 0;
        System.out.println("[__________");
        for(Player player:state.getBoard().players){
            if(player == current){
                for(Checker c: player.playingCheckers){
                    if(c.stack == null){
                        good += c.getCellsFromFurthestRow();
                        System.out.println(good);

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
        System.out.println("__________]");

        state.setValue(good);
    }
}
