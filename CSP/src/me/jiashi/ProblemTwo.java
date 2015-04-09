package me.jiashi;

import java.util.*;

/**
 * Created by anderson on 15/3/1.
 * -----------------------------------------------------
 * Color  |Yellow |  Blue  |  Red   | Ivory   | Green
 * Nation |Nor    |  Ukr   |  Eng   | Span    | Japan
 * Drink  |Water  |  Tea   |  Milk  | Orange  | Coffee
 * Food   |KitKat |  Hers  |  Smart | Snicker | MilkyWay
 * Pet    |Fox    |  Horse |  Snail | Dog     | Zebra
 * -----------------------------------------------------
 * • The Englishman lives in the red house    (Color[i]=="red")<-->(Nation[i]=="English") ARC
 * • The Spaniard owns the dog  ARC
 *        • The Norwegian lives in the first house on the left  NODE
 * • The green house is immediately to the right of the ivory house    NODE/ARC
 * • The man who eats Hershey bars lives in the house next to the man with the fox  NODE/ARC
 * • Kits Kats are eaten in the yellow house    ARC
 *        • The Norwegian lives next to the blue house  NODE/ARC
 * • The Smarties eater owns snails ARC
 * • The Snickers eater drinks orange juice ARC
 * • The Ukranian drinks tea    ARC
 * • The Japanese person eats Milky Ways    ARC
 * • Kit Kats are eaten in a house next to the house where the horse is kept    NODE/ARC
 * • Coffee is drunk in the green house ARC
 *       • Milk is drunk in the middle house    NODE
 */

public class ProblemTwo implements Defination{
    Variable[][] table;
    ConstraintGroup constraintGroup;

    public ProblemTwo(){
        table = new Variable[5][5];
        for (int i=COLOR;i<=NATION;i++){
            for (int j=0;j<5;j++){
                table[i][j] = new Variable(i,j);
            }
        }
        constraintGroup = new ConstraintGroup();
    }

    public void solve(){
        System.out.println("Solving Problem Two Using BackTracking With Advanced heuristic");
        System.out.println("=======================");
        constraintGroup.nodeDomainReduce(table);
        Stack<State> frontier = new Stack<State>();
        State initialState = new State(table);
        State state = null;
        frontier.push(initialState);
        int iter = 0;
        while (!frontier.isEmpty()){
            state = frontier.pop();
            if (state.onOperate.iterator.hasNext()){
                Integer select = (Integer)state.onOperate.iterator.next();
                frontier.push(state);
                state = new State(state,select);
                if(constraintGroup.constraintCheck(state)){
                    if (state.complete) {
                        break;
                    }
                    if (constraintGroup.domainReduce(state)) {
                        state = new State(state.table);
                        frontier.push(state);
                    }
                }else {
                    state.complete = false;
                }
            }
            iter++;
        }

        System.out.println("Total iteration "+iter+".");


        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                System.out.print(String.format("%-12s",def[state.table[i][j].domains.iterator().next()]));
            }
            System.out.println();
        }
        System.out.println();

    }
    public void solveBackTrack(){
        System.out.println("Solving Problem Two Using BackTracking");
        System.out.println("=======================");

        Stack<State> frontier = new Stack<State>();
        State initialState = new State(table);
        State state = null;
        frontier.push(initialState);
        int iter = 0;
        while (!frontier.isEmpty()){
            state = frontier.pop();
            if (state.onOperate.iterator.hasNext()){
                Integer select = (Integer)state.onOperate.iterator.next();
                frontier.push(state);
                state = new State(state,select);
                boolean val = true;
                for (int i=0;i<5;i++){
                    for (int j=0;j<5;j++){
                        if(state.table[i][j].visited) val = val&&constraintGroup.constraintCheck(i,j,state.table);
                    }
                }
                if(val){
                    if (state.complete) {
                        break;
                    }
                    state = new State(state.table,true);//this construct function for non-MRV implement
                    frontier.push(state);
                }else {
                    state.complete = false;
                }
            }
            iter++;
        }

        System.out.println("Total iteration "+iter+".");


        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                System.out.print(String.format("%-12s",def[state.table[i][j].domains.iterator().next()]));
            }
            System.out.println();
        }
        System.out.println();
    }

}
class State{
    public Variable[][] table;
    public Variable onOperate;
    public boolean complete;

