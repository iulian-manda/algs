package misc;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/* The currency conversions are given in the following order:
        EUR GBP 1.2
        GBP RON 0.2
        ...
   Find the conversion rate between two arbitrary currencies.
 */
public class GoogleInterviewQuestion {

    private static Double bfs(DiGraph G, String source, String target) {
        Set<String> visited = new HashSet<>();
        Queue<SimpleEntry<String,Double>> queue = new LinkedList<>();

        visited.add(source);
        queue.add(new SimpleEntry<>(source, 1.0D));

        while (!queue.isEmpty()) {
            SimpleEntry<String, Double> current = queue.remove();
            if (current.getKey().equals(target)) {
                return current.getValue();
            }
            if (G.adj(current.getKey()) == null) {
                continue;
            }
            for (SimpleEntry<String, Double> next : G.adj(current.getKey())) {
                if (!visited.contains(next.getKey())) {
                    visited.add(next.getKey());
                    queue.add(new SimpleEntry<>(next.getKey(), next.getValue() * current.getValue()));
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {

        DiGraph G = new DiGraph();

        G.add("EUR", "RON", 4.7);
        G.add("RON", "EUR", 0.2);
        G.add("RON", "USD", 0.23);

        System.out.println(bfs(G, "EUR", "USD"));
    }

}
