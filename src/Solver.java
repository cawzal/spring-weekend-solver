abstract class Solver {

    final byte[][] moves = new byte[][]{
            {0, 4}, {0, 5}, {0, 8}, {0, 9}, {0, 10}, {0, 13}, {0, 14},
            {1, 4}, {1, 5}, {1, 8}, {1, 9}, {1, 10}, {1, 13}, {1, 14}
    };

     boolean equals(byte[] currentState, byte[] targetState) {
        for (int i = 0; i < 19; i++) {
            if (currentState[i] != targetState[i])
                return false;
        }
        return true;
    }

    void doMove(byte[] state, byte direction, byte index) {
        if (direction == 0) {
            leftClick(state, index);
        } else {
            rightClick(state, index);
        }
    }

    void unMove(byte[] state, byte direction, byte index) {
        if (direction == 1) {
            leftClick(state, index);
        } else {
            rightClick(state, index);
        }
    }

    private void leftClick(byte[] state, byte index) {
        if (index <= 5) {
            byte temp = state[index - 4];
            state[index - 4] = state[index - 3];
            state[index - 3] = state[index + 1];
            state[index + 1] = state[index + 5];
            state[index + 5] = state[index + 4];
            state[index + 4] = state[index - 1];
            state[index - 1] = temp;
        } else if (index <= 10) {
            byte temp = state[index - 5];
            state[index - 5] = state[index - 4];
            state[index - 4] = state[index + 1];
            state[index + 1] = state[index + 5];
            state[index + 5] = state[index + 4];
            state[index + 4] = state[index - 1];
            state[index - 1] = temp;
        } else if (index <= 14) {
            byte temp = state[index - 5];
            state[index - 5] = state[index - 4];
            state[index - 4] = state[index + 1];
            state[index + 1] = state[index + 4];
            state[index + 4] = state[index + 3];
            state[index + 3] = state[index - 1];
            state[index - 1] = temp;
        }
    }

    private void rightClick(byte[] state, byte index) {
        if (index <= 5) {
            byte temp = state[index - 4];
            state[index - 4] = state[index - 1];
            state[index - 1] = state[index + 4];
            state[index + 4] = state[index + 5];
            state[index + 5] = state[index + 1];
            state[index + 1] = state[index - 3];
            state[index - 3] = temp;
        } else if (index <= 10) {
            byte temp = state[index - 5];
            state[index - 5] = state[index - 1];
            state[index - 1] = state[index + 4];
            state[index + 4] = state[index + 5];
            state[index + 5] = state[index + 1];
            state[index + 1] = state[index - 4];
            state[index - 4] = temp;
        } else if (index <= 14) {
            byte temp = state[index - 5];
            state[index - 5] = state[index - 1];
            state[index - 1] = state[index + 3];
            state[index + 3] = state[index + 4];
            state[index + 4] = state[index + 1];
            state[index + 1] = state[index - 4];
            state[index - 4] = temp;
        }
    }

    int calculateEstimatedCost(byte[] currentState, byte[] targetState) {
        int distance = 0;
        for (int i = 0; i < 19; i++) {
            if (currentState[i] != targetState[i]) {
                distance++;
            }
        }
        return (distance == 0) ? 0 : Math.max((int) Math.floor(distance / 6), 1);
    }

    abstract String[] solve(byte[] currentState, byte[] targetState);
}
