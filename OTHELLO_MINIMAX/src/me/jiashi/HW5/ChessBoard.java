package me.jiashi.HW5;

import javafx.geometry.Pos;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by anderson on 3/10/15.
 */
public class ChessBoard {
    public int scale;
    public int fill;
    public Position[][] cord;
    public int occupiedStatus;
    public HashSet<Position> availableMoveOptions;
    public ArrayList<Position>[] playerOccupied;
    public ChessBoard(int scale){
        this.scale = scale;
        cord = new Position[scale][scale];
        for (int i=0;i<scale;i++) for (int j=0;j<scale;j++){
            cord[i][j]=new Position(i,j);
        }
        occupiedStatus = 0;
        availableMoveOptions = new HashSet<Position>(scale*scale);
        playerOccupied = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            playerOccupied[i] = new ArrayList<Position>();
        }
        fill = 0;
    }

    public void print(){
        for (int i=0;i<scale;i++){
            System.out.printf(String.format("%-2c", '#'));
            for (int j=0;j<scale;j++){
                if (!cord[i][j].isOccupied) System.out.print(String.format("%-2c", '.'));
                else switch (cord[i][j].color){
                    case Position.BLACK: System.out.print(String.format("%-2c", 'B'));break;
                    case Position.WHITE: System.out.printf(String.format("%-2c", 'W'));break;
                }
            }
            System.out.println();
        }
    }

    public ChessBoard(ChessBoard old){
        occupiedStatus = old.occupiedStatus + 1;
        scale = old.scale;
        cord = new Position[scale][];
        for (int i=0;i<scale;i++){
            cord[i] = old.cord[i].clone();
        }
        playerOccupied = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            playerOccupied[i] = new ArrayList<Position>();
        }
        for (int i=0;i<scale;i++) for (int j=0;j<scale;j++){
            if (cord[i][j].isOccupied){
                switch (cord[i][j].color){
                    case Position.WHITE: playerOccupied[Position.WHITE].add(cord[i][j]);break;
                    case Position.BLACK: playerOccupied[Position.BLACK].add(cord[i][j]);break;
                    default:break;
                }
            }
        }
        availableMoveOptions = (HashSet<Position>)old.availableMoveOptions.clone();
        fill = old.fill;
    }

    /**
     * Whether the game is over
     * @return
     */
    public int score(){
        int result = 0;
        for (int i=0;i<scale;i++) for (int j=0;j<scale;j++){
            if (cord[i][j].isOccupied){
                switch (cord[i][j].color){
                    case Position.BLACK: result++;break;
                    case Position.WHITE: result--;break;
                }
            }
        }
        return result;
    }

    /**
     * Evaluate the current situation
     * @return
     */
    public int eval(int sizeB,int sizeW){
        int[][] table;
        int eval = 0;
        if(scale<=10) {
            switch (scale) {
                case 8: {
                    if (fill <= 20) table = Strategy.table8;
                    else table = Strategy.table8;
                }
                break;
                case 6:
                    table = Strategy.table6;
                    break;
                case 4:
                    table = Strategy.table4;
                    break;
                case 10:
                    table = Strategy.table10;
                    break;
                default:
                    table = new int[2][2];
            }
            for (int i = 0; i < scale; i++)
                for (int j = 0; j < scale; j++) {
                    if (cord[i][j].isOccupied) {
                        switch (cord[i][j].color) {
                            case Position.BLACK:
                                eval = eval + table[i][j];
                                break;
                            case Position.WHITE:
                                eval = eval - table[i][j];
                                break;
                        }
                    }
                }
            if (fill < scale * scale / 4) {
                eval = eval + 5 * (sizeB - sizeW);
            } else {
                eval = eval + 10 * (sizeB - sizeW);
            }
        }else {
            for (int i=0;i<scale;i++) for (int j=0;j<scale;j++){
                if (cord[i][j].isOccupied){
                    switch (cord[i][j].color) {
                        case Position.BLACK:
                            eval++;
                            break;
                        case Position.WHITE:
                            eval--;
                            break;
                    }
                }
            }
       }
        return eval;
    }

    public boolean init(){
        if (scale%2==1){System.out.println("#fail to init");return false;}
        int x = scale/2;
        int y = x-1;
        fill = 4;
        cord[y][y] = new Position(y,y,Position.WHITE);
        cord[x][x] = new Position(x,x,Position.WHITE);
        cord[x][y] = new Position(x,y,Position.BLACK);
        cord[y][x] = new Position(y,x,Position.BLACK);
        for (int i=x-2;i<x+2;i++){
            availableMoveOptions.add(cord[x-2][i]);
            availableMoveOptions.add(cord[x+1][i]);
        }
        for (int i=x-1;i<x+1;i++){
            availableMoveOptions.add(cord[i][x-2]);
            availableMoveOptions.add(cord[i][x+1]);
        }
        return true;
    }

    public ChessBoard put(int type, Position pos){
        ChessBoard newboard = new ChessBoard(this);
        if (pos == null){
            return newboard;
        }
        int x=pos.x,y=pos.y;
        boolean valid = false;
        int temp = Strategy.searchXPlus(newboard,pos,type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x+i][y] = new Position(x+i,y,type);
            }
        }
        temp = Strategy.searchXMinus(newboard,pos,type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x-i][y] = new Position(x-i,y,type);
            }
        }
        temp = Strategy.searchYMinus(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x][y-i] = new Position(x,y-i,type);
            }
        }
        temp = Strategy.searchYPlus(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x][y+i] = new Position(x,y+i,type);
            }
        }

        temp = Strategy.searchXPYP(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x+i][y+i] = new Position(x+i,y+i,type);
            }
        }

        temp = Strategy.searchXMYM(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x-i][y-i] = new Position(x-i,y-i,type);
            }
        }

        temp = Strategy.searchXMYP(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x-i][y+i] = new Position(x-i,y+i,type);
            }
        }

        temp = Strategy.searchXPYM(newboard, pos, type);
        if (temp!=0){
            valid = true;
            for (int i=0;i<temp;i++){
                newboard.cord[x+i][y-i] = new Position(x+i,y-i,type);
            }
        }
        if (!valid){
            System.out.println("#Invalid Move");

            return this;
        }else {
            newboard.availableMoveOptions.remove(pos);
            for (int i=-1;i<=1;i++){
                for (int j=-1;j<=1;j++){
                    int xx = x+i,yy=y+j;
                    if (xx>=0&&xx<scale&&yy>=0&&yy<scale&&(i!=0||j!=0)){
                        newboard.availableMoveOptions.add(new Position(xx,yy));
                    }
                }
            }
            newboard.fill++;
            return newboard;
        }
    }
}
