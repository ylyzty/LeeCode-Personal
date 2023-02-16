package DynamicProgramming;

public class SegmentTree {
    /**
     * LeeCode 300: 最长上升子序列
     * Segment Tree
     * array = [min_value, min_vaue + 1, ..., max_value - 1, max_value]
     * 初始状态 array 全为 0
     * 
     * 线段树维护的最大值的意义是以 array[i] 结尾的最长上升子序列
     * 
     * 遍历 nums 的过程中, 逐步构建线段树, 最后返回线段树最大值即 tree[1]
     * @param nums
     * @return
     */
    int[] tree;
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int res;

        int max_value = Integer.MIN_VALUE;
        int min_value = Integer.MAX_VALUE;
        for (int num : nums) {
            max_value = Math.max(num, max_value);
            min_value = Math.min(num, min_value);
        }
        tree = new int[4 * (max_value - min_value + 1)];

        for (int num : nums) {
            if (num == min_value) {
                update(1, 1, max_value - min_value + 1, 1, 1);
            }
            else {
                res = 1 + query(1, 1, max_value - min_value + 1, 1, num - min_value);
                update(1, 1, max_value - min_value + 1, num - min_value + 1, res);
            }
        }

        return tree[1];
    }


    public void update(int cur, int left, int right, int index, int val) {
        if (left == right) {
            tree[cur] = val;
            return;
        }

        int mid = left + (right - left) / 2;
        if (index <= mid) {
            update(cur * 2, left, mid, index, val);
        }
        else {
            update(cur * 2 + 1, mid + 1, right, index, val);
        }

        tree[cur] = Math.max(tree[cur * 2], tree[cur * 2 + 1]);
    }

    public int query(int cur, int left, int right, int L, int R) {
        if (L <= left && R >= right) {
            return tree[cur];
        }

        int res = 0;
        int mid = left + (right - left) / 2;
        if (L <= mid) {
            res = query(cur * 2, left, mid, L, R);
        }
        if (R > mid) {
            res = Math.max(res, query(cur * 2 + 1, mid + 1, right, L, R));
        }

        return res;
    }
}
