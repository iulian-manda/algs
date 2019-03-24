package misc;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

public class CountTriplets {

    // Complete the countTriplets function below.
    private static long countTriplets(List<Long> arr, long r) {
        long count = 0;
        Map<Long, SortedSet<Integer>> multipleR = new HashMap<>();
        int index = -1;
        for (Long a : arr) {
            index++;
            if (a % r != 0) continue;
            SortedSet<Integer> indexes = multipleR.computeIfAbsent(a, k -> new TreeSet<>());
            indexes.add(index);
        }
        Long[] a = new Long[arr.size()];
        a = arr.toArray(a);
        for (int i = 0; i < a.length - 2; i++) {
            Long b = a[i] * r;
            SortedSet<Integer> j = multipleR.get(b);
            if (j == null) continue;
            SortedSet<Integer> k = multipleR.get(b * r);
            if (k == null) continue;
            SortedSet<Integer> setJ = j.subSet(i+1, k.last());
            count+= setJ.size() * k.tailSet(setJ.first() + 1).size();
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

        long ans = countTriplets(arr, r);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
