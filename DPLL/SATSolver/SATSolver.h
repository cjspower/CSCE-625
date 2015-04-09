//
//  SATSolver.h
//  SATSolver
//
//  Created by ChenAnderson on 3/30/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#ifndef __SATSolver__SATSolver__
#define __SATSolver__SATSolver__

#include "CNF.h"
#include <map>
#include <stdio.h>
#include <unordered_set>
bool solve(CNF *cnf, unordered_map<string, Clause*> clauses, unordered_set<string> symbols, unordered_map<string, bool> assignment);
void clearCount(const unordered_map<string, Count*> &counting);

#endif /* defined(__SATSolver__SATSolver__) */
