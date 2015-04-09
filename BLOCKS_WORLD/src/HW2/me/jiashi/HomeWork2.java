/**
 * Created by anderson on 2/12/15.
 * Contains main method
 */
package HW2.me.jiashi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HomeWork2 {
    public static void main(String[] args) {
        int stackNum = 0;
        int blockNum = 0;
        int mode = -1;
        /**
         * argument (1,2,4,3) for A*
         * try argument (2,3,7,4) and add no depth in h, this algorithm shall be much efficient than A* search,
         * But since it's not A*, I didn't provide this option in my code.
         */

        try {
            System.out.println("BlocksWorld solving program, please enter number of blocks and stacks, split with space.");
            System.out.println("e.g 10 5 means 10 blocks and 5 stacks");
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String command = in.readLine();
                String[] str = command.split(" ");
                if (str.length==2){
                    blockNum = Integer.parseInt(str[0]);
                    stackNum = Integer.parseInt(str[1]);
                    break;
                }else {
                    System.out.println("Please provide a valid input");
                }
            }
            System.out.println("Enter 0 for problem solving, 1 for performance test, 2 for solving with program trace\r\n" +
                    "(do not try input too large numbers in performance test mode, running 12 6 for 500 times would cost about 1 minutes on" +
                    " my laptop," +
                    "I didn't test larger numbers.)");
            while (true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String command = in.readLine();
                mode = Integer.parseInt(command);
                if (!((mode==0)||(mode==1)||(mode==2))){
                    System.out.println("Please enter 0,1 or 2");
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        switch (mode){
            case 0:{
                Game game = new Game(blockNum,stackNum,false);
                game.randomStart(1,2,4,3);
                game.solve();
            }
            case 1:{
                int averageT = 0;
                int averageQ = 0;
                int averageD = 0;
                System.out.println("Running test randomly for 500 times");
                for (int i=0;i<500;i++) {
                    Game game = new Game(blockNum, stackNum, true);
                    game.randomStart(1, 2, 4, 3);
                    averageD = averageD + game.solve();
                    averageT = averageT+game.goalTested;
                    averageQ = averageQ+game.maxQueueSize;
                }
                averageT=averageT/500;
                averageQ=averageQ/500;
                averageD=averageD/500;
                System.out.println("Solving problem BlocksWorld "+blockNum+" "+stackNum+" for 500 times");
                System.out.println("Average_depth="+averageD+" average goal_tested="+averageT+" average max_queue_size="+averageQ);
            }
            case 2:{
                Game game = new Game(blockNum,stackNum,false);
                game.randomStart(1,2,4,3);
                game.programTrace=true;
                game.solve();
            }
            default:break;
        }

//
//        int average = 0;
//        Game game = new Game(10,3);
//        game.randomStart(1,2,3,3);
//        game.solve();
    }
}
