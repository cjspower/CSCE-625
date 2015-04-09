package me.jiashi;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by anderson on 1/28/15.
 */
public class Node implements Comparable<Node>{
    private int index;
    private Node parent;
    private int depth;

    private double distance;
    boolean discover = false;
    public Iterator iterator;
    ArrayList<Node> neighbour;

    public int getX() {
        return x;
    }

    private int x;

    public int getY() {
        return y;
    }

    private int y;

    /**
     *
     * @param index
     * @param parent
     * @param depth
     */
    public Node(int index, Node parent, int depth){
        this.index=index;
        this.parent=parent;
        this.depth=depth;
        neighbour = new ArrayList<Node>();
    }

    /**
     *
     * @param index
     * @param parent
     */
    public Node(int index, Node parent){
        this.index=index;
        this.parent=parent;
        neighbour = new ArrayList<Node>();
    }
    /**
     *
     * @param index
     * @param x
     * @param y
     */

    public Node(int index, int x, int y){
        this.index=index;
        this.x=x;
        this.y=y;
        neighbour = new ArrayList<Node>();
    }

    public void setSuccessors(Node i){
        neighbour.add(i);
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public void discover(){ this.discover=true;}
    public boolean isDiscover(){ return discover;}


    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    public int getDepth() {
        return depth;
    }

    ArrayList<Node> successors(){
        return neighbour;
    }

    public void setParent(Node parent){
        this.parent=parent;
    }
    public Node getParent(){
        return parent;
    }
    public void traceBack() {

        if (this.parent==null){
            System.out.println("solution path:");
            System.out.println(String.format(("vertex %d (%d, %d)"), this.getIndex(), this.getX(), this.getY()));
        }else {
            this.parent.traceBack();
            System.out.println(String.format(("vertex %d (%d, %d)"), this.getIndex(), this.getX(), this.getY()));
        }
    }

    /**
     * Implement of comparable, in order to be sorted in a priority queue.
     * @param node
     * @return
     */
    @Override
    public int compareTo(Node node) {

        if (node!=null) {
            if (this.distance > node.getDistance())
                return 1;
            else if (this.distance < node.getDistance())
                return -1;
        }
        return 0;
    }

}
