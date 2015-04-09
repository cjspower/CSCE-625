package me.jiashi;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by anderson on 2/15/15.
 */
public class Tsp {
    private ArrayList<City> cityList;
    public Tsp(String fileName){
        try {
            File file = new File(fileName);
            if (file.isFile()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader buffer = new BufferedReader(read);
                cityList = new ArrayList<City>();
                String lineText;
                while ((lineText = buffer.readLine())!=null) {
                    String[] cityInfo = lineText.split("\\s+");
                    City city = new City(cityInfo[0], Double.parseDouble(cityInfo[1]), Double.parseDouble(cityInfo[2]));
                    cityList.add(city);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found, please check filename.");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("Read Error.");
            e.printStackTrace();
        }
    }

    public ArrayList<City> calculate(double temp, int target){
        LinkedList<Double> linkedList;
        linkedList = new LinkedList<Double>();
        ArrayList<City> route;
        route = (ArrayList<City>)cityList.clone();
        Collections.shuffle(route);
        State state = new State(route);
        State successor;
        double dE;
        System.out.println("initial state, tour length=" + state.getLength() + " miles");
        state.printLine();
        double t;
        int iter = 0;
        while (iter<target){
            t = 2*temp/(1+Math.exp(iter/(target/8.0)));
            //t=temp-iter*temp/target;
            linkedList.add(state.getLength());
            successor = new State(state);
            dE = successor.getLength() - state.getLength();
            if ((iter<=5) || (iter >= target - 10)) {
                System.out.print("iter=" + iter + " len=" + state.getLength()
                        + " newlen=" + successor.getLength() + " delta=" + dE
                        + " temp=" + t);
            }
            //System.out.println(state.getLength());
            //System.out.println(successor.getLength());
            //state.print();
            //System.out.println();
            //successor.print();
            if (dE<0){
                state = successor;
                if ((iter<=5) || (iter >= target - 10)) {
                    System.out.println("update");
                    state.printLine();
                }
            }else {
                if ((iter<=5) || (iter >= target - 10)) {
                    System.out.println(" p<" + Math.exp(-dE / t));
                }
                if (Math.random()<Math.exp(-dE/t)){
                    state = successor;
                    if ((iter<=5) || (iter >= target - 10)) {
                        System.out.println("update");
                        state.printLine();
                    }
                }
            }
//            if (iter%10000000 == 0){
//                System.out.println(state.getLength());
//                System.out.println("iter: "+iter);
//            }
            iter++;
        }

        System.out.println("End ");
        System.out.println("Total length="+state.getLength());
        state.printLine();
        state.print();
        System.out.println(state.getLength());

        /**
         * Write to route length text file for plotting
         */
        System.out.println(linkedList.size());
        try {
            File file = new File("plot.txt");
            if (file.exists()){
                System.out.println("file exist, delete the original file.");
                file.delete();
            }
            System.out.println("create new file: plot.txt.");
            file.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            Iterator iterator = linkedList.iterator();
            int ii = 0;
            int jj = 0;
            while (iterator.hasNext())
            {
                Double l = (Double) iterator.next();

                output.write(l + " ");

                ii++;
                jj++;
            }
            System.out.println("created."+ii+" "+jj);
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }


}
