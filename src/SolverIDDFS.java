import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SolverIDDFS extends Solver {

    private boolean helper(byte[] currentState, byte[] targetState, int depth, int maxDepth, Stack<String> sequence) {
        if (depth == maxDepth) {
            return equals(currentState, targetState);
        }
        for (byte[] move : moves) {
            doMove(currentState, move[0], move[1]);
            if (helper(currentState, targetState, depth + 1, maxDepth, sequence)) {
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
        byte maxDepth = 0;
        while (maxDepth < 11) {
            if (helper(state, targetState, 0, maxDepth, sequence)) {
                final List<String> actions = new ArrayList<>(sequence);
                Collections.reverse(actions);
                return actions.toArray(new String[0]);
            }
            maxDepth++;
        }
        return null;
    }
}
