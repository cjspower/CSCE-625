CSCE-625 PROGRAMMING ASSIGNMENT 6
JIASHI CHEN, 124004770, cjspower@email.tamu.edu

Compile
==================================
Source code in /src/me/jiashi/
Please compile using ant.(Type ant in terminal)

Run
==================================
command java me.jiashi.HomeWork6 <filename>
for test case in the document <filename> = input.txt
for test case Sammy’s Sport Shop <filename> = input_2.txt

Program Trace
==================================
Initial clauses: 
0: (C1B -O1Y C1Y )
1: (C2B -O2W C2W )
2: (C3B -O3Y C3Y )
3: (-L1W -C1W )
4: (-L2Y -C2Y )
5: (-C3B -L3B )
6: (C1B C1W C1Y )
7: (C2B C2W C2Y )
8: (C3B C3W C3Y )
9: (-C1Y -C2Y )
10: (-C3Y -C1Y )
11: (-C1W -C2W )
12: (-C3W -C1W )
13: (-C2B -C1B )
14: (-C3B -C1B )
15: (O1Y )
16: (O2W )
17: (O3Y )
18: (L1W )
19: (L2Y )
20: (L3B )
21: (-C2W )
-----------
Qsize=31 resolving 3 and 18 on L1W : (-L1W -C1W ) and (L1W ) -> (-C1W )
23: (-C1W )
Qsize=31 resolving 4 and 19 on L2Y : (-L2Y -C2Y ) and (L2Y ) -> (-C2Y )
24: (-C2Y )
Qsize=31 resolving 5 and 20 on L3B : (-C3B -L3B ) and (L3B ) -> (-C3B )
25: (-C3B )
Qsize=32 resolving 7 and 21 on C2W : (C2B C2W C2Y ) and (-C2W ) -> (C2B C2Y )
26: (C2B C2Y )
Qsize=35 resolving 23 and 25 on C2Y : (-C2Y ) and (C2B C2Y ) -> (C2B )
27: (C2B )
Qsize=35 resolving 13 and 26 on C2B : (-C2B -C1B ) and (C2B ) -> (-C1B )
28: (-C1B )
Qsize=36 resolving 6 and 22 on C1W : (C1B C1W C1Y ) and (-C1W ) -> (C1Y C1B )
29: (C1Y C1B )
Qsize=40 resolving 27 and 28 on C1B : (-C1B ) and (C1Y C1B ) -> (C1Y )
30: (C1Y )
Qsize=41 resolving 9 and 29 on C1Y : (-C1Y -C2Y ) and (C1Y ) -> (-C2Y )
Qsize=40 resolving 10 and 29 on C1Y : (-C3Y -C1Y ) and (C1Y ) -> (-C3Y )
31: (-C3Y )
Qsize=41 resolving 9 and 25 on C2Y : (-C1Y -C2Y ) and (C2B C2Y ) -> (-C1Y C2B )
32: (-C1Y C2B )
Qsize=45 resolving 29 and 31 on C1Y : (C1Y ) and (-C1Y C2B ) -> (C2B )
Qsize=44 resolving 8 and 30 on C3Y : (C3B C3W C3Y ) and (-C3Y ) -> (C3B C3W )
33: (C3B C3W )
Qsize=47 resolving 24 and 32 on C3B : (-C3B ) and (C3B C3W ) -> (C3W )
34: (C3W )
Qsize=47 resolving 12 and 33 on C3W : (-C3W -C1W ) and (C3W ) -> (-C1W )
Qsize=46 resolving 0 and 27 on C1B : (C1B -O1Y C1Y ) and (-C1B ) -> (C1Y -O1Y )
35: (C1Y -O1Y )
Qsize=49 resolving 15 and 34 on O1Y : (O1Y ) and (C1Y -O1Y ) -> (C1Y )
Qsize=48 resolving 9 and 28 on C1Y : (-C1Y -C2Y ) and (C1Y C1B ) -> (C1B -C2Y )
36: (C1B -C2Y )
Qsize=52 resolving 27 and 35 on C1B : (-C1B ) and (C1B -C2Y ) -> (-C2Y )
Qsize=51 resolving 10 and 28 on C1Y : (-C3Y -C1Y ) and (C1Y C1B ) -> (-C3Y C1B )
37: (-C3Y C1B )
Qsize=55 resolving 27 and 36 on C1B : (-C1B ) and (-C3Y C1B ) -> (-C3Y )
Qsize=54 resolving 13 and 25 on C2B : (-C2B -C1B ) and (C2B C2Y ) -> (-C1B C2Y )
38: (-C1B C2Y )
Qsize=61 resolving 35 and 37 on C1B C2Y : (C1B -C2Y ) and (-C1B C2Y ) -> ()
38()
success - empty clause!
----------
proof trace:
38: ()[35,37]
  35: (C1B -C2Y )[9,28]
    9: (-C1Y -C2Y )input
    28: (C1Y C1B )[6,22]
      6: (C1B C1W C1Y )input
      22: (-C1W )[3,18]
        3: (-L1W -C1W )input
        18: (L1W )input
  37: (-C1B C2Y )[13,25]
    13: (-C2B -C1B )input
    25: (C2B C2Y )[7,21]
      7: (C2B C2W C2Y )input
      21: (-C2W )input