package me.jiashi.HW5;

/**
 * Created by anderson on 3/10/15.
 */
public class Position {
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public int x;
    public int y;
    public int color;
    public boolean isOccupied;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
        color = 0;
        isOccupied = false;
    }

    public Position(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
        isOccupied = true;
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31*result + x;
        result = 31*result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Position){
            return (x==((Position) obj).x)&&(y==((Position) obj).y);
        }
        else return false;
    }
}
