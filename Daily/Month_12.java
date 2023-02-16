package Daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Month_12 {
    public int nearestValidPoint(int x, int y, int[][] points) {
        int ans = -1;
        int dis = Integer.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {
            if (points[i][0] == x || points[i][1] == y) {
                int temp = Math.abs(points[i][0] - x) + Math.abs(points[i][1] - y);
                if (temp < dis) {
                    ans = i;
                    dis = temp;
                }
            }
        }

        return ans;
    }


    public int[] minOperations(String boxes) {
        int n = boxes.length();
        int[] ans = new int[n];

        int left = boxes.charAt(0) - '0';
        int right = 0;
        int operations = 0;
        for (int i = 1; i < n; i++) {
            if (boxes.charAt(i) == '1') {
                right += 1;
                operations += i;
            }
        }

        ans[0] = operations;
        for (int i = 1; i < n; i++) {
            operations += left - right;

            if (boxes.charAt(i) == 1) {
                left += 1;
                right -= 1;
            }

            ans[i] = operations;
        }

        return ans;
    }


    public int secondHighest(String s) {
        int first = -1;
        int second = -1;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int num = ch - '0';
                if (num > first) {
                    first = num;
                    second = first;
                }
                else if (num != first && num > second) {
                    second = num;
                }
            }
        }

        return second;
    }


    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int m = toppingCosts.length;
        
        int min = Arrays.stream(baseCosts).min().getAsInt();
        if (min >= target) {
            return min;
        }

        int ans = 2 * target - min;
        Arrays.sort(toppingCosts);

        int targetCur = ans;
        boolean[][] dp = new boolean[m + 1][targetCur + 1];
        for (int baseCost : baseCosts) {
            if (baseCost <= target) {
                for (int i = 0; i <= m; i++) {
                    dp[i][baseCost] = true;
                }
            }
            else {
                ans = Math.min(ans, baseCost);
            }
        }


        
        for (int i = 1; i <= m; i++) {
            
                for (int j = 1; j <= targetCur; j++) {
                    
                    if (j >= 2 * toppingCosts[i - 1]) {
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - toppingCosts[i - 1]] || dp[i - 1][j - 2 * toppingCosts[i - 1]];
                    }
                    else if (j >= toppingCosts[i - 1]) {
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - toppingCosts[i - 1]];
                    }
                    else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            
        }

        for (int j = 0; j <= ans - target; j++) {
            if (dp[m][target - j]) {
                return target - j;
            }

            if (dp[m][target + j]) {
                return target + j;
            }
        }

        return ans;
    }


    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();

        int n = word.length();
        int left = 0;
        int right = n - 1;
        while (true) {
            while (left < n && !Character.isDigit(word.charAt(left))) {
                left += 1;
            }

            if (left == n) {
                break;
            }

            right = left;
            while (right < n && Character.isDigit(word.charAt(right))) {
                right += 1;
            }
            while (left < right - 1 && word.charAt(left) == '0') {
                left += 1;
            }

            set.add(word.substring(left, right));
            left = right;
        }

        return set.size();
    }


    public int minOperations(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int sum1 = Arrays.stream(nums1).sum();
        int sum2 = Arrays.stream(nums2).sum();

        if (m > n * 6 || n > m * 6) {
            return -1;
        }

        int diff = sum2 - sum1;
        if (diff < 0) {
            diff = -diff;
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        // sum1 < sum2
        int ans = 0;
        int[] count = new int[6];
        for (int num : nums1) {
            count[6 - num] += 1;
        }
        for (int num : nums2) {
            count[num - 1]++;
        }

        for (int i = 5; i >= 0; i--) {
            if (i * count[i] >= diff) {
                return ans + (diff + i - 1) / i;
            }

            ans += count[i];
            diff -= i * count[i];
        }

        return ans;
    }


    public boolean checkPowersOfThree(int n) {
        int count = 0;
        int num = 1;
        while (num < n) {
            num *= 3;
            count += 1;
        }

        if (num == n) {
            return true;
        }

        while (count >= 0) {
            if ((int) Math.pow(3, count) <= n) {
                n -= (int) Math.pow(3, count);
            }

            count -= 1;
        }

        return n == 0;
    }


    public int beautySum(String s) {
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            int[] count = new int[26];
            int max = 0;

            for (int j = i; j < s.length(); j++) {
                count[s.charAt(j) - 'a'] += 1;
                max = Math.max(max, count[s.charAt(j) - 'a']);

                int min = s.length();
                for (int k = 0; k < 26; k++) {
                    if (count[k] > 0) {
                        min = Math.min(min, count[k]);
                    }
                }

                ans += max - min;
            }
        }

        return ans;
    }


    public boolean checkIfPangram(String sentence) {
        boolean[] flags = new boolean[26];
        int count = 0;
        for (char ch : sentence.toCharArray()) {
            if (!flags[ch - 'a']) {
                flags[ch - 'a'] = true;
                count += 1;

                if (count == 26) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 并查集
     * @param n
     * @param edgeList
     * @param queries
     * @return
     */
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        // edge rank by length
        Arrays.sort(edgeList, (o1, o2) -> {
            return o1[2] - o2[2];
        });

        // query 按从小到达查询
        int len = queries.length;
        Integer[] ids = new Integer[len];
        for (int i = 0; i < len; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (o1, o2) -> {
            return queries[o1][2] - queries[o2][2];
        });

        
        boolean[] ans = new boolean[len];
        int[] uf = new int[n];
        for (int i = 0; i < n; i++) {
            uf[i] = i;
        }

        int i = 0;
        for (int id : ids) {
            while (i < edgeList.length && edgeList[i][2] < queries[id][2]) {
                merge(uf, edgeList[i][0], edgeList[i][1]);
                i++;
            }

            ans[i] = find(uf, queries[id][0]) == find(uf, queries[id][1]);
        }

        return ans;
    }

    public int find(int[] uf, int x) {
        if (uf[x] == x) {
            return x;
        }

        return uf[x] = find(uf, uf[x]);
    }

    public void merge(int[] uf, int x, int y) {
        x = find(uf, x);
        y = find(uf, y);

        uf[y] = x;
    }


    public int getLucky(String s, int k) {
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            sb.append(String.valueOf(ch - 'a' + 1));
        }

        String ans = sb.toString();
        while (k > 0) {
            k -= 1;
            ans = String.valueOf(help(ans));
        }

        return Integer.valueOf(ans);
    }

    public int help(String s) {
        int res = 0;
        for (char ch : s.toCharArray()) {
            res += ch - '0';
        }

        return res;
    }


    public int minElements(int[] nums, int limit, int goal) {
        long sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int ans = 0;

        long diff = Math.abs(sum - goal);
        ans += diff / limit;

        if (diff % limit != 0) {
            ans += 1;
        }

        return ans;
    }


    public boolean canChoose(int[][] groups, int[] nums) {
        int k = 0;
        for (int i = 0; i < groups.length; i++) {
            k = find(nums, k, groups[i]);
            if (k == -1) {
                return false;
            }

            k += groups[i].length;
        }

        return true;
    }

    public int find(int[] nums, int k, int[] group) {
        if (group.length + k > nums.length) {
            return -1;
        }

        int[] next = buildNext(group);

        for (int i = k, j = 0; i < nums.length; i++) {
            if (j !=  0 && nums[i] != group[j]) {
                j = next[j - 1];
            }

            if (nums[i] == group[j]) {
                j += 1;
            }

            if (j == group.length) {
                return i - j + 1;
            }
        }

        return -1;
    }

    public int[] buildNext(int[] array) {
        int n = array.length;
        int[] next = new int[n];

        for (int i = 1; i < n; i++) {
            int j = next[i - 1];

            while (j > 0 && array[i] != array[j]) {
                j = next[j - 1];
            }

            if (array[i] == array[j]) {
                j += 1;
            }

            next[i] = j;
        }

        return next;
    }


    public int maxHeight(int[][] cuboids) {
        return 0;
    }


    public static void main(String[] args) {
        // Month_12 test = new Month_12();
        // System.out.println(test.beautySum("aabcb"));

        Color c = Color.valueOf("RED");
        Color c1 = Color.valueOf(Color.class, "RED");
        System.out.println(c1.getClass());
        System.out.println(c.getClass().getSuperclass());
        System.out.println(c.getClass().getSuperclass().getSuperclass());
    }
}


/**
 * Color 默认继承 java.lang.Enum 抽象类
 * RED ==> public static final Color RED = new Color();
 */
enum Color {
    RED("The color is red."),
    GREEN("The color is green."),
    BLUE("The color is blue.");

    private final String description;

    private Color(String description) {
        this.description = description;
        System.out.println("Constructor called for: " + this.toString());
    }

    public String getDescription() {
        return this.description;
    }

    public static void main(String[] args) {
        Color c = Color.GREEN;
        System.out.println(c.getDescription());
    }
}
