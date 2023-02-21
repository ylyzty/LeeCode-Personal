package Daily;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class KMP2 {
    public int[] buildNext(String s) {
        int n = s.length();
        int[] next = new int[n];

        for (int i = 1; i < n; i++) {
            int j = next[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j += 1;
            }

            next[i] = j;
        }

        return next;
    }

    /**
     * 时间复杂度 O(M + N)
     * @param s
     * @param pattern
     * @return
     */
    public int match(String s, String pattern) {
        int[] next = buildNext(pattern);

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            while (j != 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }

            if (j == pattern.length()) {
                return i - j + 1;
            }
        }

        return -1;
    }
}


/**
 * 根据元素的频率维护多个栈
 */
class FreqStack {
    // 元素的频率
    private Map<Integer, Integer> freq;

    // 根据频率维护的栈
    private Map<Integer, Deque<Integer>> values;

    private int maxFreq;
    
    // 构造
    public FreqStack() {
        freq = new HashMap<>();
        values = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        freq.put(val, freq.getOrDefault(val, 0) + 1);
        values.putIfAbsent(freq.get(val), new ArrayDeque<>());
        
        values.get(freq.get(val)).push(val);
        maxFreq = Math.max(maxFreq, freq.get(val));
    }

    public int pop() {
        int res = values.get(maxFreq).peek();
        freq.put(res, freq.get(res) - 1);

        values.get(maxFreq).pop();
        if (values.get(maxFreq).isEmpty()) {
            maxFreq -= 1;
        }

        return res;
    }
}


public class Month_11 {
    /**
     * x <= max(x)
     * y <= max(y)
     * @param towers
     * @param radius
     * @return
     */
    public int[] bestCoordinate(int[][] towers, int radius) {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int[] tower : towers) {
            maxX = Math.max(maxX, tower[0]);
            maxY = Math.max(maxY, tower[1]);
        }

        int[] pos = {0, 0};
        int max = 0;

        for (int i = 0; i < maxX; ++i) {
            for (int j = 0; j < maxY; ++j) {
                int[] cur = {i, j};
                int temp = 0;

                for (int[] tower : towers) {
                    int dis = getDistance(cur, tower);
                    if (dis <= radius * radius) {
                        temp += (int) Math.floor(tower[2] / (1 + Math.sqrt(dis)));
                    }
                }
                
                if (temp > max) {
                    max = temp;
                    pos[0] = cur[0];
                    pos[1] = cur[1];
                }
            }
        }

