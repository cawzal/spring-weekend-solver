import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        Point gameClientPosition = Scraper.findGameClientPosition();
        if (gameClientPosition == null) {
            throw new Exception("Unable to detect game client!");
        }

        // Solver solver = new SolverBFS();
        Solver solver = new SolverIDDFS();
        for (int i = 1; i <= 5; i++) {
            Puzzle puzzle;
            do {
                puzzle = Scraper.findPuzzle((int)gameClientPosition.getX(), (int)gameClientPosition.getY());
            } while (puzzle == null);

            final long a = System.nanoTime();
            final String[] actions = solver.solve(puzzle.getCurrentState(), puzzle.getTargetState());
            final long b = System.nanoTime();
            if (actions == null)
                throw new Exception("Unable to solve puzzle!");

            Mouse.perform(actions, (int)gameClientPosition.getX(), (int)gameClientPosition.getY());
            System.out.println("Level " + i);
            System.out.println("Board: " + Arrays.toString(puzzle.getCurrentState()));
            System.out.println("Target: " + Arrays.toString(puzzle.getTargetState()));
            System.out.println("Time: " + (b - a));
            System.out.println("Moves: " + actions.length);
            System.out.println("Actions: " + Arrays.toString(actions));
            System.out.println();
            Thread.sleep(1500);
        }
    }
}
