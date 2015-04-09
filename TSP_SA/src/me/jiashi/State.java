package me.jiashi;

import java.util.ArrayList;

/**
 * Created by anderson on 2/15/15.
 */
public class State{
    public ArrayList<City> getRoute() {
        return route;
    }

    ArrayList<City> route;
    int size;

    public double getLength() {
        return length;
    }

    private double length;
    public State(ArrayList<City> inputRoute){
        this.route = inputRoute;
        int size = route.size();
        length = 0;

        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                length = length + calc(route.get(i), route.get(i+1));
            }
            length = length + calc(route.get(size-1), route.get(0));
        }else {
            System.out.println("This list only has one city.");
            length = 0;
        }
    }
    public State(State parentState){

        route = (ArrayList<City>)parentState.getRoute().clone();
        size = route.size();
        int index1 = (int)(size*Math.random());
        int index2 = (int)(size*Math.random());
        while ((Math.abs(index1-index2)<=1)||(Math.abs(index1-index2)==size-1)){
            index2 = (int)(size*Math.random());
        }
        if (index1>index2){
            int p = index1;
            index1 = index2;
            index2 = p;
        }

        int a = index1;
        int b = index2;

        double oLength1, oLength2;
        double newLength1, newLength2;

        if (index1==0){
            oLength1 = calc(route.get(size-1),route.get(0));
            newLength1 = calc(route.get(size-1),route.get(index2));
        }else {
            oLength1 = calc(route.get(index1-1),route.get(index1));
            newLength1 = calc(route.get(index1-1),route.get(index2));
        }

        if (index2==size-1){
            oLength2 = calc(route.get(index2),route.get(0));
            newLength2 = calc(route.get(index1),route.get(0));
        }else {
            oLength2 = calc(route.get(index2),route.get(index2+1));
            newLength2 = calc(route.get(index1),route.get(index2+1));
        }

        while (a < b){
            City r = route.get(a);
            route.set(a,route.get(b));
            route.set(b,r);
            a++;
            b--;

        }

//        length = 0;
//        if (size > 1) {
//            for (int i = 0; i < size - 1; i++) {
//                length = length + calc(route.get(i), route.get(i+1));
//            }
//            length = length + calc(route.get(size-1), route.get(0));
//        }else {
//            System.out.println("This list only has one city.");
//            length = 0;
//        }
        length = parentState.getLength()-oLength1-oLength2+newLength1+newLength2;
    }

    public void print(){
        for (City c: route){
            System.out.println(c.getName());
        }
    }

    public void printLine(){
        for (City c: route){
            System.out.print(c.getName() + " ");
        }
        System.out.println();
    }

    public static double calc(City cityA, City cityB){
        double dlon = cityB.getLongitude() - cityA.getLongitude();
        double dlat = cityB.getLatitude() - cityA.getLatitude();
        double a = Math.pow(Math.sin(dlat / 2),2)
                + Math.cos(cityA.getLatitude())*Math.cos(cityB.getLatitude())*Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = 3961 * c;

        return d;
    }
}
