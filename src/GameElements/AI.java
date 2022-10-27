package GameElements;

public class AI extends Player{

    Player player;
    public AI(Player player){
        super(PlayerType.AI, player.getColor());
        this.player = player;
    }

    public void performMove(){
        for(Checker checker: player.playingCheckers){
            if(checker.stack==null){

            }
            // Check if need to crown
            //          can slide
            //              then crown
            //       check for impasse
        }
        for(StackCheckers stacks: player.stacks){
            // Check if can slide
            //      If on closest row -> bear off
        }
        // If no moves can be performed > IMPASSE
        //      If removing from stack -> check if need to crown

    }


}
