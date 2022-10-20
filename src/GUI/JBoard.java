package GUI;

import GameElements.Board;
import GameElements.Cell;
import GameElements.StackCheckers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JBoard extends JPanel {
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    Board board;
    int unitSize;
    JCell[][] cells;
//    ArrayList<JPiece> pieces = new ArrayList<>();

//    JPiece pieceSelected = null;
    Cell previousFrom = null;
    Cell previousTo = null;
    Cell from = null;
//    Cell to = null;


    StackCheckers stackSelected;
    ArrayList<Cell> cellsToCrown = new ArrayList<>();

    public JBoard(int size){

        this.board = new Board(size);
        this.cells = new JCell[size][size];
        this.unitSize = SCREEN_HEIGHT/(size+2);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);

    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        createGUIboard(g);
    }

    // Create the JCells
    // Create the JCheckers
    public void createGUIboard(Graphics g){
        // x, y = real coordinates from the panel
        int x = unitSize;
        for(int i=0; i< board.getSize(); i++){
            // Doing it from bottom to top in the
            int y = SCREEN_HEIGHT - 2*unitSize;
            for(int j=0;j<board.getCells()[i].length; j++){
//                if
                cells[i][j] = new JCell(this, board.getCells()[i][j], x, y, unitSize);
                this.add(cells[i][j]);
                y = y - unitSize;
            }
            x = x + unitSize;
        }
    }

    //---------------------------------------------------------
    // HANDLE CLICKS HERE


    public void cellNotifying(JCell jCell){
        Cell cell = jCell.getCell();
        if(cell.isOccupied()){
            // Stack selected
            if(cell.getOccupyingStack()!=null){
                from = cell;
                stackSelected = cell.getOccupyingStack();
                System.out.println("Selecting stack at "+ cell.getID() + ">" + stackSelected.getBottomChecker().getPosition().getID() + "***" +
                        stackSelected.getTopChecker().getPosition().getID());

            }
            // Checker selected
            else if(cell.getOccupying()!=null) {
                // Stack chosen before current checker
                if(stackSelected!=null
                        && stackSelected.getTopChecker().canTranspose(cell)
                        && stackSelected.getColor().equals(cell.getOccupying().getColor())){
                    // Transpose
                    updateTranspose(cell);
                    stackSelected = null;
                }
                else{
                    // just select the checker
                    System.out.println("Selecting checker at " + cell.getID());
                    from = cell;
                }
            }
        }
        else if(from!=null){
//            System.out.println("Sliding element");
            updateSliding(cell);
            this.from = null;
            this.stackSelected = null;
        }
//        System.out.println("current board ");
//        for(Cell[] row: board.getCells()){
//            for(Cell c:row){
//                System.out.print(this.board.getCell(c.getRow(), c.getColumn()).getID() + ">[" + (c.getOccupying()!=null) + "," + (c.getOccupyingStack()!=null) + "]  ");
//            }
//        }
//        System.out.println();
    }

    public void updateSliding(Cell to){
        if(from.isOccupied()){
            if(from.getOccupyingStack()!=null){
                from.getOccupyingStack().doSlide(to);
            }else if(from.getOccupying()!=null){
                from.getOccupying().doSlide(to);
            }
        }
    }

    public void updateTranspose(Cell to){
        System.out.println("Willing to transpose " + stackSelected.getPosition().getID() + " to " + to.getID() + ">" + stackSelected.getTopChecker().canTranspose(to));
        if(stackSelected.getTopChecker().canTranspose(to)){
            from.getOccupyingStack().getTopChecker().doTranspose(to);
        }
    }





//    public void addArrow(){
//        System.out.println("previous " + previousFrom  + " and " + previousTo);
//        if(previousFrom!=null && previousTo!=null){
//            getGraphics().setColor(Color.red);
//            getGraphics().drawLine(from.getX()+unitSize/2, from.getY()+unitSize/2,
//                    to.getX()+unitSize/2, to.getY()+unitSize/2);
//        }
//
//    }

    /// FOR EVENTUAL CROWNING

    // Select checker to transpose
//                if(stackSelected!=null){
////                    System.out.println("Willing for transpose");
//                    stackSelected.doTranspose(cell);
//                }
//                // Check for crown
//                if(cell.getOccupying().getPlayer().checkersToCrown.size()>0){
//
//                }
//                if(cell.getOccupying().canCrown()){
//                    // Check if new checker to crown is selected
//                    if(cell.getOccupyingStack()==null){
//                        cell.getOccupying().getPlayer().crowning(cell, cell.getOccupying());
//                    }
//                    cell.getOccupying().getPlayer().shouldCrown(cell);
//                    this.cellsToCrown.add(cell);
//                    // Check if there are availables checkers to crown
//                    jCell.showCrown();
//
////                    if(cell.getOccupying().getPlayer()){
////
////
////                    }
//                }
    // Need available checkers to crown

}

