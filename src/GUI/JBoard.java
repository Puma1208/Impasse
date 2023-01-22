package GUI;

import GameElements.*;

import javax.swing.*;
import java.awt.*;

public class JBoard extends JPanel {
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
    Play gamePlay;
    Board board;
    int unitSize;
    JCell[][] cells;

    public JBoard(int size, PlayerType type1, PlayerType type2)  {
        Play gamePlay = new Play(size, type1, type2);
        this.gamePlay = gamePlay;
        this.board = gamePlay.board;
        this.cells = new JCell[size][size];
        this.unitSize = SCREEN_HEIGHT/(size+2);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);

    }

    public JBoard(Board b){
        this.board = b;
        this.unitSize = SCREEN_HEIGHT/(b.getSize()+2);
        this.cells = new JCell[b.getSize()][b.getSize()];

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

    /** Create the JCells
    * Create the JCheckers
 *  */
    public void createGUIboard(Graphics g){
        // x, y = real coordinates from the panel
        int x = unitSize;
        for(int i=0; i< board.getSize(); i++){
            // Doing it from bottom to top in the
            int y = SCREEN_HEIGHT - 2*unitSize;
            for(int j=0;j<board.getCells()[i].length; j++){
                cells[i][j] = new JCell(this, board.getCells()[i][j], x, y, unitSize);
                this.add(cells[i][j]);
                y = y - unitSize;
            }
            x = x + unitSize;
        }
    }
    public void movedFromTo(Cell from, Cell to){

    }

}

