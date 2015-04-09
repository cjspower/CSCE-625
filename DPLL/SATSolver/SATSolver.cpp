//
//  SATSolver.cpp
//  SATSolver
//
//  Created by ChenAnderson on 3/30/15.
//  Copyright (c) 2015 AndersonCHEN. All rights reserved.
//

#include "SATSolver.h"

bool solve(CNF *cnf, unordered_map<string, Clause*> clauses, unordered_set<string> symbols, unordered_map<string, bool> assignment)
{
    static int round = 0;
    round++;
    cout<<round<<": model ={";
    for (auto iter = assignment.begin(); iter != assignment.end(); iter++) {
        cout<<"'"<<iter->first<<"'"<<":";
        if (iter->second) {
            cout<<"True ";
        }else{
            cout<<"False ";
        }
    }
    cout<<"}"<<endl;
    
    int flag = 1;
    for (auto iter = clauses.begin(); iter != clauses.end(); iter++) {
        iter->second->currentValue = iter->second->value(assignment);
        if(iter->second->currentValue==-1) {
            cout<<"Backtracking"<<endl;
            return false;
        }else if(iter->second->currentValue==0) {
            flag = 0;
        }
    }
    if(flag == 1) {
        if (symbols.size()!=0) {
            cout<<"some symbols are unassigned, they could be set randomly"<<endl;
            for (auto iter = symbols.begin(); iter != symbols.end(); iter++) {
                cout<<*iter<<": True, ";
                assignment.insert(make_pair(*iter, true));
            }
            cout<<endl;
        }
        cout<<"================="<<endl;
        cout<<"model ={";
        for (auto iter = assignment.begin(); iter != assignment.end(); iter++) {
            cout<<"'"<<iter->first<<"'"<<":";
            if (iter->second) {
                cout<<"True ";
            }else{
                cout<<"False ";
            }
        }
        cout<<"}"<<endl;
        
        cout<<"================="<<endl;
        cout<<"model(only display True value) ={";
        for (auto iter = assignment.begin(); iter != assignment.end(); iter++) {
            if (iter->second) {
                cout<<iter->first<<": "<<"True "<<endl;
            }
        }
        cout<<"}"<<endl;
        cout<<"total iteration:"<<round<<endl;
        //printClause(*clauses["-T1_FA_L -T2_FA_R -T1_CH_L -T2_CH_R T1_MV_CH_L_R"]);
        return true;
    }
    
    unordered_map<string, Clause*> newClauses = clauses;
    unordered_set<string> newSymbols = symbols;
    unordered_map<string, bool> newAssignment = assignment;
    unordered_map<string, Count*> counting;
    
    for (auto iter = symbols.begin(); iter != symbols.end(); iter++) {
        counting[*iter] = new Count();
    }
    
    for (auto iter = clauses.begin(); iter != clauses.end(); iter++) {
        for(auto iter2 = iter->second->getsymbols().begin(); iter2 != iter->second->getsymbols().end(); iter2++){
            if (symbols.count(iter2->first)) {
                if (iter2->second) {
                    counting[iter2->first]->pos[iter->first] = iter->second;
                }else{
                    counting[iter2->first]->neg[iter->first] = iter->second;
                }
            }
        }
    }
    
    for (auto iter = counting.begin(); iter != counting.end(); iter++) {
        if (iter->second->pos.size()==0) {
            newSymbols.erase(iter->first);
            newAssignment[iter->first] = false;
            for (auto iter2 = iter->second->neg.begin(); iter2 != iter->second->neg.end(); iter2++) {
                newClauses.erase(iter2->first);
            }
            clearCount(counting);
            cout<<"pure symbol:"<<iter->first<<" False"<<endl;
            return solve(cnf,newClauses, newSymbols, newAssignment);
        }else if(iter->second->neg.size()==0){
            newSymbols.erase(iter->first);
            newAssignment[iter->first] = true;
            for (auto iter2 = iter->second->pos.begin(); iter2 != iter->second->pos.end(); iter2++) {
                newClauses.erase(iter2->first);
            }
            clearCount(counting);
            cout<<"pure symbol:"<<iter->first<<" True"<<endl;
            return solve(cnf,newClauses, newSymbols, newAssignment);
        }
    }

    for (auto iter = clauses.begin(); iter != clauses.end(); iter++) {
        int count = 0;
        string name;
        bool state;
        for(auto iter2 = iter->second->getsymbols().begin(); iter2 != iter->second->getsymbols().end(); iter2++){
            if (symbols.count(iter2->first)) {
                name = iter2->first;
                state = iter2->second;
                count++;
            }
        }
        if (count == 1) {
            newAssignment[name] = state;
            newSymbols.erase(name);
            if (state) {
                for (auto iter2 = counting[name]->pos.begin(); iter2 != counting[name]->pos.end(); iter2++) {
                    newClauses.erase(iter2->first);
                }
            }else{
                for (auto iter2 = counting[name]->neg.begin(); iter2 != counting[name]->neg.end(); iter2++) {
                    newClauses.erase(iter2->first);
                }
            }
            clearCount(counting);
            cout<<"unit clause:"<<name;
            if (state) {
                cout<<" True"<<endl;
            }else{
                cout<<" False"<<endl;
            }
           // cout<<clauses.size()<<" "<<newClauses.size()<<endl;
            return solve(cnf,newClauses, newSymbols, newAssignment);
        }
    }
    
    auto iter = newSymbols.begin();
    string s = *iter;
    newAssignment[s] = true;
    newSymbols.erase(s);
    cout<<"trying "<<s<<"=True"<<endl;
    
    for (auto iter2 = counting[s]->pos.begin(); iter2 != counting[s]->pos.end(); iter2++) {
        newClauses.erase(iter2->first);
    }
    if(solve(cnf,newClauses, newSymbols, newAssignment)) {
        clearCount(counting);
        return true;
    }
    else {
        newAssignment[s] = false;
        unordered_map<string, Clause*> newClauses = clauses;
        for (auto iter2 = counting[s]->neg.begin(); iter2 != counting[s]->neg.end(); iter2++) {
            newClauses.erase(iter2->first);
        }
        clearCount(counting);
        cout<<"trying "<<s<<"=False"<<endl;
        return solve(cnf,newClauses, newSymbols, newAssignment);
    }
}

void clearCount(const unordered_map<string, Count*> &counting){
    for (auto iter2 = counting.begin(); iter2 != counting.end(); iter2++) {
        delete iter2->second;
    }
}