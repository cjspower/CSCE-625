package me.jiashi;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by anderson on 1/31/15.
 * MyQueue is a data structure that work as a stack a queue or a priority queue depends
 * on the parameter of the constructed function.
 */
public class MyQueue {
    int method;

    public int getMaxSize() {
        return maxSize;
    }

    int maxSize;

    public int getVisited() {
        return visited;
    }

    int visited;

    Deque<Node> frontier;
    PriorityQueue<Node> priorityQueue;
    public MyQueue(int method){
        this.method=method;
        switch (method){
            case 0:
            case 1:  frontier = new LinkedList<Node>(); break;
            case 2:  priorityQueue = new PriorityQueue<Node>();break;
        }
        visited = 0;
        maxSize = 0;
    }

    public boolean isEmpty(){
        switch (method){
            case 0:
            case 1: return frontier.isEmpty();
            case 2: return priorityQueue.isEmpty();
            default: return true;
        }
    }

    public Node get(){
        switch(method) {
            case 0: return frontier.pop();
            case 1: return frontier.poll();
            case 2: return priorityQueue.poll();
            default: return null;
        }
    }

    public void put(Node e){
        switch (method){
            case 0: frontier.push(e);break;
            case 1: frontier.offer(e);break;
            case 2: priorityQueue.add(e);break;
            default:
        }
        visited++;
        if(this.size()>=maxSize){
            maxSize=this.size();
        }
    }

    public int size(){
        switch (method) {
            case 0:
            case 1: return frontier.size();
            case 2: return priorityQueue.size();
            default:return -1;
        }
    }






}
