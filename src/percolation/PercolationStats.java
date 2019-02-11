package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double DOUBLE = 1.00000000000000000000D;
    private final double[] results;
    private final int t;
    private final static double CONFIDENCE_95 = 1.96;
    private double mean;
    private double stddev;
    private double sqrtStddev;
    private double sqrtT;
    private double confidenceLo;
    private double confidenceHi;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N or trials should be greater than 0!");
        }
        t = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = monteCarloSimulation(n);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        sqrtStddev = Math.sqrt(stddev);
        sqrtT = Math.sqrt(t);
        double calc = ((CONFIDENCE_95 * sqrtStddev) / sqrtT);
        confidenceLo =  mean - calc;
        confidenceHi = mean + calc;
    }

    private double monteCarloSimulation(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(row, col);
        }
        return (percolation.numberOfOpenSites() * DOUBLE) / (n * n * DOUBLE);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("%-25s= %.20f\n", "mean", percolationStats.mean());
        StdOut.printf("%-25s= %.20f\n", "stddev", percolationStats.stddev());
        StdOut.printf("%-25s= [%.20f, %.20f]\n", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}