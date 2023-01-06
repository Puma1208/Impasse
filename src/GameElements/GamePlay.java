package GameElements;

import java.util.ArrayList;
import java.util.Scanner;

public class GamePlay {
    private static Scanner scanner;
    final static ArrayList<String> ACTIONS = new ArrayList<>();
    public ArrayList<GameState> gameStates = new ArrayList<>();
    public Player currentPlayer;
    public Board board;
    Cell from = null;

    public GamePlay(int size, PlayerType player1, PlayerType player2){
        ACTIONS.add("SLIDE");
        ACTIONS.add("TRANSPOSE");
        ACTIONS.add("CROWN");
        ACTIONS.add("BEAROFF");
        ACTIONS.add("IMPASSE");

        this.board = new Board(this, 8, player1, player2);
        currentPlayer = board.players[0];

        GameState gameState = new GameState(this.board, currentPlayer);
//        GameState gameState = new GameState(new Board(8,
//                PlayerType.AI,
//                PlayerType.HUMAN),
//                board.players[0]);
//        currentPlayer =
//        ininitalState.setPlayer(currentPlayer);
        gameStates.add(gameState);
//        startGame();

    }

//    public void startGame(){
//        while(true){
//            System.out.println("Player " + currentPlayer.getPlayerIndex() + " to make a move");
//            makeAction();
//            switchPlayers();
//            gameStates.add(new GameState(this.board, currentPlayer));
//        }
//
//    }

//    public void notifying(Cell cell){
//        // Check if impasse
//
//        // If current cell is not occupied -> checker or stack slide
//        if(!cell.isOccupied()){
//            if(from!=null && from.isOccupied()){
//                if(from.getOccupying()!=null){
//                    from.getOccupying().getPlayer().slide(from.getOccupying(), cell);
//                    from = null;
//                }else if(from.getOccupyingStack()!=null){
//                    // Transpose
//                    if(cell.getOccupying()!=null){
//                        from.getOccupyingStack().getPlayer().transpose(from.getOccupyingStack(), cell.getOccupying());
//                    }else if(cell.getOccupyingStack()!=null){
//                        // Select the other stack
//                        from = cell;
//                    }else{
//                        // Slide
//                        from.getOccupyingStack().getPlayer().slide(from.getOccupyingStack(), cell);
//                        from = null;
//                    }
//
//                }
//            }
//            // Just select the cell
//            else{
//                from = cell;
//            }
//        }
//        else if(cell.getOccupying()!=null){
//
//            // Check if impasse and should remove
//            if(cell.getOccupying().getPlayer().shouldImpasse()){
//                currentPlayer.impasse(cell.getOccupying());
//            }
//            // Check if need to crown
//            if(cell.getOccupying().selectedToCrown){
////                cell.getOccupying().doCrown();x
//            }
//            if(board.selectedToCrown!=null){
//                cell.getOccupying().doCrown(board.selectedToCrown);
//            }
//
//
//
//        }else if(cell.getOccupyingStack()!=null){
//
//            // Check if impasse and should remove the top checker
//            if(cell.getOccupyingStack().getPlayer().shouldImpasse()){
//                currentPlayer.impasse(cell.getOccupyingStack());
//            }
//        }
//
//
//
//
//
//    }
    public void actionPerformed(){
        switchPlayers();
        if(currentPlayer.type==PlayerType.HUMAN){
            // Get input from GUI

        }
        if(currentPlayer.type==PlayerType.AI){
            // get input from ai
        }
        GameState gs = new GameState(gameStates.get(gameStates.size()-1).board, currentPlayer);

    }
    public void addState(GameState state){
        gameStates.add(state);
    }
//    public void makingAction(Board currentBoard){
//        gameStates.add(new GameState(currentBoard));
//    }
    public void waitAction(){
        if(currentPlayer.type==PlayerType.AI){

        }
    }
    public void switchPlayers(){
        if(currentPlayer.getPlayerIndex()==0){
            currentPlayer = board.players[1];
        }else if(currentPlayer.getPlayerIndex()==1){
            currentPlayer = board.players[0];
        }
    }

//    public void makeAction(){
//        scanner = new Scanner(System.in);
//        System.out.println("Make an action");
//        String action = scanner.nextLine();
////        System.out.println("    Action=" + action);
//        decriptAction(action);
//    }

//    public void decriptAction(String inputAction){
////        String action = inputAction.split(" ");
////        System.out.println("valid action " + correct(inputAction));
//        performAction(inputAction);
//    }

    public boolean correct(String action){
        String[] strAction = action.split(" ");
        int row1 = Character.getNumericValue(strAction[1].charAt(0));
        int row2 = Character.getNumericValue(strAction[2].charAt(0));

        return isAction(strAction[0])
                && board.getCell(row1, strAction[1].toUpperCase().charAt(1))!=null
                && board.getCell(row2, strAction[2].toUpperCase().charAt(1))!=null;
    }
    public boolean isAction(String action){
        return ACTIONS.contains(action.toUpperCase());
    }


//    private void performAction(String action) {
//        if(correct(action)){
//            String[] strAction = action.split(" ");
//            switch (strAction[0].toUpperCase()){
//                case "SLIDE":
//                    System.out.println("[" + Character.getNumericValue(strAction[1].charAt(1))
//                                        + ", " + strAction[1].toUpperCase().charAt(0) + "]"
//                                        + "[" + Character.getNumericValue(strAction[2].charAt(1)) + ", "
//                                        + strAction[2].toUpperCase().charAt(0)+"]");
////                    System.out.println(Character.getNumericValue(strAction[1].charAt(1)));
//                    Cell cell1 = board.getCell(Character.getNumericValue(strAction[1].charAt(1)), strAction[1].toUpperCase().charAt(0));
//                    Cell cell2 = board.getCell(Character.getNumericValue(strAction[2].charAt(1)), strAction[2].toUpperCase().charAt(0));
//                    System.out.println(cell1.getID() + "<>" + cell2.getID());
//                    board.slide(board.getCell(Character.getNumericValue(strAction[1].charAt(0)), strAction[1].toUpperCase().charAt(1)),
//                            board.getCell(Character.getNumericValue(strAction[2].charAt(0)), strAction[2].toUpperCase().charAt(1)));
//            }
//
//        }
//        else{
//            System.out.println("not valid action");
//        }
//
//    }


}