    //this cope construct function is for Non-MRV implement
    public State(Variable[][] old, boolean bool){
        complete = false;
        int minSize = 6;
        table = new Variable[5][5];
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                table[i][j] = new Variable(old[i][j]);
                if (!table[i][j].visited){
                    onOperate = table[i][j];
                }
            }
        }
        onOperate.iterator = onOperate.domains.iterator();
        onOperate.visited = true;
    }
    //find the MRV after forward checking.
    public State(Variable[][] old){
        complete = false;
        int minSize = 6;
        table = new Variable[5][5];
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                table[i][j] = new Variable(old[i][j]);
                if (table[i][j].domains.size()<minSize&&!table[i][j].visited){
                    minSize = table[i][j].domains.size();
                    onOperate = table[i][j];
                }
            }
        }
        onOperate.iterator = onOperate.domains.iterator();
        onOperate.visited = true;
    }
    //select one valid domain in the variable on operate
    public State(State old, Integer select){
        complete = true;
        Variable onOperate = old.onOperate;
        int type = onOperate.type;
        int position = onOperate.position;
        onOperate.visited=true;
        table = new Variable[5][5];
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                table[i][j] = new Variable(old.table[i][j]);
                complete = complete&&table[i][j].visited;
            }
        }
        table[type][position].domains.clear();
        table[type][position].domains.add(select);
        this.onOperate = table[type][position];
        this.onOperate.visited = true;
        this.onOperate.iterator = onOperate.domains.iterator();
    }
}

abstract class Constraint{
    public abstract int[][] belongsTo();
    public abstract int[] classification();
    public abstract boolean deleteInConsistency(int type, int position, Variable[][] table);
    public abstract boolean checkConsistency(int type, int position, Variable[][] table);
}

/**
 *                offset
 *           1       2       3
 *  type
 *  type    value
 *  type
 *
 */
class ALLDIFF extends Constraint implements Defination{
    public int[][] belongsTo(){
        int[][] belong = {{COLOR,-1},{NATION,-1},{DRINK,-1},{FOOD,-1},{PET,-1}};
        return belong;
    }
    public int[] classification(){
        int[] classificaton = {ALLDIFF};
        return classificaton;
    }


    public boolean deleteInConsistency(int type, int position, Variable[][] table){
        Integer integer = table[type][position].domains.iterator().next();
        for (int i=0;i<5;i++){
            if (i!=position){
                table[type][i].domains.remove(integer);
                if (table[type][i].domains.isEmpty()) return false;
            }

        }
        return true;
    }

    public boolean checkConsistency(int type, int position, Variable[][] table){
        Integer integer = table[type][position].domains.iterator().next();
        for (int i=0;i<5;i++){
            if (i!=position){
                if (table[type][i].domains.remove(integer)){
                    if (table[type][i].domains.isEmpty()) return false;
                    else table[type][i].domains.add(integer);
                }else {
                    return true;
                }
            }

        }
        return true;
    }
}

class ARC extends Constraint implements Defination{
    int type1,type2,value1,value2,offset;
    public ARC(int type1,int value1,int type2,int value2, int offset){
        this.type1 = type1;
        this.value1 = value1;
        this.type2 = type2;
        this.value2 = value2;
        this.offset = offset;
    }

