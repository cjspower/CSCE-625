package me.jiashi;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by anderson on 3/24/15.
 */
public class HomeWork6 {
    public static void main(String[] args) {
        String fileName;
        ArrayList<Clause> clauses = new ArrayList<Clause>();
        if (args.length==1){
        fileName = args[0];
        }else {
            System.out.println("No file name");
            return;
        }
        File file = new File(fileName);
        try {
            if (file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader buf = new BufferedReader(read);
                String temp;
                while ((temp=buf.readLine())!=null){
                    if (temp.charAt(0)!='#') clauses.add(new Clause(temp));
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
        //System.out.println(clauses.size());
        CNF cnf = new CNF(clauses);
        CNFSolver.Solve(cnf);
    }
}
