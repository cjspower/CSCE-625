package me.jiashi;

import java.util.*;

/**
 * Created by anderson on 3/23/15.
 */
public class CNF {
    ArrayList<Clause> clauses;

    public CNF(ArrayList<Clause> clauses){
        this.clauses = new ArrayList<Clause>();
        for (Clause i: clauses) this.clauses.add(i);
    }
}

class Clause implements Comparable<Clause>{
    HashMap<String, Boolean> table;
    public int depth;
    public int index;
    public int parent1 = -1, parent2 = -1;
    public Clause(String input){
        table = new HashMap<String, Boolean>();
        String[] keys = input.split(" ");
        for (String key :keys){
            if (key.charAt(0)=='-') table.put(key.substring(1),false);
            else table.put(key,true);
        }
    }

    public Clause(ClausePair pair) {
        table = new HashMap<String, Boolean>();
        table.putAll(pair.pair[0].table);
        table.putAll(pair.pair[1].table);
        for (String key: pair.duplicate){
            table.remove(key);
        }
        parent1 = pair.index1;
        parent2 = pair.index2;
    }

    @Override
    public int compareTo(Clause clause){
       if (this.table.size()>clause.table.size()) return 1;
       else if (this.table.size()==clause.table.size()) return 0;
       else return -1;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Clause)) return false;
        Clause obj = (Clause)object;
        if (obj.table.size()!=this.table.size()) return false;

        for (Map.Entry<String,Boolean> entry: obj.table.entrySet()){
            if (table.containsKey(entry.getKey())){
                if (!table.get(entry.getKey()).equals(entry.getValue())) return false;
            }else return false;
        }

        return true;
    }
}

