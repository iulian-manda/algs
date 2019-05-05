package boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private final TrieST<Integer> trie;
    private BoggleBoard currentBoard;
    private boolean[] marked;
    private Set<String> words;
    private StringBuilder wordBuilder;
    private int n, m;

    public BoggleSolver(String[] dictionary) {
        trie = new TrieST<>();
        for (String word : dictionary) {
            trie.put(word, pointsFor(word));
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        Iterable<String> allValidWords = solver.getAllValidWords(board);
        for (String word : allValidWords) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Entries = " + ((Set<String>) allValidWords).size());
        StdOut.println("Score = " + score);
    }

    private int pointsFor(String word) {
        switch (word.length()) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> validWords = new HashSet<>();
        this.currentBoard = board;
        n = board.rows();
        m = board.cols();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                validWords.addAll(findWords(new Cube(i, j)));
            }
        }
        return validWords;
    }

    private Set<String> findWords(Cube source) {
        words = new HashSet<>();
        marked = new boolean[n * m];
        wordBuilder = new StringBuilder();
        dfs(source);
        return words;
    }

    private void dfs(Cube v) {
        marked[v.index(m)] = true;
        char letter = currentBoard.getLetter(v.i, v.j);
        wordBuilder.append(letter);
        if (letter == 'Q') {
            wordBuilder.append('U');
        }
        String currentWord = wordBuilder.toString();
        if (currentWord.length() >= 3 && trie.contains(currentWord)) {
            words.add(currentWord);
        }
        if (!trie.hasKeysWithPrefix(currentWord)) {
            return;
        }
        for (Cube w : v.adj(n, m)) {
            if (!marked[w.index(m)]) {
                dfs(w);
                wordBuilder.deleteCharAt(wordBuilder.length() - 1);
                if (wordBuilder.charAt(wordBuilder.length() - 1) == 'Q') {
                    wordBuilder.deleteCharAt(wordBuilder.length() - 1);
                }
                marked[w.index(m)] = false;
            }
        }
    }

    public int scoreOf(String word) {
        Integer score = trie.get(word);
        if (score == null) {
            return 0;
        }
        return score;
    }

    private class Cube {
        private final int i, j;

        private Cube(int i, int j) {
            this.i = i;
            this.j = j;
        }

        private Iterable<Cube> adj(int n, int m) {
            Queue<Cube> queue = new Queue<>();
            for (int k = -1; k <= 1; k++) {
                for (int p = -1; p <= 1; p++) {
                    if (k == 0 && p == 0) continue;
                    int x = i + k;
                    int y = j + p;
                    if (verifyIndex(x, y, n, m)) {
                        queue.enqueue(new Cube(x, y));
                    }
                }
            }
            return queue;
        }

        private boolean verifyIndex(int x, int y, int height, int width) {
            return x >= 0 && x < height && y >= 0 && y < width;
        }

        private int index(int m) {
            return i * m + j;
        }
    }

}
