package Weekly;

import java.util.ArrayList;
import java.util.List;

/**
 * 第 313 单周赛
 */
public class Day_2022_1002 {
    public int commonFactors(int a, int b) {
        int count = 1;
        int num = Math.min(a, b);
        
        for (int i = 2; i <= num; i++) {
            if (a % i == 0 && b % i == 0) {
                count += 1;
            }
        }
        
        return count;
    }


    public int maxSum(int[][] grid) {
        int res = 0;

        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i <= m - 3; i++) {
            int value = 0;
            value += grid[i][0] + grid[i][1] + grid[i][2];
            value += grid[i + 2][0] + grid[i + 2][1] + grid[i + 2][2];
            value += grid[i + 1][1];

            res = Math.max(res, value);
            for (int j = 1; j <= n - 3; j++) {
                value -= (grid[i][j - 1] + grid[i + 2][j - 1]);
                value += (grid[i][j + 2] + grid[i + 2][j + 2]);
                value -= grid[i + 1][j];
                value += grid[i + 1][j + 1];

                res = Math.max(res, value);
            }
        }

        return res;
    }


    public int minimizeXor(int num1, int num2) {
        int count1 = getBits(num1);
        int count2 = getBits(num2);

        /**
         * count1 == count2: return nums1
         * count1 <  count2: 从 nums1 低位0 开始填充1
         * count1 >  count2: 从 nums1 低位1 开始填充0
         */
        int temp = Math.abs(count1 - count2);
        
        if (count1 < count2) {
            List<Integer> zeroIndex = getIndex(num1, 0);
            int cur = 0;
            while (temp > 0) {
                num1 += (int)Math.pow(2, zeroIndex.get(cur));
                cur += 1;
                temp -= 1;
            }

            return num1;
        }
        else if (count1 > count2) {
            List<Integer> oneIndex = getIndex(num1, 1);
            int cur = 0;
            while (temp > 0) {
                num1 -= (int)Math.pow(2, oneIndex.get(cur));
                count2 -= 1;
                cur += 1;
            }

            return num1;
        }
        else {
            return num1;
        }
    }

    public int getBits(int num) {
        int count = 0;
        while (num > 0) {
            if ((num & 1) == 1) {
                count += 1;
            }

            num >>= 1;
        }

        return count;
    }

    public List<Integer> getIndex(int num, int flag) {
        List<Integer> res = new ArrayList<>();
        int index = 0;
        if (flag == 0) {
            while (num > 0) {
                if ((num & 1) == 0) {
                    res.add(index);
                }
    
                num >>= 1;
                index += 1;
            }
    
            for (int i = index; i < 31; i++) {
                res.add(i);
            }
        }
        
        if (flag == 1) {
            while (num > 0) {
                if ((num & 1) == 1) {
                    res.add(index);
                }

                num >>= 1;
                index += 1;
            }
        }

        return res;
    }

    
    /**
     * LeeCode 6195: 对字母串可执行的最大删除数
     * @param s
     * @return
     */
    public int deleteString(String s) {
        // 动态规划求 lcp
        // lcp(i, j): 表示字符串s从下标i开始的后缀和从下标j开始的后缀的 最长公共前缀长度 
        // lcp(i, j) = lcp(i + 1, j + 1) + 1 (s[i] == s[j])
        int n = s.length();
        int[][] lcp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            lcp[i][n] = 0;
            lcp[n][i] = 0;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (s.charAt(i) == s.charAt(j)) {
                    lcp[i][j] = lcp[i + 1][j + 1] + 1;
                }
                else {
                    lcp[i][j] = 0;
                }
            }
        }

        int[] dp = new int[n];

        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; i + 2 * j <= n; j++) {
                if (lcp[i][i + j] >= j) {  // s[i : i + j) == s[i + j : i + 2j)
                    dp[i] = Math.max(dp[i], dp[i + j] + 1);
                }
            }
        }

        return dp[0];
    }
}