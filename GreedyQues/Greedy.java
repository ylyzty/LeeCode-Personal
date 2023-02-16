package GreedyQues;

import java.util.Arrays;

public class Greedy {
    // 贪心第一周

    /**
     * 对数组 g, s 排序
     * 从小到大遍历 g 中的元素，对于每个元素找到能满足该元素的 s 中尺寸最小的饼干
     * 即对于 g[i], 找到 未被使用的最小的 j 满足 g[i] <= s[j] 
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int count = 0;
        int index = 0;

        for (int i = 0; i < g.length; i++) {
            while (index < s.length) {
                if (g[i] <= s[index++]) {
                    count += 1;
                    break;
                }
            }

            if (index >= s.length) {
                break;
            }
        }

        return count;
    }


    /**
     * 每次选择 峰 或者 谷
     * @param nums
     * @return
     */
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int prev = nums[1] - nums[0];
        int res = prev == 0 ? 1 : 2;

        for (int i = 2; i < nums.length; i++) {
            int cur = nums[i] - nums[i - 1];

            if ((cur > 0 && prev <= 0) || (cur < 0 && prev >= 0)) {
                res += 1;
                prev = cur;
            }
        }

        return res;
    }


    /**
     * 从第一个元素开始，计算元素和, 并与 res 比较
     * 若 sum <= 0, 则说明当前下标的前面所有部分 对后面元素 sum 的影响是负的
     * 所有要清空前面的元素和, 即将下一个元素设置为子数组的起始元素
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int res = Integer.MIN_VALUE, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            res = Math.max(res, sum);

            if (sum <= 0) {
                sum = 0;
            }
        }

        
        return res;
    }

    /**
     * 动态规划
     * f(i) 表示以第i个元素结尾的连续子数组的最大和
     * f(i) = Math.max(f(i - 1) + nums[i], nums[i])
     * @param nums
     * @return
     */
    public int maxSubArray2(int[] nums) {
        int res = nums[0];
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum = Math.max(sum + nums[i], nums[i]);
            res = Math.max(res, sum);
        }

        return res;
    }


    // 贪心第二周

    /**
     * 股票涨的前一天买入
     * 即 profit = 所有 上升区间的右端点与左端点的差值 和
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            res += Math.max(0, prices[i] - prices[i - 1]);
        }

        return res;
    }

    /**
     * 动态规划
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int[][] dp = new int[prices.length][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            // 卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);

            // 买入
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[prices.length - 1][0];
    }


    /**
     * 实时维护一个 最远可到达位置
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int pos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= pos) {
                pos = Math.max(pos, i + nums[i]);
            }

            if (pos >= nums.length - 1) {
                return true;
            }
        }

        return false;
    }

    public int jump(int[] nums) {
        int pos = 0;
        int end = 0;
        int steps = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            pos = Math.max(pos, i + nums[i]);
            if (i == end) {
                end = pos;
                steps += 1;
            }
        }

        return steps;
    }


    public int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);
        int res = 0;
        int index = 0;
        while (k > 0) {
            if (nums[index] < 0) {
                res += -nums[index];
                index += 1;
                k -= 1;
            }
            else {
                if (k % 2 == 0) {
                    break;
                }
                else {
                    if (index == 0) {
                        res -= nums[index++];
                    }
                    else {
                        res += Math.max(nums[index - 1], -nums[index]);
                        index += 1;
                    }
                    break;
                }
            }
        }

        for (int i = index; i < nums.length; i++) {
            res += nums[i];
        }
        return res;
    }

    public static void main(String[] args) {
        Greedy greedy = new Greedy();
        int[] nums = {-8, 3, -5, -3, -2, -5};
        System.out.println(greedy.largestSumAfterKNegations(nums, 6));
    }
}
