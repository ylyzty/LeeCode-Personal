package Weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Day_2022_1113 {
    public int subarrayLCM(int[] nums, int k) {
        int res = 0;

        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > k) {
                continue;
            }

            int temp = nums[i];
            for (int j = i; j < nums.length; j++) {
                if (nums[j] > k) {
                    break;
                }

                temp = lcm(temp, nums[j]);
                if (temp > k) {
                    break;
                }

                if (temp == k) {
                    res += 1;
                }
            }
        }

        return res;
    }

    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }

        return gcd(b % a, a);
    }


    public int minimumOperations(TreeNode root) {
        //BFS
        int res = 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                arr[i] = node.val;

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            if (size <= 1) {
                continue;
            }
            
            res += getTimes(arr);
        }

        return res;
    }


    public int getTimes(int[] arr) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        for (int i = 0; i < copy.length; i++) {
            map.put(copy[i], i);
        }

        boolean[] flags = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++) {
            if (!flags[i]) {
                int j = i;
                while (!flags[j]) {
                    flags[j] = true;
                    j = map.get(arr[j]);
                }

                flags[i] = true;
                count += 1;
            }
        }

        return arr.length - count;
    }


    public int maxPalindromes(String s, int k) {
        char[] arr = s.toCharArray();
        int n = arr.length;

        int[] dp = new int[n + 1];

        // 中心拓展枚举回文子串
        for (int i = 0; i < 2 * n - 1; i++) {
            int left = i / 2;
            int right = i / 2 + i % 2;

            dp[left + 1] = Math.max(dp[left + 1], dp[left]);
            while (left >= 0 && right < n && arr[left] == arr[right]) {
                if (right - left + 1 >= k) {
                    dp[right + 1] = Math.max(dp[right + 1], dp[left] + 1);
                }

                left -= 1;
                right += 1;
            }
        }

        return dp[n];
    }
}
