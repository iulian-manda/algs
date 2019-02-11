package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF unionFind;
    private boolean[][] isOpen;
    private boolean[][] isFull;
    private final int n;
    private int openSites;
    private final int topVirtualSite;
    private final int bottomVirtualSite;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0!");
        }
        this.n = n;
        topVirtualSite = n * n;
        bottomVirtualSite = n * n + 1;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        isOpen = new boolean[n][n];
        isFull = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            unionFind.union(topVirtualSite, i);
            unionFind.union(bottomVirtualSite, (n * (n - 1)) + i);
        }
    }
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        if (isOpen[i][j]) {
            return;
        }
        isOpen[i][j] = true;
        openSites++;
        int currentSite = i * n + j;
        // above site
        if (i > 0 && isOpen[i - 1][j]) {
            unionFind.union(currentSite, (i - 1) * n + j);
        }
        // left site
        if (j > 0 && isOpen[i][j - 1]) {
            unionFind.union(currentSite, i * n + (j - 1));
        }
        // right site
        if (j < n - 1 && isOpen[i][j + 1]) {
            unionFind.union(currentSite, i * n + (j + 1));
        }
        // below site
        if (i < n - 1 && isOpen[i + 1][j]) {
            unionFind.union(currentSite, (i + 1) * n + j);
        }
    }
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        return isOpen[i][j];
    }
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        if (!isOpen[i][j]) {
            return false;
        }
        if (isFull[i][j]) {
            return true;
        }
        isFull[i][j] = unionFind.connected(topVirtualSite, i * n + j);
        return isFull[i][j];
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }
    // does the system percolate?
    public boolean percolates() {
        if(n == 1 && !isOpen[0][0]) return false;
        return unionFind.connected(topVirtualSite, bottomVirtualSite);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row: " + row + " or column: " + col + " is outside of its prescribed range!");
        }
    }
/*
    void printOpenSites() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(isOpen[i][j]) {
                    StdOut.print("1 ");
                } else {
                    StdOut.print("0 ");
                }
            }
            StdOut.println();
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(new File("/Users/iulian.manda/IdeaProjects/percolation/resources/input20.txt")));
        int n = sc.nextInt();
        Percolation percolation = new Percolation(n);
        int count = 0;
        while (sc.hasNext()) {
            int row = sc.nextInt();
            int col = sc.nextInt();
            percolation.open(row, col);
            StdOut.println(++count + ": " + row + ", " + col + ": " + percolation.isFull(row, col));
        }
        StdOut.println("Percolates: " + percolation.percolates());
    }
    */
}
