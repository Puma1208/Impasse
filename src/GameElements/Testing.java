package GameElements;

import GUI.GameFrame;
import GUI.JBoard;

import java.awt.*;

public class Testing {
    public static void main(String[] args) {

        Player p = new Player(PlayerType.AI, Color.WHITE);

        Board b = new Board(new GamePlay(8, PlayerType.AI, PlayerType.AI), 8, PlayerType.AI, PlayerType.AI);
        Checker c = new WhiteChecker(
                p,
                new Cell(1, 1),
                b);



        Cell cell = b.getCell(7, 'C');
        System.out.println("cell=" + cell.getID());

        StackCheckers s = cell.getOccupyingStack();
        s.setPosition(b.getCell(3, 'G'));
        System.out.println(b.getCell(7, 'C').occupyingStack + "_"+ b.getCell(7, 'C').isOccupied()+ " vs " + b.getCell(3, 'G').occupyingStack + "_"+ b.getCell(3, 'G').isOccupied());
        System.out.println(b.getCell(2, 2).getID());
        System.out.println("can transpose to cell " + b.getCell(2, 'H').getID() + " " + s.canTranspose(b.getCell(2, 'H')));

        System.out.println("___________________");
        Cell cell2 = b.getCell(2, 'B');
        StackCheckers s2 =cell2.getOccupyingStack();

        Cell transpose1 = b.getCell(3, 'A');
        Cell transpose2 =  b.getCell(3, 'C');
        Checker c1 = b.getCell(7, 'A').getOccupying();
        Checker c2 = b.getCell(8, 'D').getOccupying();

        c1.setPosition(transpose1);
        c2.setPosition(transpose2);
        System.out.println(b.getCell(7, 'A').getID() + " " + b.getCell(7, 'A').getOccupying() + " VS "
                +transpose1.getID() + " " + transpose1.getOccupying());
        System.out.println(b.getCell(8, 'D').getID() + " " + b.getCell(8, 'D').getOccupying() + " VS "
                +transpose2.getID() + " " + transpose2.getOccupying());
        for(Cell to: s2.getPossibleTranspose()){
            System.out.println("Transpose from " + s2.position.getID() + " to " + to.getID());
        }
        s2.setPosition(b.getCell(4, 'D'));

        System.out.println(b.getCell(4, 'D').getID() + " " + b.getCell(4, 'D').getOccupying() + " VS "
                +b.getCell(2, 'B').getID() + " " + b.getCell(2, 'B').getOccupying());
        for(Cell to: s2.getPossibleTranspose()){
            System.out.println("Transpose from " + s2.position.getID() + " to " + to.getID());
        }

//        System.out.println(cell2.occupyingStack + "_"+ cell2.isOccupied()+ " vs " + go2.occupyingStack + "_"+ go2.isOccupied());
//        System.out.println("Current position " + s2.position.getID());
//        System.out.println("can transpose to cell " + transpose2.getID() + " " + s2.canTranspose(transpose2));


        System.out.println("________________________________________________");
        System.out.println("_______________Testing transpose________________");

        Cell transposingTo = b.getCell(3, 'C');

        System.out.println("Cell from -> " + s2.position.getID() + " " + s2.position.isOccupied()+"-" + s2.position.getOccupying() + "-" + s2.position.getOccupyingStack() +"***" +s2.topChecker);
        System.out.println("Cell   to -> " + transposingTo.getID() + " " + transposingTo.isOccupied()+"-" + transposingTo.getOccupying() + "-" + transposingTo.getOccupyingStack());

        s2.transpose(transposingTo);
        System.out.println();

        System.out.println("Cell from -> " + s2.position.getID() + " " + s2.position.isOccupied()+"-" + s2.position.getOccupying() + "-" + s2.position.getOccupyingStack());
        System.out.println("Cell   to -> " + transposingTo.getID() + " " + transposingTo.isOccupied()+"-" + transposingTo.getOccupying() + "-" + transposingTo.getOccupyingStack() + "***" +transposingTo.getOccupyingStack().topChecker);

        Cell cE7 = b.getCell(2, 'D');
        Checker blackE7 = cE7.occupying;
        System.out.println(cE7.getID() + " occupied by checker " + cE7.getOccupying() + " by stack " + cE7.getOccupyingStack());
        System.out.println(blackE7.getPossibleSlide().size());
        for(Cell possible: blackE7.getPossibleSlide()){
            System.out.println("Possible " + possible.getID() + " " +possible.getOccupying() + "**" + possible.getOccupyingStack());
        }
        System.out.println(b.getCell(3, 'C').getID() + b.getCell(3, 'C').getOccupying() + b.getCell(3, 'C').getOccupyingStack());
        JBoard jb = new JBoard(b);
        new GameFrame(jb);
    }
}
