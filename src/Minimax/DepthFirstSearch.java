package Minimax;

import java.util.ArrayList;

public class DepthFirstSearch {

    public static ArrayList<Node> traversing(Tree tree){
        Node root = tree.getRoot();
        ArrayList<Node> ordering = new ArrayList<>();
        ordering.add(root);
        for(Node child: root.getChildren()){
            ArrayList<Node> children = traversing(new Tree(child));
            for(Node node: children){
                ordering.add(node);
            }
        }
        return ordering;
    }
}

