import java.util.*;

public class SolverAStar extends Solver {

    private class Node {
        private byte[] state;
        private int cost;
        private int remaining;
        private int direction;
        private int index;
        private Node parent;

        Node(byte[] state, int cost, int remaining, int direction, int index, Node parent) {
            this.state = state;
            this.cost = cost;
            this.remaining = remaining;
            this.direction = direction;
            this.index = index;
            this.parent = parent;
        }

        byte[] getState() {
            return state;
        }

        int getCost() {
            return cost;
        }

        Node getParent() {
            return parent;
        }

        int getRemaining() {
            return remaining;
        }

         int getDirection() {
            return direction;
        }

        int getIndex() {
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
    String[] solve(byte[] currentState, byte[] targetState) {
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                int t1 = n1.getCost() + n1.getRemaining();
                int t2 = n2.getCost() + n2.getRemaining();
                return Integer.compare(t1, t2);
            }
        });
        priorityQueue.add(new Node(currentState, 0, 0, -1, -1, null));
        final Set<Node> visited = new HashSet<>();

        while(!priorityQueue.isEmpty()) {
            Node node = priorityQueue.remove();
            if (equals(node.getState(), targetState)) {
                final Stack<String> sequence = new Stack<>();
                while (node!= null) {
                    sequence.push((node.getDirection() == 0 ? "L" : "R") + node.getIndex());
                    node = node.getParent();
                }
                sequence.pop();

                final List<String> actions = new ArrayList<>(sequence);
                Collections.reverse(actions);
                return actions.toArray(new String[0]);
            } else {
                for (byte[] move : moves) {
                    byte[] board = node.getState().clone();
                    doMove(board, move[0], move[1]);
                    final Node neighbour = new Node(board, node.getCost() + 1, calculateEstimatedCost(board, targetState), move[0], move[1], node);
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        priorityQueue.add(neighbour);
                    }
                }
            }
        }
        return null;
    }
}
