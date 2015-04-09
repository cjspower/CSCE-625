/**
 * Created by anderson on 2/10/15.
 * Use PriorityQueue as frontier
 * HashMap store the set of States that has been visited or already in the frontier
 * HashMap provide a high query efficiency, key is the string version of state.
 */

package HW2.me.jiashi;
import java.util.*;

public class Game {
    int blockNum;
    int stackNum;
    int maxQueueSize;
    int goalTested;
    public boolean programTrace;
    boolean test;
    State startState;
    PriorityQueue<State> frontier;
    HashMap<String, State> visitedSet;

    public Game(int blockNum, int stackNum, boolean test){
        frontier = new PriorityQueue<State>();
        visitedSet = new HashMap<String, State>();
        this.blockNum=blockNum;
        this.stackNum=stackNum;
        this.test=test;
    }

    public void randomStart(int a,int b,int cc,int d){
        startState = new State(blockNum,stackNum,a,b,cc,d);
        char c = 'A';
        for (int i = 0; i < blockNum; i++) {
            startState.table[0].add(new MyCharacter(c));
            c++;
        }
        randomMove(startState, 5000);
        startState.generateCode();
        startState.computeH(a,b,cc,d);
        if(!test) {
            System.out.println("Random initial state");
            startState.print();
        }
    }

    protected boolean visit(State state){
        if (state.check()) return true;
        state.generateSuccessors();
        return false;
    }

    public int solve(){
        int finalDepth = 0;
        if (!test) {
            System.out.println("Solving");
        }
        visitedSet.put(startState.code, startState);
        State now = startState;
        goalTested=0;
        maxQueueSize=0;
        frontier.add(now);
        while (!frontier.isEmpty()){
            now=frontier.poll();
            if(programTrace) {
                System.out.print("iter="+goalTested+", queue="+frontier.size()+", f="+now.h+", depth="+now.depth+"\r\n");
            }
            goalTested++;
            if (!visit(now)) {
                ListIterator<State> stateListIterator = now.successors.listIterator();
                while (stateListIterator.hasNext()){
                    State s = stateListIterator.next();
                    if (visitedSet.containsKey(s.code)){
                        State sVisit = visitedSet.get(s.code);
                        if (sVisit.depth >= s.depth) {
                            sVisit.depth = s.depth;
                            sVisit.parent = s.parent;
                        }
                        stateListIterator.set(sVisit);
                    }else {
                        visitedSet.put(s.code, s);
                        frontier.offer(s);
                        if(frontier.size()>=maxQueueSize){
                            maxQueueSize=frontier.size();
                        }
                    }
                }
            }else {
                finalDepth = now.depth;
                if(!test) {
                    System.out.println("Success!, depth=" + now.depth + " total_goal_tests=" + goalTested + " max_queue_size=" + maxQueueSize);
                    System.out.println("Solution path:");
                    traceBack(now);
                }
                break;
            }
        }
        return finalDepth;
    }

    public void traceBack(State state){
        if (state.parent==null){
            state.print();
        }else {
            traceBack(state.parent);
            state.print();
        }

    }

    public static void randomMove(State state, int iteration){
        int stackNum = state.getStackNum();

        for (int i = 0; i < iteration; i++) {
            int from = (int) (Math.random() * stackNum);
            if (!state.table[from].isEmpty()) {
                int to = (int) (Math.random() * stackNum);
                int size = state.table[from].size();
                MyCharacter c = state.table[from].get(size - 1);
                state.table[from].remove(size - 1);
                state.table[to].add(c);
            }
        }
    }
}
