package CodeCatalog.Daily;

import java.security.KeyStore.Entry;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Month_2302 {

    private static final int MOD = (int) (1e9 + 7);
    private int[] rollMax;

    /**
     * LeeCode 1223: 掷骰子模拟
     * 超时回溯写法
     * @param n
     * @param rollMax
     * @return
     */
    public int dieSimulator(int n, int[] rollMax) {
        this.rollMax = rollMax;
        
        long res = 0;
        for (int j = 0; j < rollMax.length; j++) {
            res += dfsDieSimulator(n - 1, j, rollMax[j] - 1);
        }

        return (int) (res % MOD);
    }

    public int dfsDieSimulator(int cur, int last, int left) {
        if (cur == 0) {
            return 1;
        }

        long res = 0;
        for (int i = 0; i < this.rollMax.length; i++) {
            if (i != last) {
                res += dfsDieSimulator(cur - 1, i, this.rollMax[i]);
            }
            else if (left > 0) {
                res += dfsDieSimulator(cur - 1, i, left - 1);
            }
        }

        return (int) (res % MOD);
    }


    /**
     * LeeCode 1223: 掷骰子模拟
     * 
     * 记忆化搜索: cache[i][j][k]
     * i: 骰子次数
     * j: 上一次骰子的点数
     * k: 上一次骰子的点数还能连续出现的次数
     * @param n
     * @param rollMax
     * @return
     */
    private int[][][] cache;

    public int dieSimulatorMemorizedSearch(int n, int[] rollMax) {
        this.rollMax = rollMax;
        this.cache = new int[n][rollMax.length][15];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < rollMax.length; j++) {
                Arrays.fill(cache[i][j], -1);    // -1 表示该状态未到达过
            }
        }

        long res = 0;
        for (int i = 0; i < rollMax.length; i++) {
            res += dfsDieSimulatorMemorizedSearch(n - 1, i, rollMax[i] - 1);
        }

        return (int) (res % MOD);
    }

    public int dfsDieSimulatorMemorizedSearch(int cur, int last, int left) {
        if (cur == 0) {
            return 1;
        }

        if (this.cache[cur][last][left] >= 0) {
            return cache[cur][last][left];
        }

        long res = 0;
        for (int i = 0; i < this.rollMax.length; i++) {
            if (i != last) {
                res += dfsDieSimulatorMemorizedSearch(cur - 1, i, this.rollMax[i] - 1);
            }
            else if (left > 0) {
                res += dfsDieSimulatorMemorizedSearch(cur - 1, i, left - 1);
            }
        }

        cache[cur][last][left] = (int) (res % MOD);
        return cache[cur][last][left];
    }


    /**
     * LeeCode 1223: 掷骰子模拟
     * 
     * 动态规划
     * dp[i][j][k]
     * i: 当前掷骰子的次数
     * j: 上一次骰子的点数
     * k: 上一次骰子的点数还能连续出现的次数
     * @param n
     * @param rollMax
     * @return
     */
    public int dieSimulatorDynamic(int n, int[] rollMax) {
        int[][][] dp = new int[n][rollMax.length][15];

        /**
         * 初始状态
         * 掷一次骰子: 所有状态都为1
         */
        for (int j = 0; j < rollMax.length; j++) {
            Arrays.fill(dp[0][j], 1);
        }

        for (int i = 1; i < n; i++) {
            for (int last = 0; last < rollMax.length; last++) {
                for (int left = 0; left < rollMax[last]; left++) {
                    long res = 0;

                    for (int j = 0; j < rollMax.length; j++) {
                        if (j != last) {
                            res += dp[i - 1][j][rollMax[j] - 1];
                        }
                        else if (left > 0) {
                            res += dp[i - 1][j][left - 1];
                        }
                    }
                    
                    dp[i][last][left] = (int) (res % MOD);
                }
            }
        }

        long res = 0;
        for (int j = 0; j < rollMax.length; j++) {
            res += dp[n - 1][j][rollMax[j] - 1];
        }

        return (int) (res % MOD);
    }

    public int fillCups(int[] amount) {
        Arrays.sort(amount);

        if (amount[0] + amount[1] > amount[2]) {
            int tmp = 0;
            while (amount[0] + amount[2] > amount[1] && amount[0] > 0) {
                amount[0] -= 1;
                amount[2] -= 1;

                tmp += 1;
            }

            return tmp + amount[1];
        }
        else {
            return amount[2];
        }
    }


    /**
     * LeeCode 1138: 字母板上的路径
     * @param target
     * @return
     */
    public String alphabetBoardPath(String target) {
        StringBuilder sb = new StringBuilder();

        int curX = 0;
        int curY = 0;
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            int nextX = (c - 'a') / 5;
            int nextY = (c - 'a') % 5;

            // z -> 其他字符：先向上
            if (nextX < curX) {
                for (int j = 0; j < curX - nextX; j++) {
                    sb.append('U');
                }
            }

            // 其他字符 -> z: 先向左
            if (nextY < curY) {
                for (int j = 0; j < curY - nextY; j++) {
                    sb.append('L');
                }
            }

            if (nextX > curX) {
                for (int j = 0; j < nextX - curX; j++) {
                    sb.append('D');
                }
            }

            if (nextY > curY) {
                for (int j = 0; j < nextY - curY; j++) {
                    sb.append('R');
                }
            }

            curX = nextX;
            curY = nextY;
            sb.append('!');
        }

        return sb.toString();
    }

    public int balancedString(String s) {
        int n = s.length();
        int[] counts = new int[4];
        Map<Character, Integer> map = new HashMap<>();

        map.put('Q', 0);
        map.put('W', 1);
        map.put('E', 2);
        map.put('R', 3);
        
        for (int i = 0; i < s.length(); i++) {
            counts[map.get(s.charAt(i))] += 1;
        }

        // 如果所有字符出现次数均满足要求
        if (check(counts, n / 4)) {
            return 0;
        }

        int left = 0, right = 0;
        int ans = n;
        
        // 枚举待替换子串的左端点
        // counts 维护除滑动窗口外 QWER 的个数
        for (; left < n; left++) {
            while (right < n && !check(counts, n / 4)) {
                counts[map.get(s.charAt(right))] -= 1;
                right += 1;
            }

            // 表示从该左端点开始，均无法满足要求
            if (!check(counts, n / 4)) {
                break;
            }

            ans = Math.min(ans, right - left);
            counts[map.get(s.charAt(left))] += 1;
        }

        return ans;
    }

    public boolean check(int[] counts, int target) {
        if (counts[0] > target || counts[1] > target || counts[2] > target || counts[3] > target) {
            return false;
        }

        return true;
    }


    /**
     * 和大于0的最长子数组
     * 前缀和 + 单调队列
     * @param hours
     * @return
     */
    public int longestWPI(int[] hours) {
        int n = hours.length;
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            hours[i] = hours[i] > 8 ? 1 : -1;
        }

        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(0);

        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + hours[i - 1];
            if (prefix[deque.peek()] > prefix[i]) {
                deque.push(i);
            }
        }

        int ans = 0;
        for (int i = n; i >= 1; i--) {
            while (!deque.isEmpty() && prefix[deque.peek()] < prefix[i]) {
                ans = Math.max(ans,  i - deque.pop());
            }
        }

        return ans;
    }


    public boolean isGoodArray(int[] nums) {
        int divisor = nums[0];

        for (int i = 1; i < nums.length; i++) {
            divisor = gcd(divisor, nums[i]);
            if (divisor == 1) {
                return true;
            }
        }

        return divisor == 1;
    }

    /**
     * 最大公约数
     * 辗转相除法，迭代
     * @param a
     * @param b
     * @return
     */
    public int gcd(int a, int b) {
        while (b > 0) {
            int tmp = b;
            b = a % b;
            a = tmp;
        }

        return a;
    }


    /**
     * 
     * @param nums
     * @return
     */
    public int[] numberOfPairs(int[] nums) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        int pairs = 0;
        for (int num : nums) {
            map.put(num, !map.getOrDefault(num, false));
            if (!map.get(num)) {
                pairs += 1;
            }
        }

        int[] ans = new int[2];
        ans[0] = pairs;
        ans[1] = nums.length - 2 * pairs;
        
        return ans;
    }

}

