package DynamicProgramming;

import java.util.Arrays;

public class LIS {
    
    /**
     * 最长递增子序列
     * 动态规划 + 二分查找
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {

        // dp 表示长度为 (i + 1) 的尾部元素最小值
        int[] dp = new int[nums.length];
        int len = 0;

        for (int num : nums) {
            int i = 0, j = len;

            /**
             * 二分查找 dp 元素大于 num 的最小值
             */
            while (i < j) {
                int mid = (i + j) >> 1;
                if (dp[mid] < num) {
                    i = mid + 1;
                }
                else {
                    j = mid;
                }
            }

            dp[i] = num;

            // 如果没找到, 则说明 dp 中所有元素均小于 num
            if (j == len) {
                len += 1;
            }
        }

        return len;
    }


    public int maxEnvelopes(int[][] envelopes) {
        /**
         * 对宽度增序排列
         * 对高度降序排列，保证宽度相同只会在最长递增子序列中出现一次
         */
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o2[1]- o1[1];
            }

            return o1[0] - o2[0];
        });

        int n = envelopes.length;
        int[] dp = new int[n];
        int len = 0;

        for (int i = 0; i < n; i++) {
            int left = 0, right = len;
            while (left < right) {
                int mid = (left + right) >> 1;
                if (dp[mid] < envelopes[i][1]) {
                    left = mid + 1;
                }
                else {
                    right = mid;
                }
            }

            dp[left] = envelopes[i][1];

            if (right == len) {
                len++;
            }
        }

        return len;
    }


    /**
     * 最长连续递增子序列
     * 动态规划
     * @param nums
     * @return
     */
    public int findLengthOfLCIS(int[] nums) {
        int temp = 1;
        int res = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                temp += 1;
            }
            else {
                res = Math.max(res, temp);
                temp = 1;
            }

            
        }

        return Math.max(res, temp);
    }


    /**
     * 最长重复子数组
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int res = 0;

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    res = Math.max(res, dp[i][j]); 
                }
            }
        }
        return res;
    }

    
    /**
     * 最长公共子序列
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] s1 = text1.toCharArray();
        char[] s2 = text2.toCharArray();

        int m = s1.length;
        int n = s2.length;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    
    /**
     * 不相交的线
     * @param nums1
     * @param nums2
     * @return
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }


    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int res = Integer.MIN_VALUE;
        int temp = 0;

        for (int i = 1; i <= n; i++) {
            temp = Math.max(temp, 0) + nums[i - 1];
            res = Math.max(res, temp);
        }

        return res;
    }
}
