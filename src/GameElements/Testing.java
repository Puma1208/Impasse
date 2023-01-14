package GameElements;

import GUI.GameFrame;
import GUI.JBoard;

import java.awt.*;

public class Testing {
    public static void main(String[] args) throws CloneNotSupportedException {

        Player p = new Player(PlayerType.AI, Color.WHITE);

        Board b = new Board(new GamePlay(8, PlayerType.AI, PlayerType.AI), 8, PlayerType.AI, PlayerType.AI);
//        System.out.println("PLAYER=" + b.players[1]);
//        for(StackCheckers s: b.players[1].stacks){
//            System.out.println(s);
//            System.out.println("    at pos " + s.position);
//
//        }

        Cell c1 = b.getCell(1, 'C');
        Cell c3 = b.getCell(3, 'C');
        Cell g1 = b.getCell(1, 'G');
        Cell g3 = b.getCell(3, 'G');

        c1.getOccupyingStack().setPosition(c3);
        g1.getOccupyingStack().setPosition(g3);

        Cell d8 = b.getCell(8, 'D');
        Cell d6 = b.getCell(6, 'D');
        Cell h8 = b.getCell(8, 'H');
        Cell h6 = b.getCell(6, 'H');

        d8.getOccupying().setPosition(d6);
        h8.getOccupying().setPosition(h6);

        Cell a7 = b.getCell(7, 'A');
        Cell b6 = b.getCell(6, 'B');
        Cell e7 = b.getCell(7, 'E');
        Cell f6 = b.getCell(6, 'F');

        a7.getOccupying().setPosition(b6);
        e7.getOccupying().setPosition(f6);

        Cell b8 = b.getCell(8, 'B');
        Cell f8 = b.getCell(8, 'F');

        f8.getOccupying().player.removeStack(f8.getOccupyingStack());
        f8.setUnoccupied();

        Cell e3 = b.getCell(3, 'E');
        b8.getOccupyingStack().setPosition(e3);

        Cell d2 = b.getCell(2, 'D');


        d2.getOccupying().player.removeChecker(d2.getOccupying());
        d2.setUnoccupied();
//        System.out.println("D2 =" + d2.getOccupying()+"~" + d2.getOccupyingStack());
        c3.getOccupyingStack().setPosition(d2);
//        d2.setOccupyingStack(c3.getOccupyingStack());
//        c3.setUnoccupied();

        // Must use set position for the stack so that everything is updated

//        System.out.println("D2 =" + d2.getOccupying()+"~" + d2.getOccupyingStack()+  d2.getOccupyingStack().position.getID());

//        System.out.println("PLAYER=" + b.players[1]);
//        for(StackCheckers s: b.players[1].stacks){
//            System.out.println(s);
//            System.out.println("        at pos" + s.position);
//
//        }
//        System.out.println("_"+ d2.occupyingStack.player.shouldImpasse());

//        System.out.println(e3.getOccupying() + " " +e3.getOccupyingStack());
        System.out.println(e3.getOccupying().canImpasse());
        Cell c7 = b.getCell(7, 'C');
        c7.getOccupyingStack().impasse();

        Cell e1 = b.getCell(1, 'E');

        d2.getOccupyingStack().slide(c3);
        e3.getOccupyingStack().slide(d2);
//        d2.getOccupyingStack().transpose(e1);

//        System.out.println(e1.getID() + " " + e1.getOccupyingStack() + " "+ e1.getOccupyingStack().bottomChecker.stack + " " + e1.getOccupyingStack().topChecker.stack);
//        System.out.println(d2.getID() + " " + d2.getOccupying().stack );

//        e3.getOccupyingStack().slide(c1);
//        e3.getOccupyingStack().setPosition(c1);
//        c1.getOccupyingStack().bearOff();


        System.out.println("testing to get possible moves");
        System.out.println("Player 1");
        b.players[0].makeMove();
        System.out.println();
        System.out.println("Player 2");
        b.players[1].makeMove();

        JBoard jb = new JBoard(b);
        new GameFrame(jb);


        
    }
}
