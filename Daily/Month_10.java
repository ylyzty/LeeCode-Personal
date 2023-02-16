package Daily;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Month_10 {
    /**
     * 10-01
     * LeeCode 1694: 重新格式化电话号码
     * @param number
     * @return
     */
    public String reformatNumber(String number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }

        int i = 0;
        while (sb.substring(i).length() > 4) {
            sb.insert(i + 3, '-');
            i += 4;
        }

        if (sb.substring(i).length() == 4) {
            sb.insert(i + 2, '-');
        }

        return sb.toString();
    }


    /**
     * 10-02
     * LeeCode 777: 在LR字符串中交换相邻字符
     * @param start
     * @param end
     * @return
     */
    public boolean canTranform(String start, String end) {
        // 特殊情况考虑
        if (start.length() != end.length()) {
            return false;
        }

        int n = start.length();
        int i = 0, j = 0;
        while (i < n && j < n) {
            while (i < n && start.charAt(i) == 'X') {
                i += 1;
            }
            while (j < n && end.charAt(j) == 'X') {
                j += 1;
            }

            if (i < n && j < n) {
                if (start.charAt(i) != end.charAt(j)) {
                    return false;
                }

                if (start.charAt(i) == 'L' && i < j) {
                    return false;
                }
                else if (start.charAt(i) == 'R' && i > j) {
                    return false;
                }
            }

            i += 1;
            j += 1;
        }

        while (i < n) {
            if (start.charAt(i) != 'X') {
                return false;
            }
            i += 1;
        }

        while (j < n) {
            if (end.charAt(j) != 'X') {
                return false;
            }
            j += 1;
        }

        return true;
    }


    protected static List<Integer> temp  = new ArrayList<>();
    public static List<Integer> test() {
        for (int i = 1; i <= 3; i++) {
            temp.add(i);
        }

        return temp;
    }


    public int minAddToMakeValid(String s) {
        if (s.length() == 0) {
            return 0;
        }

        int ans = 0;
        int stackSize = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stackSize += 1;
            }
            else {
                if (stackSize > 0) {
                    stackSize -= 1;
                }
                else {
                    ans += 1;
                }
            }
            
        }

        return ans + stackSize;
    }


    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> map = new HashMap<>();

        for (String s : cpdomains) {
            int times = Integer.parseInt(s.split(" ")[0]);

            String domain = s.split(" ")[1];
            while (domain.length() != 0) {
                map.put(domain, map.getOrDefault(domain, 0) + times);
                
                int index = domain.indexOf('.');
                if (index == -1) {
                    break;
                }
                else {
                    domain = domain.substring(index + 1);
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getValue());
            sb.append(" ");
            sb.append(entry.getKey());
            res.add(new String(sb));
        }

        return res;
    }


    public int[] threeEqualPars(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        if (sum == 0) {
            return new int[]{0, 2};
        }

        if (sum % 3 != 0) {
            return new int[]{-1, -1};
        }


        int pos = sum / 3;
        int first = 0, second = 0, third = 0;
        int cur = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                if (cur == 0) {
                    first = i;
                }
                else if (cur == pos) {
                    second = i;
                }
                else if (cur == 2 * pos) {
                    third = i;
                }

                cur += 1;
            }
        }

        int len = arr.length - third;
        if (first + len <= second && second + len <= third) {
            for (int i = 0; i < len; i++) {
                if (arr[first + i] != arr[second + i] || arr[second + i] != arr[third + i]) {
                    return new int[]{-1, -1};
                }
            }

            return new int[]{first + len - 1, second + len};
         }

         return new int[]{-1, -1};
    }


    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        
        Integer[] id1 = new Integer[n];
        Integer[] id2 = new Integer[n];
        for (int i = 0; i < n; i++) {
            id1[i] = i;
            id2[i] = i;
        }

        Arrays.sort(id1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return nums1[o1] - nums1[o2];
            }
        });

        Arrays.sort(id2, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return nums2[o1] - nums2[o2];
            }
        });

        int[] res = new int[n];
        int left = 0;
        int right = n - 1;
        for (int i = 0; i < n; i++) {
            if (nums1[id1[i]] > nums2[id2[left]]) {
                res[id2[left]] = nums1[id1[i]];
                left += 1;
            }
            else {
                res[id2[right]] = nums1[id1[i]];
                right -= 1;
            }
        }

        return res;
    }


    public int scoreOfParentheses(String s) {
        int count = 0;
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count += 1;
            }
            else {
                count -= 1;
                if (s.charAt(i - 1) == '(') {
                    res += 1 << count;
                }
            }
        }

        return res;
    }


    public int minSwap(int[] nums1, int[] nums2) {
        int n = nums1.length;

        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = 1;

        /**
         * 每一位有交换与不交换两种状态
         * nums1[0] = a, nums1[1] = b
         * nums2[0] = c, nums2[1] = d
         * 
         * (b > a && d > c), (d > a && b > c)
         * 上述两个条件至少有一个成立，否则无法实现交换后严格递增
         * 
         * dp[i][0] 表示子序列(0, i)不交换第i位保持严格递增所需的最小交换次数
         * dp[i][1] 表示子序列(0, i)交换第i位保持严格递增所需的最小交换次数
         * 
         * 情况1 (b > a && d > c)：
         * dp[i][0] = dp[i - 1][0]
         * dp[i][1] = dp[i - 1][1] + 1
         * 
         * 情况2 (d > a && b > c)：
         * dp[i][0] = dp[i - 1][1];
         * dp[i][1] = dp[i - 1][0] + 1;
         * 
         * 情况3 上述两个条件同时满足：
         * dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1])
         * dp[i][1] = Math.min(dp[i - 1][1], dp[i - 1][0]) + 1
         * 
         */
        for (int i = 1; i < n; i++) {
            dp[i][0] = n;
            dp[i][1] = n;

            if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            }

            if (nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]) {
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + 1);
            }
        }

        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }


    public boolean areAlmostEqual(String s1, String s2) {
        List<Integer> index = new ArrayList<>();
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                index.add(i);
            }
        }

        if (index.isEmpty()) {
            return true;
        }

        if (index.size() == 2) {
            int pos1 = index.get(0);
            int pos2 = index.get(1);
            return (s1.charAt(pos1) == s2.charAt(pos2) && s1.charAt(pos2) == s2.charAt(pos1));
        }

        return false;
    }


    public int numComponents(ListNode head, int[] nums) {
        int res = 0;

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        boolean flag = false;
        while (head != null) {
            if (set.contains(head.val)) {
                if (!flag) {
                    res += 1;
                    flag = true;
                }
            }
            else {
                flag = false;
            }

            head = head.next;
        }

        return res;
    }


    public int maxChunksToSorted(int[] arr) {
        int[] origin = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            origin[i] = arr[i];
        }
        Arrays.sort(arr);

        int res = 0;
        boolean flag = true;
        int maxVal = 0;
        for (int i = 0; i < arr.length; i++) {
            if (flag) {
                if (origin[i] == arr[i]) {
                    res += 1;
                }
                else {
                    maxVal = Math.max(maxVal, origin[i]);
                    flag = false;
                }
            }
            else {
                maxVal = Math.max(maxVal, origin[i]);
                if (arr[i] == maxVal) {
                    flag = true;
                    res += 1;
                }
            }
        }

        return res;
    }



    public int distinctSubseqII(String s) {
        final int MOD = 1000000007;

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] dp = new int[s.length()];
        Arrays.fill(dp, 1);

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (last[j] != - 1) {
                    dp[i] = (dp[i] + dp[last[j]]) % MOD;
                }
            }

            last[s.charAt(i) - 'a'] = i;
        }


        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (last[i] != - 1) {
                res = (res + dp[last[i]]) % MOD;
            }
        }

        return res;
    }


    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();

        int index = 0;
        for (int i = 1; i <= n; i++) {
            if (index == target.length) {
                return res;
            }

            if (i == target[index]) {
                index += 1;
                res.add(new String("Push"));
            }
            else {
                res.add(new String("Push"));
                res.add(new String("Pop"));
            }
        }

        return res;
    }


    /**
     * 
     * @param n
     * @param dislikes
     * 
     * 染色法, BFS
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean possibleBipartition(int n, int[][] dislikes) {
        // 染色法
        int[] color = new int[n + 1];

        // 图存储
        List<Integer>[] g = new List[n + 1];

        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int[] dislike : dislikes) {
            g[dislike[0]].add(dislike[1]);
            g[dislike[1]].add(dislike[0]);
        }

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) {
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(i);
                color[i] = 1;

                while (!queue.isEmpty()) {
                    int t = queue.poll();

                    for (int next : g[t]) {
                        if (color[next] > 0 && color[next] == color[t]) {
                            return false;
                        }

                        if (color[next] == 0) {
                            color[next] = 3 ^ color[t];
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        return true;
    }


    @SuppressWarnings("unchecked")
    public boolean possibleBipartition2(int n, int[][] dislikes) {
        int[] color = new int[n + 1];
        List<Integer>[] g = new List[n + 1];

        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int[] dislike : dislikes) {
            g[dislike[0]].add(dislike[1]);
            g[dislike[1]].add(dislike[0]);
        }

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0 && !dfs(i, 1, color, g)) {
                return false;
            }
        }

        return true;
    }

    public boolean dfs(int cur, int newColor, int[] color, List<Integer>[] g) {
        color[cur] = newColor;

        for (int next : g[cur]) {
            if (color[next] != 0 && color[next] == color[cur]) {
                return false;
            }

            if (color[next] == 0 && !dfs(cur, 3 ^ newColor, color, g)) {
                return false;
            }
        }

        return true;
    }


    public int totalFruit(int[] fruits) {
        if (fruits.length == 1) {
            return 1;
        }

        int ans = 1;
        int i = 0, j = 1;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(fruits[0], 1);

        while (j < fruits.length) {
            map.put(fruits[j], map.getOrDefault(fruits[j], 0) + 1);

            while (map.size() > 2) {
                map.put(fruits[i], map.get(fruits[i]) - 1);
                if (map.get(fruits[i]) == 0) {
                    map.remove(fruits[i]);
                }
            }

            ans = Math.max(ans, j - i + 1);
            j++;
        }

        return ans;
    }


    /**
     * 数位DP
     * @param digits
     * @param n
     * @return
     */
    public int atMostNGivenDigitSet(String[] digits, int n) {
        int[] dp = new int[11];
        int[] mi = new int[11];
        dp[0] = 0;
        mi[0] = 1;

        for (int i = 1; i < 11; i++) {
            mi[i] = mi[i - 1] * digits.length;
            dp[i] = dp[i - 1] + mi[i];
        }

        int len = 0;
        int[] mod = new int[11];
        int temp = n;
        while (n > 0) {
            mod[++len] = n % 10;
            n = n / 10;
        }

        int[] digits_int = new int[digits.length];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < digits.length; i++) {
            digits_int[i] = digits[i].charAt(0) - '0';
            set.add(digits_int[i]);
        }

        return dfs(digits_int, mod, mi, len, temp, set) + dp[len - 1];
    }

    public int dfs(int[] digits, int[] mod, int[] mi, int len, int num, Set<Integer> set) {
        if (len == 1) {
            return getIndex(digits, num);
        }

        int temp = mod[len];
        num -= temp * (int) (Math.pow(10, len - 1));

        if (set.contains(mod[len])) {
            return dfs(digits, mod, mi, len - 1, num, set) + getIndex(digits, temp - 1) * mi[len - 1];
        }
        else {
            return getIndex(digits, temp - 1) * mi[len - 1];
        }
    }

    public int getIndex(int[] digits, int n) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (n >= digits[i]) {
                return i + 1;
            }
        }

        return 0;
    }


    public int countStudents(int[] students, int[] sandwiches) {
        int[] count = new int[2];
        for (int student : students) {
            count[student] += 1;
        }

        for (int sandwich : sandwiches) {
            count[sandwich] -= 1;
            if (count[0] < 0 || count[1] < 0) {
                return Math.max(count[0], count[1]);
            }
        }

        return 0;
    }


    public int kthGrammar(int n, int k) {
        return dfs(n, k);
    }

    public int dfs(int n, int k) {
        if (n == 1) {
            return 0;
        }

        if (k % 2 != 0) {
            return dfs(n - 1, (k + 1) / 2);
        }
        else {
            return dfs(n - 1, (k + 1) / 2) ^ 1;
        }
    }


    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = profit.length;
        int[][] jobs = new int[n][3];

        for (int i = 0; i < n; i++) {
            jobs[i][0] = startTime[i];
            jobs[i][1] = endTime[i];
            jobs[i][2] = profit[i];
        }

        Arrays.sort(jobs, (o1, o2) -> {
            return o1[1] - o2[1];
        });


        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // // 二分查找
            // int left = 0, right = i;
            // while (left < right) {
            //     int mid = (left + right + 1) >> 1;

            //     if (jobs[mid - 1][1] > jobs[i - 1][0]) {
            //         right = mid - 1;
            //     }
            //     else {
            //         left = mid;
            //     }
            // }

            int k = binarySearch(jobs, i - 1, jobs[i - 1][0]);

            dp[i] = Math.max(dp[i - 1], dp[k + 1] + jobs[i - 1][2]);
        }

        return dp[n];
    }


    /**
     * 二分查找最大的小于 target 的值
     * @param jobs
     * @param right
     * @param target
     * @return
     */
    public int binarySearch(int[][] jobs, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = (left + right + 1) >> 1;

            if (jobs[mid][1] > target) {
                right = mid - 1;
            }
            else {
                left = mid;
            }
        }

        return jobs[right][0] <= target ? right : -1;
    }


    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();

        int i = 0, j = 0;
        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) {
                sb.append(word1.charAt(i));
                i += 1;
            }

            if (j < word2.length()) {
                sb.append(word2.charAt(j));
                j += 1;
            }
        }

        return sb.toString();
    }


    /**
     * maxLeft <= minRight
     * @param nums
     * @return
     */
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        int[] minRight = new int[n];
        minRight[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            minRight[i] = Math.min(minRight[i + 1], nums[i]);
        }

        int maxLeft = nums[0];
        for (int i = 1; i < n; i++) {
            if (maxLeft <= minRight[i]) {
                return i;
            }
        }

        return -1;
    }

    
    public int shortestBridge(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<Integer> queue = new ArrayDeque<>();
        Queue<Integer> island = new ArrayDeque<>();
        // BFS 搜索第一座岛屿
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    queue.offer(i * m + j);
                    grid[i][j] = -1;

                    while (!queue.isEmpty()) {
                        int cur = queue.poll();
                        int x = cur / m;
                        int y = cur % n;

                        island.offer(x * m +  y);
                        
                        for (int k = 0; k < directions.length; ++k) {
                            int nextX = x + directions[k][0];
                            int nextY = y + directions[k][1];

                            if (nextX >= 0 && nextY >= 0 && nextX < m && nextY < n && grid[nextX][nextY] == 1) {
                                queue.offer(nextX * m + nextY);
                                grid[nextX][nextY] = -1;
                            }
                        }
                    }

                    int res = 0;
                    while (!island.isEmpty()) {
                        int size = island.size();
                        for (int t = 0; t < size; ++t) {
                            int cur = island.poll();
                            int x = cur / m;
                            int y = cur % n;

                            for (int k = 0; k < directions.length; ++k) {
                                int nextX = x + directions[k][0];
                                int nextY = y + directions[k][1];

                                if (nextX >= 0 && nextY >= 0 && nextX < m && nextY < n && grid[nextX][nextY] != -1) {
                                    if (grid[nextX][nextY] == 1) {
                                        return res;
                                    }
                                    else {
                                        grid[nextX][nextY] = -1;
                                        island.offer(nextX * m + nextY);
                                    }
                                }
                            }
                        }

                        res += 1;
                    }
                }
            }
        }

        return 0;
    }

    
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        int res = n + 1;

        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] += prefix[i - 1] + nums[i - 1];
        }

        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerLast(0);

        for (int i = 1; i <= n; i++) {
            long cur = prefix[i + 1];

            while (!deque.isEmpty() && cur - prefix[deque.peekFirst()] >= k) {
                res = Math.min(res, i - deque.pollFirst());
            }

            while(!deque.isEmpty() && prefix[deque.peekLast()] >= cur) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }
        
        return res == n + 1 ? -1 : res;
    }


    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        if (!ruleKey.equals("type") && !ruleKey.equals("color") && !ruleKey.equals("name")) {
            return 0;
        }

        int res = 0;
        for (List<String> item : items) {
            res += isPair(item, ruleKey, ruleValue) ? 1 : 0;
        }

        return res;
    }

    public boolean isPair(List<String> item, String ruleKey, String ruleValue) {
        if (ruleKey.equals("type")) {
            return item.get(0).equals(ruleValue);
        }
        else if (ruleKey.equals("color")) {
            return item.get(1).equals(ruleValue);
        }
        else {
            return item.get(2).equals(ruleValue);
        }
    }


    private List<String> res = new ArrayList<>();
    public List<String> letterCasePermutation(String s) {
        StringBuilder sb = new StringBuilder();
        dfs(s, 0, sb);

        return res;
    }

    public void dfs(String s, int cur, StringBuilder sb) {
        if (cur == s.length()) {
            res.add(sb.toString());
            return;
        }

        char ch = s.charAt(cur);
        sb.append(ch);
        dfs(s, cur + 1, sb);
        sb.deleteCharAt(sb.length() - 1);

        if (ch - 'a' >= 0 && ch - 'z' <= 0) {
            sb.append((char) (ch - 32));
            dfs(s, cur + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
        else if (ch - 'A' >= 0 && ch - 'Z' <= 0) {
            sb.append((char) (ch + 32));
            dfs(s, cur + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    public int magicalString(int n) {
        if (n < 4) {
            return 1;
        }

        char[] arr = new char[n];
        arr[0] = '1';
        arr[1] = '2';
        arr[2] = '2';

        int left = 2, right = 3;
        int res = 1;
        while (right < n) {
            int len = arr[left] - '0';
            int num = '3' - arr[right - 1];

            while (len > 0 && right < n) {
                arr[right] = (char) ('0' + num);
                if (num == 1) {
                    res += 1;
                }
                right += 1;
                len -= 1;
            }

            left += 1;
        }

        return res;
    }
    

    public static void main(String[] args) {
        
    }
}


/**
 * 单调栈
 */
class StockSpanner {
    
    Stack<int[]> stack;
    int index;

    public StockSpanner() {
        stack = new Stack<>();
        stack.push(new int[]{-1, Integer.MAX_VALUE});
        index = -1;
    }
    
    public int next(int price) {
        index += 1;

        while (stack.peek()[1] <= price) {
            stack.pop();
        }

        int res = index - stack.peek()[0];
        stack.push(new int[]{index, price});
        
        return res;
    }
}
