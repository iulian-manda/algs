package cracking.the.coding.interview.java;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Problem8 {

    static List<Integer> getRandomSubset(List<Integer> list) {
        Random random = new Random();
        return list.stream().filter(el -> random.nextBoolean()).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getRandomSubset(Arrays.asList(1, 2, 3, 4, 5, 6)));
    }

}
