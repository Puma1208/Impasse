import Minimax.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        int[][]M = new int[4][4];
        M[0][1] = 5;
        int[][] N = {{1, 2}, {3, 4}};

        System.out.println("M");
        System.out.println(Arrays.deepToString(M));
        System.out.println("N");
        System.out.println(Arrays.deepToString(N));

        Node MaxRoot = new Node();
        Node minRoot1 = new Node();
        Node minRoot2 = new Node();
        MaxRoot.addChild(minRoot1);
        MaxRoot.addChild(minRoot2);

        Node Max21 = new Node();
        Node Max22 = new Node();
        minRoot1.addChild(Max21);
        minRoot1.addChild(Max22);

        Node Max23 = new Node();
        Node Max24 = new Node();
        minRoot2.addChild(Max23);
        minRoot2.addChild(Max24);

        Node min21 = new Node();
        Node min22 = new Node();
        Max21.addChild(min21);
        Max21.addChild(min22);

        Node min23 = new Node();
        Max22.addChild(min23);
        Node min24 = new Node();
        Max23.addChild(min24);
        Node min25 = new Node();
        Node min26 = new Node();
        Max24.addChild(min25);
        Max24.addChild(min26);

        Node max31 = new Node(7);
        Node max32 = new Node(9);
        min21.addChild(max31);
        min21.addChild(max32);

        Node max33 = new Node(-3);
        Node max34 = new Node(10);
        min22.addChild(max33);
        min22.addChild(max34);

        Node max35 = new Node(5);
        Node max36 = new Node(2);
        min23.addChild(max35);
        min23.addChild(max36);

        Node max37 = new Node(3);
        Node max38 = new Node(8);
        min24.addChild(max37);
        min24.addChild(max38);

        Node max39 = new Node(4);
        min25.addChild(max39);
        Node max310 = new Node(-5);
        min26.addChild(max310);


        //int TreeValue = MiniMax.MiniMax(MaxRoot, 5, 1);
        //Tree.printTree(MaxRoot, "*");

        //System.out.println("MiniMax value = " + TreeValue);

//        int alphaBeta = AlphaBeta.AlphaBeta(MaxRoot, 1);
//        System.out.println("AlphaBeta=" + alphaBeta);
//
//        Tree.printTree(MaxRoot, "*");

//        Tree 2 ----------------------------------------------------
//        Node leaf1 = new Node(5);
//        Node leaf2 = new Node(0);
//        Node leaf3 = new Node(6);
//        Node leaf4 = new Node(2);
//        Node leaf5 = new Node(8);
//        Node leaf6 = new Node(4);
//        Node leaf7 = new Node(-6);
//        Node leaf8 = new Node(-4);
//        Node leaf9 = new Node(-2);
//        Node leaf10 = new Node(2);
//        Node leaf11 = new Node(5);
//        Node leaf12 = new Node(4);
//        Node leaf13 = new Node(6);
//        Node leaf14 = new Node(-4);
//
//        Node parent12 = new Node((int)Double.NEGATIVE_INFINITY); parent12.addChild(leaf1); parent12.addChild(leaf2);
//        Node parent3456 = new Node((int)Double.NEGATIVE_INFINITY); parent3456.addChild(leaf3); parent3456.addChild(leaf4); parent3456.addChild(leaf5); parent3456.addChild(leaf6);
//        Node parent78 = new Node((int)Double.NEGATIVE_INFINITY); parent78.addChild(leaf7); parent78.addChild(leaf8);
//        Node parent910 = new Node((int)Double.NEGATIVE_INFINITY); parent910.addChild(leaf9); parent910.addChild(leaf10);
//        Node leaf15 = new Node(-6);
//        Node leaf16 = new Node(-4);
//        Node parent1112 = new Node((int)Double.NEGATIVE_INFINITY); parent1112.addChild(leaf11); parent1112.addChild(leaf12);
//        Node parent1314 = new Node((int)Double.NEGATIVE_INFINITY); parent1314.addChild(leaf13); parent1314.addChild(leaf14);
//
//        Node min1 = new Node((int)Double.NEGATIVE_INFINITY); min1.addChild(parent12); min1.addChild(parent3456); min1.addChild(parent78); min1.addChild(parent910);
//        Node min2 = new Node((int)Double.NEGATIVE_INFINITY); min2.addChild(leaf15); min2.addChild(leaf16);
//        Node min3 = new Node((int)Double.NEGATIVE_INFINITY); min3.addChild(parent1112); min3.addChild(parent1314);
//
//        Node root = new Node((int)Double.NEGATIVE_INFINITY); root.addChild(min1); root.addChild(min2); root.addChild(min3);
//        Tree 2 ___________________________________________________

