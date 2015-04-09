package me.jiashi;

import me.jiashi.Tsp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HomeWork3 {

    public static void main(String[] args) {
        System.out.println("Please enter total iterations and start temperature(split by space)," +
                "leave them all blank for default.(1,000,000 500)"+" Reasonable total iteration should be less than 500,000,000");
        System.out.println("e.g 1000000 500");
        double startTemp;
        int totalIteration;

        while (true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String command = in.readLine();
                String[] str = command.split(" ");
                if (str.length==2){
                    totalIteration = Integer.parseInt(str[0]);
                    startTemp = Double.parseDouble(str[1]);
                    break;
                }else {
                    totalIteration = 1000000;
                    startTemp = 500;
                    break;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsp problem = new Tsp("texas-cities.dat");
        problem.calculate(startTemp, totalIteration);
    }
}
