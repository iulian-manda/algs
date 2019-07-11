package cracking.the.coding.interview.recursion.and.dynamic.programming;

import java.util.HashSet;
import java.util.LinkedList;

public class Problem2 {

    public static void main(String[] args) {
        boolean[][] grid = {
                {false, false, true, false, false},
                {true, false, false, false, false},
                {false, false, false, false, false}
        };
        for (Position p : findPath(grid)) {
            System.out.println(p);
        }
    }

static boolean findPath(boolean[][] grid, int r, int c, LinkedList<Position> path, HashSet<Position> failedPoints) {
    if (r >= grid.length || c >= grid[0].length) return false;
    if (grid[r][c]) return false;
    boolean isFinish = r == grid.length - 1 && c == grid[0].length - 1;
    Position p = new Position(r, c);
    if (failedPoints.contains(p)) return false;
    if (isFinish || findPath(grid, r + 1, c, path, failedPoints) || findPath(grid, r, c + 1, path, failedPoints)) {
        path.addFirst(p);
        return true;
    }
    failedPoints.add(p);
    return false;
}

static Iterable<Position> findPath(boolean[][] grid) {
    if (grid.length == 0 || grid[0].length == 0) return null;
    LinkedList<Position> path = new LinkedList<>();
    HashSet<Position> failedPoints = new HashSet<>();
    if (findPath(grid, 0, 0, path, failedPoints)) return path;
    else return null;
}

static class Position {
    int x, y;

    public Position(int i, int j) {
        x = i;
        y = j;
    }

    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}


}
