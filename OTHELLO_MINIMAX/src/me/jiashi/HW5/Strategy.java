package me.jiashi.HW5;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;
import java.util.Iterator;

/**
 * Created by anderson on 3/10/15.
 */
public abstract class Strategy {
    public static int[][] table8 = {
            {800,-50,5,5,5,5,-50,800},
            {-50,-60,3,3,3,3,-60,-50},
            {5,3,3,1,1,3,3,5},
            {5,3,1,1,1,1,3,5},
            {5,3,1,1,1,1,3,5},
            {5,3,3,1,1,3,3,5},
            {-50,-60,3,3,3,3,-60,-50},
            {800,-50,5,5,5,5,-50,800}};

    public static int[][] table6 = {{60,-40,10,10,-40,60},
            {150,-50,5,5,-50,150},
            {10,5,1,1,5,10},
            {10,5,1,1,5,10},
            {-40,-50,5,5,-50,-40},
            {150,-40,10,10,-40,150}};

    public static int[][] table4 = {{50,50,50,50},{50,50,50,50},{50,50,50,50},{50,50,50,50}};

    public static int[][] table10 = {
            {400,-80,10,10,10,10,10,10,-80,400},
            {-80,-100,3,3,3,3,3,3,-100,-60},
            {5,3,3,1,1,1,1,3,3,5},
            {5,3,1,1,1,1,1,1,3,5},
            {5,3,1,1,1,1,1,1,3,5},
            {5,3,1,1,1,1,1,1,3,5},
            {5,3,1,1,1,1,1,1,3,5},
            {5,3,3,1,1,1,1,3,3,5},
            {-80,-100,3,3,3,3,3,3,-100,-80},
            {400,-80,10,10,10,10,10,10,-80,400}};

    public static int searchXPlus(ChessBoard board, Position testPos, int player){
        int x=testPos.x, y=testPos.y;
        if (x<board.scale-2) {
            x = x + 1;
            boolean xPlus = false;
            while (x < board.scale) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xPlus = true;
                    break;
                } else x++;
            }
            if (xPlus && board.cord[x - 1][y].isOccupied && board.cord[x - 1][y].color != player) {
                return x-testPos.x;     //number of flip including the new plate
            }
        }
        return 0;
    }
    public static int searchXMinus(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (x>1){
            x = x - 1;
            boolean xMinus = false;
            while (x >= 0) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xMinus = true;
                    break;
                } else x--;
            }
            if (xMinus && board.cord[x+1][y].isOccupied&&board.cord[x + 1][y].color != player) {
                return testPos.x-x;
            }
        }
        return 0;
    }
    public static int searchYMinus(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y>1){
            y = y - 1;
            boolean yMinus = false;
            while (y >= 0) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    yMinus = true;
                    break;
                } else y--;
            }
            if (yMinus && board.cord[x][y + 1].isOccupied&&board.cord[x][y + 1].color != player) {
                return testPos.y-y;
            }
        }
        return 0;
    }
    public static int searchYPlus(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y<board.scale-2) {
            y = y + 1;
            boolean yPlus = false;
            while (y < board.scale) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    yPlus = true;
                    break;
                } else y++;
            }
            if (yPlus && board.cord[x][y - 1].isOccupied&&board.cord[x][y - 1].color != player) {
                return y-testPos.y;
            }
        }
        return 0;
    }
    public static int searchXPYP(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y<board.scale-2&&x<board.scale-2){
            x = x + 1;y = y + 1;
            boolean xPlusyPlus = false;
            while (y<board.scale&&x<board.scale) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xPlusyPlus = true;
                    break;
                } else {
                    x++;
                    y++;
                }
            }
            if (xPlusyPlus&&board.cord[x-1][y-1].isOccupied&&board.cord[x-1][y-1].color != player){
                    return x-testPos.x;
            }
        }
        return 0;
    }
    public static int searchXMYM(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y>1&&x>1){
            x = x - 1;y = y - 1;
            boolean xMinusyMinus = false;
            while (y>=0&&x>=0) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xMinusyMinus = true;
                    break;
                } else {
                    x--;
                    y--;
                }
            }
            if (xMinusyMinus&&board.cord[x+1][y+1].isOccupied&&board.cord[x+1][y+1].color != player){
                return testPos.x-x;
            }
        }
        return 0;
    }
    public static int searchXPYM(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y>1&&x<board.scale-2){
            x = x + 1;y = y - 1;
            boolean xPlusyMinus = false;
            while (y>=0&&x<board.scale) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xPlusyMinus = true;
                    break;
                } else {
                    x++;
                    y--;
                }
            }
            if (xPlusyMinus&&board.cord[x-1][y+1].isOccupied&&board.cord[x-1][y+1].color != player){
                return x-testPos.x;
            }
        }
        return 0;
    }
    public static int searchXMYP(ChessBoard board, Position testPos, int player){
        int x=testPos.x,y=testPos.y;
        if (y<board.scale-2&&x>1){
            x = x - 1;y = y + 1;
            boolean xMinusyPlus = false;
            while (y<board.scale&&x>=0) {
                if (!board.cord[x][y].isOccupied) break;
                else if (board.cord[x][y].color == player) {
                    xMinusyPlus = true;
                    break;
                } else {
                    x--;
                    y++;
                }
            }
            if (xMinusyPlus&&board.cord[x+1][y-1].isOccupied&&board.cord[x+1][y-1].color != player){
                return testPos.x-x;
            }
        }
        return 0;
    }

    public static List<Position> validPosition(ChessBoard board, int player){
        Iterator<Position> iterator = board.availableMoveOptions.iterator();
        List<Position> result = new LinkedList<Position>();
        while (iterator.hasNext()){
            Position testPos = iterator.next();
            if (!board.cord[testPos.x][testPos.y].isOccupied) {
                if (searchXPlus(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchXMinus(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchYMinus(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchYPlus(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchXPYP(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchXMYM(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchXPYM(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
                if (searchXMYP(board, testPos, player) != 0) {
                    result.add(testPos);
                    continue;
                }
            }
        }
        return result;
    }
}
