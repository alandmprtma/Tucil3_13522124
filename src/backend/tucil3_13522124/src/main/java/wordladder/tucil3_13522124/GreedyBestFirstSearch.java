package wordladder.tucil3_13522124;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class GreedyBestFirstSearch {
     /**
     * Mencari jalur terpendek antara dua kata menggunakan algoritma Greedy Best-First Search.
     *
     * @param start kata awal
     * @param end kata target
     * @param dictionary himpunan kata-kata valid
     * @return objek Result yang berisi jalur terpendek dan jumlah node yang dikunjungi
     */
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
                return new Result(DictionaryWord.reconstructPath(parent, start, end), visitedCount);
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
    /**
     * Menghitung nilai heuristik antara dua kata.
     *
     * @param word kata pertama
     * @param end kata kedua
     * @return jumlah karakter yang berbeda antara dua kata
     */
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
