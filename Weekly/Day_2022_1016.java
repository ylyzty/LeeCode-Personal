package Weekly;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day_2022_1016 {
    public static void main(String[] args) {
        
    }

    public int findMaxK(int[] nums) {
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;

        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == 0) {
                return nums[j];
            }
            else if (sum < 0) {
                i += 1;
            }
            else {
                j -= 1;
            }
        }

        return -1;
    }


    public int countDistinctIntegers(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);

            StringBuilder sb = new StringBuilder(String.valueOf(num));
            int val = Integer.parseInt(sb.reverse().toString());
            set.add(val);
        }

        return set.size();
    }


    public boolean sumOfNumberAndReverse(int num) {
        for (int i = 0; i <= num; i++) {
            if (i + getReverse(i) == num) {
                return true;
            }
        }

        return false;
    }

    public int getReverse(int num) {
        return Integer.parseInt(new StringBuilder(String.valueOf(num)).reverse().toString());
    }


    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0;
        int left = 0;

        int posMin = -1, posMax = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == minK) {
                posMin = i;
            }

            if (nums[i] == maxK) {
                posMax = i;
            }

            if (nums[i] < minK || nums[i] > maxK) {
                left = i + 1;
            }

            ans += Math.max(0, Math.min(posMin, posMax) - left + 1);
        }

        return ans;
    }
}
