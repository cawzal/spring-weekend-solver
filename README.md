# Spring Weekend Solver

### Description
A simple program that is able to play the game Spring Weekend from the  [MS Entertainment Pack: The Puzzle Collection](https://en.wikipedia.org/wiki/Microsoft_Entertainment_Pack:_The_Puzzle_Collection).

### Project Status
- Breadth-first (BFS) search fails on the more difficult levels (runs out of memory)
- Iterative deepening depth-first search (IDDFS) is able to solve all puzzles (given enough time)
- Iterative deepening A* (IDA*) is able to decrease the time taken by IDDFS (see below)

currentState = {2, 3, 1, 1, 0, 0, 2, 0, 1, 1, 0, 0, 0, 1, 2, 0, 0, 1, 2};
targetState = {0, 1, 0, 1, 0, 0, 1, 2, 2, 3, 2, 2, 1, 0, 0, 1, 0, 1, 0};
Given the puzzle above, the solve time for IDDFS is ~8.5minutes, while the solve current time for IDA* is ~6minutes.

### Todo
- Trying different heuristics
- Trying A* algorithm
- Upload video of solver in action