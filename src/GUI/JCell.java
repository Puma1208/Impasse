package GUI;


import GameElements.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// For the GUI to click on a cell to have the position
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

//                if(cell.toCrown()){
//                    g.setColor(Color.orange);
//                    g.fillOval(3*space, 3*space, getUnitSize()-6*space, getUnitSize()-6*space);
//                }
            }

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
    public void showCrown(){    this.crown=true;}
    public void crownFinished(){ this.crown=false;}


    @Override
    public void mouseClicked(MouseEvent e) {
        jboard.cellNotifying(this);
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
