//
//  CNF.h
//  SATSolver
//
//  Created by ChenAnderson on 3/28/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#ifndef __SATSolver__CNF__
#define __SATSolver__CNF__

#include <stdio.h>
#include "Clause.h"
#include <vector>

using namespace std;

class Count {
public:
    unordered_map<string, Clause *> pos;
    unordered_map<string, Clause *> neg;
    Count();
};

class CNF {
public:
    CNF();
    ~CNF();
    unordered_map<string, Clause*> clauses;
    unordered_map<string, Count*> map;
    unordered_map<string, bool> assignment;
    void add(const string &str);
    
};




#endif /* defined(__SATSolver__CNF__) */
