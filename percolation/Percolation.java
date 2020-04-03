/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class Percolation {
    private int[][] grid;
    private int n;
    private int[] ids;
    private int[] sz;
    private int len;

    //top id n*n bottom id n*n+1
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        grid = new int[n][n];
        len = n * n + 2;

        // init sizes
        sz = new int[len];
        Arrays.fill(sz, 1);

        // init ids
        ids = new int[len];
        for (int i = 0; i < len; ++i) {
            ids[i] = i;
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (grid[row - 1][col - 1] == 0) {
            grid[row - 1][col - 1] = 1;
            int pos = (n * (row - 1) + (col - 1));
            int posUp = (n * (row - 2) + (col - 1));
            int posDown = (n * (row) + (col - 1));
            int posRight = (n * (row - 1) + (col));
            int posLeft = (n * (row - 1) + (col - 2));
            // implement up down left right union
            // union for top line the top id
            if (row != 1 && isOpen(row - 1, col)) {
                union(ids[pos], ids[posUp]);
            }
            if (row != n && isOpen(row + 1, col)) {
                union(ids[pos], ids[posDown]);
            }

            if (row == 1) {
                union(ids[len - 2], ids[pos]);
            }

            if (row == n) {
                union(ids[len - 1], ids[pos]);
            }

            // left right
            if (col != 1 && isOpen(row, col - 1)) {
                union(ids[pos], ids[posLeft]);
            }
            if (col != n && isOpen(row, col + 1)) {
                union(ids[pos], ids[posRight]);
            }

        }

    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        boolean siteopen = false;
        if (grid[row - 1][col - 1] == 1) {
            siteopen = true;
        }
        return siteopen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
        // if connected to top then full get id of it and connected?
        boolean full = false;
        if (connected(ids[(n * (row - 1) + (col - 1))], ids[len - 2])) {
            full = true;
        }

        return full;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int opensites = 0;
        for (int row = 0; row < grid.length; row += 1) {
            for (int col = 0; col < grid.length; col += 1) {
                opensites += grid[row][col];
            }
        }
        return opensites;
    }

    private int root(int i) {
        while (i != ids[i]) {
            ids[i] = ids[ids[i]];
            i = ids[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            ids[i] = j;
            sz[j] += sz[i];
        }
        else {
            ids[j] = i;
            sz[i] += sz[j];
        }
    }

    // does the system percolate?
    public boolean percolates() {
        boolean ispercolates = false;
        if (connected(ids[n * n], ids[n * n + 1])) {
            ispercolates = true;
        }
        return ispercolates;
    }

    public static void main(String[] args) {

    }
}
