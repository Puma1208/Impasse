package GameElements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Cloneable{

    static final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private final Cell[][] cells;
    static int size;
    // Need to fill in?
    private static Checker[][][] checkers;
    public final Player[] players;
    private ArrayList<StackCheckers> stacksWhite = new ArrayList<>();
    private ArrayList<StackCheckers> stacksBlack = new ArrayList<>();

    Play play;
    static GamePlay gamePlay;
    static GameState gameState;

    boolean crowningCanHappen;

    public Board(Play play, int size, PlayerType type1, PlayerType type2){
        this.play = play;
        this.size = size;
        this.cells = initialiseBoard();
        this.players = new Player[]{new Player(type1, Color.WHITE), new Player(type2, Color.BLACK)};
        initialiseCheckers();
    }

    public Board(GamePlay gp, int size, PlayerType type1, PlayerType type2) throws CloneNotSupportedException {
        this.gamePlay = gp;
        gamePlay.addState(new GameState(this, gp.currentPlayer));
        this.gameState = gamePlay.gameStates.get(gamePlay.gameStates.size()-1);
        this.size = size;
        this.cells = initialiseBoard();
        this.players = new Player[]{new Player(type1, Color.WHITE), new Player(type2, Color.BLACK)};
        // TODO method to update position
        this.checkers = new Checker[2][size][2]; // + (int)Math.ceil(size/2)];
        initialiseCheckers();
//        gamePlay = new GamePlay(new GameState(this));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Cell[][] initialiseBoard(){
        // Include colors for cells
        Cell[][] cells = new Cell[size][size];
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                cells[i][j] = new Cell(i+1, j+1);
//                System.out.print("[" + i + ", " + j + "] " +cells[i][j].getID() + " ");

            }
//            System.out.println();

        }

        return cells;
    }

    public static int whiteCol(int row){
        int mod = row%4;
        int diff = size-2;
        return (2-(mod%2)) + ((mod/2)*diff);
    }
    public static int blackCol(int row){
        int mod = row%4;
        int diff = size-2;
        int toSubtract = (mod/2)*diff;
        return size - row%2 - toSubtract;
    }

    public void initialiseCheckers(){
        // in terms of indexes NOT IN TERMS OF COLS AND ROWS
        for(int i=0; i<size; i++){
            Checker white = new WhiteChecker(this.players[0], this.cells[i][whiteCol(i+1)-1], this);
            Checker black = new BlackChecker(this.players[1], this.cells[i][blackCol(i+1)-1], this);
            // create stack if needed and add to checkers
            initialisePlayingPiece(white, white.getPlayer(), i);
            initialisePlayingPiece(black, black.getPlayer(), i);
        }
    }

    // Storing either checker at current position
    // Or storing the initial stack at current position
    public void initialisePlayingPiece(Checker checker, Player player, int index){
        this.checkers[player.getPlayerIndex()][index][0] = checker;
        if(canCreateStack(checker)){
            // White
            Checker topChecker = null;
            if(player.indexFromColor()==0){
                topChecker = new WhiteChecker(player, checker.getPosition(), this);
            }else{
                topChecker = new BlackChecker(player, checker.getPosition(), this);

            }
            this.checkers[player.getPlayerIndex()][index][1] = topChecker;
            StackCheckers stack = new StackCheckers(this, checker, topChecker);
//            this.players[player.getPlayerIndex()].addStack(stack);
        }
        else{
//            this.players[player.getPlayerIndex()].addChecker(checker);
        }
    }
    public boolean canCreateStack(Checker checker){
        if((checker.getColor()==Color.WHITE && checker.getPosition().getRow()>2)
            ||(checker.getColor()==Color.BLACK && checker.getPosition().getRow()<=2)) {
            return true;
        }
        return false;
    }

    public Cell[][] getCells(){
        return cells;
    }

    public int getSize(){
        return this.size;
    }

    // Get a cell from its row and column - not indexes
    public Cell getCell(int row, int column){
        if(row<=0||row>size){
            System.out.println("Not possible to get the row " + row);
        }
        if(column<=0||column>size){
            System.out.println("Not possible to get the column " + column);
        }
        int updateRow = row-1;
        int updateColumn = column - 1;
        if(row<0){
            updateRow = 0;
        }
        if(row>size-1){
            updateRow = size-1;
        }
        if(column<0){
            updateColumn = 0;
        }
        if(column>size-1){
            updateColumn = size-1;
        }
        return cells[updateColumn][updateRow];
    }

    public Cell getCell(int row, char col){
        return getCell(row, stringColToIndex(col));
    }

    public int stringColToIndex(char col){
        List<Character> letters = new ArrayList<>();
        for(char c: LETTERS){
            letters.add(c);
        }
        if(letters.contains(col)){
            return letters.indexOf(col)+1;
        }
        return -1;

    }



}
