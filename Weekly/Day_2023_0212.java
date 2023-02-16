package Weekly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day_2023_0212 {

    /**
     * 二分查找
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long ans = 0;

        for (int i = 1; i < nums.length; i++) {
            int l = lowerBound(nums, i - 1, lower - nums[i]);
            int r = lowerBound(nums, i - 1, upper - nums[i] + 1);

            ans += r - l;
        }

        return ans;
    }

    /**
     * 寻找 nums 数组 [0, right - 1] 中第一个大于等于 target 的下标
     * @param nums
     * @param right
     * @param target
     * @return
     */
    public int lowerBound(int[] nums, int right, int target) {
        if (nums[0] >= target) {
            return 0;
        }

        if (nums[right] < target) {
            return right + 1;
        }

        int left = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] >= target) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return left;
    }


    /**
     * 哈希预处理
     * 10^9 < 2^30 => 最大2进制字符串长度为30
     * 
     * @param s
     * @param queries
     * @return
     */
    public int[][] substringXorQueries(String s, int[][] queries) {
        char[] arr = s.toCharArray();
        Map<Integer, int[]> map = new HashMap<>();

        int n = arr.length;
        for (int left = 0; left < n; left++) {
            int tmp = 0;
            for (int right = left; right < Math.min(left + 30, n); right++) {
                tmp = (tmp << 1) | (arr[right] & 1);

                if (!map.containsKey(tmp) || map.get(tmp)[1] - map.get(tmp)[0] > (right - left)) {
                    map.put(tmp, new int[]{left, right});
                }
            }
        }

        int[][] ans = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            int num = queries[i][0] ^ queries[i][1];
            ans[i] = map.getOrDefault(num, new int[]{-1, -1});
        }

        return ans;
    }

    /**
     * 十进制转二进制
     * @param num
     * @return
     */
    public String decimalToBinary(int num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(num % 2);
            num /= 2;
        }

        return sb.reverse().toString();
    }




    public static void main(String[] args) {
        Day_2023_0212 test = new Day_2023_0212();
        System.out.println(test.decimalToBinary(9));
    }
}
