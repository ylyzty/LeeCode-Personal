package HardLevelQues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 困难题合集
 */
public class HardQues {
    
    /**
     * n个字符串的最长公共连续子串
     * 
     * 滚动哈希 + 二分
     * 
     * 
     * 滚动哈希介绍: 将字符串看成某个进制下的整数, 并将其对应的十进制作为哈希值
     * 可能存在的问题: 字符串的哈希值会很大, 可能会超过64位整数类型
     * 解决方法: 对计算出的哈希值取mod, 但是会带来哈希冲突
     * 进制的选择: 大于所有的元素即可 k > max(a)
     * @param n
     * @param paths
     * @return
     */
    public static final int MOD1 = (int) (1e9 + 7);
    public static final int MOD2 = (int) (1e9 + 9);
    public int longestCommonSubpath(int n, int[][] paths) {
        Random random = new Random();
        int base1 = (int) (1e6) + random.nextInt((int) 9e6 + 1);
        int base2 = (int) (1e6) + random.nextInt((int) 9e6 + 1);

        // 计算二分的上下限
        int left = 0;
        int right = Integer.MAX_VALUE;
        int ans = 0;
        for (int i = 0; i < paths.length; i++) {
           right = Math.min(right, paths[i].length);
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // 预处理求解 k^len
            long mult1 = 1;
            long mult2 = 1;

            for (int i = 0; i < mid; i++) {
                mult1 = mult1 * base1 % MOD1;
                mult2 = mult2 * base2 % MOD2;
            }

            Set<int[]> s = new HashSet<>();
            boolean check = true;

            for (int i = 0; i < paths.length; i++) {
                long hash1 = 0;
                long hash2 = 0;

                // 计算 [0, mid) 的哈希值
                for (int j = 0; j < mid; j++) {
                    hash1 = (hash1 * base1 + paths[i][j]) % MOD1;
                    hash2 = (hash2 * base2 + paths[i][j]) % MOD2;
                }

                Set<int[]> t = new HashSet<>();
                if (i == 0 || s.contains(new int[]{(int) hash1, (int) hash2})) {
                    t.add(new int[]{(int) hash1, (int) hash2});
                }
                
                // 计算后续哈希值
                for (int j = mid; j < paths[i].length; j++) {
                    // 作差可能出现负数, 所有添加额外操作 
                    hash1 = ((hash1 * base1 % MOD1 - paths[i][j - mid] * mult1 % MOD1 + paths[i][j]) % MOD1 + MOD1) % MOD1;
                    hash2 = ((hash2 * base2 % MOD2 - paths[i][j - mid] * mult2 % MOD2 + paths[i][j]) % MOD2 + MOD2) % MOD2;
                    if (i == 0 || s.contains(new int[]{(int) hash1, (int) hash2})) {
                        t.add(new int[]{(int) hash1, (int) hash2});
                    }
                }

                if (t.isEmpty()) {
                    check = false;
                    break;
                }

                s = t;
            }

            if (check) {
                ans = mid;
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }


        return ans;
    }


    /**
     * 最长回文子串
     * 
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        int n = charArray.length;

        int maxLen = 0;
        String ans = null;
        for (int i = 1; i < 2 * n; i++) {
            int left = (i - 1) / 2;
            int right = i / 2;

            while (left >= 0 && right < n) {
                if (charArray[left] == charArray[right]) {
                    left -= 1;
                    right += 1;
                }
                else {
                    break;
                }
            }

            if (right - left - 1 > maxLen) {
                maxLen = right - left - 1;
                ans = s.substring(left + 1, right);
            }
        }

        return ans;
    }


    /**
     * 换根 DP
     * 
     * int[] 长度为2, 转换成 long 类型的数, 通过 哈希 判断是否存在
     * @param edges
     * @param guesses
     * @param k
     * @return
     */
    private List<Integer>[] graph;
    private Set<Long> set = new HashSet<>();
    private int count = 0;
    private int k;
    private int ans = 0;
    public int rootCount(int[][] edges, int[][] guesses, int k) {
        this.k = k;

        int n = edges.length;
        graph = new List[n + 1];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }


        // Build Graph
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        // 哈希, 两个int类型压缩成一个long类型数
        // 可以这么压缩的原因是都是正数, 高位为0
        for (int[] guess : guesses) {
            set.add((long) guess[0] << 32 | guess[1]);
        }

        dfsWithRootZero(0, -1);
        reroot(0, -1, count);

        return ans;
    }

