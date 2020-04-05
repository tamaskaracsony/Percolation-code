/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int gridend = n + 1;
        int totalsites = n * n;
        // perform independent trials on an n-by-n grid
        double[] perctresh = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, gridend), StdRandom.uniform(1, gridend));
            }
            // store trial treshold for precolation open sites
            perctresh[i] = (double) perc.numberOfOpenSites() / (double) totalsites;
        }
        mean = StdStats.mean(perctresh);
        stddev = StdStats.stddev(perctresh);
        double confidencefrac = (1.96 * stddev) / Math.sqrt(trials);
        confidenceLo = mean - confidencefrac;
        confidenceHi = mean + confidencefrac;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean = " + ps.mean);
        StdOut.println("stddev = " + ps.stddev);
        StdOut.println(
                "95% confidence interval = [" + ps.confidenceLo + "," + ps.confidenceHi + "]");
    }
}
