package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class WordNet {

    private final SAP sap;
    private final List<String> synset;
    private final Map<String, Set<Integer>> words;

    WordNet(String synsets, String hypernyms) {
        assertNotNull(synsets);
        assertNotNull(hypernyms);
        In synsetInput = new In(synsets);
        synset = new ArrayList<>();
        this.words = new HashMap<>();
        for (String line : synsetInput.readAllLines()) {
            String[] tokens = line.split(",");
            for (String word : tokens[1].split(" ")) {
                this.words.computeIfAbsent(word, k -> new HashSet<>());
                this.words.get(word).add(Integer.parseInt(tokens[0]));
            }
            synset.add(tokens[1]);
        }
        synsetInput.close();

        Digraph G = new Digraph(synset.size());
        In hypernymInput = new In(hypernyms);
        for (String line : hypernymInput.readAllLines()) {
            String[] tokens = line.split(",");
            int v = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                G.addEdge(v, Integer.parseInt(tokens[i]));
            }
        }
        hypernymInput.close();
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle() || !hasRoot(G)) {
            throw new IllegalArgumentException("Graph has a cycle!");
        }
        sap = new SAP(G);
    }

    public static void main(String[] args) {
        wordnet.WordNet wordnet = new wordnet.WordNet(args[0], args[1]);
        StdOut.println(wordnet.sap("dog", "tiger"));
        StdOut.println(wordnet.distance("dog", "tiger"));
        wordnet.Outcast outcast = new wordnet.Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

    private boolean hasRoot(Digraph G) {
        int roots = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                roots++;
            }
        }
        return roots == 1;
    }

    public Iterable<String> nouns() {
        return words.keySet();
    }

    private boolean isNoun(String word) {
        assertNotNull(word);
        return words.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        assertNotNull(nounA);
        assertNotNull(nounB);
        assertNoun(nounA);
        assertNoun(nounB);
        return sap.length(words.get(nounA), words.get(nounB));
    }

    private String sap(String nounA, String nounB) {
        assertNotNull(nounA);
        assertNotNull(nounB);
        assertNoun(nounA);
        assertNoun(nounB);
        return synset.get(sap.ancestor(words.get(nounA), words.get(nounB)));
    }

    private void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument can not be null!");
        }
    }

    private void assertNoun(String word) {
        if (!isNoun(word)) {
            throw new IllegalArgumentException("Argument " + word + " must be a wordnet.WordNet noun!");
        }
    }

}
