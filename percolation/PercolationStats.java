/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] perctresh;
    private int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        int gridend = n + 1;
        int totalsites = n * n;
        perctresh = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, gridend), StdRandom.uniform(1, gridend));
            }
            // store trial treshold for precolation open sites
            perctresh[i] = (double) perc.numberOfOpenSites() / (double) totalsites;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(perctresh);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(perctresh);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // if (args.length > 0) {
        //     try {
        //         n = Integer.parseInt(args[0]);
        //     }
        //     catch (NumberFormatException e) {
        //         System.err.println("Argument" + args[0] + " must be an integer.");
        //         System.exit(1);
        //     }
        //     try {
        //         trials = Integer.parseInt(args[1]);
        //     }
        //     catch (NumberFormatException e) {
        //         System.err.println("Argument" + args[1] + " must be an integer.");
        //         System.exit(1);
        //     }
        // }

        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println(
                "95% confidence interval = [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
    }
}
