package Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Day_2022_1106 {
    public int[] applyOperations(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; ++i) {
            if (nums[i] == nums[i + 1]) {
                nums[i] = 2 * nums[i];
                nums[i + 1] = 0;
            }
        }

        int[] res = new int[n];

        int index = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                res[index++] = nums[i];
            }
        }

        return res;
    }


    public long maximumSubarraySum(int[] nums, int k) {
        int n = nums.length;

        if (k > n) {
            return 0;
        }

        
        Map<Integer, Integer> map = new HashMap<>();
        long res = 0;
        long sum = 0;
        int left = 0, right = 0;

        while (right < n) {
            sum += nums[right];
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
            
            if (right - left + 1 == k) {
                if (map.size() == k) {
                    res = Math.max(res, sum);
                }

                sum -= nums[left];
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                }

                left += 1;
            }

            right += 1;
        }

        return res;
    }


    public long totalCost(int[] costs, int k, int candidates) {
        int n = costs.length;
        long res = 0;

        if (candidates * 2 >= n) {
            Arrays.sort(costs);
            for (int i = 0; i < k; i++) {
                res += costs[i];
            }

            return res;
        }

        // 小顶堆模拟
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (costs[o1] == costs[o2]) {
                return o1 - o2;
            }

            return costs[o1] - costs[o2];
        });


        for (int i = 0; i < candidates; i++) {
            priorityQueue.offer(i);
            priorityQueue.offer(n - i - 1);
        }

        int left = candidates;
        int right = n - candidates - 1;

        while (k > 0) {
            int cur = priorityQueue.poll();
            res += costs[cur];

            if (left <= right) {
                if (cur < left) {
                    priorityQueue.offer(left++);
                }
                else {
                    priorityQueue.offer(right--);
                }
            }

            k -= 1;
        }

        return res;
    }


    /**
     * T4: 最小移动总距离
     * 思路: 动态规划
     * 
     * 性质1: 
     * 对于机器人 x, y, 且位置 x < y
     * 对于工厂 f1, f2, 且位置 f1 < f2
     * 
     * 则移动距离最小的做法为: x --> f1, y --> f2 
     * 
     * dp[i, j]: 用前 i 个工厂修理前 j 个机器人的最小移动总距离
     * @param robot
     * @param factory
     * @return
     */
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        int[] robotArr = new int[robot.size()];
        robotArr = robot.stream().mapToInt(i -> i).toArray();
        Arrays.sort(robotArr);
        Arrays.sort(factory, (o1, o2)->{
            return o1[0] - o2[0];
        });

        // DP
        int m = robotArr.length;
        int n = factory.length;

        long[][] dp = new long[n + 1][m + 1];
        
        /**
         * 初始化
         * dp[i][0] = 0
         * dp[i][1] = inf
         */
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = (long) 1e18;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j]);
                // 枚举第 i 个工厂修理了 k 个机器人
                // 则前 i - 1 个工厂修理 j - k 个机器人
                long cost = 0;
                for (int k = 1; k <= Math.min(j, factory[i - 1][1]); k++) {
                    cost += Math.abs(factory[i - 1][0] - robotArr[j - k]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - k] + cost);
                }
            }
        }

        return dp[n][m];
    }


    public long minimumTotalDistanceCompressDP(List<Integer> robot, int[][] factory) {
        int[] robotArr = new int[robot.size()];
        robotArr = robot.stream().mapToInt(i -> i).toArray();
        Arrays.sort(robotArr);
        Arrays.sort(factory, (o1, o2)->{
            return o1[0] - o2[0];
        });

        // DP
        int m = robotArr.length;
        long[] dp = new long[m + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = (long) 1e18;
        }
        dp[0] = 0;

        for (int[] fa : factory) {
            for (int j = m; j >= 1; j--) {
                long cost = 0;
                for (int k = 1; k <= Math.min(j, fa[1]); k++) {
                    cost += Math.abs(fa[0] - robotArr[j - k]);
                    dp[j] = Math.min(dp[j], dp[j - k] + cost);
                }
            }
        }

        return dp[m];
    }


    public static void main(String[] args) {
        Day_2022_1106 test = new Day_2022_1106();
        int[] nums = {1, 5, 4, 2, 9, 9, 9};
        int k = 3;

        List<Integer> robot = new ArrayList<>();
        robot.add(0);
        robot.add(4);
        robot.add(6);

        int[][] factory = {{2, 2}, {6, 2}};
        System.out.println(test.minimumTotalDistance(robot, factory));
    }
}
