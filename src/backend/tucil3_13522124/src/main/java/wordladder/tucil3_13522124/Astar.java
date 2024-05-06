package wordladder.tucil3_13522124;

import java.util.*;
import java.io.IOException;

public class Astar {
     /**
     * Fungsi heuristik: menghitung jumlah karakter yang berbeda antara dua kata.
     *
     * @param word kata pertama
     * @param endWord kata kedua
     * @return jumlah karakter yang berbeda antara dua kata
     */
    private static int heuristic(String word, String endWord) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (i < endWord.length() && word.charAt(i) != endWord.charAt(i)) {
                count++;
            }
        }
        return count;
    };
    
    /**
     * Mencari jalur terpendek antara dua kata menggunakan algoritma A*.
     *
     * @param start kata awal
     * @param end kata target
     * @param dictionary himpunan kata-kata valid
     * @return objek Result yang berisi jalur terpendek dan jumlah node yang dikunjungi
     */
    public static Result wordLadder(String start, String end, Set<String> dictionary) {
        PriorityQueue<Pair<Integer, String>> openList = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        Set<String> visited = new HashSet<>();
        Map<String, String> parents = new HashMap<>();
        Map<String, Integer> costSoFar = new HashMap<>();
        int visitedCount = 0;
    
        openList.offer(new Pair<>(heuristic(start, end), start));
        costSoFar.put(start, 0);
    
        while (!openList.isEmpty()) {
            String currentWord = openList.poll().getValue();
            if (currentWord.equals(end)) {
                List<String> path = new ArrayList<>();
                while (currentWord != null) {
                    path.add(currentWord);
                    currentWord = parents.get(currentWord);
                }
                Collections.reverse(path);
                return new Result(path, visitedCount);
            }
    
            if (visited.add(currentWord)) {
                visitedCount++;
                int currentCost = costSoFar.get(currentWord);
    
                for (int i = 0; i < currentWord.length(); i++) {
                    StringBuilder sb = new StringBuilder(currentWord);
                    for (char c = 'a'; c <= 'z'; c++) {
                        sb.setCharAt(i, c);
                        String neighbor = sb.toString();
                        if (dictionary.contains(neighbor) && !visited.contains(neighbor)) {
                            int newCost = currentCost + 1;
                            if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                                costSoFar.put(neighbor, newCost);
                                int f = newCost + heuristic(neighbor, end);
                                openList.offer(new Pair<>(f, neighbor));
                                parents.put(neighbor, currentWord);
                            }
                        }
                    }
                }
            }
        }
    
        return new Result(Collections.emptyList(), visitedCount);
    }
    
    public static void main(String[] args) {
        try{
            String startWord = "base";
            String endWord = "root";
            Set<String> dictionary = DictionaryWord.loadDictionary("./Dictionary/words_alpha.txt");
            long startTime = System.currentTimeMillis();
            Result path = wordLadder(startWord, endWord, dictionary);
            long endTime = System.currentTimeMillis();
            System.out.println("Path: " + path);
            long duration = endTime - startTime;
            System.out.println("Waktu eksekusi: " + duration + " ms");
        }catch(IOException e){
            System.out.println("Error reading dictionary file: " + e.getMessage());
        }

    }
}