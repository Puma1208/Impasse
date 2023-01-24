package GameElements;
import java.awt.Color;
import java.util.ArrayList;

public class Cell {
    static final char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    // Row and column start counting from 1
    final int column;
    final int row;
    final private String id;
    final private Color color;

    private boolean occupied;
    public Checker occupying;
    public StackCheckers occupyingStack;

    boolean needToCrown;

    public boolean isPossibleTranspose;
    public boolean isPossibleSlide;


    public Cell(int column, int row){
        this.column = column;
        this.row = row;
        id = convertColToLetter() + String.valueOf(row);
        this.color = setColor();
        this.occupied = false;
        this.occupying = null;
        this.occupyingStack = null;
        this.needToCrown = false;

        this.isPossibleSlide = false;
        this.isPossibleTranspose = false;
    }

    public String getID(){
        return id;
    }

    public char convertColToLetter(){
        return alphabet[column-1];
    }

    public Color setColor(){
        if((this.row+this.column)%2==1){    return Color.LIGHT_GRAY;   }
        else{   return Color.DARK_GRAY; }
    }
    public Color getColor(){
        return this.color;
    }


    public void setUnoccupied(){
        this.occupied = false;
        this.occupying = null;
        this.occupyingStack = null;
    }
    public void setOccupation(Checker checker) {
        this.occupied = true;
        this.occupyingStack = null;
        this.occupying = checker;
    }

    /**
     * Should only be called form StackChecker setPosition()
     * @param stack
     */
    public void setOccupyingStack(StackCheckers stack){
        if(stack==null){
            this.occupied = false;
            this.occupyingStack = null;
        }else{
            this.occupied = true;
            stack.bottomChecker.setPosition(this);
            stack.getBottomChecker().position = this;
            stack.getTopChecker().position = this;
            this.occupyingStack = stack;
        }


    }
    public boolean isOccupied(){
        return (occupied && (this.occupying!=null || this.occupyingStack!=null));
    }

    public int getColumn(){
        return column;
    }
    public int getRow(){
        return row;
    }
    public Checker getOccupying(){  return occupying;}
    public StackCheckers getOccupyingStack(){
        return occupyingStack;
    }

    public Piece getOccupyingPiece(){
        if(this.occupyingStack!=null){
            return this.occupyingStack;
        }if(this.occupying!=null){
            return this.occupying;
        }
        return null;
    }



}
