package GameElements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Cloneable{

    static final char[] LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private Cell[][] cells;
    static int size;
    public Player[] players;

    public Play play;
    GameState gameState;

    public boolean crownMode;


    public Board(Play play, int size, PlayerType type1, PlayerType type2){
        this.play = play;
        this.size = size;
        this.cells = initialiseBoard();
        this.players = new Player[]{Player.createPlayer(type1, Color.WHITE), Player.createPlayer(type2, Color.BLACK)};
        this.players[0].setBoard(this);
        this.players[1].setBoard(this);
        initialiseCheckers();
        crownMode = false;
    }


//    public Board(GamePlay gp, int size, PlayerType type1, PlayerType type2)  {
////        this.gamePlay = gp;
////        gamePlay.addState(new GameState( gp.currentPlayer));
//        this.gameState = gamePlay.gameStates.get(gamePlay.gameStates.size()-1);
//        this.size = size;
//        this.cells = initialiseBoard();
//        this.players = new Player[]{Player.createPlayer(type1, Color.WHITE), Player.createPlayer(type2, Color.BLACK)};
//        initialiseCheckers();
//    }

    public Board(Player[] players, int indexCurrentPlayer){
        this.players = new Player[]{Player.createPlayer(players[0].type, Color.WHITE), Player.createPlayer(players[1].type, Color.BLACK)};
        this.players[0].setBoard(this);
        this.players[1].setBoard(this);
        this.play = new Play(gameState,this, this.players[indexCurrentPlayer]);
        this.size = players[0].board.getSize();
        this.cells = initialiseBoard();
        for(Player player: players){
            for(StackCheckers stack: player.stacks){
                Checker bottom;
                Checker top;
                if(stack.color==Color.WHITE){
                    bottom = new WhiteChecker(this.players[0], this.getCell(stack.position.row, stack.position.column), this);
                    top = new WhiteChecker(this.players[0], this.getCell(stack.position.row, stack.position.column), this);

                }else{
                    bottom = new BlackChecker(this.players[1], this.getCell(stack.position.row, stack.position.column), this);
                    top = new BlackChecker(this.players[1], this.getCell(stack.position.row, stack.position.column), this);
                }
                new StackCheckers(this, bottom, top, this.getCell(stack.position));
            }

            for(Checker checker: player.playingCheckers){
                if(checker.stack == null){
                    if(checker instanceof WhiteChecker){
                        new WhiteChecker(this.players[0], this.getCell(checker.position.row, checker.position.column), this);
                    }else{
                        new BlackChecker(this.players[0], this.getCell(checker.position.row, checker.position.column), this);

                    }
                }
            }
        }
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Board cloned = new Board(this.players, this.play.getCurrentPlayer().getPlayerIndex());
        return cloned;
    }

    private Cell[][] initialiseBoard(){
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

    /**
     * Storing either checker at current position
     * Or storing the initial stack at current position

    */


    public void initialisePlayingPiece(Checker checker, Player player, int index){
        if(canCreateStack(checker)){
            // White
            Checker topChecker = null;
            if(player.getColor()==Color.WHITE){
                topChecker = new WhiteChecker(player, checker.getPosition(), this);
            }else{
                topChecker = new BlackChecker(player, checker.getPosition(), this);

            }
            new StackCheckers(this, checker, topChecker);
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

    /** Get a cell from its row and column - not indexes
     *
     * @param row
     * @param column
     * @return
     */
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

    public void setCellsPossible(ArrayList<Cell> slides){
        for(Cell c:slides){
            c.isPossibleSlide = true;
        }
    }

    /**
     * After a player has moved - no need to show possible slides
     * TODO: to speed up - only cells that have been possible for the selected checker
     */
    public void notSlide(){
        for(Cell[] row: this.cells){
            for(Cell c: row){
                c.isPossibleSlide = false;
            }
        }
    }

    public void setCellsTranspose(ArrayList<Cell> slides){
        for(Cell c:slides){
            c.isPossibleTranspose = true;
        }
    }

    public void notTranspose(){
        for(Cell[] row: this.cells){
            for(Cell c: row){
                c.isPossibleTranspose = false;
            }
        }
    }

    public void setNoBasicMove(){
        for(Cell[] row: this.cells){
            for(Cell c: row){
                c.isPossibleSlide = false;
                c.isPossibleTranspose = false;
            }
        }
    }

    public void printBoard(){
        System.out.println("__________________________________________________");
        for(Cell[] row: this.cells){
            for(Cell cell: row){
                System.out.print("[" + cell.getID() +"]" + cell.getOccupyingPiece());
            }
            System.out.println();
        }
    }

    /**
     *
     * @param cell
     * @return the cell at the same position as the parameter
     */
    public Cell getCell(Cell cell){
//        System.out.println("Searching for cell " + cell.getID());
//        System.out.println("                    " +getCell(cell.row, cell.column).getID());
        return getCell(cell.row, cell.column);
    }



}
