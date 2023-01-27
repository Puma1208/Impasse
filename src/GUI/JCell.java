package GUI;


import GameElements.Cell;
import GameElements.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** For the GUI to click on a cell to have the position
 *
 */
public class JCell extends JPanel implements MouseListener {
    final JBoard jboard;
    private final Cell cell;
    private final int x;
    private final int y;
    private int size;
    private final Color color;


    boolean crown = false;
    JCell(JBoard jboard, final Cell cell, int x, int y, int size){
        this.jboard = jboard;
        this.cell = cell;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = cell.getColor();
        addMouseListener(this);

    }

    @Override
    public void paintComponent(Graphics g){
        draw(g);
    }

    public void draw(Graphics g){
        setBounds(x, y, getUnitSize(), getUnitSize());
        setBackground(color);
        if(cell.isOccupied()){
            int space = getUnitSize()/9;
            if(cell.getOccupyingStack()!=null){

                Color color = cell.getOccupyingStack().getBottomChecker().getColor();
                g.setColor(color);
                g.fillOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);
                g.setColor(Color.DARK_GRAY);
                g.fillOval(2*space, 2*space, getUnitSize()-4*space, getUnitSize()-4*space);
                g.setColor(color);
                g.fillOval(3*space, 3*space, getUnitSize()-6*space, getUnitSize()-6*space);
            }
            else if(cell.getOccupying()!=null){

                g.setColor(cell.occupying.getColor());
                g.drawOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);
                g.fillOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);

                if(cell.getOccupying().getPlayer().checkersToCrown.size()>0
                        && !cell.getOccupying().getPlayer().checkersToCrown.contains(cell.getOccupying())){
                    g.setColor(Color.orange);
                    g.fillOval(3*space, 3*space, getUnitSize()-6*space, getUnitSize()-6*space);
                }
            }

        }

        // Check if should be shown as a possible slide
        if(cell.isPossibleSlide){
            int space = getUnitSize()/3;
            g.setColor(new Color(200, 100, 100, 150));
            g.drawOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);
            g.fillOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);

        }
        // Check if should be shown as a possible slide
        if(cell.isPossibleTranspose && cell.getOccupying()!=null){
            int space = getUnitSize()/9;
            Color color = cell.getOccupying().getColor();
            g.setColor(color);
            g.fillOval(space/2, space/2, getUnitSize()-space, getUnitSize()-space);
            g.setColor(Color.darkGray);
            g.fillOval(2*space, 2*space, getUnitSize()-4*space, getUnitSize()-4*space);
            g.setColor(color);
            g.fillOval(3*space, 3*space, getUnitSize()-6*space, getUnitSize()-6*space);

            g.setColor(new Color(200, 10, 10, 150));
            g.fillOval(space/2-1, space/2-1, getUnitSize()-space+2, getUnitSize()-space+2);
        }

        String id = cell.getID();
        g.setColor(new Color(220, 30, 50));
        g.drawString(id, getUnitSize()/2, getUnitSize()/2);
        repaint();

    }
    public int getX(){ return x;}
    public int getY(){ return y;}
    public Cell getCell(){
        return cell;
    }
    public int getUnitSize(){
        return this.size;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(jboard.board.play.gameStopped){

        }else{
            Piece occupying = cell.getOccupyingPiece();
            if(occupying!=null){
                if(jboard.board.crownMode){
                    if(jboard.board.play.getCurrentPlayer().selectedPiece==null){
                        occupying.getPlayer().notifySelectPiece(occupying);
                    }else{
                        occupying.getPlayer().notifySelectedCell(cell);
                    }
                }else{
                    if(occupying.getPlayer()==jboard.board.play.getCurrentPlayer()){
                        occupying.getPlayer().notifySelectPiece(occupying);
                    } else {
                        System.out.println("    Piece at " + cell.getID() + " is not a piece of the current player");
                    }
                }
            }
            else{
                jboard.board.play.getCurrentPlayer().notifySelectedCell(cell);
            }

    }
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


}
