/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int n;
    private int opensites = 0;
    private final int len;
    private final WeightedQuickUnionUF forprec;
    private final WeightedQuickUnionUF forfull;
    private int virtualtopperc;
    private int virtualtopfull;
    private int virtualbottom;


    // top id n*n bottom id n*n+1
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        grid = new boolean[n][n];
        len = n * n + 2;
        forprec = new WeightedQuickUnionUF(len);
        forfull = new WeightedQuickUnionUF(len);
        virtualtopperc = forprec.find(len - 2);
        virtualtopfull = forfull.find(len - 2);
        virtualbottom = forprec.find(len - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (!grid[row - 1][col - 1]) {
            opensites += 1;
            grid[row - 1][col - 1] = true;
            int percpos = forprec.find((n * (row - 1) + (col - 1)));
            int fullpos = forfull.find((n * (row - 1) + (col - 1)));

            int posUp = (n * (row - 2) + (col - 1));
            int posDown = (n * (row) + (col - 1));
            int posRight = (n * (row - 1) + (col));
            int posLeft = (n * (row - 1) + (col - 2));
            virtualtopperc = forprec.find(len - 2);
            virtualtopfull = forfull.find(len - 2);
            virtualbottom = forprec.find(len - 1);
            // implement up down left right union
            // union for top line the top id
            if (row != 1 && isOpen(row - 1, col)) {
                forprec.union(percpos, forprec.find(posUp));
                forfull.union(fullpos, forfull.find(posUp));
            }
            if (row != n && isOpen(row + 1, col)) {
                forprec.union(percpos, forprec.find(posDown));
                forfull.union(fullpos, forfull.find(posDown));
            }

            if (row == 1) {
                forprec.union(percpos, virtualtopperc);
                forfull.union(fullpos, virtualtopfull);
            }

            if (row == n) {
                forprec.union(percpos, virtualbottom);
            }

            // left right
            if (col != 1 && isOpen(row, col - 1)) {
                forprec.union(percpos, forprec.find(posLeft));
                forfull.union(fullpos, forfull.find(posLeft));
            }
            if (col != n && isOpen(row, col + 1)) {
                forprec.union(percpos, forprec.find(posRight));
                forfull.union(fullpos, forfull.find(posRight));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        // if connected to top then full get id of it and connected?
        boolean full = false;
        if (forfull.connected(forfull.find((n * (row - 1) + (col - 1))), virtualtopfull)) {
            full = true;
        }

        return full;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean ispercolates = false;
        if (forprec.connected(virtualtopperc, virtualbottom)) {
            ispercolates = true;
        }
        return ispercolates;
    }
}
