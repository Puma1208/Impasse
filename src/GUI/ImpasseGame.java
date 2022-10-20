package GUI;

import GameElements.*;

import java.awt.*;

public class ImpasseGame {
    public static void main(String[] args) {

        // To keeeeep!
//        System.out.println("here it iss");
//        for(int i=0; i<20; i++){
//            int mod1 = i%4;
//            int transform1 = 2-(mod1%2);
//            int transform2 = (int)(mod1/2)*6;
//            int result = transform1 + transform2;
//            System.out.println("index = " + i +  " has col " + result);
//        }
//
//        System.out.println("here it iss for black");
//        for(int i=0; i<20; i++){
//            int mod1 = i%4;
//            int transform1 = ((2-(mod1/2))*6);
//
//            int result = 8 - i%2 - (mod1/2)*6;
//            System.out.println("index = " + i +  " has col " + result);
//        }
        // Testing transpose rule
//        Cell cell1 = new Cell(3, 3);
//        Cell cell2 = new Cell(2, 4);
//        Player player1 = new Player(Color.WHITE);
//        WhiteChecker checker1 = new WhiteChecker(player1, cell2);
//        WhiteChecker checker2 = new WhiteChecker(player1, cell2);
//        WhiteChecker checker3 = new WhiteChecker(player1, cell1);
//        StackCheckers stack = new StackCheckers(checker1, checker2, checker2.getPosition());
//        System.out.println(stack.getTopChecker().transposeAllowed(checker3.getPosition()));


        new GameFrame(8);
//        Board.attemptYPos(10);


    }
}
