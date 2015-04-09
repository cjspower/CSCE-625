package me.jiashi;

import me.jiashi.ClausePair;

import java.util.*;

/**
 * Created by anderson on 3/23/15.
 */
public abstract class CNFSolver {
    public static boolean Solve(CNF inputCNF){
        ArrayList<Clause> list = new ArrayList<Clause>();
        PriorityQueue<ClausePair> queue = new PriorityQueue<ClausePair>();
        System.out.println("Initial clauses: ");
        for (Clause clause:inputCNF.clauses){
            System.out.print(list.size() + ": ");
            System.out.print("(");
            for (Map.Entry<String,Boolean> entry: clause.table.entrySet()){
                if (!entry.getValue()) System.out.print("-");
                System.out.print(entry.getKey()+" ");
            }System.out.println(")");
            clause.index = list.size();
            list.add(clause);
        }

        for (int i = 1;i < inputCNF.clauses.size();i++){
            for (int j = 0;j<i;j++){
                ArrayList<String> strList = sameKey(inputCNF.clauses.get(j),inputCNF.clauses.get(i));
                if (!strList.isEmpty()){
                    queue.offer(new ClausePair(inputCNF.clauses.get(j),inputCNF.clauses.get(i),strList,j,i));
                }
            }
        }
        System.out.println("-----------");

        while (!queue.isEmpty()){
            ClausePair resPair = queue.poll();
            System.out.print("Qsize="+queue.size()+" ");
            System.out.print("resolving " + resPair.index1 + " and " + resPair.index2 + " ");
            System.out.print("on ");
            for (String str: resPair.duplicate){
                System.out.print(str+" ");
            }System.out.print(": (");

            for (Map.Entry<String,Boolean> entry: resPair.pair[0].table.entrySet()){
                if (!entry.getValue()) System.out.print("-");
                System.out.print(entry.getKey()+" ");
            }System.out.print(") and (");
            for (Map.Entry<String,Boolean> entry: resPair.pair[1].table.entrySet()){
                if (!entry.getValue()) System.out.print("-");
                System.out.print(entry.getKey()+" ");
            }System.out.print(") -> (");

            Clause newClause = new Clause(resPair);

            for (Map.Entry<String,Boolean> entry: newClause.table.entrySet()){
                if (!entry.getValue()) System.out.print("-");
                System.out.print(entry.getKey()+" ");
            }System.out.println(")");


            if (newClause.table.isEmpty()){
                System.out.println(list.size() + "()");
                System.out.println("success - empty clause!");
                System.out.println("----------");
                System.out.println("proof trace:");

                Stack<Clause> stack = new Stack<Clause>();
                Clause now;
                newClause.depth = 0;
                newClause.index = list.size();
                stack.push(newClause);
                while (!stack.isEmpty()){
                    now = stack.pop();
                    for (int i = 0;i<now.depth;i++) System.out.print("  ");
                    System.out.print(now.index+": ");
                    printClause(now);
                    if (now.parent1==-1){
                        System.out.println("input");
                    }else {
                        System.out.println("["+now.parent1+","+now.parent2+"]");
                        list.get(now.parent2).depth = now.depth+1;
                        list.get(now.parent1).depth = now.depth+1;
                        stack.push(list.get(now.parent2));
                        stack.push(list.get(now.parent1));
                    }
                }

                return true;
            }
            else {
                if (!list.contains(newClause)){
                    for (int i=0;i<list.size();i++){
                        ArrayList<String> strList = sameKey(list.get(i),newClause);
                        if (!strList.isEmpty()){
                            queue.offer(new ClausePair(list.get(i),newClause,strList,i,list.size()));
                        }
                    }
                    System.out.print(list.size() + 1 + ": (");
                    for (Map.Entry<String,Boolean> entry: newClause.table.entrySet()){
                        if (!entry.getValue()) System.out.print("-");
                        System.out.print(entry.getKey()+" ");
                    }System.out.println(")");
                    newClause.index = list.size();
                    list.add(newClause);
                }
            }
        }

        System.out.println("fail!");

        return false;
    }

    public static void printClause(Clause clause){
        System.out.print("(");
        for (Map.Entry<String,Boolean> entry: clause.table.entrySet()){
            if (!entry.getValue()) System.out.print("-");
            System.out.print(entry.getKey()+" ");
        }System.out.print(")");
    }


    public static ArrayList<String> sameKey(Clause clause1, Clause clause2){
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String ,Boolean> map1 = clause1.table;
        HashMap<String ,Boolean> map2 = clause2.table;

        Iterator iterator = map2.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Boolean> entry = (Map.Entry)iterator.next();
            String s = entry.getKey();
            Boolean bool = entry.getValue();
            if (map1.containsKey(s)){
                if (!map1.get(s).equals(bool)) result.add(s);
            }
        }
        return result;
    }
}