    public int[] classification(){
        int[] classificaton = {type1,type2};
        return classificaton;
    }
    public int[][] belongsTo(){
        int[][] belong = {{type1,value1},{type2,value2}};
        return belong;
    }
    public boolean deleteInConsistency(int type, int position, Variable[][] table) {
        if (type1 != type2) {
            if (type == type1) {
                if (position+offset<5) {
                    HashSet<Integer> valueTable = table[type2][position + offset].domains;
                    if (table[type1][position].domains.contains(value1)) {
                        if (!valueTable.contains(value2)) return false;
                        valueTable.clear();
                        valueTable.add(value2);
                        return true;
                    } else {
                        valueTable.remove(value2);
                        return !valueTable.isEmpty();
                    }
                }else {
                    if (table[type1][position].domains.contains(value1)) return false;
                    else return true;
                }
            } else {
                if (position-offset>=0) {
                    HashSet<Integer> valueTable = table[type1][position - offset].domains;
                    if (table[type2][position].domains.contains(value2)) {
                        if (!valueTable.contains(value1)) return false;
                        valueTable.clear();
                        valueTable.add(value1);
                        return true;
                    } else {
                        valueTable.remove(value1);
                        return !valueTable.isEmpty();
                    }
                }else {
                    if (table[type2][position].domains.contains(value2)) return false;
                    else return true;
                }
            }
        }else {
            if (table[type1][position].domains.contains(value1)){
                if (position+offset<5){
                    HashSet<Integer> valueTable = table[type1][position + offset].domains;
                    if (!valueTable.contains(value2)) return false;
                    valueTable.clear();
                    valueTable.add(value2);
                }else return false;
            }else if (table[type1][position].domains.contains(value2)){
                if (position-offset>=0){
                    HashSet<Integer> valueTable = table[type1][position - offset].domains;
                    if (!valueTable.contains(value1)) return false;
                    valueTable.clear();
                    valueTable.add(value1);
                }else return false;
            }else {
                if (position+offset<5){
                    table[type1][position+offset].domains.remove(value2);
                    if (table[type1][position+offset].domains.isEmpty()) return false;
                }
                if (position - offset >= 0) {
                    table[type1][position-offset].domains.remove(value1);
                    if (table[type1][position-offset].domains.isEmpty()) return false;
                }
            }
        }
        return true;
    }

    public boolean checkConsistency(int type, int position, Variable[][] table){
        if (table[type][position].domains.contains(value1)){
            if (position+offset>4) return false;
            else {
                if (table[type2][position+offset].domains.contains(value2)) return true;
                else return false;
            }
        }else if (table[type][position].domains.contains(value2)){
            if (position-offset<0) return false;
            else {
                if (table[type1][position-offset].domains.contains(value1)) return true;
                else return false;
            }
        }else return true;
    }

}

class ORARC extends Constraint implements Defination{
    int type1,type2,value1,value2;
    public ORARC(int type1,int value1,int type2,int value2){
        this.type1 = type1;
        this.value1 = value1;
        this.type2 = type2;
        this.value2 = value2;
    }

    public int[] classification(){
        int[] classificaton = {type1,type2};
        return classificaton;
    }

    public int[][] belongsTo(){
        int[][] belong = {{type1,value1},{type2,value2}};
        return belong;
    }

    public boolean checkConsistency(int type, int position, Variable[][] table){
        if (position+1==5){
            if (table[type][position].domains.contains(value1)){
                if (table[type2][position-1].domains.contains(value2)) return true;
                else return false;
            }else if (table[type][position].domains.contains(value2)){
                if (table[type1][position-1].domains.contains(value1)) return true;
                else return false;
            }else {
                return true;
            }
        }else if (position==0){
            if (table[type][position].domains.contains(value1)){
                if (table[type2][position+1].domains.contains(value2)) return true;
                else return false;
            }else if (table[type][position].domains.contains(value2)){
                if (table[type1][position+1].domains.contains(value1)) return true;
                else return false;
            }else {
                return true;
            }
        }else {
            if (table[type][position].domains.contains(value1)){
                if (table[type2][position+1].domains.contains(value2)||table[type2][position-1].domains.contains(value2)) return true;
                else return false;
            }else if (table[type][position].domains.contains(value2)){
                if (table[type1][position+1].domains.contains(value1)||table[type1][position-1].domains.contains(value1)) return true;
                else return false;
            }else {
                return true;
            }
        }
    }

