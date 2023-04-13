package Reviews;

/**
 * 携程笔试复盘
 */
public class Xiecheng {
    public long question_4(int n, int[] prices, int[] likes, int x) {
        /**
         * 01背包 + 状态转移
         * 
         * 0: 原价
         * 1: 半价
         * 2: 不买
         */
        long[][][] dp = new long[n + 1][x + 1][3];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= x; j++) {
                // 半价买
                if (i > 1 && j - prices[i - 1] / 2 >= prices[i - 2]) {
                    dp[i][j][1] = dp[i - 1][j - prices[i - 1] / 2][0] + likes[i - 1];
                }

                // 原价买
                if (j >= prices[i - 1]) {
                    dp[i][j][0] = Math.max(Math.max(dp[i][j - prices[i - 1]][0], dp[i][j - prices[i - 1]][1]), dp[i][j - prices[i - 1]][2]) + likes[i - 1];
                }

                // 不买
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i][j][0]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i][j][1]);
                dp[i][j][0] = Math.max(Math.max(dp[i - 1][j][0], dp[i - 1][j][1]), dp[i - 1][j][2]);
            }
        }

        return Math.max(Math.max(dp[n][x][0], dp[n][x][1]), dp[n][x][2]);
    }
}
