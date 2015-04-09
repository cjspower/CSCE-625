package me.jiashi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anderson on 1/28/15.
 */
public class Tree {

    private int vertices;
    private int edges;
    private String vLineText;
    private String eLineText;

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }
    private ArrayList<Node> nodeList;

    /**
     *
     * @param name
     */
    public Tree(String name){
        nodeList = new ArrayList<Node>();
        try {
            File file = new File(name);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader buf = new BufferedReader(read);
                vLineText = buf.readLine();
                Pattern pattern = Pattern.compile("[^0-9]");
                Matcher matcher = pattern.matcher(vLineText);
                vertices = Integer.parseInt(matcher.replaceAll("").trim());
                for (int line=0;line<vertices;line++){
                    vLineText = buf.readLine();
                    //String[] str = vLineText.split(" ");
                    nodeList.add(line, new Node(Integer.parseInt(vLineText.split(" ")[0]),Integer.parseInt(vLineText.split(" ")[1]),Integer.parseInt(vLineText.split(" ")[2])));
                    //System.out.println("node:"+nodeList.get(line).getIndex()+" X:"+nodeList.get(line).getX()+" Y:"+nodeList.get(line).getY());
                }
                eLineText=buf.readLine();
                matcher = pattern.matcher(eLineText);
                edges = Integer.parseInt(matcher.replaceAll("").trim());

                while ((eLineText=buf.readLine())!=null){
                    nodeList.get(Integer.parseInt(eLineText.split(" ")[1])).
                            setSuccessors(nodeList.get(Integer.parseInt(eLineText.split(" ")[2])));
                    nodeList.get(Integer.parseInt(eLineText.split(" ")[2])).
                            setSuccessors(nodeList.get(Integer.parseInt(eLineText.split(" ")[1])));
                }

            }
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }

        System.out.println("vertices="+vertices+", edges="+edges);
    }
    public int getNodeByCoordinate(int x, int y){
        Iterator iterator = nodeList.iterator();
        while (iterator.hasNext()){
            Node i = (Node)iterator.next();
            if ((i.getX()==x)&&(i.getY()==y)){
                return i.getIndex();
            }
        }
        return -1;
    }
    public int getVerticesNum(){
        return vertices;

    }
    public int getEdgeNum(){
        return edges;
    }
    public void test(){
        Iterator iterator = nodeList.iterator();
        while (iterator.hasNext()){
            Node a = (Node)iterator.next();
            Iterator iterator2 = a.successors().iterator();
            while (iterator2.hasNext()){
                Node b = (Node)iterator2.next();
                System.out.println(b.getIndex());
            }
            System.out.println("=============");

        }
        nodeList.get(1).successors();
    }
    public void init(){

        for(Node e: nodeList){
            e.setDepth(-1);
            e.setParent(null);
            e.discover=false;
        }

    }

    /**
     * Search algorithm on tree.
     param method: 0 for depth-first, 1 for breadth first and 2 for greedy best first search
     *  Keep track of the shortest path, that means the path returned by DFS or GBFS may not be the path the same
     *  as the path which lead to it.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param method
     * @return
     */
    public boolean search(int x1, int y1, int x2, int y2, int method){
        this.init();
        int start = this.getNodeByCoordinate(x1,y1);
        int end = this.getNodeByCoordinate(x2,y2);

        System.out.println("start=("+x1+","+y1+")"+", goal=("+x2+","+y2+"), vertices: "+start+" and "+end);
        MyQueue frontier = new MyQueue(method);
        String searchAlgorithm;

        switch (method){
            case 0: searchAlgorithm = "DFS"; break;
            case 1: searchAlgorithm = "BFS"; break;
            case 2: searchAlgorithm = "GBFS"; break;
            default: searchAlgorithm = "Unknown"; break;
        }

        Node now = nodeList.get(start);
        now.discover();
        frontier.put(now);
        now.setDepth(0);
        int iter=1;
        while (!frontier.isEmpty()){
            now=frontier.get();
            System.out.println(String.format("iter=%d, frontier=%d, popped=%d, (%d,%d) depth=%d, dist2goal=%.1f"
                    , iter, frontier.size(), now.getIndex(), now.getX(), now.getY(), now.getDepth(),
                    now.getDistance()));
            if(now.getIndex()==end){
                System.out.println("=============================");
                System.out.println("Path found");
                now.traceBack();
                System.out.println("=============================");
                System.out.println("search algorithm = " + searchAlgorithm);
                System.out.println("total iterations = " + iter);
                System.out.println("max frontier size = "+ frontier.getMaxSize());
                System.out.println(String.format("vertices visited = %d/%d",frontier.getVisited(),nodeList.size()));
                System.out.println("path length = "+now.getDepth());
                System.out.println("=============================");
                return true;
            }
            for (Node e: now.successors()){
                if (!e.isDiscover()){
                    e.discover();
                    e.setParent(now);
                    e.setDepth(now.getDepth() + 1);
                    e.setDistance(Math.sqrt(Math.pow(e.getX()-x2,2)+Math.pow(e.getY()-y2,2)));
                    frontier.put(e);
                    System.out.println("into frontier: " + e.getIndex());
                }else {
                    if ((now.getDepth()+1)<e.getDepth()){
                        e.setParent(now);
                        e.setDepth(now.getDepth()+1);
                    }
                }
            }
            iter++;
        }
        return false;
    }

}