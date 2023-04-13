package Daily;

import java.util.*;

public class Hot100 {

    /**
     * Hot100: 两数相加
     * 
     * 双指针
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode vir = new ListNode(-1);
        ListNode head = vir;
        int cnt = 0;
        while (l1 != null && l2 != null) {
            ListNode node = new ListNode((l1.val + l2.val + cnt) % 10);
            cnt = (l1.val + l2.val + cnt) / 10;

            head.next = node;
            head = head.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            ListNode node = new ListNode((l1.val + cnt) % 10);
            cnt = (l1.val + cnt) / 10;
            head.next = node;
            l1 = l1.next;
            head = head.next;
        }

        while (l2 != null) {
            ListNode node = new ListNode((l2.val + cnt) % 10);
            cnt = (l2.val + cnt) / 10;
            head.next = node;
            l2 = l2.next;
            head = head.next;
        }

        if (cnt == 1) {
            ListNode node = new ListNode(1);
            head.next = node;
        }

        return vir.next;
    }

    
    /**
     * Hot100: 无重复的最长子串
     * 滑动窗口
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;

        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(left, map.get(s.charAt(right)) + 1);
            }

            map.put(s.charAt(right), right);
            max = Math.max(max, right - left + 1);
        }

        return max;
    }


    /**
     * LeeCode 最长回文子串
     * 
     * 中心拓展
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        char[] array = s.toCharArray();

        int len = 1;
        String ans = s.substring(0, 1);
        for (int i = 0; i < 2 * n - 1; i++) {
            int left = i / 2;
            int right = (i + 1) / 2;

            while (left >= 0 && right < n && array[left] == array[right]) {
                left -= 1;
                right += 1;
            }

            if ((right - left - 1) > len) {
                len = right - left - 1;
                ans = s.substring(left + 1, right);
            }
        }

        return ans;
    }


    /**
     * Hot100: 盛最多水的容器
     * 
     * 容器面积 由短边决定，所有不断移动短边
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;

        int area = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                area = Math.max(area, height[left] * (right - left));
                left += 1;
            }
            else {
                area = Math.max(area, height[right] * (right - left));
                right -= 1;
            }
        }

        return area;
    }


    /**
     * Hot100: 三数之和
     * 
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();

        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // 排除重复三元组
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int target = -nums[i];
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                if (nums[left] + nums[right] < target) {
                    left += 1;
                }

                else if (nums[left] + nums[right] < target) {
                    right -= 1;
                }

                else {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(nums[i]);
                    tmp.add(nums[left]);
                    tmp.add(nums[right]);

                    ans.add(tmp);

                    // 去除重复元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left += 1;
                    }

                    while (left < right && nums[right] == nums[right - 1]) {
                        right -= 1;
                    }

                    left += 1;
                    right -= 1;
                }
            }
        }

        return ans;
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode vir = new ListNode(-1);
        vir.next = head;

        ListNode node1 = vir;
        ListNode node2 = vir;

        while (n > 0) {
            node2 = node2.next;
            n -= 1;
        }

        while (node2.next != null) {
            node1 = node1.next;
            node2 = node2.next;
        }

        node1.next = node1.next.next;

        return vir.next;
    }


    Map<Character, String> map;
    List<String> ans;
    public List<String> letterCombinations(String digits) {
        ans = new ArrayList<>();

        if (digits.length() == 0) {
            return ans;
        }

        map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        char[] arr = digits.toCharArray();
        StringBuilder sb = new StringBuilder();
        letterCombinationsDfs(sb, 0, arr);

        return ans;
    }

    public void letterCombinationsDfs(StringBuilder sb, int cur, char[] arr) {
        if (cur == arr.length) {
            ans.add(sb.toString());
            return;
        }

        for (char c : map.get(arr[cur]).toCharArray()) {
            sb.append(c);
            letterCombinationsDfs(sb, cur + 1, arr);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    /**
     * Hot100: 括号生成
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        StringBuilder sb = new StringBuilder();
        generateParenthesisDfs(sb, n, 0, 0);

        return ans;
    }

    public void generateParenthesisDfs(StringBuilder sb, int n, int left, int right) {
        if (right == n) {
            ans.add(sb.toString());
            return;
        }

        if (left < n) {
            sb.append('(');
            generateParenthesisDfs(sb, n, left + 1, right);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (right < left) {
            sb.append(')');
            generateParenthesisDfs(sb, n, left, right + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    /**
     * 下一个排列
     * 
     * 扫描找到第一个较小数, 并与最右边的第一个大于它的数交换
     * 交换之后将右边的数字顺序反转
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        // 第一遍扫描, 找到较小数
        int left = nums.length - 2;
        while (left >= 0 && nums[left] >= nums[left + 1]) {
            left -= 1;
        }

        if (left >= 0) {
            for (int right = nums.length - 1; right > left; right--) {
                if (nums[right] > nums[left]) {
                    swap(nums, left, right);
                    break;
                }
            }
        }

        // 重排列 left 右边的数, 使其升序排列
        left = left + 1;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left += 1;
            right -= 1;
        }
    }


    /**
     * 搜索旋转排序数组
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    right = mid - 1;
                }
                else {
                    left = mid + 1;
                }
            }
            else {
                if (target <= nums[nums.length - 1] && nums[mid] < target) {
                    left = mid + 1;
                }
                else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }


    /**
     * 第一个缺失的正整数
     * 
     * 原地哈希
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != (i + 1)) {
                return i + 1;
            }
        }

        return n + 1;
    }


    /**
     * 查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};
        if (nums.length == 0) {
            return ans;
        }

        ans[0] = searchLeftAndRight(nums, target, true);
        ans[1] = searchLeftAndRight(nums, target, false);

        return ans;
    }

    /**
     * 
     * @param nums
     * @param target
     * @param flag: true -> 第一个, false -> 最后一个
     * @return
     */
    public int searchLeftAndRight(int[] nums, int target, boolean flag) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            if (flag) {
                int mid = left + (right - left) / 2;

                if (nums[mid] >= target) {
                    right = mid;
                }
                else {
                    left = mid + 1;
                }
            }
            else {
                int mid = left + (right - left + 1) / 2;

                if (nums[mid] <= target) {
                    left = mid;
                }
                else {
                    right = mid - 1;
                }
            }
        }

        if (nums[left] == target) {
            return left;
        }

        return -1;
    }


    /**
     * 组合总和
     * 
     * 回溯
     * @param candidates
     * @param target
     * @return
     */
    List<List<Integer>> combinationSumAns;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        combinationSumAns = new ArrayList<>();
        combinationSumDfs(0, 0, target, candidates, new ArrayList<>());

        return combinationSumAns;
    }

    public void combinationSumDfs(int index, int curSum, int target, int[] candidates, List<Integer> tmp) {
        if (curSum == target) {
            combinationSumAns.add(new ArrayList<>(tmp));
            return;
        }



        for (int i = index; i < candidates.length; i++) {
            if (curSum + candidates[i] > target) {
                break;
            }

            tmp.add(candidates[i]);
            combinationSumDfs(i, curSum + candidates[i], target, candidates, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }


    /**
     * 全排列
     * @param nums
     * @return
     */
    List<List<Integer>> permuteAns;
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> tmp = new ArrayList<>();
        for (int num : nums) {
            tmp.add(num);
        }

        permuteAns = new ArrayList<>();
        permuteDfs(0, nums.length, tmp);

        return permuteAns;
    }

    public void permuteDfs(int cur, int n, List<Integer> tmp) {
        if (cur == n) {
            permuteAns.add(new ArrayList<>(tmp));
            return;
        }

        // 动态维护数组, 在 cur 位置填入不同的数字
        for (int i = cur; i < n; i++) {
            Collections.swap(tmp, cur, i);
            permuteDfs(cur + 1, n, tmp);
            Collections.swap(tmp, cur, i);
        }
    }


    /**
     * 全排列II
     * @param nums
     * @return
     */
    List<List<Integer>> permuteUniqueAns;
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<Integer> tmp = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        permuteUniqueAns = new ArrayList<>();
        permuteUniqueDfs(0, nums, tmp, used);

        return permuteUniqueAns;
    }

    public void permuteUniqueDfs(int cur, int[] nums, List<Integer> tmp, boolean[] used) {
        if (cur == nums.length) {
            permuteUniqueAns.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {

            // 去除重复填入
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }

            tmp.add(nums[i]);
            used[i] = true;
            permuteUniqueDfs(cur + 1, nums, tmp, used);
            used[i] = false;
            tmp.remove(tmp.size() - 1);
        }
    }


    /**
     * 原地旋转图像
     * 
     * [i, j] -> [j, n - 1 - i] -> [n - 1 - i, n - 1 - j] -> [n - 1 - j, i] -> [i, j]
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 -i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - i - 1] = tmp;
            }
        }
    }


    /**
     * 字母异位词分组
     * 
     * key: 排序后的字符串
     * value: List<String>
     * 
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
           char[] arr = str.toCharArray();
           Arrays.sort(arr);
           String key = new String(arr);

            List<String> tmp = map.getOrDefault(key, new ArrayList<>());
            tmp.add(str);
            map.put(key, tmp);
        }

        return new ArrayList<>(map.values());
    }


    /**
     * 最大子数组和
     * 动态规划
     * 
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int pre = 0;

        for (int i = 0; i < nums.length; i++) {
            pre = Math.max(pre, 0) + nums[i];
            ans = Math.max(ans, pre);
        }

        return ans;
    }


    /**
     * 跳跃游戏
     * 
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;

        int maxPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= maxPos) {
                maxPos = Math.max(maxPos, i + nums[i]);
                if (maxPos >= n - 1) {
                    return true;
                }
            }
        }
        
        return false;
    }


    /**
     * 合并区间
     * 排序
     * 
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> {
            return o1[0] - o2[0];
        });

        List<List<Integer>> ans = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (right >= intervals[i][0]) {
                right = Math.max(right, intervals[i][1]);
            }
            else {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(left);
                tmp.add(right);
                ans.add(tmp);

                left = intervals[i][0];
                right = intervals[i][1];
            }
        }

        List<Integer> tmp = new ArrayList<>();
        tmp.add(left);
        tmp.add(right);
        ans.add(tmp);

        int[][] arr = new int[ans.size()][2];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = ans.get(i).get(0);
            arr[i][1] = ans.get(i).get(1);
        }

        return arr;
    }


    /**
     * 不同路径
     * 
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        dp[1][1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m][n];
    }


    /**
     * 最小路径和
     * 
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }


    /**
     * 颜色分类
     * 
     * @param nums
     */
    public void sortColors(int[] nums) {
        int index1 = 0;
        int index2 = nums.length - 1;

        int cur = 0;
        while (cur <= index2) {
            if (nums[cur] == 0) {
                swap(nums, index1, cur);
                index1 += 1;
                cur += 1;
            }
            else if (nums[cur] == 2) {
                swap(nums, index2, cur);
                index2 -= 1;
            }
            else {
                cur += 1;
            }
        }
    }


    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {
        Hot100 test = new Hot100();

        int[] nums = {-1, 0, 1, 2, -1, 4};
        System.out.println(test.threeSum(nums));
    }

}
 