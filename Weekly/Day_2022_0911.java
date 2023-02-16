package Weekly;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Day_2022_0911 {

    /**
     * LeeCode 6176 出现最频繁的偶数元素
     * 哈希表统计
     * @param nums
     * @return
     */
    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (num % 2 != 0) {
                continue;
            }

            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        if (map.isEmpty()) {
            return -1;
        }

        // 出现最频繁的最小偶数
        int times = 0;
        int res = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > times) {
                res = entry.getKey();
                times = entry.getValue();
            }
            else if (entry.getValue() == times) {
                res = Math.min(res, entry.getKey());
            }
        }

        return res;
    }


    /**
     * LeeCode 6177: 子字符串的最优划分
     * 贪心 -> 时间复杂度 O(26 * s.length)
     * 
     * 维护一个状态数组flags, 若出现重复字母则清空该数组, res=res+1
     * @param s
     * @return
     */
    public int partitionString(String s) {
        int res = 1;
        boolean[] flags = new boolean[26];

        for (char ch : s.toCharArray()) {
            int index = ch - 'a';
            if (flags[index]) {
                res += 1;
                flags = new boolean[26];
            }

            flags[index] = true;
        }

        return res;
    }
    
    /**
     * LeeCode 6178 将区间分为最少数组
     * 贪心 + 堆 + 排序
     * 
     * 对数组进行排序, 根据起始位置从小到大
     * 维护一个区间的 end 值的优先队列
     * 每次贪心的选择 end 最小的区间进行判断:
     *     start[i] > end  -> 加入该分组
     *     start[i] <= end -> 无法加入任何分组, 重新开一个分组
     * 
     * 最后 return priorityQueue.size()
     * @param intervals
     * @return
     */
    public int minGroups(int[][] intervals) {
        // 根据数组的第一列值从小到大排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }

                return o1[0] - o2[0];
            }
        });

        // 小顶堆
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int[] interval: intervals) {
            if (!priorityQueue.isEmpty() && priorityQueue.peek() < interval[0]) {
                priorityQueue.poll();
            }

            priorityQueue.offer(interval[1]);
        }

        return priorityQueue.size();
    }


    // Build Segment Tree Start!
    public int[] tree;

    private void update(int cur, int left, int right, int idx, int value) {
        if (left == right) {
            tree[cur] = value;
            return;
        }

        int mid = left + (right - left) / 2;
        
        if (idx <= mid) {
            update(cur * 2, left, mid, idx, value);
        }
        else {
            update(cur * 2 + 1, mid + 1, right, idx, value);
        }

        tree[cur] = Math.max(tree[cur * 2], tree[cur * 2 + 1]);
    }

    /**
     * 查询区间   -> [L, R]
     * 当前区间   -> [left, right]
     * 当前根结点 -> [root]
     * @param cur
     * @param left
     * @param right
     * @param L
     * @param R
     * @return
     */
    private int query(int cur, int left, int right, int L, int R) {
        if (L <= left && R <= right) {
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


    /**
     * 动态规划 + 线段树
     * 时间复杂度 O( N(logn) )
     * dp[i] 表示以 nums[i] 结尾的最长递增子序列
     * dp[i] = Math.max(dp[i - k] ~ dp[i - 1]) + 1
     * @param nums
     * @param k
     * @return
     */
    public int lengthOfLIS(int[] nums, int k) {
        int max_value = 0;
        for (int num : nums) {
            max_value = Math.max(max_value, num);
        }

        // 初始化线段树
        tree = new int[4 * max_value];

        for (int num : nums) {
            // 对于数字为 1 的情况, 不存在满足的条件, 保存长度为 1 即可
            if (num == 1) {
                update(1, 1, max_value, 1, 1);
            }
            else {
                int res = 1 + query(1, 1, max_value, Math.max(num - k, 1), num - 1);
                update(1, 1, max_value, num, res);
            }
        }

        return tree[1];
    }
}