//
//  Clause.h
//  SATSolver
//
//  Created by AndersonCHEN on 3/27/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#ifndef __SATSolver__Clause__
#define __SATSolver__Clause__

#include <stdio.h>
#include <string>
#include <sstream>
#include <iostream>
#include <unordered_map>

using namespace std;

class Clause{
public:
    Clause(const string &str);
    string key;
    int currentValue;
    unordered_map<string, bool> const& getsymbols();
    int value(unordered_map<string, bool> const &assignment);
private:
    unordered_map<string, bool> symbols;
};

void printClause(Clause &clause);

#endif /* defined(__SATSolver__Clause__) */
