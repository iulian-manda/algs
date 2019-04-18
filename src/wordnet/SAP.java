package wordnet;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;

public class SAP {

    private final Digraph G;

    SAP(Digraph G) {
        assertNotNull(G);
        Digraph copy = new Digraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                copy.addEdge(v, w);
            }
        }
        this.G = copy;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        wordnet.SAP sap = new wordnet.SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private int ancestor(int v, int w) {
        List<Integer> listV = new ArrayList<>();
        listV.add(v);
        List<Integer> listW = new ArrayList<>();
        listW.add(w);
        return bfs(listV, listW).ancestor;
    }

    public int length(int v, int w) {
        List<Integer> listV = new ArrayList<>();
        listV.add(v);
        List<Integer> listW = new ArrayList<>();
        listW.add(w);
        return bfs(listV, listW).length;
    }

    int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return bfs(v, w).ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return bfs(v, w).length;
    }

    private AncestorResult bfs(Iterable<Integer> v, Iterable<Integer> w) {
        assertNotNull(v);
        assertNotNull(w);
        for (Integer i : v) {
            assertNotNull(i);
        }
        for (Integer i : w) {
            assertNotNull(i);
        }
        assertVertex(v);
        assertVertex(w);
        Queue<Integer> queueV = new Queue<>();
        boolean[] visitedV = new boolean[G.V()];
        for (int x : v) {
            queueV.enqueue(x);
            visitedV[x] = true;
        }
        BFSResult bfs = bfs(queueV, visitedV);

        Queue<Integer> queue = new Queue<>();
        boolean[] visited = new boolean[G.V()];
        int[] distTo = new int[G.V()];
        for (int x : w) {
            queue.enqueue(x);
            visited[x] = true;
        }
        AncestorResult result = new AncestorResult();
        result.length = -1;
        result.ancestor = -1;
        int minLength = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            if (bfs.visited[current]) {
                int currentLength = bfs.distTo[current] + distTo[current];
                if (minLength > currentLength) {
                    minLength = currentLength;
                    result.length = currentLength;
                    result.ancestor = current;
                }
            }
            for (int next : G.adj(current)) {
                if (!visited[next]) {
                    visited[next] = true;
                    distTo[next] = distTo[current] + 1;
                    queue.enqueue(next);
                }
            }
        }
        return result;
    }

    private BFSResult bfs(Queue<Integer> queue, boolean[] visited) {
        int[] distTo = new int[G.V()];
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!visited[w]) {
                    visited[w] = true;
                    distTo[w] = distTo[v] + 1;
                    queue.enqueue(w);
                }
            }
        }
        BFSResult result = new BFSResult();
        result.visited = visited;
        result.distTo = distTo;
        return result;
    }

    private void assertVertex(Iterable<Integer> v) {
        for (int w : v) {
            if (w < 0 || w >= G.V()) {
                throw new IllegalArgumentException("Vertex is outside of the range!");
            }
        }
    }

    private void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument must not be null!");
        }
    }

    private class BFSResult {
        private boolean[] visited;
        private int[] distTo;
    }

    private class AncestorResult {
        private int length;
        private int ancestor;
    }

}
