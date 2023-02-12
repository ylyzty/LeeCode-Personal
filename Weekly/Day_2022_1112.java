package CodeCatalog.Weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import CodeCatalog.Daily.KMP;

/**
 * 91 双周赛
 */
public class Day_2022_1112 {
    public int distinctAverages(int[] nums) {
        Arrays.sort(nums);
        Set<Double> set = new HashSet<>();

        int i = 0, j = nums.length - 1;
        while (i < j) {
            double mid  = (nums[i] + nums[j]) / 2.0;
            if (!set.contains(mid)) {
                set.add(mid);
            }

            i += 1;
            j -= 1;
        }

        return set.size();
    }


    public static final int MOD = 1000000007;

    /**
     * dp[i]: 表示长度为 i 字符串构造方案数
     * dp[0] = 1, 空字符串有1种方案
     * 
     * dp[i] = (dp[i - zero] + dp[i - one]) % MOD
     * @param low
     * @param high
     * @param zero
     * @param one
     * @return
     */
    public int countGoodStrings(int low, int high, int zero, int one) {
        int res = 0;

        int[] dp = new int[high + 1];
        dp[0] = 1;

        
        for (int i = 1; i <= high; ++i) {
            if (i >= zero) {
                dp[i] = (dp[i] + dp[i - zero]) % MOD;
            }

            if (i >= one) {
                dp[i] = (dp[i] + dp[i - one]) % MOD;
            }

            if (i >= low) {
                res = (res + dp[i]) % MOD;
            }
        }

        return res;
    }


    public int ans = Integer.MIN_VALUE;
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        // Build Tree
        int n = amount.length;
        List<Integer>[] tree = new List[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];

            tree[x].add(y);
            tree[y].add(x);
        }

        int[] bobTime = new int[n];
        Arrays.fill(bobTime, n);

        dfs_bob(tree, bobTime, bob, -1, 0);
        // 区分根节点与叶子节点
        tree[0].add(-1);
        dfs_alice(tree, amount, bobTime, 0, -1, 0, 0);
        return ans;
    }

    /**
     * cur: 当前节点
     * last: 上一个节点
     * t: 当前次数
     * 
     * @param tree
     * @param bobTime
     * @param cur
     * @param last
     * @param t
     * @return
     */
    public boolean dfs_bob(List<Integer>[] tree, int[] bobTime, int cur, int last, int t) {
        if (cur == 0) {
            bobTime[cur] = t;
            return true;
        }

        for (int next : tree[cur]) {
            // 可用顺利走到根节点的路径才标记
            // next != last: 避免循环访问
            if (next != last && dfs_bob(tree, bobTime, next, cur, t + 1)) {
                bobTime[cur] = t;
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param tree: 树
     * @param amount: 分值数组
     * @param bobTime: bob路径上经过每个节点的时间数组
     * @param cur: 当前节点
     * @param last: 上一个节点
     * @param t: 时间
     * @param temp: 当前路径分值
     */
    public void dfs_alice(List<Integer>[] tree, int[] amount, int[] bobTime, int cur, int last, int t, int temp) {
        if (t < bobTime[cur]) {
            temp += amount[cur];
        }
        else if (t == bobTime[cur]) {
            temp += amount[cur] / 2;
        }

        // 到达叶节点
        if (tree[cur].size() == 1) {
            ans = Math.max(ans, temp);
            return;
        }

        for (int next : tree[cur]) {
            if (next != last) {
                dfs_alice(tree, amount, bobTime, next, cur, t + 1, temp);
            }
        }
    }


    public String[] splitMessage(String message, int limit) {
        int n = message.length();

        // 枚举分割个数 i, 计算其容量
        int i = 1;
        int cap =  0;
        int tail_length = 0;
        while (true) {
            if (i < 10) {
                tail_length = 5;
            }
            else if (i < 100) {
                if (i == 10) {
                    cap -= 9;
                }
                tail_length = 7;
            }
            else if (i < 1000) {
                if (i == 100) {
                    cap -= 99;
                }
                tail_length = 9;
            }
            else {
                if (i == 1000) {
                    cap -= 999;
                }
                tail_length = 11;
            }

            if (tail_length >= limit) {
                return new String[]{};
            }

            cap += limit - tail_length;

            if (cap >= n) {
                break;
            }

            i += 1;
        }

        String[] ans = new String[i];
        int index = 0;
        for (int j = 0; j < i; j++) {
            String tail = "<" + (j + 1) + "/" + i + ">";

            if (j == i - 1) {
                ans[j] = message.substring(index) + tail;
            }
            else {
                int len = limit - tail.length();
                ans[j] = message.substring(index, index + len) + tail;
                index += len;
            }
        }

        return ans;
    }


    public static void main(String[] args) {
        Day_2022_1112 test = new Day_2022_1112();
    }
    
}
