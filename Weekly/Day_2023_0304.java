package Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day_2023_0304 {
    
    public int splitNum(int num) {
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            if (num % 10 != 0) {
                list.add(num % 10);
            }
            num /= 10;
        }

        list.sort((o1, o2) -> {
            return o1 - o2;
        });

        int ans = 0;
        for (int i = 0; i < list.size(); i = i + 2) {
            ans = ans * 10 + list.get(i);
        }

        int tmp = 0;
        for (int i = 1; i < list.size(); i = i + 2) {
            tmp = tmp * 10 + list.get(i);
        }
        ans += tmp;

        return ans;
    }


    public long coloredCells(int n) {
        if (n == 1) {
            return 1;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + 4 * (i - 1);
        }

        return dp[n];
    }


    private static final int MOD = (int)(1e9 + 7);
    public int countWays(int[][] ranges) {
        Arrays.sort(ranges, (int[]o1, int[] o2) -> {
            return o1[0] - o2[0];
        });

        int count = 1;
        int max = ranges[0][1];

        for (int i = 1; i < ranges.length; i++) {
            int[] a = ranges[i];

            if (a[0] > max) {
                count += 1;
            }

            max = Math.max(max, a[1]);
        }

        int ans = 1;
        for (int i = 0; i < count; i++) {
            ans = ans * 2 % MOD;
        }

        return ans;
    }


    public static void main(String[] args) {
        
    }
}
