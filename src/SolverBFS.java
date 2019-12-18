import java.util.*;

public class SolverBFS implements Solver {

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

    private final byte[][] moves = new byte[][]{
            {0, 4}, {0, 5}, {0, 8}, {0, 9}, {0, 10}, {0, 13}, {0, 14},
            {1, 4}, {1, 5}, {1, 8}, {1, 9}, {1, 10}, {1, 13}, {1, 14}
    };

    @Override
    public String[] solve(byte[] board, byte[] target) {
        final Node root = new Node(Arrays.copyOf(board, 19), null, (byte)0, (byte)0);
        final Set<Node> visited = new HashSet<>();
        visited.add(root);
        final Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node active = queue.remove();
            if (equals(active.getState(), target)) {
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

    private static boolean equals(byte[] board, byte[] target) {
        for (int i = 0; i < 19; i++) {
            if (board[i] != target[i])
                return false;
        }
        return true;
    }

    private void doMove(byte[] board, byte direction, byte index) {
        if (direction == 0) {
            leftClick(board, index);
        } else {
            rightClick(board, index);
        }
    }

    private static void leftClick(byte[] board, byte index) {
        if (index <= 5) {
            byte temp = board[index - 4];
            board[index - 4] = board[index - 3];
            board[index - 3] = board[index + 1];
            board[index + 1] = board[index + 5];
            board[index + 5] = board[index + 4];
            board[index + 4] = board[index - 1];
            board[index - 1] = temp;
        } else if (index <= 10) {
            byte temp = board[index - 5];
            board[index - 5] = board[index - 4];
            board[index - 4] = board[index + 1];
            board[index + 1] = board[index + 5];
            board[index + 5] = board[index + 4];
            board[index + 4] = board[index - 1];
            board[index - 1] = temp;
        } else if (index <= 14) {
            byte temp = board[index - 5];
            board[index - 5] = board[index - 4];
            board[index - 4] = board[index + 1];
            board[index + 1] = board[index + 4];
            board[index + 4] = board[index + 3];
            board[index + 3] = board[index - 1];
            board[index - 1] = temp;
        }
    }

    private static void rightClick(byte[] board, byte index) {
        if (index <= 5) {
            byte temp = board[index - 4];
            board[index - 4] = board[index - 1];
            board[index - 1] = board[index + 4];
            board[index + 4] = board[index + 5];
            board[index + 5] = board[index + 1];
            board[index + 1] = board[index - 3];
            board[index - 3] = temp;
        } else if (index <= 10) {
            byte temp = board[index - 5];
            board[index - 5] = board[index - 1];
            board[index - 1] = board[index + 4];
            board[index + 4] = board[index + 5];
            board[index + 5] = board[index + 1];
            board[index + 1] = board[index - 4];
            board[index - 4] = temp;
        } else if (index <= 14) {
            byte temp = board[index - 5];
            board[index - 5] = board[index - 1];
            board[index - 1] = board[index + 3];
            board[index + 3] = board[index + 4];
            board[index + 4] = board[index + 1];
            board[index + 1] = board[index - 4];
            board[index - 4] = temp;
        }
    }
}
