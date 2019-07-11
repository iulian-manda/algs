package misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleZurichInterviewQuestion {

public static int findBestBlock(String[][] blocks, Set<String> requirements) {
    Map<String, List<Integer>> amenityToBlocks = parseAmenities(blocks);
    int minMaxDistance = Integer.MAX_VALUE;
    int liveAt = 0;
    for (int block = 0; block < blocks.length; block++) {
        int maxDistance = 0;
        for (String requirement : requirements) {
            int distance = findDistance(block, amenityToBlocks.get(requirement));
            if (distance > maxDistance)
                maxDistance = distance;
        }
        if (maxDistance < minMaxDistance) {
            minMaxDistance = maxDistance;
            liveAt = block;
        }
    }
    return liveAt + 1;
}

private static Map<String, List<Integer>> parseAmenities(String[][] blocks) {
    Map<String, List<Integer>> result = new HashMap<>();
    for (int block = 0; block < blocks.length; block++) {
        for (int amenity = 0; amenity < blocks[block].length; amenity++) {
            List<Integer> blocksList = result.computeIfAbsent(blocks[block][amenity], k -> new ArrayList<>());
            blocksList.add(block);
        }
    }
    return result;
}

private static int findDistance(int block, List<Integer> blocks) {
    int minDistance = Integer.MAX_VALUE;
    int left = 0;
    int right = blocks.size() - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int current = blocks.get(mid);
        int distance = Math.abs(block - current);
        if (distance < minDistance) minDistance = distance;
        if (block > current) left = mid + 1;
        else if (block < current) right = mid - 1;
        else return 0;
    }
    return minDistance;
}

    public static void main(String[] args) {
        String[][] blocks = {{"restaurant", "school"}, {"farmacy"}, {"restaurant"}, {"cinema", "parking"}, {}, {"gym"}};
        Set<String> requirements = new HashSet<>();
        requirements.add("parking");
        requirements.add("restaurant");
        requirements.add("gym");
        System.out.println(findBestBlock(blocks, requirements));
    }

}
