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
    private StackCheckers occupyingStack;

    boolean needToCrown;


    public Cell(int column, int row){
        this.column = column;
        this.row = row;
        // Set the final string ID
        id = convertColToLetter() + String.valueOf(row);
        // Set the final color for the cell that will not change
        // >> make it work when the color is final
        this.color = setColor();
        this.occupied = false;
        this.occupying = null;
        this.occupyingStack = null;
        this.needToCrown = false;
    }

    // Only use after initialising with the position
    public String getID(){
        return id;
    }

    public char convertColToLetter(){
        return alphabet[column-1];
    }

    public Color setColor(){
//        if((this.row+this.column)%2==1){    return Color.WHITE;   }
//        else{   return Color.BLACK; }
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
        this.occupying = checker;
    }
    public void setOccupyingStack(StackCheckers stack){
        if(stack==null){
            this.occupied = false;
            this.occupyingStack = null;
        }else{
            this.occupied = true;
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
    // Keep bottom checker on current cell and remove stack from current position
    public void removeCurrentStack(){
        if(this.getOccupyingStack()!=null){// && occupied){
            this.occupying = occupyingStack.bottomChecker;
            setOccupation(occupyingStack.bottomChecker);
            this.occupyingStack=null;
        }
    }
//    public void needToCrown(){
////        if(occupied && occupyingStack==null && occupying!=null){
////            // 0 = White
////            if(occupying.getPlayer().getPlayerIndex()==0){
////                needToCrown = (this.row==)
////            }
////        }
//        if(this.occupying!=null && this.occupyingStack==null){
//            this.needToCrown= getOccupying().canCrown;
//
//        }
//    }
    public void crownDone(){
        this.needToCrown = false;
    }
    public boolean toCrown(){
        return needToCrown;
    }

//    public void getAvailableSlide(Checker checker){
//        ArrayList<Cell> canSlide = new ArrayList<>();
//        if(checker.getColor()==Color.BLACK){
//            int row;
//            for()
//        }
//    }


}
