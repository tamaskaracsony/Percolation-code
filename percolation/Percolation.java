/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int len;
    private WeightedQuickUnionFindwPathComp forprec;
    private WeightedQuickUnionFindwPathComp forfull;

    // top id n*n bottom id n*n+1
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        grid = new boolean[n][n];
        len = n * n + 2;
        forprec = new WeightedQuickUnionFindwPathComp(len);
        forfull = new WeightedQuickUnionFindwPathComp(len);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            int pos = (n * (row - 1) + (col - 1));
            int posUp = (n * (row - 2) + (col - 1));
            int posDown = (n * (row) + (col - 1));
            int posRight = (n * (row - 1) + (col));
            int posLeft = (n * (row - 1) + (col - 2));
            // implement up down left right union
            // union for top line the top id
            if (row != 1 && isOpen(row - 1, col)) {

                forprec.union(forprec.ids[pos], forprec.ids[posUp]);
                forfull.union(forfull.ids[pos], forfull.ids[posUp]);
            }
            if (row != n && isOpen(row + 1, col)) {
                forprec.union(forprec.ids[pos], forprec.ids[posDown]);
                forfull.union(forfull.ids[pos], forfull.ids[posDown]);
            }

            if (row == 1) {
                forprec.union(forprec.ids[pos], forprec.ids[len - 2]);
                forfull.union(forfull.ids[pos], forfull.ids[len - 2]);
            }

            if (row == n) {
                forprec.union(forprec.ids[pos], forprec.ids[len - 1]);
            }

            // left right
            if (col != 1 && isOpen(row, col - 1)) {
                forprec.union(forprec.ids[pos], forprec.ids[posLeft]);
                forfull.union(forfull.ids[pos], forfull.ids[posLeft]);
            }
            if (col != n && isOpen(row, col + 1)) {
                forprec.union(forprec.ids[pos], forprec.ids[posRight]);
                forfull.union(forfull.ids[pos], forfull.ids[posRight]);
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
        if (forfull.connected(forfull.ids[(n * (row - 1) + (col - 1))], forfull.ids[len - 2])) {
            full = true;
        }

        return full;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int opensites = 0;
        for (int row = 0; row < grid.length; row += 1) {
            for (int col = 0; col < grid.length; col += 1) {
                if (grid[row][col]) {
                    opensites += 1;
                }
            }
        }
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean ispercolates = false;
        if (forprec.connected(forprec.ids[n * n], forprec.ids[n * n + 1])) {
            ispercolates = true;
        }
        return ispercolates;
    }

    public static void main(String[] args) {

    }
}
