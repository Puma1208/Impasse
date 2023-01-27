package GameElements;

import java.util.ArrayList;

public class Play {

    public Board board;
    protected Player current;
    public boolean gameStopped;

    ArrayList<GameState> states;

    public Play(int size, PlayerType type1, PlayerType type2)  {
        this.board = new Board(this, size, type1, type2);
        this.states = new ArrayList<>();
        this.gameStopped = false;
        this.current = this.board.players[1];
        updatePlayer();
        startGame();
    }

    /**
     *
     * @param state
     * @param b
     * @param current
     */
    public Play(GameState state, Board b, Player current){
        this.board = b;
        this.states = new ArrayList<>();
        this.gameStopped = false;
        this.current = current;
        this.states.add(state);

    }

    public Player getCurrentPlayer(){
        return this.current;
    }


    public void updatePlayer(){
        if(current.getPlayerIndex()==0){
            current = board.players[1];
        }else if(current.getPlayerIndex()==1){
            current = board.players[0];
        }
        System.out.println("----------------------- " + current.type + " " + current.getPlayerIndex() + " ----------------------- " );

    }

    /**
     * Check if need to crown
     * And updates to next player and next game state
     */
    public void playerMoved() {
        board.setNoBasicMove();
        board.notTranspose();
        ArrayList<Checker> toCrown = current.getCheckersToCrown();
        ArrayList<Checker> singles = current.getSingleCheckers();
        if(toCrown.size()>0 && singles.size()>1){
            System.out.println("Possible to CROWN");
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
        GameState.setStartingBoard(board);
        states.add(new GameState(current));
        if(current.type != PlayerType.HUMAN){
            current.makeMove();
        }
    }

    public void stopGame(){
        gameStopped = true;
        System.out.println("✨ PLAYER " + current.indexFromColor() + " " + current.color + " WON ✨");
    }


    /**
     * static
     */
    public static void updatePlayer(Board board){
        if(board.gameState.current.getPlayerIndex()==0){
            board.gameState.current = board.players[1];
        }else if(board.gameState.current.getPlayerIndex()==1){
            board.gameState.current = board.players[0];
        }
    }

    public static void playerMoved(Board board) {
        board.setNoBasicMove();
        board.notTranspose();
        ArrayList<Checker> toCrown = board.gameState.current.getCheckersToCrown();
        ArrayList<Checker> singles = board.gameState.current.getSingleCheckers();
        if(toCrown.size()>0 && singles.size()>1){
            System.out.println("Possible to CROWN");
            board.crownMode = true;
        }else{
            playerFinishTurn(board);
        }

    }
    public static void playerFinishTurn(Board board){
        if(board.gameState.current.playingCheckers.size()==0 && board.gameState.current.stacks.size()==0){
            System.out.println("✨ PLAYER " + board.gameState.current.indexFromColor() + " " + board.gameState.current.color + " WON ✨");
        }
        else{
            board.notSlide();
            board.setNoBasicMove();
            board.notTranspose();
            updatePlayer(board);
            addGameState(board.gameState,board);
            board.gameState.current.makeMove();
        }
    }

    public static void addGameState(GameState gameState, Board board) {
        gameState.addChild(new GameState(gameState.current, board));

    }



}
