package wordladder.tucil3_13522124;

import java.io.IOException;
import java.util.*;

public class UCS {
    // Get neighbors that differ by exactly one letter
    public static Result wordLadder(String start, String end, Set<String> dictionary) {
        PriorityQueue<Pair<Integer, String>> openList = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        Map<String, Integer> cost = new HashMap<>();
        Map<String, String> parents = new HashMap<>();
        Set<String> visited = new HashSet<>();
        int visitedCount = 0;
    
        openList.offer(new Pair<>(0, start));
        cost.put(start, 0);
    
        while (!openList.isEmpty()) {
            Pair<Integer, String> current = openList.poll();
            String currentWord = current.getValue();
    
            if (currentWord.equals(end)) {
                return new Result(reconstructPath(parents, start, end), visitedCount);
            }
    
            if (!visited.contains(currentWord)) {
                visited.add(currentWord);
                visitedCount++;
                List<String> neighbors = DictionaryWord.getNeighbors(currentWord, dictionary);
                for (String neighbor : neighbors) {
                    int newCost = cost.get(currentWord) + 1;
                    if (!cost.containsKey(neighbor) || newCost < cost.get(neighbor)) {
                        parents.put(neighbor, currentWord);
                        cost.put(neighbor, newCost);
                        openList.offer(new Pair<>(newCost, neighbor));
                    }
                }
            }
        }
    
        return new Result(Collections.emptyList(), visitedCount);
    }

    private static List<String> reconstructPath(Map<String, String> parents, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = end; at != null; at = parents.get(at)) {
            path.addFirst(at);
        }
        return path;
    }

    public static void main(String[] args) {
        try {
            Set<String> dictionary = DictionaryWord.loadDictionary("./Dictionary/words_alpha.txt");
            String startWord = "volunteer";
            String endWord = "reception";
            long startTime = System.currentTimeMillis();
            Result path = wordLadder(startWord, endWord, dictionary);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Path: " + path);
            System.out.println("Waktu eksekusi: " + duration + " ms");
        } catch (IOException e) {
            System.out.println("Error reading dictionary file: " + e.getMessage());
        }
    }
}