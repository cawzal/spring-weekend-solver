import java.util.*;

public class SolverIDAStar extends Solver {

    private boolean helper(byte[] currentState, byte[] targetState, int cost, int maxCost, Stack<String> sequence) {
        if (cost > maxCost)
            return false;

        final int estimate = calculateEstimatedCost(currentState, targetState);
        if (estimate == 0)
            return true;

        if (estimate + cost > maxCost)
            return false;

        for (byte[] move : moves) {
            doMove(currentState, move[0], move[1]);
            if (helper(currentState, targetState, cost + 1, maxCost, sequence)) {
                sequence.push((move[0] == 0 ? "L" : "R") + move[1]);
                return true;
            }
            unMove(currentState, move[0], move[1]);
        }
        return false;
    }

    @Override
    public String[] solve(byte[] currentState, byte[] targetState) {
        final byte[] state = currentState.clone();
        final Stack<String> sequence = new Stack<>();
        byte maxCost = 0;
        while (maxCost < 11) {
            if (helper(state, targetState, 0, maxCost, sequence)) {
                final List<String> actions = new ArrayList<>(sequence);
                Collections.reverse(actions);
                return actions.toArray(new String[0]);
            }
            maxCost++;
        }
        return null;
    }
}
