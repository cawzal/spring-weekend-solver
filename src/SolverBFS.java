import java.util.*;

public class SolverBFS extends Solver {

    class Node {
        private byte[] state;
        private Node parent;
        private byte direction;
        private byte index;

        Node(byte[] state, Node parent, byte direction, byte index) {
            this.state = state;
            this.parent = parent;
            this.direction = direction;
            this.index = index;
        }

        byte[] getState() {
            return state;
        }

        Node getParent() {
            return parent;
        }

        byte getDirection() {
            return direction;
        }

        byte getIndex() {
            return index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Arrays.equals(state, node.state);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(state);
        }
    }

    @Override
    public String[] solve(byte[] currentState, byte[] targetState) {
        final Node root = new Node(Arrays.copyOf(currentState, 19), null, (byte)0, (byte)0);
        final Set<Node> visited = new HashSet<>();
        visited.add(root);
        final Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node active = queue.remove();
            if (equals(active.getState(), targetState)) {
                final Stack<String> stack = new Stack<>();
                Node node = active;
                while (node != null) {
                    stack.push((node.getDirection() == 0 ? "L" : "R") + node.getIndex());
                    node = node.getParent();
                }
                stack.pop(); // discard move from root node

                final List<String> actions = new ArrayList<>(stack);
                Collections.reverse(actions);
                return actions.toArray(new String[0]);
            }

            for (byte[] move : moves) {
                final byte[] state = Arrays.copyOf(active.getState(), 19);
                doMove(state, move[0], move[1]);
                final Node neighbour = new Node(state, active, move[0], move[1]);
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return null;
    }
}
