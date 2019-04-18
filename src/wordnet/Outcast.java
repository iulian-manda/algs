package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public static void main(String[] args) {
        wordnet.WordNet wordnet = new wordnet.WordNet(args[0], args[1]);
        wordnet.Outcast outcast = new wordnet.Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

    String outcast(String[] nouns) {
        int[] distance = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            String nounA = nouns[i];
            for (String nounB : nouns) {
                distance[i] += wordnet.distance(nounA, nounB);
            }
        }
        int max = distance[0];
        int maxIndex = 0;
        for (int i = 1; i < distance.length; i++) {
            if (max < distance[i]) {
                maxIndex = i;
                max = distance[i];
            }
        }
        return nouns[maxIndex];
    }

}
