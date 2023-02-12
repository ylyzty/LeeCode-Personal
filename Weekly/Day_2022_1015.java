package CodeCatalog.Weekly;

import java.util.ArrayList;
import java.util.List;

public class Day_2022_1015 {
    public int countTime(String time) {
        int res = 1;

        // 处理分钟
        if (time.charAt(3) == '?') {
            res *= 6;
        }

        if (time.charAt(4) == '?') {
            res *= 10;
        }

        // 处理小时
        if (time.charAt(0) == '?' && time.charAt(1) == '?') {
            res *= 24;
        }
        else if (time.charAt(0) == '?') {
            if (time.charAt(1) >= '4') {
                res *= 2;
            }
            else {
                res *= 3;
            }
        }
        else if (time.charAt(1) == '?') {
            if (time.charAt(0) == '2') {
                res *= 4;
            }
            else {
                res *= 10;
            }
        }

        return res;
        
    }


    private static final int MOD = 1000000007;
    public int[] productQueries(int n, int[][] queries) {
        List<Integer> list = new ArrayList<>();

        int index = 0;
        while(n > 0) {
            if ((n & 1) == 1) {
                list.add(1 << index);
            }

            n >>= 1;
            index++;
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];

            long temp = list.get(left);
            for (int j = left + 1; j <= right; j++) {
                temp = (temp * list.get(j)) % MOD;
            }

            res[i] = (int)(temp % MOD);
        }

        return res;
    }


    public int minimizeArrayValue(int[] nums) {
        int maxVal = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            maxVal = Math.max(maxVal, (sum + i) / (i + 1));
        }

        return maxVal;
    }

}
