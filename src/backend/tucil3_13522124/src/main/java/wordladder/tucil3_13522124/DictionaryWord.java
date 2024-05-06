package wordladder.tucil3_13522124;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;

public class DictionaryWord {
     /**
     * Memuat kamus kata-kata dari file teks.
     *
     * @param filename nama file kamus
     * @return himpunan kata-kata dalam kamus
     * @throws IOException jika terjadi kesalahan saat membaca file
     */
    public static Set<String> loadDictionary(String filename) throws IOException {
        return new HashSet<>(Files.readAllLines(Paths.get(filename)));
    }

     /**
     * Mengembalikan daftar kata-kata yang merupakan tetangga-tetangga dari suatu kata
     * dalam kamus, yaitu kata-kata yang hanya berbeda satu karakter.
     *
     * @param word kata yang akan dicari tetangganya
     * @param dictionary kamus kata-kata yang digunakan sebagai referensi
     * @return daftar kata-kata tetangga dari kata yang diberikan
     */
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
            wordArray[i] = originalChar;
        }

        return neighbors;
    }

    /**
     * Merekonstruksi jalur terpendek dari awal ke akhir menggunakan peta orang tua.
     *
     * @param parents peta yang berisi hubungan orang tua-anak dari kata-kata
     * @param start kata awal
     * @param end kata target
     * @return daftar kata yang mewakili jalur terpendek dari awal ke akhir
     */
    public static List<String> reconstructPath(Map<String, String> parents, String start, String end) {
        LinkedList<String> path = new LinkedList<>();
        for (String at = end; at != null; at = parents.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}