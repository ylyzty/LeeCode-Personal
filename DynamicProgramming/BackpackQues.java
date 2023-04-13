package DynamicProgramming;


/**
 * 选择一次: 从大到小遍历
 * 选择多次: 从小到大遍历
 * 
 * 组合问题: 先遍历背包，再遍历容量
 * 排列问题: 先遍历容量，再遍历背包
 */
public class BackpackQues {
    
    // 01背包
    public int back01(int[] weight, int[] value, int size) {
        int n = weight.length;
        int[][] dp = new int[n][size + 1];

        // dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= size; j++) {
                if (j >= weight[i]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
                else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[n - 1][size];
    }


    // 01背包, 滚动数组
    // 选择一次: 从大到小遍历
    // 选择多次: 从小到大遍历
    public int back02(int[] weight, int[] value, int size) {
        int n = weight.length;
        int[] dp = new int[size + 1];

        for (int i = 0; i < n; i++) {
            for (int j = size; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }

        return dp[size];
    }


    // 完全背包
    public int back03(int[] weight, int[] value, int size) {
        int n = weight.length;

        int[][] dp = new int[n][size + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= size; j++) {
                if (j >= weight[i]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - weight[i]] + value[i]);
                }
            }
        }

        return dp[n - 1][size];
    }


    // 完全背包, 滚动数组
    // 组合问题: 先遍历背包，再遍历容量
    // 排列问题: 先遍历容量，再遍历背包
    public int back04(int[] weight, int[] value, int size) {
        int n = weight.length;
        int[] dp = new int[size + 1];

        for (int i = 0; i < n; i++) {
            for (int j = weight[i]; j <= size; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }

        return dp[size];
    }
}
