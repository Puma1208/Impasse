package GameElements;

import java.awt.*;
import java.util.ArrayList;

public class WhiteChecker extends Checker{

    final static Color color = Color.WHITE;
    public WhiteChecker(Player player, Cell cell, Board board) {
        super(player, cell, board);
    }

    public Color getColor(){
        return this.color;
    }

    @Override
    public boolean canSlide(Cell cell) {
        return getPossibleSlide().contains(cell);
    }

    @Override
    public void slide(Cell cell) {
        super.slide(cell);
        if(mustCrown()){
            this.mustCrown = true;
        }
    }


    /**
     * @return all the possible cell positions the current piece can be in
     */
    @Override
    public ArrayList<Cell> getPossibleSlide() {
        Cell current = this.position;
        ArrayList<Cell> cells = new ArrayList<>();
        // Slides to the left
        while(getNextLeft(current)!=null && !getNextLeft(current).isOccupied()) {
            cells.add(getNextLeft(current));
            current = getNextLeft(current);

        }
        current = this.position;
        // Slides to the right
        while(getNextRight(current)!=null && !getNextRight(current).isOccupied()) {
            cells.add(getNextRight(current));
            current = getNextRight(current);


        }
        return cells;
    }


    /**
     * @return The next cell to the left of the player
     */
    @Override
    public Cell getNextLeft(Cell current) {
        if(current.row>=board.getSize() || current.column<=1){
            return null;
        }
        return board.getCell(current.row+1, current.column-1);
    }

    /**
     * @return The next cell to the right of the player
     */
    @Override
    public Cell getNextRight(Cell current) {
        if(current.row>=board.getSize() || current.column>=board.getSize()){
            return null;
        }
        return board.getCell(current.row+1, current.column+1);
    }

    @Override
    public boolean mustCrown() {
        return super.mustCrown() && this.position.row==board.getSize();
    }

    @Override
    public boolean canCrownWith(Checker checker) {
        return super.canCrownWith(checker) && mustCrown();
    }
}
