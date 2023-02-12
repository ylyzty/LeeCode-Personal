package CodeCatalog.Weekly;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;


/**
 * 314 单周赛
 */
public class Day_2022_1009 {
    public int hardestWorker(int n, int[][] logs) {
        for (int i = logs.length - 1; i > 0; i--) {
            logs[i][1] -= logs[i - 1][1];
        }

        Arrays.sort(logs, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                // TODO Auto-generated method stub
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }

                return o2[1] - o1[1];
            }
            
        });

        return logs[0][0];
    }

    
    public int[] findArray(int[] pref) {
        int n = pref.length;

        int[] res = new int[n];
        res[0] = pref[0];

        for (int i = 1; i < n; i++) {
            res[i] = pref[i - 1] ^ pref[i];
        }

        return res;
    }


    public String robotWithString(String s) {
        int n = s.length();

        char[] array = new char[n];

        char min = 'z';
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) < min) {
                min = s.charAt(i);
            }

            array[i] = min;
        }


        Stack<Character> stack = new Stack<>();
        StringBuilder res = new StringBuilder();
        stack.push(s.charAt(0));
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) > stack.peek()) {
                while (stack.peek() <= array[i]) {
                    res.append(stack.pop());
                }
            }

            stack.push(s.charAt(i));
        }

        while (!stack.empty()) {
            res.append(stack.pop());
        }

        return res.toString();
        
    }


    public final static int MOD = 1000000007;
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][k];
        dp[0][0][grid[0][0] % k] = 1;
        for (int i = 1; i < m; i++) {
            int val = grid[i][0];
            for (int t = 0; t < k; t++) {
                dp[i][0][(val + t) % k] = dp[i - 1][0][t];
            }
        }
        for (int j = 1; j < n; j++) {
            int val = grid[0][j];
            for (int t = 0; t < k; t++) {
                dp[0][j][(val + t) % k] = dp[0][j - 1][t];
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int val = grid[i][j];
                for (int t = 0; t < k; t++) {
                    dp[i][j][(val + t) % k] = (dp[i - 1][j][t] + dp[i][j - 1][t]) % MOD; 
                }
            }
        }
        
        return dp[m - 1][n - 1][0];
    }


    public int findMinIndex(String s) {
        char min = 'z';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < min) {
                min = s.charAt(i);
            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == min) {
                return i;
            }
        }

        return -1;
    }

    public String reverse(String origin) {
        StringBuilder sb = new StringBuilder();
        for (int i = origin.length() - 1; i >= 0; i--) {
            sb.append(origin.charAt(i));
        }
        
        return sb.toString();
    }
}
