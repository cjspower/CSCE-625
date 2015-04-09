/**
 * Created by anderson on 2/11/15.
 * Container of blocks
 */
package HW2.me.jiashi;

public class MyCharacter{
    public char c;

    public String print(){
        return String.valueOf(c);
    }

    public MyCharacter(char c){
        this.c=c;
    }

    public boolean is(char a){
        return c==a;
    }

    public int toInt(){
        return c-'A';
    }
}
