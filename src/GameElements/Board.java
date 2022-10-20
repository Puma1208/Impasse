package GameElements;

import java.awt.Color;
import java.util.ArrayList;

public class Board {
//    static final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private final Cell[][] cells;
    static int size;
    // Need to fill in?
    private static Checker[][][] checkers;
    public final Player[] players;
    private ArrayList<StackCheckers> stacksWhite = new ArrayList<>();
    private ArrayList<StackCheckers> stacksBlack = new ArrayList<>();

    static GamePlay gamePlay;
    public boolean justBearOff=false;

    public Board(int size){

        this.size = size;
        this.cells = initialiseBoard(size);
        this.players = new Player[]{new Player(Color.WHITE), new Player(Color.BLACK)};
        // TODO method to update position
        this.checkers = new Checker[2][size][2]; // + (int)Math.ceil(size/2)];
        initialiseCheckers();

        gamePlay = new GamePlay(new GameState(this));
    }

    private Cell[][] initialiseBoard(int size){
        // Include colors for cells
        Cell[][] cells = new Cell[size][size];
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                cells[i][j] = new Cell(i+1, j+1);
            }
        }
        return cells;
    }

    public static Checker[][][] getCheckers(){
        return checkers;
    }

    public static int whiteCol(int row){
        int mod = row%4;
        // testing for bigger boards
        int diff = size-2;
        return (2-(mod%2)) + ((mod/2)*diff);
    }
    public static int blackCol(int row){
        int mod = row%4;
        // testing for bigger boards
        int diff = size-2;
        int toSubtract = (mod/2)*diff;
//        return 8 - row%2 - toSubtract;
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
            this.players[player.getPlayerIndex()].stacks.add(stack);
        }
        else{
            this.players[player.getPlayerIndex()].addChecker(checker);
        }
    }
    public boolean canCreateStack(Checker checker){
        if((checker.getColor()==Color.WHITE && checker.getPosition().getRow()>2)
            ||(checker.getColor()==Color.BLACK && checker.getPosition().getRow()<=2)) {
            return true;
        }
        return false;
    }

    public boolean canCreateStack(Checker checker, Cell cell){
        if(cell.getRow()>2 && checker.getColor()==Color.WHITE){
            return true;
        }
        if(cell.getRow()<=2 && checker.getColor()==Color.BLACK){
            return true;
        }
        return false;
    }

    public Cell[][] getCells(){ return cells; }
    public int getSize(){   return this.size; }
    // Get a cell from its row and column - not indexes
    public Cell getCell(int row, int column){
        int updateRow = row-1;
        int updateColumn = column - 1;
        if(row<0){
            updateRow = 0;
        }
        if(row>7){
            updateRow = 7;
        }
        if(column<0){
            updateColumn = 0;
        }
        if(column>7){
            updateColumn = 7;
        }
        return cells[updateColumn][updateRow];
    }

    public void notifyToCrown(Checker checker) {
        checker.getPlayer().checkersToCrown.add(checker);
    }

    public void notifyToBearOff(StackCheckers stack) {
        stack.position.removeCurrentStack();
        stack.player.removeTopCheckerFromBoard(stack);
        justBearOff = true;
    }
}
