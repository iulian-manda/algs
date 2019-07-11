package cracking.the.coding.interview.moderate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Problem2 {

    public static void main(String[] args) throws Exception {
        WordCountCache cache = new WordCountCache();
        String book = new String(Files.readAllBytes(Paths.get(args[0])));
        long start = System.currentTimeMillis();
        System.out.println("cRosSed: " + cache.getWordCount(book, "cRosSed"));
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start));
        System.out.println("piCtUre: " + cache.getWordCount(book, "pIctUre"));
        long end2 = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end2 - end));
    }

    static class WordCountCache {
        private Map<String, Map<String, Integer>> cache = new HashMap<>();

        Integer getWordCount(String book, String word) {
            Map<String, Integer> bookWords = cache.computeIfAbsent(book, this::computeWordCount);
            return bookWords.get(word.toLowerCase());
        }

        private Map<String, Integer> computeWordCount(String book) {
            String[] words = book.split("[ \\.,\\n]");
            Map<String, Integer> wordCount = new HashMap<>();
            for (String word : words) {
                String normalized = word.toLowerCase();
                Integer count = wordCount.computeIfAbsent(normalized, k -> 0);
                wordCount.put(normalized, count + 1);
            }
            return wordCount;
        }
    }

}

