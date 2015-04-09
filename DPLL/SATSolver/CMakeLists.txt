cmake_minimum_required (VERSION 2.8)

project (SATSolver)

add_executable(SATSolver main.cpp SATSolver.cpp Clause.cpp CNF.cpp)