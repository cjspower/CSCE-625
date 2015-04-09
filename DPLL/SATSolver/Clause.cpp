//
//  Clause.cpp
//  SATSolver
//
//  Created by AndersonCHEN on 3/27/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#include "Clause.h"

Clause::Clause(const string &str)
{
    key = str;
    currentValue = 0;
    symbols = unordered_map<string, bool>();
    if (str.size() == 0) {
        return;
    }
    stringstream ss(str);
    string buf;
    while (ss >> buf) {
        if (buf[0] == '-') {
            symbols[buf.substr(1)] = false;
        }
        else{
            symbols[buf] = true;
        }
    }
    
}

unordered_map<string, bool> const& Clause::getsymbols()
{
    return symbols;
}

int Clause::value(const unordered_map<string, bool> &assignment)
{
    int output = -1;
    for (auto iter = symbols.begin(); iter != symbols.end(); ++iter) {
        auto search = assignment.find(iter->first);
        if (search != assignment.end()) {
            if (!(search->second^iter->second)) {
                return 1;
            }
        }else {
            output = 0;
        }
    }
    return output;
}

void printClause(Clause &clause){
    cout<<"(";
    unordered_map<string, bool> const &myMap = clause.getsymbols();
    if (myMap.size() == 0) {
        cout<<")"<<endl;
        return;
    }

    auto iter = myMap.begin();
    if (!iter->second)
    {
        cout<<"-";
    }
    cout<<iter->first;
    ++iter;
    while (iter!=myMap.end())
    {
        cout<<" v ";
        if (!iter->second)
        {
            cout<<"-";
        }
        cout<<iter->first;
        ++iter;
    }
    cout<<")"<<endl;
}