package wordladder.tucil3_13522124;

import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {
    private String message;
    private Object data;
    private long executionTime;
    private int visitedNode;
    private long executionMemory;

    // Constructor
    public Response(String message, Object data, long executionTime, int visitedNode, long executionMemory) {
        this.message = message;
        this.data = data;
        this.executionTime = executionTime;
        this.visitedNode = visitedNode;
        this.executionMemory = executionMemory;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public int getVisitedNode() {
        return visitedNode;
    }

    public void setVisitedNode(int visitedNode){
        this.visitedNode = visitedNode;
    }

    public long getExecutionMemory() {
        return executionMemory;
    }

    public void setExecutionMemory(int executionMemory){
        this.executionMemory = executionMemory;
    }

    // Convert this Response object into a JSON ResponseEntity
    public ResponseEntity<String> json() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Error processing JSON\"}");
        }
    }
}