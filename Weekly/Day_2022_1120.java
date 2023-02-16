package Weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class Day_2022_1120 {
    public int unequalTriplets(int[] nums) {
        Arrays.sort(nums);

        int ans = 0;
        int n = nums.length;
        int left = 0, right = 0;
        
        while (right < n) {
            while (right < n && nums[right] == nums[left]) {
                right += 1;
            }

            ans += left * (right - left) * (n - right);
            left = right;
        }

        return ans;
    }

    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        //BFS搜索树
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);

        List<Integer> list = new ArrayList<>();
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                list.add(node.val);

                if (node.left != null) {
                    deque.offer(node.left);
                }

                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
        }

        list.sort((o1, o2) -> {return o1 - o2;});

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            ans.add(binarySearch(list, queries.get(i)));
        }

        return ans;
    }

    public List<Integer> binarySearch(List<Integer> list, int target) {
        List<Integer> ans = new ArrayList<>();
        if (list.get(0) > target) {
            ans.add(-1);
            ans.add(list.get(0));

            return ans;
        }
        else if (list.get(list.size() - 1) < target) {
            ans.add(list.get(list.size() - 1));
            ans.add(-1);
            
            return ans;
        }

        int left = 0, right = list.size() - 1;
        
        // 二分查找小于等于 target 的最大值
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (list.get(mid) > target) {
                right = mid - 1;
            }
            else {
                left = mid;
            }
        }
        ans.add(list.get(left));

        left = 0; 
        right = list.size() - 1;
        //二分查找大于等于 target 的最小值
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        ans.add(list.get(left));

        return ans;
    }


    public long ans = 0;
    public long minimumFuelCost(int[][] roads, int seats) {
        int len = roads.length;
        // Build Tree
        List<Integer>[] tree = new List[len + 1];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int src = road[0];
            int trg = road[1];

            tree[src].add(trg);
            tree[trg].add(src);
        }

        dfs(tree, 0, -1, seats);
        return ans;
    }

    public int dfs(List<Integer>[] tree, int cur, int last, int seats) {
        // size 表示当前节点需要运送多少人
        int size = 1;

        for (int next : tree[cur]) {
            if (next != last) {
                size += dfs(tree, next, cur, seats);
            }
        }

        if (cur > 0) {
            ans += (size + seats - 1) / seats;
        }

        return size;
    }

    
    public static final int MOD = (int) (1e9 + 7);
    public int beautifulPartitions(String s, int k, int minLength) {
        int n = s.length();
        char[] arr = s.toCharArray();

        if (k * minLength > n || !isPrime(arr[0]) || isPrime(arr[n - 1])) {
            return 0;
        }


        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;

        // 枚举分割次数
        for (int i = 1; i <= k; i++) {
            int sum = 0;

            // 枚举分割位置
            for (int j = i * minLength; j + (k - i) * minLength <= n; j++) {
                if (canSplit(arr, j - minLength)) {
                    sum = (sum + dp[i - 1][j - minLength]) % MOD;
                }

                if (canSplit(arr, j)) {
                    dp[i][j] = sum;
                }
            }
        }

        return dp[k][n];
    }

    // 判断质数
    public boolean isPrime(char c) {
        return c == '2' || c == '3' || c == '5' || c == '7';
    }

    // 判断 j 是否可以作为分割点
    public boolean canSplit(char[] arr, int j) {
        return j == 0 || j == arr.length || (!isPrime(arr[j - 1]) && isPrime(arr[j]));
    }


    public static void main(String[] args) {
        Day_2022_1120 test = new Day_2022_1120();
        String s = "783938233588472343879134266968";
        System.out.println(test.beautifulPartitions(s, 4, 6));
    }
}