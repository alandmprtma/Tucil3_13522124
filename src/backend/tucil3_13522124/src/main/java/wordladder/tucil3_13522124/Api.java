package wordladder.tucil3_13522124;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Api {
    @GetMapping(value = "/api", produces = "application/json")
    public ResponseEntity<Object> api(
            @RequestParam(value = "start") String startWord,
            @RequestParam(value = "target") String endWord,
            @RequestParam(value = "algorithm") String method){
        startWord = startWord.toLowerCase();
        endWord = endWord.toLowerCase();
        Set<String> dictionary = null;
        try {
            dictionary = DictionaryWord.loadDictionary("src/main/java/wordladder/tucil3_13522124/Dictionary/words_alpha.txt");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading dictionary file: " + e.getMessage());
        }
        if(!dictionary.contains(startWord) || !dictionary.contains(endWord)){
            return ResponseEntity.ok(new Response("Start Word or End Word is not a valid word.",null,0,0,0));
        }
        Result path = null;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startTime = System.nanoTime();
        long memoryBefore = runtime.freeMemory();
        if(method.equals("UCS")){
            path = UCS.wordLadder(startWord, endWord, dictionary);
        }
        else if(method.equals("ASTAR")){
            path = Astar.wordLadder(startWord, endWord, dictionary);
        } else {
            path = GreedyBestFirstSearch.findWordLadder(startWord, endWord, dictionary);
        }
        long memoryAfter = runtime.freeMemory();
        long endTime = System.nanoTime();
        double executionTime = (endTime-startTime)/1000000.0;
        long executionMemory = (memoryBefore - memoryAfter)/1000;
        return ResponseEntity.ok(new Response(null, path.getPath(), executionTime, path.getVisitedCount(),executionMemory));
    }
}