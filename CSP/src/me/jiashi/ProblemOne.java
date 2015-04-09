package me.jiashi;

/**
 * Created by anderson on 15/3/1.
 */
public class ProblemOne {
    int[] count;

    public void solve(){
        System.out.println("Solving Problem One");
        System.out.println("=======================");
        String[] jobs = {"chef","guard","nurse","clerk","police officer","teacher","actor","boxer"};
        String[] names = {"Roberta","Thelma","Steve","Pete"};
        count = new int[4];
        int i=0;
        int x[] = new int[8];
        for (int j=0;j<8;j++){

        }
        while (x[0]<4){
            count[x[i]]++;
            if (consistencyCheck(i,x)){
                if (i!=7){
                    i++;
                }else {
                    break;
                }
            }else {
                i = next(i,x);
            }
        }
        for (int j=0;j<8;j++){
            System.out.println(jobs[j]+" is "+names[x[j]]);
        }
        System.out.println();
    }

    public int next(int i,int[] x){
        while (i>0&&x[i]==3){
            count[x[i]]--;
            x[i]=0;
            i--;
        }
        count[x[i]]--;
        x[i]++;
        return i;
    }

//    String[] jobs = {"chef","guard","nurse","clerk","police officer","teacher","actor","boxer"};
//    String[] names = {"Roberta","Thelma","Steve","Pete"};

    public boolean consistencyCheck(int i, int[] x){
        if (count[x[i]]>2) return false;

        if (i==0){
            if (x[0]!=1) return false;
        }
        if (i==2){
            if (x[2]!=2) return false;
        }
        if (i==3){
            if (x[3]==0||x[3]==1) return false;
        }
        if (i==4){
            if (x[4]==3||x[4]==0) return false;
            if (x[0]==x[4]) return false;
        }
        if (i==5){
            if (x[5]==3) return false;
        }
        if (i==6){
            if (x[6]==0||x[6]==1) return false;
        }
        if (i==7){
            if (x[7]==0) return false;
        }

        return true;
    }
}
