package me.jiashi.HW5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anderson on 3/9/15.
 */
public class Othello {
    public static void main(String[] args) {

        int scale = Integer.parseInt(args[0]);
        //System.out.println(scale);
        int depth = Integer.parseInt(args[1]);
        if (scale%2!=0||scale<4||depth>12){
            System.out.println("#Illegal input, scale must be even and greater or equal to 4. " +
                    "Depth over 10 will cost unreasonable running time.");

            return;
        }
        ChessBoard board=null;
        while (true){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String command = in.readLine();
                String[] str = command.split(" ");
                switch (str.length){
                    case 1:{
                        switch (str[0]){
                            case "init":{
                                board = new ChessBoard(scale);
                                board.init();
                                break;
                            }
                            case "reset":{
                                board = new ChessBoard(scale);
                                break;
                            }
                            case "quit":{
                                return;
                            }
                            default:{
                                System.out.println("#Illegal input");

                                break;
                            }
                        }
                        break;}
                    case 2:{
                        int color;
                        if (board!=null){
                        if (str[0].equalsIgnoreCase("move")){
                            switch (str[1]){
                                case "B": color = Position.BLACK;break;
                                case "W": color = Position.WHITE;break;
                                default: color = -1;
                            }
                            if (color>=0){
                                Tuple<Integer,Position> tuple = AlphaBetaMinimax.alphaBetaSearch(board,color==Position.BLACK,Integer.MIN_VALUE, Integer.MAX_VALUE,depth,true);
                                Position pos = tuple.position;
                                if (tuple.position==null){
                                    System.out.println("forfeit");
                                }else {
                                    System.out.println("(" + pos.x + "," + pos.y + ")");
                                }
                                board = board.put(color,pos);
                            }
                        }}else {
                            System.out.println("#Board should be initialized first.");

                        }
                        break;}

                    case 4:{
                        int color;
                        if (board!=null){
                            switch (str[1]){
                                case "B": color = Position.BLACK;break;
                                case "W": color = Position.WHITE;break;
                                default: color = -1;
                            }
                            //System.out.println(Integer.parseInt(str[2])+", "+Integer.parseInt(str[3]));
                            if (!(Integer.parseInt(str[2])>=0&&Integer.parseInt(str[2])<scale&&Integer.parseInt(str[3])>=0&&Integer.parseInt(str[3])<scale)){
                                System.out.println("#Illegal move");

                                break;
                            }
                            board = board.put(color,board.cord[Integer.parseInt(str[2])][Integer.parseInt(str[3])]);
                        }else {
                            System.out.println("#Board should be initialized first.");

                        }
                        break;}
                    default:{
                        System.out.println("#Illegal input");

                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (board!=null) {

                if (Strategy.validPosition(board, Position.BLACK).isEmpty() && Strategy.validPosition(board, Position.WHITE).isEmpty()) {
                    System.out.println("game over");

                }
                board.print();

            }

        }
    }
}
