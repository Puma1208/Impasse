package Minimax;

public class BinaryNode extends Node{

    Node leftChild;
    Node rightChild;

    public BinaryNode(int value) {
        super(value);
    }

    public void addLeftChild(Node node){
        this.leftChild = node;
    }
    public void addRightChild(Node node){
        this.rightChild = node;
    }
//    @Override
//    public void addChild(Node child) {
//        super.addChild(child);
//    }
}
