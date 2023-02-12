package CodeCatalog.Weekly;

import java.util.HashMap;
import java.util.Map;

public class Day_2022_0904 {
    public boolean checkDistance(String s, int[] distance) {
        // 使用 map 记录两个相同字母间的距离
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!map.containsKey(ch)) {
                map.put(ch, i);
            }
            else {
                map.put(ch, i - map.get(ch) - 1);
            }
        }

        for (int i = 0; i < 26; i++) {
            char ch = (char) ('a' + i);
            if (!map.containsKey(ch)) {
                continue;
            }
            else {
                if (distance[i] != map.get(ch)) {
                    return false;
                }
            }
        }

        return true;
    }

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


    public int numOfWays(int startPos, int endPos, int k) {
        if (k < (endPos - startPos) || (k - (endPos - startPos)) % 2 != 0) {
            return 0;
        }

        return combinationalNumber(k, (k + endPos - startPos) / 2);
    }
    


    public int longestNiceSubArray(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }

        int i = 0, j = 0;
        int res = 1;
        while (j < nums.length) {
            if (i == j) {
                j += 1;
                continue;
            }

            // 判断 j 是否与前面所有元素的与运算
            int index = i;
            for (; index < j; index++) {
                if ((nums[index] & nums[j]) != 0) {
                    i = index + 1;
                    break;
                }
            }

            if (index == j) {
                res = Math.max(res, j - i + 1);
                j += 1;
            }
        }

        return res;
    }

    public static void main(String[] args) {
    }
}