    public boolean deleteInConsistency(int type, int position, Variable[][] table){
        if (type1 != type2){
            if (type == type1){
                if (table[type1][position].domains.contains(value1)) {
                    for (int i = 0; i < 5; i++) {
                        if (!(i == (position - 1) || (i == (position + 1)))) {
                            table[type2][i].domains.remove(value2);
                            if (table[type2][i].domains.isEmpty()) return false;
                        }
                    }
                }
            }else {
                if (table[type2][position].domains.contains(value2)) {
                    for (int i = 0; i < 5; i++) {
                        if (!(i == (position - 1) || (i == (position + 1)))) {
                            table[type1][i].domains.remove(value1);
                            if (table[type1][i].domains.isEmpty()) return false;
                        }
                    }
                }
            }
        }else {
            if (table[type][position].domains.contains(value1)){
                for (int i = 0; i < 5; i++) {
                    if (!(i == (position - 1) || (i == (position + 1)))) {
                        table[type][i].domains.remove(value2);
                        if (table[type][i].domains.isEmpty()) return false;
                    }
                }
            }else if (table[type][position].domains.contains(value2)){
                for (int i = 0; i < 5; i++) {
                    if (!(i == (position - 1) || (i == (position + 1)))) {
                        table[type][i].domains.remove(value1);
                        if (table[type][i].domains.isEmpty()) return false;
                    }
                }
            }else {
                return true;
            }
        }
        return true;
    }
}

class NODE extends Constraint implements Defination{
    int type, value, num;
    public int[] classification(){
        int[] classificaton = {NODE};
        return classificaton;
    }

    public NODE(int type, int value, int num){
        this.type = type;
        this.value = value;
        this.num = num;
    }
    public int[][] belongsTo(){
        int[][] belong = {{type,value}};
        return belong;
    }
    public boolean deleteInConsistency(int type, int position,Variable[][] table){
        if (!table[this.type][this.num].domains.contains(value)) return false;
        table[this.type][this.num].domains.clear();
        table[this.type][this.num].domains.add(value);
        return true;
    }
    public boolean checkConsistency(int type, int position, Variable[][] table){
        if (type==this.type&&position==num){
            return table[type][position].domains.contains(value);
        }else if (table[type][position].domains.contains(value)){
            return type==this.type&&position==num;
        }else return true;
    }
}

class ConstraintGroup implements Defination{
    ArrayList<Constraint> constraints;
    LinkedList<Constraint>[] typeList;
    LinkedList<Constraint> nodeList;
    ALLDIFF alldiff;

    public ConstraintGroup(){
        constraints = new ArrayList<Constraint>();
        alldiff = new ALLDIFF();
        constraints.add(new ARC(NATION,Englishman,COLOR,Red,0));
        constraints.add(new ARC(NATION,Spaniard,PET,Dog,0));
        constraints.add(new NODE(NATION,Norwegian,0));
        constraints.add(new ARC(COLOR,Ivory,COLOR,Green,1));
        constraints.add(new ORARC(FOOD,HersHey,PET,Fox));
        constraints.add(new ARC(FOOD,KitKat,COLOR,Yellow,0));
        constraints.add(new ORARC(NATION,Norwegian,COLOR,Blue));
        constraints.add(new ARC(FOOD,Smarties,PET,Snail,0));
        constraints.add(new ARC(FOOD,Snickers,DRINK,OrangeJuice,0));
        constraints.add(new ARC(NATION,Ukranian,DRINK,Tea,0));
        constraints.add(new ARC(NATION,Japanese,FOOD,MilkyWay,0));
        constraints.add(new ORARC(FOOD,KitKat,PET,Horse));
        constraints.add(new ARC(DRINK,Coffee,COLOR,Green,0));
        constraints.add(new NODE(DRINK,Milk,2));

        typeList = new LinkedList[5];
        for (int i = 0; i < 5; i++) {
            typeList[i] = new LinkedList<Constraint>();
        }
        nodeList = new LinkedList<Constraint>();
        for (Constraint constraint:constraints){
            int[] classification = constraint.classification();
            for (int type:classification){
                if (type!=NODE){
                    typeList[type].add(constraint);
                }else {
                    nodeList.add(constraint);
                }
            }
        }
    }

