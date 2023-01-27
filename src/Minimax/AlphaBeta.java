package Minimax;

import GameElements.GameState;

import java.awt.font.GlyphMetrics;

public class AlphaBeta {


    // Steps
    // go down to the first child completely to set a value
    // for other children, compare to set value ✅
    // prune when among the first researched children
    //      value to maximise is bigger than parents node to minimise
    //      value to minimise is smaller than parents node to maximise

    // player == 1 >> wants to maximise
    // player == 0 >> wants to minimise

    // Fail hard variation - Working, actually not***
    public static int alphaBetaFailHard(Node node, int depth, int alpha, int beta, int player){
        if(depth==0 || node.isLeaf()){
            return node.getValue();
        }
        int value;
        if(player==1){
            value = (int) Double.NEGATIVE_INFINITY;
            for(Node child: node.getChildren()){

                value = Math.max(value, alphaBetaFailHard(child, depth-1, alpha, beta, 0));
                alpha = Math.max(alpha, value);
                if(alpha>=beta){
                    node.removeChildrenAfter(child);
                    node.setValue(alpha);
                    break;
                }
//                return (int)alpha;
            }
        }else{
            value = (int) Double.POSITIVE_INFINITY;
            for(Node child: node.getChildren()){

                value = Math.min(value, alphaBetaFailHard(child, depth-1, alpha, beta, 1));
                if(value <=alpha){
                    node.removeChildrenAfter(child);
                    node.setValue(value);
                    break;
                }
                beta = Math.min(beta, value);
            }

        }
        System.out.println("setting value " + value);
        node.setValue(value);
        return value;

    }




    public static int AlphaBeta(Node current, int depth, int player, int alpha, int beta){
        if(current.isLeaf() || depth==0){   return current.getValue();  }
        else{
            if(player==1){
                for(Node child: current.getChildren()){
                    if(current.getParent()!= null && alpha > current.getParent().getValue()){
                        current.removeChildrenAfter(child);
                        break;
                    }
                    int childValue = AlphaBeta(child,depth-1, 0, alpha, beta);
                    if(childValue > alpha){
                        alpha = childValue;
                        current.setValue(alpha);
                    }
                    // need to prune "siblings" if current has value higher than minimizing parent -> the alpha? value

                }
                return current.getValue();
            }
            else
            {
                for(Node child: current.getChildren()){
                    if(current.getParent()!= null && beta < current.getParent().getValue()){
                        current.removeChildrenAfter(child);
                        break;
                    }
                    int childValue = AlphaBeta(child, depth-1, 1, alpha, beta);
                    if(childValue < beta){
                        beta = childValue;
                        current.setValue(beta);
                    }
                    // need to prune "siblings" if current has value lower than maximizing parent


                }
                return current.getValue();
            }
        }


    }
    public static int AlphaBetaNegaMax(Node n, int depth, int alpha, int beta){
        /// PROBLEM found -  the negative bounds don't become positive!  
        System.out.println("entering with bounds " + alpha + " and " + beta);
//        System.out.println("next bounds should be " + Math.negateExact(beta) + " and " + + Math.negateExact(alpha)); //Math.negateExact(alpha));

        if(n.isLeaf() || depth==0){
            n.setValue(Math.negateExact(n.getValue()));
//            return Math.negateExact(n.getValue());
            return n.getValue();

        }
            int score = (int) Double.NEGATIVE_INFINITY;
            int updateAlpha = alpha;
            for(Node child: n.getChildren()){
                System.out.println("alpha=" + Math.negateExact(beta) + " beta=" + Math.negateExact(updateAlpha));
                int value = Math.negateExact(AlphaBetaNegaMax(child, depth-1, Math.negateExact(beta), Math.negateExact(updateAlpha)));
                if(value>score){
                    score = value;
                }
                if(score>alpha){
                    alpha = score;
                    updateAlpha = score;
                    n.setValue(updateAlpha);
                }
                if(score >= beta){
                    // remove children
                    n.removeChildrenAfter(child);

                    break;
                }
            }
//        System.out.println("current score " + score);
//        n.setValue(score);
        return score;
    }


    public static double alphaBetaFailHard(GameState node, int depth, double alpha, double beta, int player){
        if(depth==0 ){
            return node.getValue();
        }
        double value;
        if(player==1){
            value = (int) Double.NEGATIVE_INFINITY;
            for(GameState child: node.getChildren()){

                value = Math.max(value, alphaBetaFailHard(child, depth-1, alpha, beta, 0));
                alpha = Math.max(alpha, value);
                if(alpha>=beta){
                    node.removeChildrenAfter(child);
                    node.setValue(alpha);
                    break;
                }
            }
        }else{
            value = (int) Double.POSITIVE_INFINITY;
            for(GameState child: node.getChildren()){

                value = Math.min(value, alphaBetaFailHard(child, depth-1, alpha, beta, 1));
                if(value <=alpha){
                    node.removeChildrenAfter(child);
                    node.setValue(value);
                    break;
                }
                beta = Math.min(beta, value);
            }

        }
        node.setValue(value);
        return value;

    }


}
