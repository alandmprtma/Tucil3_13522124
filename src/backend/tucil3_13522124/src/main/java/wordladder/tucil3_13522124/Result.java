package wordladder.tucil3_13522124;

import java.util.List;

public class Result {
    private List<String> path;
    private int visitedCount;

    public Result(List<String> path, int visitedCount) {
        this.path = path;
        this.visitedCount = visitedCount;
    }

    public List<String> getPath() {
        return path;
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }
}