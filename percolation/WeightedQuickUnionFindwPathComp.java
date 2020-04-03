/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class WeightedQuickUnionFindwPathComp {
    public int[] ids;
    public int[] sz;
    private int len;

    public WeightedQuickUnionFindwPathComp(int len) {
        // init sizes
        sz = new int[len];
        Arrays.fill(sz, 1);

        // init ids
        ids = new int[len];
        for (int i = 0; i < len; ++i) {
            ids[i] = i;
        }
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
}
