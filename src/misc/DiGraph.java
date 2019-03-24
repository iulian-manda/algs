package misc;

import java.util.*;

public class DiGraph {

    private Map<String, Set<AbstractMap.SimpleEntry<String, Double>>> adj;

    public DiGraph() {
        adj = new HashMap<>();
    }

    public void add(String c1, String c2, Double conv) {
        if (adj.containsKey(c1)) {
            adj.get(c1).add(new AbstractMap.SimpleEntry<>(c2, conv));
        } else {
            Set<AbstractMap.SimpleEntry<String, Double>> set = new HashSet<>();
            set.add(new AbstractMap.SimpleEntry<>(c2, conv));
            adj.put(c1, set);
        }
    }

    Set<AbstractMap.SimpleEntry<String, Double>> adj(String node) {
        return adj.get(node);
    }

}