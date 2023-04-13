package Reviews;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Alibaba {
    public static void main(String[] args) {
        Alibaba test = new Alibaba();

        int[] nums = {2, 1, 2, 2};
        System.out.println(test.test_2(nums));
    }

    public int test_2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        Set<Integer> set = map.keySet();
        int[] keys = new int[set.size()];
        int index = 0;
        for (int key : set) {
            keys[index++] = key;
        }
        Arrays.sort(keys);

        int ans = 0;
        for (int i = 1; i < keys.length; i++) {
            if (keys[i] - keys[i - 1] == 1) {
                int cnt1 = map.get(keys[i - 1]);
                int cnt2 = map.get(keys[i]);
                ans += cnt1 * (cnt2 * (cnt2 - 1) / 2) + cnt2 * (cnt1 * (cnt1 - 1) / 2);
            }
        }

        return ans;
    }

    
    public int test_3(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);

        // 枚举×2的个数 i
        // 前 i 小的元素乘 2
        // 前 k - i 大的元素除 2
        int ans = (int) 1e9;
        for (int i = 0; i <= k; i++) {
            int mi = (int) 1e9;
            int mx = 0;

            if (i > 0) {
                mi = Math.min(mi, nums[0] * 2);
                mx = Math.max(mx, nums[i - 1] * 2);
            }

            if ((k - i) > 0) {
                mi = Math.min(mi, nums[n - (k - i)] / 2);
                mx = Math.max(mx, nums[n - 1] / 2);
            }

            if (n != k) {
                mi = Math.min(mi, nums[i]);
                mx = Math.max(mx, nums[n - (k - i) - 1]);
            }

            ans = Math.min(ans, mx - mi);
        }

        return ans;
    }
}
