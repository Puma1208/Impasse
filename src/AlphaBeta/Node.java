package AlphaBeta;

import java.util.ArrayList;

public class Node {

    Node parent = null;
    private int value;
    private ArrayList<Node> children = new ArrayList<Node>();

    public Node(int value){
        this.value = value;
    }
    public Node(){}
    public void setValue(int value){    this.value = value; }
    public void addChild(Node child){
        children.add(child);
        child.setParent(this);
    }

    public void setParent(Node parent){
        this.parent = parent;
        //parent.addChild(this);
    }
    public void addChildren(ArrayList<Node> children){
        for(Node child:children){
            this.addChild(child);
        }
    }
    public ArrayList<Node> getChildren(){   return children;}
    public int getValue(){  return value;}
    public Node getParent(){    return this.parent; }
    public void removeChild(Node child){
        if(children.contains(child)){
            children.remove(child);
        }
    }
    public void removeChildrenFrom(Node child){
        if(children.contains(child)){
            children.subList(children.indexOf(child), children.size()).clear();
        }
    }
    public void removeChildrenAfter(Node child){
        if(children.contains(child)){
            children.subList(children.indexOf(child)+1, children.size()).clear();
        }
    }
    public boolean isLeaf(){    return children.isEmpty();  }
}
