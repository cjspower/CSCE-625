def write():
    f = open('input3.txt','w')
    objt = ["_CH","_FX","_GR","_FA"]
    # location = ["_L","_R"]
    timeStamp = ["T0","T1","T2","T3","T4","T5","T6","T7"]
    # generate start state
    for str in objt:
        f.write("T0"+str+"L"+"\n")
    # generate end state
    for str in objt:
        f.write("T7"+str+"R"+"\n")
    # indicate that any object could only be on one side in each state
    for time in timeStamp:
        for obj in objt:
            f.write(time+obj+"L"+" "+time+obj+"R"+"\n")
            f.write("-"+time+obj+"L"+" "+"-"+time+obj+"R"+"\n")
    # transport rule 1: Ti_FAL and Ti+1_FAR and Ti_ObjL and Ti+1_ObjR <=> Ti_MV_Obj_LR
    # vice versa
   
    for i in range(7):
        for obj in objt[0:3]:
            f.write("-"+timeStamp[i]+"_FAL" +" "+ "-"+timeStamp[i+1]+"_FAR" +" "+ "-"+timeStamp[i]+obj+"L" +" "+ "-"+timeStamp[i+1]+obj+"R" +" "+ timeStamp[i]+"_MV"+obj+"_LR"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_LR"+" "+timeStamp[i]+"_FAL"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_LR"+" "+timeStamp[i+1]+"_FAR"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_LR"+" "+timeStamp[i]+obj+"L"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_LR"+" "+timeStamp[i+1]+obj+"R"+"\n")
            f.write("-"+timeStamp[i]+"_FAR" +" "+ "-"+timeStamp[i+1]+"_FAL" +" "+ "-"+timeStamp[i]+obj+"R" +" "+ "-"+timeStamp[i+1]+obj+"L" +" "+ timeStamp[i]+"_MV"+obj+"_RL"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_RL"+" "+timeStamp[i]+"_FAR"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_RL"+" "+timeStamp[i+1]+"_FAL"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_RL"+" "+timeStamp[i]+obj+"R"+"\n")
            f.write("-"+timeStamp[i]+"_MV"+obj+"_RL"+" "+timeStamp[i+1]+obj+"L"+"\n")
            
    
    # transport rule 2: FA could go by himself
    for i in range(7):
        # Ti_MV_NO_LR => Ti_FAL and Ti_FAR
        f.write("-"+timeStamp[i]+"_MV"+"_FA"+"_LR"+" "+timeStamp[i]+"_FA"+"L"+"\n")
        f.write("-"+timeStamp[i]+"_MV"+"_FA"+"_LR"+" "+timeStamp[i+1]+"_FA"+"R"+"\n")
        # f.write(timeStamp[i]+"_MV"+"_FA"+"_L_R"+" ")
        # f.write("-"+timeStamp[i]+"_FA"+"_L"+" "+"-"+timeStamp[i+1]+"_FA"+"_R"+"\n")

        f.write("-"+timeStamp[i]+"_MV"+"_FA"+"_RL"+" "+timeStamp[i]+"_FAR"+"\n")
        f.write("-"+timeStamp[i]+"_MV"+"_FA"+"_RL"+" "+timeStamp[i+1]+"_FAL"+"\n")
        # f.write(timeStamp[i]+"_MV"+"_FA"+"_R_L"+" ")
        # f.write("-"+timeStamp[i]+"_FA"+"_R"+" "+"-"+timeStamp[i+1]+"_FA"+"_L"+"\n")
        
    for i in range(6):
        for obj in objt:
            f.write("-"+timeStamp[i]+"_MV"+obj+"_LR"+" "+"-"+timeStamp[i+1]+"_MV"+obj+"_RL"+"\n");
            f.write("-"+timeStamp[i]+"_MV"+obj+"_RL"+" "+"-"+timeStamp[i+1]+"_MV"+obj+"_LR"+"\n");

    # transport rule 3: Only one action could be executed in each time stamp
    cond = [[],[],[],[],[],[],[]]
    for i in range(7):
        for obj in objt:
            cond[i].append(timeStamp[i]+"_MV"+obj+"_LR")
            cond[i].append(timeStamp[i]+"_MV"+obj+"_RL")

    for times in range(7):
        for i in range(7):
            for j in range(i+1,8):
                f.write("-"+cond[times][i]+" "+"-"+cond[times][j]+"\n")
        for i in range(7):
            f.write(cond[times][i]+" ")
        f.write(cond[times][7]+"\n")

    # transportation rule 4: Tj_ObjR => Ti_MV_Obj_LR, j>i

    for obj in objt[:3]:
        for i in range(1, 8):
            f.write("-"+timeStamp[i]+obj+"R")
            for early in range(i):
                f.write(" "+timeStamp[early]+"_MV"+obj+"_LR")
            f.write("\n")

    # The eating relationship
    for time in timeStamp:
        f.write("-"+time+"_CHL"+" "+"-"+time+"_GRL"+" "+"-"+time+"_FAR"+"\n")
        f.write("-"+time+"_CHR"+" "+"-"+time+"_GRR"+" "+"-"+time+"_FAL"+"\n")
        f.write("-"+time+"_CHL"+" "+"-"+time+"_FXL"+" "+"-"+time+"_FAR"+"\n")
        f.write("-"+time+"_CHR"+" "+"-"+time+"_FXR"+" "+"-"+time+"_FAL"+"\n")
                

    f.close()

def main():
    write()

if __name__ == '__main__':
    main()