import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        Point gameClientPosition = Scraper.findGameClientPosition();
        if (gameClientPosition == null) {
            throw new Exception("Unable to detect game client!");
        }

        Solver solver = new SolverBFS();
        for (int i = 1; i <= 100; i++) {
            Puzzle puzzle;
            do {
                puzzle = Scraper.findPuzzle(gameClientPosition.x, gameClientPosition.y);
            } while (puzzle == null);

            final long a = System.nanoTime();
            final String[] actions = solver.solve(puzzle.getBoard(), puzzle.getTarget());
            final long b = System.nanoTime();
            System.out.println("Level " + i);
            System.out.println("Board: " + Arrays.toString(puzzle.getBoard()));
            System.out.println("Target: " + Arrays.toString(puzzle.getTarget()));
            System.out.println("Time: " + (b - a));
            System.out.println("Moves: " + actions.length);
            System.out.println("Actions: " + Arrays.toString(actions));
            System.out.println();
            Mouse.perform(actions, gameClientPosition.x, gameClientPosition.y);
            Thread.sleep(1500);
        }
    }
}
