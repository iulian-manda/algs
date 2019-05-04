package seam;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        throwIfNull(picture);
        this.picture = new Picture(picture);
        computeEnergy();
    }

//    public static void main(String[] args) {
//        Picture picture = new Picture(args[0]);
//        SeamCarver seamCarver = new SeamCarver(picture);
//        int[] seam = seamCarver.findHorizontalSeam();
//        for (int y = 0; y < seamCarver.height(); y++) {
//            for (int x = 0; x < seamCarver.width(); x++) {
//                StdOut.print(" " + seamCarver.energy(x, y));
//            }
//            StdOut.print('\n');
//        }
//        for (int i = 0; i < seam.length; i++) {
//            StdOut.print(" " + seam[i]);
//        }
//    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        throwIfOutsideWidthRange(x);
        throwIfOutsideHeightRange(y);
        return energy[x][y];
    }

    private void computeEnergy() {
        energy = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
                    energy[x][y] = 1000d;
                    continue;
                }
                Color top = picture.get(x, y - 1);
                Color bottom = picture.get(x, y + 1);
                Color left = picture.get(x - 1, y);
                Color right = picture.get(x + 1, y);

                double rX = left.getRed() - right.getRed();
                double gX = left.getGreen() - right.getGreen();
                double bX = left.getBlue() - right.getBlue();
                double dX = rX * rX + gX * gX + bX * bX;

                double rY = top.getRed() - bottom.getRed();
                double gY = top.getGreen() - bottom.getGreen();
                double bY = top.getBlue() - bottom.getBlue();
                double dY = rY * rY + gY * gY + bY * bY;

                energy[x][y] = Math.sqrt(dX + dY);
            }
        }
    }

    public int[] findVerticalSeam() {
        double[][] distTo = new double[width()][height()];
        Node[][] edgeTo = new Node[width()][height()];
        Queue<Node> queue = new Queue<>();

        for (int i = 0; i < width(); i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
            distTo[i][0] = 0;
            queue.enqueue(new Node(i, 0));
        }
        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            for (int i = -1; i <= 1; i++) {
                int x1 = current.x + i;
                int y1 = current.y + 1;
                if (x1 < 0 || x1 >= width() || y1 < 0 || y1 >= height()) {
                    continue;
                }
                double e = energy(x1, y1);
                if (distTo[x1][y1] > distTo[current.x][current.y] + e) {
                    distTo[x1][y1] = distTo[current.x][current.y] + e;
                    edgeTo[x1][y1] = current;
                    queue.enqueue(new Node(x1, y1));
                }
            }
        }
        double min = Double.POSITIVE_INFINITY;
        int minIndex = 0;
        for (int i = 0; i < width(); i++) {
            if (distTo[i][height() - 1] < min) {
                min = distTo[i][height() - 1];
                minIndex = i;
            }
        }
        Stack<Node> stack = new Stack<>();
        Node n = edgeTo[minIndex][height() - 1];
        while (n != null) {
            stack.push(n);
            n = edgeTo[n.x][n.y];
        }
        int[] result = new int[height()];
        int i = 0;
        while (!stack.isEmpty()) {
            result[i++] = stack.pop().x;
        }
        result[i] = minIndex;
        return result;
    }

    public int[] findHorizontalSeam() {
        double[][] distTo = new double[width()][height()];
        Node[][] edgeTo = new Node[width()][height()];
        Queue<Node> queue = new Queue<>();

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                distTo[j][i] = Double.POSITIVE_INFINITY;
            }
            distTo[0][i] = 0;
            queue.enqueue(new Node(0, i));
        }
        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            for (int i = -1; i <= 1; i++) {
                int x1 = current.x + 1;
                int y1 = current.y + i;
                if (x1 < 0 || x1 >= width() || y1 < 0 || y1 >= height()) {
                    continue;
                }
                double e = energy(x1, y1);
                if (distTo[x1][y1] > distTo[current.x][current.y] + e) {
                    distTo[x1][y1] = distTo[current.x][current.y] + e;
                    edgeTo[x1][y1] = current;
                    queue.enqueue(new Node(x1, y1));
                }
            }
        }
        double min = Double.POSITIVE_INFINITY;
        int minIndex = 0;
        for (int i = 0; i < height(); i++) {
            if (distTo[width() - 1][i] < min) {
                min = distTo[width() - 1][i];
                minIndex = i;
            }
        }
        Stack<Node> stack = new Stack<>();
        Node n = edgeTo[width() - 1][minIndex];
        while (n != null) {
            stack.push(n);
            n = edgeTo[n.x][n.y];
        }
        int[] result = new int[width()];
        int i = 0;
        while (!stack.isEmpty()) {
            result[i++] = stack.pop().y;
        }
        result[i] = minIndex;
        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        verifyHorizontalSeam(seam);
        Picture newPicture = new Picture(width(), height() - 1);
        double[][] newEnergy = new double[width()][height() - 1];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (y < seam[x]) {
                    newPicture.setRGB(x, y, picture.getRGB(x, y));
                    newEnergy[x][y] = energy[x][y];
                } else if (y > seam[x]) {
                    newPicture.setRGB(x, y - 1, picture.getRGB(x, y));
                    newEnergy[x][y - 1] = energy[x][y];
                }
            }
        }
        picture = newPicture;
        energy = newEnergy;
    }

    public void removeVerticalSeam(int[] seam) {
        verifyVerticalSeam(seam);
        Picture newPicture = new Picture(width() - 1, height());
        double[][] newEnergy = new double[width() - 1][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                if (x < seam[y]) {
                    newPicture.setRGB(x, y, picture.getRGB(x, y));
                    newEnergy[x][y] = energy[x][y];
                } else if (x > seam[y]) {
                    newPicture.setRGB(x - 1, y, picture.getRGB(x, y));
                    newEnergy[x - 1][y] = energy[x][y];
                }
            }
        }
        picture = newPicture;
        energy = newEnergy;
    }

    private void verifyHorizontalSeam(int[] seam) {
        throwIfNull(seam);
        if (height() <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }
        verifySeam(seam);
    }

    private void verifyVerticalSeam(int[] seam) {
        throwIfNull(seam);
        if (width() <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }
        verifySeam(seam);
    }

    private void verifySeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void throwIfNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private void throwIfOutsideWidthRange(int x) {
        if (x < 0 || x > width() - 1) {
            throw new IllegalArgumentException("x: " + x
                    + " is outside of the range [0, " + width() + "]");
        }
    }

    private void throwIfOutsideHeightRange(int y) {
        if (y < 0 || y > height() - 1) {
            throw new IllegalArgumentException("x: " + y
                    + " is outside of the range [0, " + height() + "]");
        }
    }

    private class Node {
        private final int x;
        private final int y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
