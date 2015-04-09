CSCE-625 PROGRAMMING ASSIGNMENT 5
JIASHI CHEN, 124004770, cjspower@email.tamu.edu

Compile
==================================
Source code in /src/me/jiashi/HW5
Please compile using ant.(Type ant in terminal)

Run
==================================
command java me.jiashi.HW5.Othello <scale> <depth>
init
move B/W
put B/W <X> <Y>

AI
==================================
alpha-beta minimax search 
a table of values on each position and mobility check for evaluation.

Program Trace
==================================
java Othello 6 4
init
# . . . . . . 
# . . . . . . 
# . . W B . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move B
(2,1)
# . . . . . . 
# . . . . . . 
# . B B B . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move W
(1,3)
# . . . . . . 
# . . . W . . 
# . B B W . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move B
(0,4)
# . . . . B . 
# . . . B . . 
# . B B W . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move W
(0,3)
# . . . W B . 
# . . . W . . 
# . B B W . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move B
(0,2)
# . . B B B . 
# . . . W . . 
# . B B W . . 
# . . B W . . 
# . . . . . . 
# . . . . . . 
move W
(3,1)
# . . B B B . 
# . . . W . . 
# . B W W . . 
# . W W W . . 
# . . . . . . 
# . . . . . . 
move B
(4,3)
# . . B B B . 
# . . . B . . 
# . B W B . . 
# . W B B . . 
# . . . B . . 
# . . . . . . 
move W
(3,4)
# . . B B B . 
# . . . B . . 
# . B W B . . 
# . W W W W . 
# . . . B . . 
# . . . . . . 
move B
(2,5)
# . . B B B . 
# . . . B . . 
# . B W B . B 
# . W W W B . 
# . . . B . . 
# . . . . . . 
move W
(3,5)
# . . B B B . 
# . . . B . . 
# . B W B . B 
# . W W W W W 
# . . . B . . 
# . . . . . . 
move B
(4,5)
# . . B B B . 
# . . . B . . 
# . B W B . B 
# . W W W B B 
# . . . B . B 
# . . . . . . 
move W
(1,0)
# . . B B B . 
# W . . B . . 
# . W W B . B 
# . W W W B B 
# . . . B . B 
# . . . . . . 
move B
(3,0)
# . . B B B . 
# W . . B . . 
# . W W B . B 
# B B B B B B 
# . . . B . B 
# . . . . . . 
move W
(2,4)
# . . B B B . 
# W . . B . . 
# . W W W W B 
# B B B B B B 
# . . . B . B 
# . . . . . . 
move B
(1,5)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B B B B B 
# . . . B . B 
# . . . . . . 
move W
(4,2)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B W B B B 
# . . W B . B 
# . . . . . . 
move B
(5,3)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B W B B B 
# . . B B . B 
# . . . B . . 
move W
(5,2)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B W B B B 
# . . W B . B 
# . . W B . . 
move B
(5,1)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B W B B B 
# . . B B . B 
# . B B B . . 
move W
(4,4)
# . . B B B . 
# W . . B . B 
# . W W W B B 
# B B W W B B 
# . . B B W B 
# . B B B . . 
move B
(2,0)
# . . B B B . 
# W . . B . B 
# B B B B B B 
# B B W W B B 
# . . B B W B 
# . B B B . . 
move W
(4,0)
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B W W B B 
# W . B B W B 
# . B B B . . 
move B
(5,5)
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B W B B B 
# W . B B B B 
# . B B B . B 
move W
(5,4)
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B W B B B 
# W . B W B B 
# . B B B W B 
move B
(4,1)
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B B B B B 
# W B B W B B 
# . B B B W B 
move W
(5,0)
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B B B B B 
# W B B W B B 
# W W W W W B 
move B
forfeit
# . . B B B . 
# W . . B . B 
# W B B B B B 
# W B B B B B 
# W B B W B B 
# W W W W W B 
move W
(1,1)
# . . B B B . 
# W W . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move B
(0,0)
# B . B B B . 
# W B . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move W
(0,1)
# B W B B B . 
# W W . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move B
forfeit
# B W B B B . 
# W W . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move W
(0,5)
# B W W W W W 
# W W . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move B
forfeit
# B W W W W W 
# W W . B . B 
# W W B B B B 
# W W B B B B 
# W W B W B B 
# W W W W W B 
move W
(1,2)
# B W W W W W 
# W W W B . B 
# W W W B B B 
# W W W B B B 
# W W W W B B 
# W W W W W B 
move B
forfeit
# B W W W W W 
# W W W B . B 
# W W W B B B 
# W W W B B B 
# W W W W B B 
# W W W W W B 
move W
(1,4)
game over
# B W W W W W 
# W W W W W B 
# W W W W W B 
# W W W B W B 
# W W W W W B 
# W W W W W B 
