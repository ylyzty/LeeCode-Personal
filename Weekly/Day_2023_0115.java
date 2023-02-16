package Weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_2023_0115 {
    
    public int differenceOfSum(int[] nums) {
        int sum1 = 0;
        int sum2 = 0;

         for (int num : nums) {
            sum1 += num;

            while (num != 0) {
                sum2 += num % 10;
                num /= 10;
            }
         }

         return Math.abs(sum1 - sum2);
    }


    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] ans = new int[n][n];

        for (int[] query : queries) {
            int row1 = query[0];
            int col1 = query[1];
            int row2 = query[2];
            int col2 = query[3];

            for (int row = row1; row <= row2; row++) {
                for (int col = col1; col <= col2; col++) {
                    ans[row][col] += 1;
                }
            }
        }

        return ans;
    }


    public long countGood(int[] nums, int k) {
        int n = nums.length;
        
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0, right = 1;
        int count = 0;
        map.put(nums[0], 1);
        
        long ans = 0;
        while (right < n) {
            while (count < k && right < n) {
                map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
                count += map.get(nums[right]) - 1;
                right += 1;
            }

            if (count >= k) {
                ans += n - right + 1;

                while (count >= k) {
                    map.put(nums[left], map.get(nums[left]) - 1);
                    count -= map.get(nums[left]);
                    left += 1;
                    
                    if (count >= k) {
                        ans += n - right + 1;
                    }
                }
            }
        }

        return ans;
    }


    public long maxOutput(int n, int[][] edges, int[] price) {
        // Build Graph
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int src = edge[0];
            int tgr = edge[1];

            graph[src].add(tgr);
            graph[tgr].add(src);
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            
        }

        return 0;
    }

    public long dfs(int cur, int[] price, int last) {
        return 0;
    }

}
