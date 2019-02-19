package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private Node solution;

    private boolean isSolution;
    private int moves;
    private Stack<Board> solutionBoards;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Comparator<Node> comparator = Comparator.comparingInt(Node::getPriority);
        MinPQ<Node> minPQ = new MinPQ<>(comparator);
        MinPQ<Node> twinMinPQ = new MinPQ<>(comparator);
        minPQ.insert(new Node(initial, initial.manhattan(), 0));
        Board twin = initial.twin();
        twinMinPQ.insert(new Node(twin, twin.manhattan(), 0));

        while (true) {
            Node node = minPQ.delMin();
            Board board = node.getBoard();
            Node twinNode = twinMinPQ.delMin();
            Board twinBoard = twinNode.getBoard();

            if (board.isGoal()) {
                isSolution = true;
                moves = node.moves;
                solution = node;
                break;
            }
            if (twinBoard.isGoal()) {
                break;
            }

            addNeighbors(node, board, minPQ);
            addNeighbors(twinNode, twinBoard, twinMinPQ);
        }
        if (isSolution) {
            solutionBoards = new Stack<>();
            while (solution != null) {
                solutionBoards.push(solution.board);
                solution = solution.previous;
            }
        }
    }

    private void addNeighbors(Node node, Board board, MinPQ<Node> queue) {
        Node previousNode = node.getPrevious();
        Board previous = null;
        if (previousNode != null) {
            previous = previousNode.getBoard();
        }
        for (Board neighbor : board.neighbors()) {
            if (previous != null && previous.equals(neighbor)) {
                continue;
            }
            queue.insert(new Node(neighbor, neighbor.manhattan(), node.moves + 1, node));
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolution;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolution) {
            return -1;
        }
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionBoards;
    }

    private class Node {
        private Node previous;
        private final Board board;
        private int moves;
        private int priority;

        public Node(Board board, int manhattan, int moves) {
            this.board = board;
            this.moves = moves;
            this.priority = manhattan + moves;
        }

        public Node(Board board, int manhattan, int moves, Node previous) {
            this(board, manhattan, moves);
            this.previous = previous;
        }

        public Board getBoard() {
            return board;
        }

        public Node getPrevious() {
            return previous;
        }

        public int getPriority() {
            return priority;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}