package CodeCatalog.Weekly;

public class Day_2022_1126 {
    
    private static final int MOD = (int) (1e9 + 7);


    public int numberOfCuts(int n) {
        if (n == 1) {
            return 0;
        }
        if (n % 2 == 0) {
            return n / 2;
        }
        
        return n;
    }


    public int[][] onesMinusZeros(int[][] grid) {
        // 第一遍遍历统计第 i 行 0 的数目和第 j 列 0 的数目
        int m = grid.length;
        int n = grid[0].length;
        
        int[] zeroRow = new int[m];
        int[] zeroCol = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    zeroRow[i] += 1;
                    zeroCol[j] += 1;
                }
            }
        }

        // 构建差值矩阵
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = (n - zeroRow[i]) + (m - zeroCol[j]) - (zeroRow[i]) - (zeroCol[j]);
            }
        }

        return grid;
    }


    public int bestClosingTime(String customers) {
        int pos = 0;
        int cost = 0;
        for (int i = 0; i < customers.length(); i++) {
            if (customers.charAt(i) == 'Y') {
                cost += 1;
            }
        }

        if (cost == 0) {
            return pos;
        }

        int min = cost;
        for (int i = 1; i <= customers.length(); i++) {
            if (customers.charAt(i - 1) == 'Y') {
                cost -= 1;
            }
            else {
                cost += 1;
            }

            if (cost < min) {
                min = cost;
                pos = i;
            }
        }

        return pos;
    }


    /**
     * 核心思路：回文串长度为5, 等价于回文中心是单个数字，且左右各有两个对称的数字, 即 12321
     * 
     * 枚举所有的回文中心 i (0 <= i <= s.length() - 1)
     * 统计 s[0 ... i - 1] 中 xy 的个数 count1, xy只有 100 种情况
     * 统计 s[i + 1 ... s.length() - 1] 中 yx 的个数 count2
     * 
     * ans = \sum_{0}^{s.length() - 1} {\sum_{0}^{100} {count1 * count2}}
     * @param s
     * @return
     */
    public int countPalindromes(String s) {
        char[] arr = s.toCharArray();
        
        int[] prefix = new int[10];
        int[] suffix = new int[10];

        int[][] prefix2 = new int[10][10];
        int[][] suffix2 = new int[10][10];

        // 逆序遍历, 统计 xy 个数
        // dp[pos][x][y] = dp[pos + 1][x][y] + suffix[y]  if (cur == x)
        // dp[pos][x][y] = dp[pos + 1][x][y]              if (cur != x)
        for (int i = arr.length - 1; i >= 0; i--) {
            int cur = arr[i] - '0';
            for (int j = 0; j <= 9; j++) {
                suffix2[cur][j] += suffix[j];
            }

            suffix[cur] += 1;
        }


        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i] - '0';
            suffix[cur] -= 1;

            // 去除后序遍历计算的包括当前位置的xy个数
            for (int j = 0; j <= 9; j++) {
                suffix2[cur][j] -= suffix[j];
            }

            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    ans += (long) prefix2[k][j] * suffix2[j][k];
                }
            }

            for (int j = 0; j <= 9; j++) {
                prefix2[j][cur] += prefix[j];
            }
            prefix[cur] += 1;
        }

        return (int) (ans % MOD);
    }
}
