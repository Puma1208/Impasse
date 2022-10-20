package Minimax;

import java.util.ArrayList;

public class MiniMax {

    // Parameters: state s, int depth, int type
    // type = the type of the player that is currently playing
    // 1 = max, 0=min
    public static int MiniMax(Node current, int depth, int type){
        // if terminal node or depth==0
        int score;
        if(depth==0 || current.isLeaf()){   return evaluate(current); }
        if(type==1){
            score = (int) Double.NEGATIVE_INFINITY;
            for(Node child: current.getChildren()){
                int value = MiniMax(child, depth-1, 0);
                if(value>score){
                    score=value;
                    current.setValue(score);

                }
            }
            // for score = -infty, child=1, child<NumSuccessor(state)
        } else {

            score = (int) Double.POSITIVE_INFINITY;
            for(Node child: current.getChildren()){
                int value = MiniMax(child, depth-1, 1);
                if(value<score){
                    score=value;
                    current.setValue(score);
                }
            }

        }
        return score;
    }

    public static int evaluate(Node current){
        return current.getValue();
    }
}
