package DynamicProgramming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicProgrammingImpl {
    // 动态规划第1周

    /**
     * LeeCode 509 斐波那契数列
     * 
     * 1. 确定dp数组及下标的含义, 这里省略了dp数组, 借用两个参数a, b来代替. 下标的含义为第几个数字
     * 2. 确定递推公式, dp[i] = dp[i - 1] + dp[i - 2] 即 res = a + b
     * 3. dp数组初始化, dp[0] = 0, dp[1] = 1 即 a = 0, b = 1
     * 4. 确定遍历顺序, 2 ~ n顺序遍历
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int a = 0, b = 1;
        int res = 1;
        for (int i = 2; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }

        return res;
    }


    /**
     * LeeCode 70 爬楼梯
     * 
     * 此题与斐波那契数列属于同一题, 不过此次笔者没有省略 dp数组.
     * 1. 确定dp数组及下标的含义, 数组的含义为爬到第 i 阶楼梯的方法数
     * 2. 确定递推公式 dp[i] = dp[i - 1] + dp[i - 2]
     * 3. dp数组初始化, dp[1] = 1, dp[2] = 2
     * 4. 确定遍历顺序, 3~n 顺序遍历
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];

        // dp数组初始化
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }


    /**
     * LeeCode 746 使用最小花费上楼梯
     * 
     * 1. 确定dp数组及下标的含义, 数组的含义为到达第 i 个台阶的最小花费
     * 2. 确定递推公式 dp[i] = Math.min(dp[i - 1] + dp[i - 2]) + cost[i]
     * 3. 初始化dp数组, dp[0] = cost[0], dp[1] = cost[1]
     * 4. 确定遍历顺序, 从 2~n 顺序遍历
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        if (cost.length == 2) {
            return Math.min(cost[0], cost[1]);
        }

        int[] dp = new int[cost.length];

        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < dp.length; i++) {
            /**
             * 递推数组
             * 爬到第 i 阶楼梯的方式包括 从 i - 1 阶爬一个台阶 和 从 i - 2 阶爬两个台阶
             * 取其中的花费较小者
             */
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }

        /**
         * 到达顶部的花费选择 倒数第一个台阶和倒数第二个台阶的较小者
         */
        return Math.min(dp[cost.length - 1], dp[cost.length - 2]);
    }


    /**
     * LeeCode 62 不同路径
     * 
     * 1. 确定dp数组及下标含义, 数组的含义为到达第i行第j列的路径总数
     * 2. 确定递推公式 dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
     * 3. 初始化dp数组, 第0行和第0列全都初始化为1
     * 4. 确定遍历顺序, 内循环从上至下, 外循环从左至右
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // dp数组初始化
        // 第 0 行初始化
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        // 第 0 列初始化
        for (int j = 0; j < m; j++) {
            dp[j][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];

    }


    /**
     * LeeCode 63 不同路径II
     * 
     * 1. 确定dp数组及下标的含义, 数组的含义为到达第i行第j列的路径总数
     * 2. 确定递推公式 dp[i][j] = dp[i - 1][j] + dp[i][j - 1], 对于网格中的障碍物有dp[i][j] = 0
     * 3. 初始化dp数组, dp[0][0]根据是否有障碍物初始化为0或1, 对于第0行, 则根据左边是否有障碍物初始化为0或1, 第0列也是如此
     * 4. 确定遍历顺序, 内循环从上至下, 外循环从左至右
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];

        // dp数组初始化
        if (obstacleGrid[0][0] == 1) {
            dp[0][0] = 0;
        }
        else {
            dp[0][0] = 1;
        }

        // 第 0 行初始化
        for (int i = 1; i < n; i++) {
            if (obstacleGrid[0][i] == 1) {
                dp[0][i] = 0;
            }
            else {
                dp[0][i] = dp[0][i - 1];
            }
        }

        for (int j = 1; j < m; j++) {
            if (obstacleGrid[j][0] == 1) {
                dp[j][0] = 0;
            }
            else {
                dp[j][0] = dp[j - 1][0];
            }
        }

        // 遍历dp数组
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }
                else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }


    /**
     * 组合总数 C(n, m)
     * 公式 C(n, m) = C(n - 1, m - 1) + C(n - 1, m)
     * 
     * 1. 确定dp数组及下标的含义, 数组的含义为 C(i, j) 的值
     * 2. 确定递推公式, dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]
     * 3. 初始化dp数组, dp[i][0] = 1, 即 C(i, 0) = 1
     * 4. 确定遍历顺序, n -> 1~num, m -> 1~n
     */
    public static int MOD = 1000000007;

    public int combinationalNumber(int num, int k) {
        long[][] dp = new long[num + 1][num + 1];

        /**
         * 初始化
         * dp[i][0] = C(i, 0) = 1
         */
        for (int i = 0; i <= num; i++) {
            dp[i][0] = 1;
        }

        // 遍历dp数组
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        return (int)dp[num][k];
    }


    /**
     * 排列数 A(n, m)
     * @param num
     * @param k
     * @return
     */
    public int permutationNumber(int num, int k) {
        long[][] dp = new long[num + 1][num + 1];

        // 初始化
        for (int i = 1; i <= num; i++) {
            dp[i][0] = 1;
            dp[i][1] = i;
        }

        for (int i = 2; i <= num; i++) {
            for (int j = 2; j <= i; j++) {
                dp[i][j] = dp[i][j - 1] * (i - j + 1) % MOD;
            }
        }

        return (int)dp[num][k];

    }


    /**
     * LeeCode 343 整数拆分
     * 1. 确定dp数组及下标的含义, 数组的含义为 i 可拆分成的乘积最大值
     * 2. 确定递推公式 dp[i] = MAX(Math.max(j * (i - j), j * dp[i - j])) (1 <= j < i)
     * 3. 初始化dp数组, 全都初始化为0
     * 4. 确定遍历顺序, i -> 2~n, j -> 1~(i-1)
     * 
     * 该问题可以数学方法证明大于5时优先选择拆分出一个3
     * 
     * @param n(2~58)
     * @return
     */
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];

        
        for (int i = 2; i <= n; i++) {
            int max_value = 0;
            for (int j = 1; j < i; j++) {
                max_value = Math.max(max_value, Math.max(j * (i - j), j * dp[i - j]));
            }
            dp[i] = max_value;
        }

        return dp[n];
    }


    /**
     * LeeCode 96  不同的二叉搜索树
     * 
     * 1. 确实dp数组及其下标的含义, 数组的含义为 i 个节点能构成的不同的二叉搜索树
     * 2. 初始化dp数组, dp[0] = dp[1] = 1, 空树或单节点的树只有一种
     * 3. 确定递推公式, dp[i] += dp[j] * dp[i - j - 1], 该公式的意思为依次遍历每个 \
     *    数字将其作为根节点, 将 1~j-1 作为左子树, j+1~i 作为右子树, 同时左右子树也 \
     *    需要满足是二叉搜索树, 且由于根值不同, 可以保证每棵二叉搜索树是不同的, 所以 \
     *    该问题转化成了两个规模更小的问题
     * 4. 确定遍历顺序, i -> 2~n, j -> 1~i 
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];

        // dp数组初始化
        dp[0] = 1;
        dp[1] = 1;

        // 遍历数组
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }

        return dp[n];
    }


    /**
     * 01背包问题
     * 
     * 有 n 件物品和一个最大能背重量为 w 的背包. 第 i 件物品的重量是 weight[i],
     * 得到的价值是 value[i]. 每件物品只能用一次, 求解将哪些物品装入背包里使得物品 \
     * 价值总和最大
     * 
     * 1. 确定dp数组及下标含义, 数组含义为从[0, i - 1]个物品选择若干个, 其重量和小于j的最大价值
     * 2. 初始化dp数组, dp[i][0] = 0, (0 < i < length)
     * 3. 确定递推公式: dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i])  (j>=weight[i])
     *                 dp[i][j] = dp[i - 1][j] (j < weight[i])
     * 4. 确定遍历顺序, 外循环 i -> 0~length-1, 内循环 j -> 1~bagSize
     */
    public int weightBagProblem(int[] weight, int[] value, int bagSize) {
        int length = weight.length;

        // 定义 dp 数组
        int[][] dp = new int[length][bagSize + 1];

        for (int i = 0; i <= length; i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 1; j <= bagSize; j++) {
                if (j >= weight[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[length - 1][bagSize];
    }


    /**
     * 01背包问题 —— 滚动数组
     * @param weight
     * @param value
     * @return
     */
    public int weightBagProblemWithScrollArray(int[] weight, int[] value, int bagSize) {
        int length = weight.length;

        int[] dp = new int[bagSize + 1];

        // 先遍历物品, 再遍历背包
        for (int i = 0; i < length; i++) {
            for (int j = bagSize; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }

        return dp[bagSize];
    }
    
    /**
     * LeeCode 416 分割等和子集
     * 
     * 如何转化为 01背包问题
     * 分割数组得到两个等和的子数组 -> 能否从数组中选择一些数字使其等于整个数组元素和的一半
     * 1. 确定dp数组及下标含义, 数组的含义为从 nums[0]~nums[i] 中选取若干个正整数, 是否存在和等于j
     * 2. 初始化dp数组, dp[i][0] = true 0<i<n, dp[0][nums[0]] = true
     * 3. 确定递推公式: dp[i][j] = dp[i - 1][j] || dp[i - 1][j - num] j >= num \
     *                 dp[i][j] = dp[i - 1][j] j < num
     * 4. 确定遍历顺序, 外循环 i->1~n, 内循环 j -> 1~target
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        // 考虑特殊情况
        if (nums.length == 1) {
            return false;
        }

        int sum = 0;
        int max_value = 0;
        for (int num : nums) {
            sum += num;
            max_value = Math.max(num, max_value);
        }

        if (sum % 2 != 0 || max_value > sum / 2) {
            return false;
        }


        int target = sum / 2;
        int n = nums.length;
        
        // 创建 dp 数组
        boolean[][] dp = new boolean[n][target + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;

        for (int i = 1; i < n; i++) {
            int num = nums[i];
            for (int j = 1; j <= target; j++) {
                if (j >= num) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - num];
                }
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n - 1][target];
    }


    /**
     * 转化为 01背包问题, 求两堆石头的最小差值
     * 即 背包容量最大为 sum / 2, 最多能装下多少石头
     * @param stones
     * @return
     */
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;

        for (int stone : stones) {
            sum += stone;
        }

        int target = sum / 2;

        int[] dp = new int[target + 1];

        // // 初始化第 0 列
        // for (int i = 0; i < stones.length; i++) {
        //     dp[i][0] = 0;
        // }

        // for (int i = 1; i < stones.length; i++) {
        //     for (int j = 1; j <= target; j++) {
        //         if (j >= stones[i]) {
        //             dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i]] + stones[i]);
        //         }
        //         else {
        //             dp[i][j] = dp[i - 1][j];
        //         }
        //     }
        // }

        // 滚动数组
        for (int i = 0; i < stones.length; i++) {
            for (int j = target; j>= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }

        return sum - 2 * dp[target];
    }
    
    
    /**
     * LeeCode 300: 最长递增子序列
     * 时间复杂度 O(N^2)
     * 
     * 1. 确定dp数组及下标的含义, 数组的含义为以 nums[i] 结尾的递增子序列的最大长度
     * 2. 初始化dp数组, dp[0] = 1
     * 3. 确定递推公式 if (nums[i] > nums[j]) { dp[i] = Math.max(dp[i], dp[j] + 1) }
     * 4. 确定遍历顺序, i -> 1~nums.length
     * @param nums
     * @return
     */
    public int lengthOfLIS_DP(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        dp[0] = 1;

        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            res = Math.max(res, dp[i]);
        }

        return res;
    }


    /**
     * LeeCode 494: 目标和
     * 
     * 1. 确定dp数组及下标的含义, 数组的含有为：前 i 个数选取元素和等于 j 的方案数
     * 2. 初始化dp数组, dp[0][0] = 1, dp[0][j] = 0 (1 <= j <= diff);
     * 3. 确定递推公式, dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i]]; 
     * @param nums
     * @param target
     * @return
     */
     public int findTargetSumWaysDP(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int diff = sum - target;

        if (diff < 0 || diff % 2 != 0) {
            return 0;
        }

        diff = diff / 2;
        int[] dp = new int[diff + 1];

        dp[0] = 1;

        for (int i = 1; i <= nums.length; i++) {
            int num = nums[i - 1];
            for (int j = diff; j >= num; j--) {
                dp[j] = dp[j] + dp[j - num];
            }
        }

        return dp[diff];
    }


    public int completeBagProblem(int[] weights, int[] values, int bagSize) {
        int n = weights.length;
        int[][] dp = new int[n + 1][bagSize + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= bagSize; j++) {
                if (j >= weights[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weights[i - 1]] + values[i - 1]);
                }
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n][bagSize];
    }


    /**
     * LeeCode 518: 零钱兑换
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }

        return dp[amount];
    }


    /**
     * LeeCode 377: 组合总和IV
     * 本题同一元素可以无限使用, 属于完全背包问题
     * 不同的序列视为不同的组合, 属于排列问题
     * 
     * 外循环遍历物品, 内循环遍历背包  ——> 组合问题
     * 外循环遍历背包, 内循环遍历物品  ——> 排列问题
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;


        for (int j = 0; j <= target; j++) {
            for (int i = 0; i < nums.length; i++) {
                if (j >= nums[i]) {
                    dp[j] += dp[j - nums[i]];
                }
            }
        }

        return dp[target];
    }


    /**
     * LeeCode 70: 爬楼梯
     * 将爬楼梯视作一个 1 和 2 无限使用的完全背包问题
     * 不同的序列属于不同的组合, 属于排列问题
     * @param n
     * @return
     */
    public int climbStairsBackpack(int n) {
        int[] dp = new int[n + 1];

        dp[0] = 1;

        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= 2; j++) {
                if (i >= j) {
                    dp[i] += dp[i - j];
                }
            }
        }

        return dp[n];
    }


    /**
     * LeeCode 322: 零钱兑换
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];

        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                if (dp[j - coins[i]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i] + 1]);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }


    /**
     * LeeCode 279: 完全平方数
     * 视作一个完全背包问题
     * 
     * [1, 4, 9, ...]  -> 物品, 个数无限
     *       n         -> 背包
     * @param n
     * @return
     */
    public int numSquares(int n) {

        int[] dp = new int[n + 1];
        
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i * i <= n; i++) {
            for (int j = i * i; j <= n; j++) {
                if (dp[j - i * i] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
                }
            }
        }

        return dp[n];
    }


    /**
     * LeeCode 139: 单词拆分
     * 将问题转化为完全背包问题
     * 
     * s    -> 背包
     * word -> 物品
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        // i -> 子串结束位置, j 子串起始位置
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                String temp = s.substring(j, i);
                if (wordDict.contains(temp) && dp[j]) {
                    dp[i] = true;
                }
            }
        }

        return dp[s.length()];
    }


    /**
     * 打家劫舍系列问题
     * @param prices
     * @return
     */

    /**
     * LeeCode 198: 打家劫舍
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[n];

        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }

        return dp[n - 1];
    }


    /**
     * LeeCode 213: 打家劫舍II
     * @param nums
     * @return
     */
    public int robII(int[] nums) {
        int n = nums.length;

        if (n == 1) {
            return nums[0];
        }

        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] dp1 = new int[n - 1];
        int[] dp2 = new int[n - 1];

        dp1[0] = nums[0];
        dp1[1] = Math.max(nums[0], nums[1]);
        dp2[0] = nums[1];
        dp2[1] = Math.max(nums[1], nums[2]);

        for (int i = 2; i < n - 1; i++) {
            dp1[i] = Math.max(dp1[i - 2] + nums[i], dp1[i - 1]);
            dp2[i] = Math.max(dp2[i - 2] + nums[i + 1], dp2[i - 1]);
        }

        return Math.max(dp1[n - 2], dp2[n - 2]);
    }


    /**
     * LeeCode 337: 打家劫舍III
     * 
     * f: 选择当前节点
     * g: 不选择当前节点
     * 
     * 选中节点o: 其左右孩子都不能被选中, f(o) = o.val + g(o.left) + g(o.right)
     * 不选择节点o: 其左右孩子可选也可不选, g(o) = Math.max(f(o.left), g(o.left)) + Math.max(f(o.right), g(o.right))
     * @param root
     * @return
     */
    Map<TreeNode, Integer> f = new HashMap<>();
    Map<TreeNode, Integer> g = new HashMap<>();
    public int robIII(TreeNode root) {
        dfs(root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        dfs(node.left);
        dfs(node.right);

        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) + 
                    Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }


    /**
     * 买卖股票的最佳时机系列问题
     * @param prices
     * @return
     */
    
    
    public int maxProfit(int[] prices) {
        int buy = Integer.MAX_VALUE;
        int profit = Integer.MIN_VALUE;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < buy) {
                buy = prices[i];
            }

            if (prices[i] - buy > profit) {
                profit = prices[i] - buy;
            }
        }

        return profit;
    }


    public int maxProfitII(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int[][] dp = new int[prices.length][2];

        // 0 表示未持有股票, 1表示持有股票
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }

        return dp[prices.length - 1][0];
    }


    public int maxProfitIII(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        
        int buy1 = -prices[0];
        int buy2 = -prices[0];
        int sell1 = 0;
        int sell2 = 0;
        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }


        int[][] dp = new int[prices.length][4];

        /**
         * 0 —— 第一次买入最小花费
         * 1 —— 第一次卖出最大利润
         * 2 —— 第二次买入最小花费
         * 3 —— 第二次卖出最大利润
         */
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        dp[0][2] = -prices[0];
        dp[0][3] = 0;

        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i][0] + prices[i]);
            dp[i][2] = Math.max(dp[i - 1][2], dp[i][1] - prices[i]);
            dp[i][3] = Math.max(dp[i - 1][3], dp[i][2] + prices[i]);
        }

        return dp[prices.length - 1][3];
    }


    public static void main(String[] args) {
        DynamicProgrammingImpl impl = new DynamicProgrammingImpl();
        
        int[] weights = {1, 3, 4};
        int[] values = {15, 20, 30};

        System.out.println(impl.completeBagProblem(weights, values, 4));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) {this.val = val;}
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
