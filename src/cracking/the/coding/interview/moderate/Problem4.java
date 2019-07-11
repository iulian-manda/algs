package cracking.the.coding.interview.moderate;

class Problem4 {
    int n;
    boolean hasWonX, hasWonO;
    Value[][] table;
    Value current;

    public Problem4(int n) {
        this.n = n;
        table = new Value[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = Value.EMPTY;
            }
        }
        current = Value.X;
    }


    public void putValue(int x, int y, Value value) {
        if (current != value || x < 1 || x > n || y < 1 || y > n || table[x - 1][y - 1] != Value.EMPTY) return;
        int i = x - 1;
        int j = y - 1;
        table[i][j] = current;
        boolean hasWon = hasWonHorizontal(i) || hasWonVertical(j) || hasWonDiagonal(i, j);
        if (current == Value.X) hasWonX = hasWon;
        else hasWonO = hasWon;
        current = current == Value.X ? Value.O : Value.X;
    }


    public boolean hasWon(Value value) {
        return value == Value.X ? hasWonX : hasWonO;
    }


    public Value current() {
        return current;
    }

    private boolean hasWonVertical(int j) {
        for (int k = 0; k < n; k++) {
            if (table[k][j] != current) return false;
        }
        return true;
    }


    private boolean hasWonHorizontal(int i) {
        for (int k = 0; k < n; k++) {
            if (table[i][k] != current) return false;
        }
        return true;
    }

    private boolean hasWonDiagonal(int i, int j) {
        if (i != j) return false;
        for (int k = 0; k < n; k++) {
            if (table[k][k] != current) return false;
        }
        return true;
    }


    private enum Value {
        X, O, EMPTY
    }
}