//        Treeee test 3 ___________________________________________
        Node leaf1 = new Node(2);
        Node leaf2 = new Node(7);
        Node leaf3 = new Node(-9);
        Node leaf4 = new Node(6);
        Node leaf5 = new Node(2);
        Node leaf6 = new Node(9);
        Node leaf7 = new Node(4);
        Node leaf8 = new Node(6);
        Node leaf9 = new Node(1);
        Node leaf10 = new Node(5);
        Node leaf11 = new Node(4);
        Node leaf12 = new Node(7);
        Node leaf13 = new Node(-8);
        Node leaf14 = new Node(7);
        Node leaf15 = new Node(4);
        Node leaf16 = new Node(6);

        Node parent12 = new Node(); parent12.addChild(leaf1); parent12.addChild(leaf2);
        Node parent34 = new Node(); parent34.addChild(leaf3); parent34.addChild(leaf4);
        Node parent56 = new Node(); parent56.addChild(leaf5); parent56.addChild(leaf6);
        Node parent78 = new Node(); parent78.addChild(leaf7); parent78.addChild(leaf8);
        Node parent910 = new Node(); parent910.addChild(leaf9); parent910.addChild(leaf10);
        Node parent1112 = new Node(); parent1112.addChild(leaf11); parent1112.addChild(leaf12);
        Node parent1314 = new Node(); parent1314.addChild(leaf13); parent1314.addChild(leaf14);
        Node parent1516 = new Node(); parent1516.addChild(leaf15); parent1516.addChild(leaf16);

        Node parent1234 = new Node(); parent1234.addChild(parent12); parent1234.addChild(parent34);
        Node parent5678 = new Node(); parent5678.addChild(parent56); parent5678.addChild(parent78);
        Node parent9101112 = new Node(); parent9101112.addChild(parent910); parent9101112.addChild(parent1112);
        Node parent13141516 = new Node(); parent13141516.addChild(parent1314); parent13141516.addChild(parent1516);


        Node parent12345678 = new Node(); parent12345678.addChild(parent1234); parent12345678.addChild(parent5678);
        Node parent910111213141516 = new Node(); parent910111213141516.addChild(parent9101112); parent910111213141516.addChild(parent13141516);

        Node root = new Node(); root.addChild(parent12345678); root.addChild(parent910111213141516);
//       Tree test 3 ________________________________________________

        // Other tree test 4_________________________________
//        Node leaf1 = new Node(7);
//        Node leaf2 = new Node(6);
//        Node leaf3 = new Node(3);
//        Node leaf4 = new Node(2);
//        Node leaf5 = new Node(8);
//        Node leaf6 = new Node(9);
//        Node leaf7 = new Node(4);
//        Node leaf8 = new Node(5);
//        Node leaf9 = new Node(1);
//        Node leaf10 = new Node(10);
//        Node leaf11 = new Node(2);
//        Node leaf12 = new Node(11);
//        Node leaf13 = new Node(12);
//        Node leaf14 = new Node(13);
//        Node leaf15 = new Node(14);
//        Node leaf16 = new Node(15);
//
//        Node parent12 = new Node(); parent12.addChild(leaf1); parent12.addChild(leaf2);
//        Node parent34 = new Node(); parent34.addChild(leaf3); parent34.addChild(leaf4);
//        Node parent56 = new Node(); parent56.addChild(leaf5); parent56.addChild(leaf6);
//        Node parent78 = new Node(); parent78.addChild(leaf7); parent78.addChild(leaf8);
//        Node parent910 = new Node(); parent910.addChild(leaf9); parent910.addChild(leaf10);
//        Node parent1112 = new Node(); parent1112.addChild(leaf11); parent1112.addChild(leaf12);
//        Node parent1314 = new Node(); parent1314.addChild(leaf13); parent1314.addChild(leaf14);
//        Node parent1516 = new Node(); parent1516.addChild(leaf15); parent1516.addChild(leaf16);
//
//        Node parent1234 = new Node(); parent1234.addChild(parent12); parent1234.addChild(parent34);
//        Node parent5678 = new Node(); parent5678.addChild(parent56); parent5678.addChild(parent78);
//        Node parent9101112 = new Node(); parent9101112.addChild(parent910); parent9101112.addChild(parent1112);
//        Node parent13141516 = new Node(); parent13141516.addChild(parent1314); parent13141516.addChild(parent1516);
//
//        Node parent12345678 = new Node(); parent12345678.addChild(parent1234); parent12345678.addChild(parent5678);
//        Node parent910111213141516 = new Node(); parent910111213141516.addChild(parent9101112); parent910111213141516.addChild(parent13141516);
//
//        Node root = new Node(); root.addChild(parent12345678); root.addChild(parent910111213141516);
//        // Other tree test _________________________________

        //int TreeValue = MiniMax.MiniMax(root, 3, 1);
        Tree.printTree(root, "*");
//        MiniMax.MiniMax(root, 3, 1);
//        AlphaBeta.AlphaBeta(root,1, (int) Double.NEGATIVE_INFINITY, (int) Double.POSITIVE_INFINITY);
        AlphaBeta.alphaBetaFailHard(root, 3, (int) Double.NEGATIVE_INFINITY+1, (int) Double.POSITIVE_INFINITY-1, 1);
        System.out.println("after minimax");
        Tree.printTree(root, "*");







    }
}
