/**
 * Created by anderson on 2/10/15.
 * New state created while discovered, merge with exist one when find duplicate in visitedSet(HashMap)
 */
package HW2.me.jiashi;

import java.util.ArrayList;

/**
 * Created by anderson on 2/2/15.
 */
public class State implements Comparable<State>{
    public ArrayList<MyCharacter>[] table;

    public ArrayList<State> successors;
    public int h;
    public int depth;
    public State parent;
    public String code;
    public int a,b,c,d;


    public void generateCode(){
        code = "";
        for (int i=0;i<stackNum;i++){
            code=code+(i+1);
            for (int j=0; j<table[i].size();j++){
                code = code + table[i].get(j).c;
            }
        }
    }

    public int getBlockNum() {
        return blockNum;
    }
    public int getStackNum() {
        return stackNum;
    }
    public ArrayList<MyCharacter>[] getTable() {
        return table;
    }

    private int blockNum, stackNum;

    /**
     *
     * @param blockNum
     * @param stackNum
     */
    public State(int blockNum, int stackNum,int a,int b,int c,int d){
        this.blockNum=blockNum;
        this.stackNum=stackNum;
        depth=0;
        table = new ArrayList[stackNum];
        h=0;

        for (int i=0;i<stackNum;i++){
            table[i]=new ArrayList<MyCharacter>();
        }
        parent=null;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
    }

    public State(State state){
        blockNum = state.getBlockNum();
        stackNum = state.getStackNum();
        ArrayList<MyCharacter>[] tempTable = state.getTable();
        table = new ArrayList[state.getStackNum()];
        for (int i=0;i<stackNum;i++){
            table[i] = new ArrayList<MyCharacter>();
            int ii = 0;
            for (MyCharacter c: tempTable[i]){
                table[i].add(ii,c);
                ii++;
            }
        }
        this.a=state.a;
        this.b=state.b;
        this.c=state.c;
        this.d=state.d;
    }

    public void computeH(int a,int b,int c,int d){
        int isip=0; //in stack in position but characters under that element is wrong.
        int isop=0; //in stack out of position.
        int osor=0; //out of stack out of order.
        int osir=0; //out of stack in order.
        boolean orderflag=true;

        for (int i=0;i<table[0].size();i++){
            if (table[0].get(i).toInt()!=i){
                orderflag=false;
                isop++;
            }else{
                if (!orderflag){
                    isip++;
                }
            }
        }

        for (int i=1;i<stackNum;i++){
            if (!table[i].isEmpty()) {
                int size = table[i].size();
                if (size == 1) {
                    osir++;
                }else {
                    osir++;
                    for (int j=0;j<size-1;j++){
                        if (table[i].get(j).toInt()<table[i].get(j+1).toInt()){
                            osor++;
                        }else {
                            osir++;
                        }
                    }
                }
            }
        }
        h=depth+a*osir+b*osor+c*isop+d*isip;//A*
        //h=2*osir+3*osor+7*isop+4*isip;//Greedy
        //h=depth;//BFS
    }

    public void generateSuccessors(){
        successors = new ArrayList<State>();
        for (int i=0;i<stackNum;i++){
            if (!table[i].isEmpty()) {
                for (int j = 0; j < stackNum; j++) {
                    if (i != j) {
                        State successor = new State(this);
                        successor.parent = this;
                        successor.depth = this.depth + 1;
                        int size = table[i].size();
                        successor.table[j].add(successor.table[i].get(size - 1));
                        successor.table[i].remove(size - 1);
                        successor.computeH(a,b,c,d);
                        successor.generateCode();
                        successors.add(successor);
                    }
                }
            }
        }
    }

    protected boolean check(){
        if(table[0].size()!=blockNum) return false;
        char c = 'A';
        for (int i=0;i<blockNum;i++){
            if (!table[0].get(i).is(c)){
                return false;
            }
            c++;
        }
        return true;
    }

    public void print(){
        for (int i=0;i<stackNum;i++){
            System.out.print((i+1)+" |");
            for (MyCharacter myCharacter: table[i]){
                System.out.print(" "+myCharacter.print());
            }
            System.out.print("\r\n");
        }
        System.out.println("");
    }

    @Override
    public int compareTo(State state) {

        if (state!=null) {
            if (this.h > state.h)
                return 1;
            else if (this.h < state.h)
                return -1;
        }
        return 0;
    }

}
