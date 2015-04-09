//
//  CNF.cpp
//  SATSolver
//
//  Created by ChenAnderson on 3/28/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#include "CNF.h"

Count::Count(){}

CNF::CNF(){}

CNF::~CNF(){
    for (auto iter = clauses.begin(); iter != clauses.end(); ++iter) {
        delete iter->second;
    }
    for (auto iter = map.begin(); iter != map.end(); ++iter) {
        delete iter->second;
    }
}

void CNF::add(const string &str){
    Clause *temp = new Clause(str);
    clauses[temp->key] = temp;
 
    unordered_map<string, bool> const &myMap = temp->getsymbols();
    for (auto iter = myMap.begin(); iter != myMap.end(); ++iter) {
        auto search = map.find(iter->first);
        Count *count;
        
        if (search==map.end()) {
            count = new Count();
            map[iter->first] = count;
        }
        else count = search->second;
        
        if (iter->second) count->pos[temp->key] = temp;
        else count->neg[temp->key] = temp;
    }
}

