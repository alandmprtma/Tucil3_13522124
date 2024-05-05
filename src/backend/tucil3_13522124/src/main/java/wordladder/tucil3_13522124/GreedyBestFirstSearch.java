package wordladder.tucil3_13522124;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class GreedyBestFirstSearch {
    public static Result findWordLadder(String start, String end, Set<String> dictionary) {
        PriorityQueue<Pair<String, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        int visitedCount = 0;  // Inisialisasi counter untuk node yang dikunjungi
    
        queue.offer(new Pair<>(start, heuristic(start, end)));
        visited.add(start);
        visitedCount++;
    
        while (!queue.isEmpty()) {
            Pair<String, Integer> node = queue.poll();
            String currentWord = node.getKey();
    
            if (currentWord.equals(end)) {
                return new Result(reconstructPath(parent, start, end), visitedCount);
            }
    
            for (String neighbor : DictionaryWord.getNeighbors(currentWord, dictionary)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, currentWord);
                    queue.offer(new Pair<>(neighbor, heuristic(neighbor, end)));
                    visitedCount++;  // Menginkremen jumlah node yang dikunjungi
                }
            }
        }
    
        return new Result(Collections.emptyList(), visitedCount);  // Jika tidak ditemukan jalur
    }

    private static List<String> reconstructPath(Map<String, String> parent, String start, String end) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private static int heuristic(String word, String end) {
        int mismatch = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != end.charAt(i)) {
                mismatch++;
            }
        }
        return mismatch;
    }

    
}
