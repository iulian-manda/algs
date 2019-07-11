package cracking.the.coding.interview.arrays.and.strings;

import java.util.HashSet;
import java.util.Set;

public class Problem8 {

    public static void main(String[] args) {
        int[][] m = {{1, 0, 3, 4, 5}, {5, 6, 7, 8, 0}, {9, 10, 11, 12, 2}, {13, 0, 15, 16, 1}};
        print(m);
        zeroMatrix(m);
        print(m);
    }

    private static void print(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%2d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    static void zeroMatrix(int[][] matrix) {
        if (matrix.length == 0) return;
        Set<Point> zeroPoints = findZeroPoints(matrix);
        for (Point zeroPoint : zeroPoints) {
            setRowToZero(matrix, zeroPoint.x);
            setColumnToZero(matrix, zeroPoint.y);
        }
    }

    static Set<Point> findZeroPoints(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        Set<Point> zeroPoints = new HashSet();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    zeroPoints.add(new Point(i, j));
                    break;
                }
            }
        }
        return zeroPoints;
    }

    static void setRowToZero(int[][] matrix, int row) {
        int m = matrix[0].length;
        for (int j = 0; j < m; j++) {
            matrix[row][j] = 0;
        }
    }

    static void setColumnToZero(int[][] matrix, int column) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            matrix[i][column] = 0;
        }
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
