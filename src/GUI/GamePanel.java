package GUI;

import GameElements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements MouseListener {

    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;

    Board board;

    // To recompute with respect to screen size
    int unitSize;
    JCell[][] cells;

    public GamePanel(int size){
        addMouseListener(this);
        this.board = new Board(size);
        cells = new JCell[size][size];
        this.unitSize = SCREEN_HEIGHT/(size+2);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
    }

    // After each move -> repaint the state of the board
    // Use after each performed move
    // perform move = click on checker
    //                click on next position (cell) - internally check if correct
    // Board changed from previous to current - repaint everything?
    public void paintGame(){

    }

    @Override
    protected void paintComponent(Graphics  g){
        super.paintComponent(g);
//        for(Checker[] player: this.board.getCheckers()){
//            for(Checker checker: player){
//                if(checker!=null){
//                    checker.drawSelf(g, unitSize);
//                }
//            }
//        }

        draw(g);
    }
    public void draw(Graphics g){
        // Draw board
//        g.setColor(Color.WHITE);
//        g.drawRect(unitSize, unitSize, unitSize*board.getSize(), unitSize+board.getSize());
        int x = unitSize;
        for(int i=0; i<board.getSize(); i++){
            int y = SCREEN_HEIGHT - 2*unitSize;
            for(int j=0;j<board.getCells()[i].length; j++){
                // Drawing the cells
//                // Let them draw themselves from the JCell class
//                cells[i][j] = new JCell(this, board.getCells()[i][j], x, y, unitSize);
//                cells[i][j].drawSelf(g);
//                g.setColor(color);
//                g.fillRect(x, y, unitSize, unitSize);
                // Drawing the checkers
                if(board.getCells()[i][j].isOccupied()){
                    if(board.getCells()[i][j].occupying.getColor()==Color.BLACK){
//                        g.setColor(new Color(10, 10, 50));
                        g.setColor(Color.BLACK);
                    }
                    else if(board.getCells()[i][j].occupying.getColor()==Color.WHITE){
//                        g.setColor(new Color(0, 0, 200, 1));
                        g.setColor(Color.WHITE);
//                        g.setColor(new Color(210, 210, 190));
                    }
                    g.fillOval(x, y, (int) (unitSize*0.9), (int)(unitSize*0.9));
                }
                // Showing the string
                String s = board.getCells()[i][j].getID();
                g.setColor(Color.red);
                g.drawString(s, x + (unitSize/2), y + (unitSize/2));

                //then update coordinates
                y = y - unitSize;
            }
            x = x + unitSize;
//            WhiteChecker white = new WhiteChecker(new Player(Color.red), board.getCells()[7][1]);
////            System.out.println("Checking for cell " + white.getPosition().getID() + " and " + board.getCells()[7][1].getID());
//            System.out.println(white.noCheckerInDiagonal(board, board.getCells()[1][7]));
        }
//        Checker[][] checkers = this.board.getCheckers();
//        for(int i=0; i<checkers.length; i++){
//            for (int j = 0; j<checkers[i].length; j++){
//
//                System.out.println("checker=" + checkers[i][j].getPosition().getID() + " at position " + checkers[i][j].getColor());
//            }
//        }

    }

    public void setBoard(Board board){
        this.board=board;
    }


    public void newBoard(){

    }
    public void move(Graphics g, Checker checker){
        // When selected clicked
        // Get next position from second click
        // Move if possible from the move method from checker

        //For now just move from one cell to another

    }

    public void checkImpasse(){

    }

    public void checkCrow(){

    }
    public void checkBearOff(){

    }

    public void gameOver(){

    }
//    public DragCircle() {
//        setBackground(Color.WHITE);
//        MouseDrag mouseDrag = new MouseDrag();
//        addMouseListener(mouseDrag);
//        addMouseMotionListener(mouseDrag);
//    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // Checkers
        // Click on checker -> selected
        // Click on cell -> change position of circle and remove previous
        Graphics g = getGraphics();
        // get the checker at the clicked position

        g.setColor(Color.orange);
        g.fillOval(e.getX(), e.getY(), 30, 30);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }

    }

}