    public void dfsWithRootZero(int cur, int parent) {
        for (int next : graph[cur]) {
            if (next != parent) {
                if (set.contains((long) cur << 32 | next)) {
                    this.count += 1;
                }

                dfsWithRootZero(next, cur);
            }
        }
    }

    public void reroot(int cur, int parent, int cnt) {
        if (cnt >= k) {
            ans += 1;
        }

        for (int next : graph[cur]) {
            if (next != parent) {
                int c = cnt;
                if (set.contains((long) cur << 32 | next)) {
                    c -= 1;
                }

                if (set.contains((long) next << 32 | cur)) {
                    c += 1;
                }

                reroot(next, cur, c);
            }
        }
    }


    /**
     * 并查集
     * 
     * union 方法
     * find 方法
     * @param isConnected
     * @return
     */
    private int[] parent;
    private int provices;
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        this.parent = new int[n];
        this.provices = n;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < isConnected[i].length; j++) {
                if (isConnected[i][j] == 1) {
                    union(i, j);
                }
            }
        }

        return provices;
    }

    // 路径压缩
    public int find(int x) {
        return this.parent[x] == x ? x : (parent[x] = find(parent[x]));
    }

    public boolean union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x == y) {
            return false;
        }

        parent[x] = y;
        provices -= 1;
        return true;
    }


    /**
     * 并查集
     * 
     * @param strs
     * @return
     */
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x = find(i);
                int y = find(j);
                if (x == y) {
                    continue;
                }
                
                if (check(strs[i], strs[j])) {
                    parent[x] = y;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {
                ans += 1;
            }
        }

        return ans;
    }

    // unoin函数
    public boolean check(String a, String b) {
        int diff = 0;
        int n = a.length();

        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff += 1;
                if (diff > 2) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * 差分数组
     * 区间最大重叠数
     * 
     * 记录变化量
     * @param flowers
     * @param persons
     * @return
     */
    public int[] fullBloomFlowers(int[][] flowers, int[] persons) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] flower : flowers) {
            map.put(flower[0], map.getOrDefault(flower[0], 0) + 1);
            map.put(flower[1] + 1, map.getOrDefault(flower[1] + 1, 0) - 1);
        }

        int[] times = map.keySet().stream().mapToInt(Integer::intValue).sorted().toArray();

        int n = persons.length;
        int[] ans = new int[n];
        //Integer[] ids = IntStream.range(0, persons.length).boxed().toArray(Integer[]::new);
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (o1, o2) -> {
            return parent[o1] - parent[o2];
        });

        int i = 0;
        int sum = 0;
        for (int id : ids) {
            while (i < times.length && times[i] <= persons[id]) {
                sum += map.get(times[i++]);
            }

            ans[id] = sum;
        }

        return ans;
    }



    public static void main(String[] args) {
        HardQues hardQues = new HardQues();
        int[][] paths = {{0,1,2,3,4}, {2,3,4}, {4,0,1,2,3}};
        System.out.println(hardQues.longestCommonSubpath(5, paths));
    }

}


/**
 * 数据流的中位数
 */
class MedianFinder {
    PriorityQueue<Integer> queueMin;    // 大顶堆
    PriorityQueue<Integer> queueMax;    // 小顶堆

    public MedianFinder() {
        queueMin = new PriorityQueue<>((o1, o2) -> {return o2 - o1;});
        queueMax = new PriorityQueue<>((o1, o2) -> {return o1 - o2;});
    }
    
    public void addNum(int num) {
        if (queueMin.isEmpty() || num <= queueMin.peek()) {
            queueMin.offer(num);

            if (queueMax.size() + 1 < queueMin.size()) {
                queueMax.offer(queueMin.poll());
            }
        }
        else {
            queueMax.offer(num);
            if (queueMax.size() > queueMin.size()) {
                queueMin.offer(queueMax.poll());
            }
        }
    }
    
    public double findMedian() {
        if (queueMin.size() > queueMax.size()) {
            return queueMin.peek();
        } 
        else {
            return (queueMax.peek() + queueMin.peek()) / 2.0;
        }
    }
}