    public boolean constraintCheck(State state){
        int type = state.onOperate.type;
        int position = state.onOperate.position;
        Variable[][] table = state.table;

        if (!alldiff.checkConsistency(type,position,table)) return false;
        for (Constraint constraint:nodeList){
            if(!constraint.checkConsistency(type,position,table)){
                return false;
            }
        }

        for (Constraint constraint:typeList[type]){
            if (!constraint.checkConsistency(type,position,table)){
                return false;
            }
        }
        return true;
    }

    public boolean constraintCheck(int type, int position, Variable[][] table){

        if (!alldiff.checkConsistency(type,position,table)) return false;
        for (Constraint constraint:nodeList){
            if(!constraint.checkConsistency(type,position,table)){
                return false;
            }
        }
        for (Constraint constraint:typeList[type]){
            if (!constraint.checkConsistency(type,position,table)){
                return false;
            }
        }
        return true;
    }

    public boolean nodeDomainReduce(Variable[][] table){
        for (Constraint constraint:nodeList){
            if(!constraint.deleteInConsistency(0, 0, table)){
                return false;
            }
        }
        return true;
    }

    public boolean domainReduce(State state){
        int type = state.onOperate.type;
        int position = state.onOperate.position;
        Variable[][] table = state.table;

        if (!alldiff.deleteInConsistency(type, position, table)) return false;

        for (Constraint constraint:typeList[type]){
            if (!constraint.deleteInConsistency(type, position, table)){
                return false;
            }
        }
        return true;
    }

    public boolean domainReduce(int type, int position, Variable[][] table){
        if (!alldiff.deleteInConsistency(type, position, table)) return false;

        for (Constraint constraint:typeList[type]){
            if (!constraint.deleteInConsistency(type, position, table)){
                return false;
            }
        }
        return true;
    }

}

class Variable implements Defination{
    public int position;
    public Iterator iterator;
    public HashSet<Integer> domains;
    public int type;
    public boolean visited;
    public Variable(Variable old){
        position = old.position;
        type = old.type;
        iterator = old.iterator;
        domains = (HashSet<Integer>)old.domains.clone();
        visited = old.visited;
    }

    public Variable(int type,int i){
        visited = false;
        position = i;
        domains = new HashSet<Integer>();
        this.type = type;
        switch (type){
            case 0:{
                domains.add(Yellow);
                domains.add(Blue);
                domains.add(Red);
                domains.add(Ivory);
                domains.add(Green);
                break;
            }
            case 1:{
                domains.add(Fox);
                domains.add(Horse);
                domains.add(Dog);
                domains.add(Zebra);
                domains.add(Snail);
                break;
            }
            case 2:{
                domains.add(KitKat);
                domains.add(Smarties);
                domains.add(Snickers);
                domains.add(MilkyWay);
                domains.add(HersHey);
                break;
            }
            case 3:{
                domains.add(Milk);
                domains.add(Tea);
                domains.add(OrangeJuice);
                domains.add(Water);
                domains.add(Coffee);
                break;
            }
            case 4:{
                domains.add(Englishman);
                domains.add(Norwegian);
                domains.add(Spaniard);
                domains.add(Ukranian);
                domains.add(Japanese);
                break;
            }
        }
    }
}