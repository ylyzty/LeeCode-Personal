package Daily;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Month_03 {
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        int[][] ans = new int[n - 2][n - 2];

        for (int i = 0; i < n - 2; i++) {
            int max1 = Math.max(Math.max(grid[i][0], grid[i + 1][0]), grid[i + 2][0]);
            int max2 = Math.max(Math.max(grid[i][1], grid[i + 1][1]), grid[i + 2][1]);
            int max3 = 0;
            for (int j = 2; j < n; j++) {
                max3 = Math.max(Math.max(grid[i][j], grid[i + 1][j]), grid[i + 2][j]);
                ans[i][j - 2] = Math.max(Math.max(max1, max2), max3);
                max1 = max2;
                max2 = max3;
            }
        }

        return ans;
    }


    public String printBin(double num) {
        StringBuilder sb = new StringBuilder();
        sb.append("0.");

        while (num != 0) {
            num *= 2;
            sb.append(String.valueOf((int) (num / 1)));
            num = num % 1;
            if (sb.length() > 32) {
                return "ERROR";
            }
        }

        return sb.toString();
    }


    public String[] getFolderNames(String[] names) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < names.length; i++) {
            if (!map.containsKey(names[i])) {
                map.put(names[i], 1);
            }
            else {
                int k = map.get(names[i]);
                for (; ; k++) {
                    String temp = names[i] + "(" + k + ")";
                    if (!map.containsKey(temp)) {
                        names[i] = temp;
                        map.put(names[i], k + 1);
                        map.put(temp, 1);
                        break;
                    }
                }
            }
        }

        return names;
    }


    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        if (boardingCost * 4 <= runningCost) {
            return -1;
        }

        int max = 0;
        int ans = -1;

        int profit = 0;
        int left = 0;
        int cur = 0;
        for (int i = 0; i < customers.length; i++) {
            left += customers[i];
            if (left >= 4) {
                profit += boardingCost * 4 - runningCost;
            }
            else {
                profit += boardingCost * left - runningCost;
            }

            left = Math.max(0, left - 4);
            cur += 1;
            if (profit > max) {
                max = profit;
                ans = cur;
            }
        }

        while (left >= 4) {
            profit += boardingCost * 4 - runningCost;
            left -= 4;
            cur += 1;
        }

        if (left * boardingCost > runningCost) {
            profit += left * boardingCost - runningCost;
            cur += 1;
        }

        if (profit > max) {
            max = profit;
            ans = cur;
        }
        return ans;
    }


    public int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m + 1][n + 1];
        // dp[m - 1][n - 1] = grid[m - 1][n - 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i - 1][j - 1];
            }
        }

        return dp[m][n];
    }


    public static void main(String[] args) {
        Month_03 test = new Month_03();
        System.out.println(test.printBin(0.625));
    }
}
