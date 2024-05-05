package wordladder.tucil3_13522124;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class DictionaryWord {
    public static Set<String> loadDictionary(String filename) throws IOException {
        return new HashSet<>(Files.readAllLines(Paths.get(filename)));
    }

    public static List<String> getNeighbors(String word, Set<String> dictionary) {
        List<String> neighbors = new ArrayList<>();
        char[] wordArray = word.toCharArray();
        
        for (int i = 0; i < word.length(); i++) {
            char originalChar = wordArray[i];
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch != originalChar) {
                    wordArray[i] = ch;
                    String newWord = new String(wordArray);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            wordArray[i] = originalChar;  // Reset back to original after changes
        }

        return neighbors;
    }
}