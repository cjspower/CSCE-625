package me.jiashi.HW5;

import java.util.*;

/**
 * Created by anderson on 3/10/15.
 */
public abstract class AlphaBetaMinimax {
    /*BLACK is maximizing Player and WHITE is minimizing Player*/
    public static Tuple<Integer,Position> alphaBetaSearch(ChessBoard board, boolean maxPlayer,int alpha,int beta, int depth,boolean show){
        boolean isFinished = false;
        List<Position> validPositionBLACK = Strategy.validPosition(board, Position.BLACK);
        List<Position> validPositionWHITE = Strategy.validPosition(board, Position.WHITE);
        if (validPositionBLACK.isEmpty()&&validPositionWHITE.isEmpty()) isFinished = true;
        if (depth==0){
            return new Tuple<Integer, Position>(board.eval(validPositionBLACK.size(),validPositionWHITE.size()),null);
        }
        if (isFinished){
            int score = board.score();
            if (score>0) return new Tuple<Integer, Position>(Integer.MAX_VALUE,null);
            else if (score<0)return new Tuple<Integer, Position>(Integer.MIN_VALUE,null);
            return new Tuple<Integer, Position>(0,null);
        }
        Position bestPosition=null;
        if (maxPlayer){
            int bestValue = Integer.MIN_VALUE;
            for (Position pos:validPositionBLACK){
                ChessBoard child = board.put(Position.BLACK,pos);
                int temp = alphaBetaSearch(child,false,alpha,beta,depth-1,false).value;
                if (temp>=bestValue){
                    bestValue = temp;
                    bestPosition = pos;
                }
                alpha = Math.max(alpha,bestValue);
                if (beta<=alpha) break;
            }
            return new Tuple<Integer,Position>(bestValue,bestPosition);
        }else {
            int bestValue = Integer.MAX_VALUE;
            for (Position pos:validPositionWHITE){
                ChessBoard child = board.put(Position.WHITE,pos);
                int temp = alphaBetaSearch(child, true, alpha, beta, depth - 1,false).value;
                if(temp<=bestValue){
                    bestValue = temp;
                    bestPosition = pos;
                }
                beta = Math.min(beta, bestValue);
                if (beta<=alpha) break;
            }
            return new Tuple<Integer,Position>(bestValue,bestPosition);
        }
    }
}

