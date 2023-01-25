package GameElements;

import java.util.ArrayList;

public class Play {

    public Board board;
    private Player current;
    public boolean gameStopped;

    ArrayList<GameState> states;

    public Play(int size, PlayerType type1, PlayerType type2)  {
        this.board = new Board(this, size, type1, type2);
        this.states = new ArrayList<>();
        this.gameStopped = false;
        current = this.board.players[1];
        updatePlayer();
        GameState.setBoard(board);
        states.add(new GameState(current));
        startGame();
    }


    public Player getCurrentPlayer(){
        return this.current;
    }

    /**
     * Until the game is finished - a player has won, continue
     */
    public void run(){
        int number = 10;
        int c = 1;
        while(c<number){
//            current.notifyPlayer();
            // check here if should crown
            updatePlayer();
        }
    }

    public void updatePlayer(){
        if(current.getPlayerIndex()==0){
            current = board.players[1];
        }else if(current.getPlayerIndex()==1){
            current = board.players[0];
        }
        System.out.println("----------------------- " + current.type + " " + current.color + " ----------------------- " );

    }

    /**
     * Check if need to crown
     * And updates to next player and next game state
     */
    public void playerMoved() {
        ArrayList<Checker> toCrown = current.getCheckersToCrown();
        ArrayList<Checker> singles = current.getSingleCheckers();
        if(toCrown.size()>0 && singles.size()>1){
            System.out.println("Crowning possible");
            this.board.crownMode = true;

        }else{
            playerFinishTurn();
        }

    }

    public void playerFinishTurn(){
        if(current.playingCheckers.size()==0 && current.stacks.size()==0){
            stopGame();
        }
        if(!gameStopped){
            board.notSlide();
            board.setNoBasicMove();
            board.notTranspose();
            updatePlayer();
            addGameState();
            current.makeMove();
        }
    }

    public void addGameState() {
        states.add(new GameState(this.current));

    }


    public void startGame(){
        if(current.type != PlayerType.HUMAN){
            current.makeMove();
        }
    }

    public void stopGame(){
        gameStopped = true;
        System.out.println("✨ PLAYER " + current.indexFromColor() + " " + current.color + " WON ✨");
    }



}
