class Puzzle {

    private byte[] currentState;
    private byte[] targetState;

    Puzzle(byte[] currentState, byte[] targetState) {
        this.currentState = currentState ;
        this.targetState = targetState;
    }

    byte[] getCurrentState() {
        return currentState;
    }

    byte[] getTargetState() {
        return targetState;
    }
}
