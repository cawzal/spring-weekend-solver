# Spring Weekend Solver

### Description
A simple program that is able to play the game Spring Weekend from the  [MS Entertainment Pack: The Puzzle Collection](https://en.wikipedia.org/wiki/Microsoft_Entertainment_Pack:_The_Puzzle_Collection).

### Project Status
- Breadth-first (BFS) search fails on the more difficult levels (runs out of memory)
- A* also fails on the more difficult levels (also runs out of memory)
- Iterative deepening depth-first search (IDDFS) is able to solve all puzzles (given enough time)
- Iterative deepening A* (IDA*) is able to decrease the time taken by IDDFS (see below)

```java
byte[] currentState = new byte[]{2, 3, 1, 1, 0, 0, 2, 0, 1, 1, 0, 0, 0, 1, 2, 0, 0, 1, 2};
byte[] targetState = new byte[]{0, 1, 0, 1, 0, 0, 1, 2, 2, 3, 2, 2, 1, 0, 0, 1, 0, 1, 0};
```
Given the puzzle above, the solve time for IDDFS is ~8minutes, while the solve current time for IDA* is ~4minutes.

### Todo
- Trying different heuristics
- Upload video of solver in action
- Try pruning BFS/A*
- Try creating pattern database