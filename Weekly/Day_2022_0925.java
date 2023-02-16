package Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day_2022_0925 {
    public String[] sortPeople(String[] names, int[] heights) {
        int n = names.length;
        String[] res = new String[n];
        
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        
        Arrays.sort(index, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return heights[o2] - heights[o1];
            }
            
        });

        for (int i = 0; i < n; i++) {
            res[i] = names[index[i]];
        }

        return res;
    }


    /**
     * 等价于求数组最大值连续出现的最大次数
     * @param nums
     * @return
     */
    public int longestSubarray(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt();

        int res = 0;
        int temp = 0;
        for (int num : nums) {
            if (num == max) {
                temp += 1;
            }
            else {
                temp = 0;
            }

            res = Math.max(res, temp);
        }

        return res;
    }


    public List<Integer> goodIndices(int[] nums, int k) {
        int n = nums.length;

        if (k >= n - k) {
            return new ArrayList<>();
        }

        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        List<Integer> res = new ArrayList<>();

        dp1[0] = 1;
        dp2[n - 1] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] <= nums[i - 1]) {
                dp1[i] = dp1[i - 1] + 1;
            }
            else {
                dp1[i] = 1;
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] <= nums[i + 1]) {
                dp2[i] = dp2[i + 1] + 1;
            }
            else {
                dp2[i] = 1;
            }
        }

        for (int i = k; i < n - k; i++) {
            if (dp1[i - 1] >= k && dp2[i + 1] >= k) {
                res.add(i);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Day_2022_0925 test = new Day_2022_0925();

        int[] nums = {100, 5, 5};
        System.out.println(test.longestSubarray(nums));
    }
}
