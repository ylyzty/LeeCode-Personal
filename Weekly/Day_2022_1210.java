package CodeCatalog.Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_2022_1210 {
    public int maximumValue(String[] strs) {
        int ans = 0;

        for (String str : strs) {
            int temp = 0;
            boolean flag = true;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    flag = false;
                    break;
                }

                temp = temp * 10 + str.charAt(i) - '0';
            }

            if (flag) {
                ans = Math.max(ans, temp);
            }
            else {
                ans = Math.max(ans, str.length());
            }
        }

        return ans;
    }


    public int maxStarSum(int[] vals, int[][] edges, int k) {
        int ans = Arrays.stream(vals).max().getAsInt();

        // Build Graph
        int n = vals.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        for (int i = 0; i < n; i++) {
            graph[i].sort(new Comparator<Integer>() {

                @Override
                public int compare(Integer o1, Integer o2) {
                    // TODO Auto-generated method stub
                    return vals[o2] - vals[o1];
                }
                
            });

            int temp = vals[i];
            for (int j = 1; j <= k; j++) {
                if (j <= graph[i].size() && vals[graph[i].get(j - 1)] > 0) {
                    temp += vals[graph[i].get(j - 1)];
                }
                else {
                    break;
                }
            }

            ans = Math.max(ans, temp);
        }

        return ans;
    }


    /**
     * 贪心
     * @param stones
     * @return
     */
    
    public int maxJump(int[] stones) {
        int ans = stones[1] - stones[0];

        for (int i = 2; i < stones.length; i++) {
            ans = Math.max(ans, stones[i] - stones[i - 2]);
        }

        return ans;
    }


    public long minimumTotalCost(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();

        int sumCount = 0;
        int maxCount = 0;
        int maxKey = 0;
        long ans = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == nums2[i]) {
                sumCount += 1;
                ans += i;
                
                map.put(nums1[i], map.getOrDefault(nums1[i], 0) + 1);
                if (map.get(nums1[i]) > maxCount) {
                    maxCount = map.get(nums1[i]);
                    maxKey = nums1[i];
                }
            }
        }

        if (maxCount <= sumCount / 2) {
            return ans;
        }

        int target = 2 * maxCount - sumCount;
        for (int i = 0; i < nums1.length && target > 0; i++) {
            if (nums1[i] != nums2[i] && nums1[i] != maxKey && nums2[i] != maxKey) {
                target -= 1;
                ans += i;
            }
        }
        
        if (target == 0) {
            return ans;
        }

        return -1;
    }


    public static void main(String[] args) {
        Day_2022_1210 test = new Day_2022_1210();
        System.out.println(test.maxJump(new int[]{0, 2, 5, 6, 7}));
    }
}