        return pos;
    }

    public int getDistance(int[] cur, int[] tower) {
        return (cur[0] - tower[0]) * (cur[0] - tower[0]) + (cur[1] - tower[1]) * (cur[1] - tower[1]);
    }


    public int maxRepeating(String sequence, String word) {
        int m = sequence.length();
        int n = word.length();

        int[] next = new int[n];
        for (int i = 1; i < n; i++) {
            int j = next[i - 1];
            
            while (j != 0 && word.charAt(i) != word.charAt(j)) {
                j = next[j - 1];
            }

            if (word.charAt(i) == word.charAt(j)) {
                j += 1;
            }

            next[i] = j;
        }

        int[] dp = new int[m + 1];
        int j = 0;
        for (int i = 1; i <= m; i++) {
            while (j != 0 && sequence.charAt(i - 1) != word.charAt(j)) {
                j = next[j - 1];
            }

            if (sequence.charAt(i - 1) == word.charAt(j)) {
                j += 1;
            }

            if (j == word.length()) {
                dp[i] = dp[i - j] + 1;
                j = next[j - 1];
            }
        }

        return Arrays.stream(dp).max().getAsInt();
    }


    public int reachNumber(int target) {
        target = Math.abs(target);
        int move = 0;
        int pos = 0;
        
        while (pos < target || (pos - target) % 2 != 0) {
            move += 1;
            pos += move;
        }

        return move;
    }


    /**
     * 解析布尔表达式
     * @param expression
     * @return
     */
    public boolean parseBoolExpr(String expression) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : expression.toCharArray()) {
            if (ch == ',') {
                continue;
            }
            else if (ch != ')') {
                stack.push(ch);
            }
            else {
                int countT = 0;
                int countF = 0;
                while (stack.peek() != '(') {
                    char val = stack.pop();
                    if (val == 't') {
                        countT++;
                    }
                    else {
                        countF++;
                    }
                }
                stack.pop();

                char op = stack.pop();
                if (op == '!') {
                    stack.push(countT == 1 ? 'f' : 't');
                }
                else if (op == '&') {
                    stack.push(countF == 0 ? 't' : 'f');
                }
                else {
                    stack.push(countT == 0 ? 'f' : 't');
                }
            }
        }

        return stack.peek() == 't' ? true : false;
    }


    public List<String> ambiguousCoordinates(String s) {
        List<String> res = new ArrayList<>();
        
        // 去除小括号
        s = s.substring(1, s.length() - 1);

        // 枚举逗号的位置
        for (int i = 1; i < s.length(); ++i) {
            List<String> posX = addPoint(s.substring(0, i));
            if (posX.isEmpty()) {
                continue;
            }

            List<String> posY = addPoint(s.substring(i));
            if (posY.isEmpty()) {
                continue;
            }

            for (String x : posX) {
                for (String y : posY) {
                    res.add("(" + x + ", " + y + ")");
                }
            }
        }

        return res;
    }


    public List<String> addPoint(String s) {
        List<String> res = new ArrayList<>();

        if (s.equals("0") || s.charAt(0) != '0') {
            // 不添加小数点
            res.add(s);
        }

        // 枚举小数点的位置
        for (int i = 1; i < s.length(); ++i) {
            // 去除前导0 和 尾部多余0 的情况
            if ((i != 1 && s.charAt(0) == '0') || s.charAt(s.length() - 1) == '0') {
                continue;
            }

            res.add(s.substring(0, i) + "." + s.substring(i));
        }

        return res;
    }


    /**
     * 核心问题：对于每个中心点坐标, 分别计算上下左右四个方向的最大连续 1 的个数
     * @param n
     * @param mines
     * @return
     */
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(dp[i], n);
        }

        // 存储 0 点
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < mines.length; ++i) {
            set.add(mines[i][0] * n + mines[i][1]);
        }

        int res = 0;
        // 计算左右方向最大连续 1
        for (int i = 0; i < n; ++i) {
            int count = 0;
            // 左边连续 1
            for (int j = 0; j < n; ++j) {
                if (set.contains(n * i + j)) {
                    count = 0;
                }
                else {
                    count += 1;
                }

                dp[i][j] = Math.min(dp[i][j], count);
            }

            count = 0;
            // 右边连续 1 
            for (int j = n - 1; j >= 0; --j) {
                if (set.contains(n * i + j)) {
                    count = 0;
                }
                else {
                    count += 1;
                }

                dp[i][j] = Math.min(dp[i][j], count);
            }
        }

        // 计算上下方向最大连续 1
        for (int i = 0; i < n; ++i) {
            int count = 0;
            // 上边连续
            for (int j = 0; j < n; ++j) {
                if (set.contains(j * n + i)) {
                    count = 0;
                }
                else {
                    count += 1;
                }

                dp[j][i] = Math.min(dp[j][i], count);
            }

            count = 0;
            // 下边连续
            for (int j = n - 1; j >= 0; --j) {
                if (set.contains(j * n + i)) {
                    count = 0;
                }
                else {
                    count += 1;
                }

                dp[j][i] = Math.min(dp[j][i], count);
                res = Math.max(res, dp[j][i]);
            }
        }

        return res;
    }


    /**
     * 核心：定义一个 visited 数组, 记录已经访问过的状态
     * 由于是求最短路径问题：我们不可能经过同一个房间两次, 且这两次所有的钥匙状态完全一致
     * @param grid
     * @return
     */
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();

        int keyNum = 0;
        int startX = 0;
        int stattY = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length(); ++j) {
                if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'f') {
                    keyNum += 1;
                }
                else if (grid[i].charAt(j) == '@') {
                    startX = i;
                    stattY = j;
                }
            }
        }

        boolean[][][]  visited = new boolean[m][n][1 << (keyNum - 1)];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int target = 0;
        for (int i = 0; i < keyNum; ++i) {
            target += (1 << i);
        }

        /**
         * int[0]: x
         * int[1]: y
         * int[2]: 钥匙状态
         */
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startX, stattY, 0});

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; ++k) {
                int[] cur = queue.poll();
                if (cur[2] == target) {
                    return step;
                }


                if (visited[cur[0]][cur[1]][cur[2]]) {
                    continue;
                }
                
                for (int i = 0; i < directions.length; ++i) {
                    int nx = cur[0] + directions[i][0];
                    int ny = cur[1] + directions[i][1];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx].charAt(ny) != '#') {
                        char ch = grid[nx].charAt(ny);
                        if (ch >= 'A' && ch <= 'F') {
                            if (((cur[2] >> (ch - 'A')) & 1) == 1) {
                                queue.offer(new int[]{nx, ny, cur[2]});
                            }
                        }

                        else if (ch >= 'a' && ch <= 'f') {
                            queue.offer(new int[]{nx, ny, cur[2] | (1 << (ch - 'a'))});
                        }

                        else {
                            queue.offer(new int[]{nx, ny, cur[2]});
                        }
                    }
                }

                visited[cur[0]][cur[1]][cur[2]] = true;
            }
            
            step += 1;
        }

        return -1;
    }


    // jdk9新功能
    // public static final Set<Character> set = Set.of('a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U');
    public boolean halvesAreAlike(String s) {
        int res = 0;
        String characters = "aeiouAEIOU";
        for (int i = 0; i < s.length(); i++) {
            if (i < s.length() / 2) {
                res += characters.indexOf(s.charAt(i)) >= 0 ? 1 : 0;
            }
            else {
                res -= characters.indexOf(s.charAt(i)) >= 0 ? 1 : 0;
            }
        }

        return res == 0;
    }


    public static final int MOD = 1000000007;
    public int numTilings(int n) {
        /**
         * dp[i][0]: 当前列两块都未覆盖
         * dp[i][1]: 当且列上方覆盖, 下方未覆盖
         * dp[i][2]: 当前列下方覆盖, 上方未覆盖
         * dp[i][3]: 当前列两块都覆盖
         */
        int[][] dp = new int[n + 1][4];
        dp[0][3] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][3];
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD;
            dp[i][3] = ((dp[i - 1][0] + dp[i - 1][1]) % MOD + (dp[i - 1][2] + dp[i - 1][3]) % MOD) % MOD;
        }

        return dp[n][3];
    }


    public int numTilings2(int n) {
        int[][] matrix = {{0, 0, 0, 1}, {1, 0, 1, 0}, {1, 1, 0, 0}, {1, 1, 1, 1}};
        return powMatrix(matrix, n)[3][3];
    }

    // 矩阵快速幂, 矩阵为 (m * m)
    public int[][] powMatrix(int[][] matrix, int n) {
        int m = matrix.length;
        int[][] res = new int[m][m];
        for (int i = 0; i < m; i++) {
            res[i][i] = 1;
        }

        while (n > 0) {
            if ((n & 1) == 1) {
                res = mulMatrix(res, matrix);
            }

            matrix = mulMatrix(matrix, matrix);
            n >>= 1;
        }

        return res;
    }

    // 矩阵乘法
    public int[][] mulMatrix(int[][] matrix1, int[][] matrix2) {
        int m = matrix1.length;
        int n = matrix2.length;
        int s = matrix2[0].length;

        int[][] res = new int[m][s];
        // res[i][j] = 第 i 行 * 第 j 列
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < s; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] = (int) ((res[i][j] + (long)matrix1[i][k] * matrix2[k][j]) % MOD);
                }
            }
        }

        return res;
    }


    public String customSortString(String order, String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < order.length(); i++) {
            char ch = order.charAt(i);
            if (map.containsKey(ch)) {
                for (int j = 0; j < map.get(ch); j++) {
                    sb.append(ch);
                }
            }

            map.remove(order.charAt(i));
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            for (int j = 0; j < entry.getValue(); j++) {
                sb.append(entry.getKey());
            }
        }
        
        return sb.toString();
    }


    /**
     * 折半搜索 + 哈希存储
     * @param nums
     * @return
     */
    public boolean splitArraySameAverage(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return false;
        }
        
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }

        for (int i = 0; i < n; i++) {
            nums[i] = nums[i] * n - sum;
        }

        int m = n >> 1;
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < (1 << m); i++) {
            int tmp = 0;
            for (int j = 0; j < m; j++) {
                if (((i >> j) & 1) == 1) {
                    tmp += nums[j];
                }
            }

            if (tmp == 0) {
                return true;
            }

            set.add(tmp);
        }

        for (int i = 0; i < (1 << (n - m)); i++) {
            int tmp = 0;
            for (int j = 0; j < (n - m); j++) {
                if (((i >> j) & 1) == 1) {
                    tmp += nums[j + m];
                }
            }

            if (tmp == 0 || (i != (1 << (n - m)) - 1) && set.contains(-tmp)) {
                return true;
            }
        }

        return false;
    }


    public boolean isIdealPermutation(int[] nums) {
        int max = nums[0];

        for (int i = 2; i < nums.length; i++) {
            if (nums[i] < max) {
                return false;
            }

            max = Math.max(max, nums[i - 1]);
        }

        return true;
    }

    
    /**
     * 1、多指针，分桶
     * 2、二分查找
     * @param s
     * @param words
     * @return
     */
    public int numMatchingSubseq(String s, String[] words) {
        int ans = 0;
        List<List<Integer>> help = new ArrayList<List<Integer>>();
        for (int i = 0; i < 26; i++) {
            help.add(new ArrayList<>());
        }

        for (int i = 0; i < s.length(); i++) {
            help.get(s.charAt(i) - 'a').add(i);
        }

        for (String word : words) {
            if (word.length() > s.length()) {
                continue;
            }

            int target = -1;
            int i = 0;
            for (; i < word.length(); i++) {
                List<Integer> temp = help.get(word.charAt(i) - 'a');
                if (temp.isEmpty() || temp.get(temp.size() - 1) <= target) {
                    break;
                }

                target = binarySearch(temp, target);
            }

            if (i == word.length()) {
                ans += 1;
            }
        }

        return ans;
    }

    public int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (list.get(mid) > target) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return list.get(left);
    }


    // x^y
    // public static final int MOD = 1000000007;
    public int pow(long x, int y) {
        long res = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                res = (res * x) % MOD;
            }

            y = y >> 1;
            x = (x * x) % MOD;
        }

        return (int) res;
    }

    /**
     * 以 nums[i] 为最大值的子序列个数: 2^i
     * 以 nums[i] 为最小值的子序列个数: 2^(n - 1 - i)
     * 
     * ans = 最大值之和 - 最小值之和
     * @param nums
     * @return
     */
    public int sumSubseqWidth(int[] nums) {
        long res = 0;
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length; i++) {
            res += (pow(2l, i) - pow(2l, nums.length - i - 1)) * nums[i];
        }

        return (int) (res % MOD + MOD) % MOD;
    }


    /**
     * 最大值之和 - 最小值之和
     * 
     * 规定: 若 nums[i] == nums[j] 则比较下标的大小
     * - i < j: nums[i] < nums[j]
     * - i > j: nums[i] > nums[j]
     * 
     * 
     * 以 nums[i] 为最大值的子数组：左边小于等于 nums[i], 右边小于 nums[i]
     * 以 nums[i] 为最小值的子数组：左边大于 nums[i], 右边小于等于 nums[i]
     * 
     * 
     * 核心观点
     * n: 右侧第一个大于nums[i]的下标
     * m: 左侧第一个小于等于nums[i]的下标
     * - 以 nums[i] 为最小值的子数组个数为 (n - i) * (i - m)
     * @param nums
     * @return
     */
    public long subArrayRanges(int[] nums) {
        int n  = nums.length;

        /**
         * minLeft: 左边第一个小于等于 nums[i] 的下标
         * maxLeft: 左边第一个大于 nums[i] 的下标
         * minRight: 右边第一个小于 nums[i] 的下标
         * maxRight: 右边第一个大于等于 nums[i] 的下标
         * 
         * 以 nums[i] 为最小值的子数组个数: 
         * sumMin = nums[i] * (minRight - i) * (i - minLeft)
         * 
         * 以 nums[i] 为最大值的子数组个数: 
         * sumMax = nums[i] * (maxRight - i) * (i - maxLeft)
         */
        int[] minLeft = new int[n];
        int[] maxLeft = new int[n];
        int[] minRight = new int[n];
        int[] maxRight = new int[n];

        Deque<Integer> minStack = new ArrayDeque<>();
        Deque<Integer> maxStack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }

            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);

            while (!maxStack.isEmpty() && nums[maxStack.peek()] <= nums[i]) {
                maxStack.pop();
            }

            maxLeft[i] = maxStack.isEmpty() ? -1 : maxStack.peek();
            maxStack.push(i);
        }

        minStack.clear();
        maxStack.clear();

        for (int i = n - 1; i >= 0; i--) {
            while (!minStack.isEmpty() && nums[minStack.peek()] >= nums[i]) {
                minStack.pop();
            }
            minRight[i] = minStack.isEmpty() ? n : minStack.peek();
            minStack.push(i);


            while (!maxStack.isEmpty() && nums[maxStack.peek()] < nums[i]) {
                maxStack.pop();
            }
            maxRight[i] = maxStack.isEmpty() ? n : maxStack.peek();
            maxStack.push(i);
        }


        long sumMax = 0, sumMin = 0;
        for (int i = 0; i < n; i++) {
            sumMin += (long)(minRight[i] - i) * (i - minLeft[i]) * nums[i];
            sumMax += (long)(maxRight[i] - i) * (i - maxLeft[i]) * nums[i];
        }

        return sumMax - sumMin;
    }


    public int largestAltitude(int[] gain) {
        int ans = 0;
        int temp = 0;

        for (int i = 0; i < gain.length; i++) {
            temp += gain[i];
            ans = Math.max(ans, temp);
        }

        return ans;
    }


    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] dp = new double[query_row + 1][query_row + 2];
        dp[0][1] = poured;

        for (int i = 1; i <= query_row; i++) {
            for (int j = 1; j <= i + 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j - 1] - 1, 0) / 2 + Math.max(dp[i - 1][j] - 1, 0) / 2;
            }
        }

        return dp[query_row][query_glass + 1] >= 1 ? 1 : dp[query_row][query_glass + 1];
    }


    /**
     * dp[i][j]: A剩下i份, B剩下j份的概率值
     * 状态转移：[[4, 0], [3, 1], [2, 2], [1, 3]]
     * @param n
     * @return
     */
    public double soupServings(int n) {
        n = (int)Math.ceil((double) n / 25);

        if (n >= 179) {
            return 1;
        }

        double[][] dp = new double[n + 1][n + 1];
        
        // Init
        dp[0][0] = 0.5;
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 1.0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = 0.25 * (dp[Math.max(i - 4, 0)][j] + dp[Math.max(i - 3, 0)][Math.max(j - 1, 0)]
                            + dp[Math.max(i - 2, 0)][Math.max(j - 2, 0)] + dp[Math.max(i - 1, 0)][Math.max(j - 3, 0)]);
            }
        }

        return dp[n][n];
    }


    public int countBalls(int lowLimit, int highLimit) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] record = new int[]{-1, 0};

        for (int i = lowLimit; i <= highLimit; i++) {
            int temp = digitSum(i);
            map.put(temp, map.getOrDefault(temp, 0) + 1);

            if (map.get(temp) > record[1]) {
                record[0] = temp;
                record[1] = map.get(temp);
            }
        }

        return record[1];
    }

    public int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }


    public int nthMagicalNumber(int n, int a, int b) {
        long left = 0;
        long right = n * Math.min(a, b);
        int c = lcm(a, b);

        while (left <= right) {
            long mid = left + (right - left) / 2; 
            long count = mid / a + mid / b + mid / c;

            if (count >= n) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }

        return (int) ((right + 1) % MOD);
    }


    public int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    public int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }

        return gcd(a % b, a);
    }


    /**
     * 以 nums[i] 为最大值的子数组
     * 找到左边第一个大于它的元素，找到右边第一个大于等于它的元素
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int n = nums.length;
        int[] leftPos = new int[n];
        int[] rightPos = new int[n];

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peek()] <= nums[i]) {
                deque.pop();
            }

            leftPos[i] = deque.isEmpty() ? -1 : deque.peek();
            deque.push(i);
        }

        deque.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!deque.isEmpty() && nums[deque.peek()] < nums[i]) {
                deque.pop();
            }

            rightPos[i] = deque.isEmpty() ? n : deque.peek();
            deque.push(i);
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
               sum += (i - leftPos[i]) * (rightPos[i] - i);
            }
        }

        return sum;
    }

    public int numSubarrayBoundedMax2(int[] nums, int left, int right) {
        int sum = 0;
        int last1 = -1;
        int last2 = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
                last1 = i;
            }

            if (nums[i] > right) {
                last2 = i;
                last1 = -1;
            }

            if (last1 != -1) {
                sum += last1 - last2;
            }
        }

        return sum;
    }


    public int expressiveWords(String s, String[] words) {
        int ans = 0;

        for (String word : words) {
            if (help(s, word)) {
                ans += 1;
            }
        }

        return ans;
    }

    public boolean help(String s, String t) {
        int i = 0;
        int j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) != t.charAt(j)) {
                return false;
            }

            int countS = 0;
            char ch = s.charAt(i);
            while (i < s.length() && s.charAt(i) == ch) {
                countS += 1;
                i += 1;
            }

            int countT = 0;
            ch = t.charAt(j);
            while (j < t.length() && t.charAt(j) == ch) {
                countT += 1;
                j += 1;
            }

            if (countS != countT && (countS < 3 || countS < countT)) {
                return false;
            }
        }

        if (i == s.length() && j == t.length()) {
            return true;
        }

        return false;
    }

    public boolean check(int[] nums) {
        // int[] origin = Arrays.copyOf(nums, nums.length);
        // Arrays.sort(origin);

        int cur = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < cur) {
                count += 1;
            }

            cur = nums[i];
        }

        if (count == 0) {
            return true;
        }
        else if (count >= 2) {
            return false;
        }
        else {
            return nums[nums.length] <= nums[0];
        }
    }


    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // Build Graph
        List<int[]>[] graph = new List[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int src = edge[0];
            int trg = edge[1];
            int nodes = edge[2];

            graph[src].add(new int[]{trg, nodes});
            graph[trg].add(new int[]{src, nodes});
        }

        int ans = 0;
        Map<Integer, Integer> used = new HashMap<Integer, Integer>();
        Set<Integer> visited = new HashSet<Integer>();

        // Dijska
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
            (o1, o2) -> {return o1[0] - o2[0];});
        priorityQueue.offer(new int[]{0, 0});

        while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] <= maxMoves) {
            int[] cur = priorityQueue.poll();
            int step = cur[0];
            int src = cur[1];

            if (!visited.add(src)) {
                continue;
            }

            ans += 1;
            for (int[] next : graph[src]) {
                int trg = next[0];
                int nodes = next[1];

                if (nodes + step + 1 <= maxMoves && !visited.contains(trg)) {
                    priorityQueue.offer(new int[]{nodes + step + 1, trg});
                }

                used.put(encode(src, trg, n), Math.min(nodes, maxMoves - step));
            }
        }

        for (int[] edge : edges) {
            int src = edge[0];
            int trg = edge[1];
            int nodes = edge[2];

            ans += Math.min(nodes, used.getOrDefault(encode(src, trg, n), 0) + used.getOrDefault(encode(trg, src, n), 0));
        }

        return ans;
    }

    public int encode(int u, int v, int n) {
        return u * n + v;
    }


    public double largestSumOfAverages(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        // dp[i][j] 表示 [0, i - 1] 被切分成 j 个子数组的最大平均值和
        double[][] dp = new double[n + 1][k + 1];
        
        // 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][1] = prefix[i] / i;
        }

        // 状态转移
        // 枚举分割数组
        for (int j = 2; j <= k; j++) {
            // 枚举起始位置
            for (int i = j; i <= n; i++) {
                // 枚举上一个分割点的位置
                for (int pos = j - 1; pos < i; pos++) {
                    dp[i][j] = Math.max(dp[i][j], dp[pos][j - 1] + (prefix[i] - prefix[pos]) / (i - pos));
                }
            }
        }

        return dp[n][k];
    }


    public int minOperations(String s) {
        int beginWithOne = 0;
        int beginWithZero = 0;

        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                if (arr[i] == '0') {
                    beginWithOne += 1;
                }
                else {
                    beginWithZero += 1;
                }
            }
            else {
                if (arr[i] == '0') {
                    beginWithZero += 1;
                }
                else {
                    beginWithOne += 1;
                }
            }
        }

        return Math.min(beginWithOne, beginWithZero);
    }




    public static void main(String[] args) {
        Month_11 test = new Month_11();
        System.out.println(test.expressiveWords("heeellooo", new String[]{"hello", "hi"}));
    }
}
