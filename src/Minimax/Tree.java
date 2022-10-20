package Minimax;

import java.util.ArrayList;

public class Tree {
    private Node root;

    ArrayList<Node> nodes = new ArrayList<>();
    public Tree(Node root){
        this.root = root;
    }

    public Node getRoot(){  return root;    }
//    public void countChildren(){}

    // Get depth
    // Traverse tree
    public void printTree(){

    }
    public static void printTree(Node current, String app){
        System.out.println(app + current.getValue());
        current.getChildren().forEach(each ->  printTree(each, app + app));

//        for(Node child: current.getChildren()){
//            printTree(child, separator+separator);
//        }
    }






}
