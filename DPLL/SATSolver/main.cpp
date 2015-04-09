//
//  main.cpp
//  SATSolver
//
//  Created by AndersonCHEN on 3/27/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#include <iostream>
#include "Clause.h"
#include "CNF.h"
#include <fstream>
#include "SATSolver.h"

using namespace std;

int main(int argc, const char * argv[]) {
    string str;
    str = argv[1];
    
    ifstream infile(str);
    CNF *cnf = new CNF();
    string line;
    while (getline(infile, line)) {
        cnf->add(line);
    }
    cout<<cnf->clauses.size()<<endl;
    
    unordered_set<string> symbols;
  //  printClause(*cnf->clauses["-T1_FA_L -T2_FA_R -T1_CH_L -T2_CH_R T1_MV_CH_L_R"]);
    
    cout<<cnf->map.size()<<endl;
    for (auto iter = cnf->map.begin(); iter != cnf->map.end(); iter++) {
        symbols.insert(iter->first);
    }

    unordered_map<string, bool> assignment;
    
    if(solve(cnf, cnf->clauses, symbols, assignment)) cout<<"true"<<endl;
    else cout<<"false"<<endl;
    

    delete cnf;
}
