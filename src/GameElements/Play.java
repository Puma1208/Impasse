package GameElements;

import java.util.ArrayList;

public class Play {

    public Board board;
    private Player current;
    boolean gameStopped;

    ArrayList<GameState> states;

    public Play(int size, PlayerType type1, PlayerType type2)  {
        this.board = new Board(this, size, type1, type2);
        this.states = new ArrayList<>();
        this.gameStopped = false;
        current = this.board.players[0];
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
        System.out.println("----------------------- " + current.type + " ----------------------- " );
    }

    /**
     * Check if need to crown
     * And updates to next player and next game state
     */
    public void playerMoved() {
        if(!gameStopped){
            board.notSlide();
            for(Player p:board.players){
//            for(StackCheckers s: p.stacks){
//                System.out.println("                stack at " + s.position.getID());
//            }
//            for(Checker c: p.playingCheckers){
//                System.out.println("                checker at " + c.position.getID());
//            }
                if(p.playingCheckers.size()==0 && p.stacks.size()==0){
                    System.out.println("Player " + p.color + " won!");
                    stopGame();
                }
            }
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
    public void playerCrowned(){

    }

    public void startGame(){
        if(current.type != PlayerType.HUMAN){
            current.makeMove();
        }
    }

    public void stopGame(){
        gameStopped = true;
        System.out.println("✨ PLAYER " + current.indexFromColor()  + " WON✨");
    }



}
