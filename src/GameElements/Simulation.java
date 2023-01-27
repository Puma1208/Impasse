package GameElements;

import java.util.ArrayList;

public class Simulation extends Play{


    /**
     * To finish
     *
     * @param state
     * @param b
     * @param current
     */
    public Simulation(GameState state, Board b, Player current) {
        super(state, b, current);
    }

    @Override
    public void playerMoved() {
        board.setNoBasicMove();
        board.notTranspose();
        ArrayList<Checker> toCrown = this.current.getCheckersToCrown();
        ArrayList<Checker> singles = this.current.getSingleCheckers();
        if(toCrown.size()>0 && singles.size()>1){
            System.out.println("Possible to CROWN");
            this.board.crownMode = true;
        }else{
            playerFinishTurn();
        }
    }

    @Override
    public void playerFinishTurn() {
        board.notSlide();
        board.setNoBasicMove();
        board.notTranspose();
        updatePlayer();
        addGameState();
//        current.makeMove();
    }
}
