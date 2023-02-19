package Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day_2023_0218 {
    public int minMaxDifference(int num) {
        if (num < 10) {
            return 9;
        }

        StringBuilder sb = new StringBuilder(String.valueOf(num));
        StringBuilder sb2 = new StringBuilder(sb);

        int len = sb.length();
        char c = sb.charAt(0);
        for (int i = 0; i < len; i++) {
            if (sb.charAt(i) == c) {
                sb.setCharAt(i, '0');
            }
        }

        int min = Integer.valueOf(sb.toString());

        for (int i = 0; i < len; i++) {
            char ch = sb2.charAt(i);
            if (ch < '9') {
                for (int j = i; j < len; j++) {
                    if (sb2.charAt(j) == ch) {
                        sb2.setCharAt(j, '9');
                    }
                }

                break;
            }
        }

        int max = Integer.valueOf(sb2.toString());

        return max - min;
    }


    public int minimizeSum(int[] nums) {
        if (nums.length == 3) {
            return 0;
        }

        Arrays.sort(nums);

        // 情况一
        int max = Integer.MAX_VALUE;
        max = Math.min(max, nums[nums.length - 3] - nums[0]);
        max = Math.min(max, nums[nums.length - 2] - nums[1]);
        max = Math.min(max, nums[nums.length - 1] - nums[1]);
        max = Math.min(max, nums[nums.length - 1] - nums[2]);

        return max;
    }


    public int minImpossibleOR(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        for (int i = 1; i < Integer.MAX_VALUE; i = i * 2) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return 0;
    }


    public long[] handleQuery(int[] nums1, int[] nums2, int[][] queries) {
        List<Long> list = new ArrayList<>();
        long sum = Arrays.stream(nums2).sum();

        // 维护 1
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == 1) {
                set.add(i);
            }
        }
        
        for (int[] query : queries) {
            if (query[0] == 1) {
                int left = query[1];
                int right = query[2];

                for (int i = left; i <= right; i++) {
                    if (set.contains(i)) {
                        set.remove(i);
                    }
                    else {
                        set.add(i);
                    }
                }
            }
            else if (query[0] == 2) {
                long p = query[1];
                sum += set.size() * p;
            }
            else if (query[0] == 3) {
                list.add(sum);
            }
        }

        long[] ans = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }

        return ans;
    }


    public static void main(String[] args) {
        // Day_2023_0218 test = new Day_2023_0218();
        // test.minMaxDifference(11891);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        set.remove(1);
        set.remove(3);
        System.out.println("111");
    }
}
