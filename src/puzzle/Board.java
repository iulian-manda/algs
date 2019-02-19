package puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board {

    private final int[][] blocks;
    private final int n;
    private int hamming;
    private int manhattan;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        hamming = 0;
        manhattan = 0;
        n = input[0].length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            if (input[i].length != n) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < n; j++) {
                blocks[i][j] = input[i][j];
                computeManhattanAndHamming(i, j);
            }
        }
    }

    private void computeManhattanAndHamming(int i, int j) {
        int current = blocks[i][j];
        current--; // blocks start from 1, so transform it to 0-index
        if (current != -1) {
            // if the current block is not in his position, increment hamming
            if (current != i * n + j) {
                hamming++;
            }
            int goalX = current / n;
            int goalY = current % n;
            manhattan += Math.abs(goalX - i) + Math.abs(goalY - j);
        }
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0 && manhattan == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int [][] twinBlocks = deepCopy();
        swapRandomBlocks(twinBlocks);
        return new Board(twinBlocks);
    }

    private int[][] deepCopy() {
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = blocks[i][j];
            }
        }
        return result;
    }

    private void swapRandomBlocks(int[][] input) {
        int first = findNonZeroPosition(0);
        int second = findNonZeroPosition(1);
        int x1 = first / n;
        int y1 = first % n;
        int x2 = second / n;
        int y2 = second % n;
        int temp = input[x1][y1];
        input[x1][y1] = input[x2][y2];
        input[x2][y2] = temp;
    }

    private int findZeroPosition() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    return i * n + j;
                }
            }
        }
        return -1;
    }

    private int findNonZeroPosition(int count) {
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    if (index == count) {
                        return i * n + j;
                    }
                    index++;
                }
            }
        }
        return -1;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        int zeroPosition = findZeroPosition();
        int x0 = zeroPosition / n;
        int y0 = zeroPosition % n;
        if (x0 > 0) {
            neighbors.enqueue(move0(-1, 0, x0, y0));
        }
        if (x0 < n - 1) {
            neighbors.enqueue(move0(1, 0, x0, y0));
        }
        if (y0 > 0) {
            neighbors.enqueue(move0(0, -1, x0, y0));
        }
        if (y0 < n - 1) {
            neighbors.enqueue(move0(0, 1, x0, y0));
        }
        return neighbors;
    }

    private Board move0(int horizontal, int vertical, int x0, int y0) {
        int[][] newBlocks = deepCopy();
        int x = x0 + horizontal;
        int y = y0 + vertical;
        newBlocks[x0][y0] = newBlocks[x][y];
        newBlocks[x][y] = 0;
        return new Board(newBlocks);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
//    public static void main(String[] args) {
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        puzzle.Board board = new puzzle.Board(blocks);
//        StdOut.println(board);
//        StdOut.println("Hamming: " + board.hamming());
////        StdOut.println("Manhattan: " + board.manhattan());
////        StdOut.println("Is Solution: " + board.isGoal());
////        StdOut.println("Twin: \n" + board.twin());
//        StdOut.println("Neighbors:");
//        for (puzzle.Board board1 : board.neighbors()) {
//            StdOut.println(board1);
//            StdOut.println("Hamming1: " + board1.hamming());
//            for(puzzle.Board board2 : board1.neighbors()) {
//                StdOut.println(board2);
//                StdOut.println("Hamming2: " + board2.hamming());
//            }
//        }
//    }

}