/**
 * LeeCode 1797: 设计一个验证系统
 * 
 * 哈希表: Map<String, Integer>         55ms
 * 哈希表 and 双链表:                    26ms
 *     - 哈希表存储 <tokenId, Node> 键值对
 *     - 双链表维护键的过期顺序
 */
class Node {
    private String tokenId;
    private int expiredTime;
    private Node prev;
    private Node next;

    public Node(int expiredTime) {
        this(null, expiredTime, null, null);
    }

    public Node(String tokenId, int expiredTime) {
        this(tokenId, expiredTime, null, null);
    }

    public Node(String tokenId, int expiredTime, Node prev, Node next) {
        this.tokenId = tokenId;
        this.expiredTime = expiredTime;
        this.prev = prev;
        this.next = next;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    
}


class AuthenticationManager {
    private Map<String, Node> map;
    private int timeToLive;
    private Node head;
    private Node tail;

    public AuthenticationManager(int timeToLive) {
        this.map = new HashMap<>();
        this.timeToLive = timeToLive;
        this.head = new Node(-1);
        this.tail = new Node(-1);
        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);
    }

    public void generate(String tokenId, int currentTime) {
        Node node = new Node(tokenId, currentTime + timeToLive);
        map.put(tokenId, node);

        Node last = this.tail.getPrev();

        last.setNext(node);
        node.setPrev(last);

        node.setNext(this.tail);
        this.tail.setPrev(node);
    }

    public void renew(String tokenId, int currentTime) {
        if (map.containsKey(tokenId) && map.get(tokenId).getExpiredTime() > currentTime) {
            Node node = map.get(tokenId);

            Node prev = node.getPrev();
            Node next = node.getNext();
            prev.setNext(next);
            next.setPrev(prev);

            node.setExpiredTime(currentTime + timeToLive);

            Node last = this.tail.getPrev();
            last.setNext(node);
            node.setPrev(last);
            node.setNext(this.tail);
            this.tail.setPrev(node);
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        
        while (this.head.getNext().getExpiredTime() > 0 && this.head.getNext().getExpiredTime() <= currentTime) {
            Node node = head.getNext();
            map.remove(node.getTokenId());
            head.setNext(node.getNext());
            node.getNext().setPrev(head);
        }

        return map.size();
    }

}
