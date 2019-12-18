class Puzzle {

    private byte[] board;
    private byte[] target;

    Puzzle(byte[] board, byte[] target) {
        this.board = board;
        this.target = target;
    }

    byte[] getBoard() {
        return board;
    }

    byte[] getTarget() {
        return target;
    }
}
