package me.jiashi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by anderson on 2/1/15.
 */
public class HomeWork1 {
    public static void main(String[] args){
        String input;
        int method;
        try{
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String command = in.readLine();
                String[] str = command.split(" ");

                switch (str[0]) {
                    case "DFS_nav":
                        method = 0;
                        break;
                    case "BFS_nav":
                        method = 1;
                        break;
                    case "GBFS_nav":
                        method = 2;
                        break;
                    default:
                        System.out.println("unknown command sequence");
                        continue;
                }
                Tree tree = new Tree(str[1]);
                int x1, x2, y1, y2;
                x1 = Integer.parseInt(str[2]);
                y1 = Integer.parseInt(str[3]);
                x2 = Integer.parseInt(str[4]);
                y2 = Integer.parseInt(str[5]);
                tree.search(x1, y1, x2, y2, method);
            }


        }catch (Exception err){
            err.printStackTrace();
        }

    }
}
