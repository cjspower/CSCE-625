package me.jiashi;

import java.util.ArrayList;

/**
 * Created by anderson on 3/24/15.
 */
public class ClausePair implements Comparable<ClausePair>{
    public Clause[] pair;
    ArrayList<String> duplicate;
    int index1, index2;
    public ClausePair(Clause clause1, Clause clause2, ArrayList<String> duplicate, int index1, int index2){
        pair = new Clause[2];
        this.index1 = index1;
        this.index2 = index2;
        pair[0] = clause1;
        pair[1] = clause2;
        this.duplicate = duplicate;
    }

    @Override
    public int compareTo(ClausePair clauses){
        int size = clauses.pair[0].table.size() + clauses.pair[1].table.size() - clauses.duplicate.size()*2;
        int thisSize = pair[0].table.size() + pair[1].table.size() - duplicate.size()*2;
        if (thisSize > size) return 1;
        else if (thisSize == size) return 0;
        else return -1;
    }
}
