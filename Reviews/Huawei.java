package Reviews;

import java.util.*;

/**
 * 华为22秋招机试题
 */
public class Huawei {
    public static void main(String[] args) {
        Huawei test = new Huawei();
    }


    /**
     * 旅行商问题
     *
     * @description 回溯法, 时间复杂度 O(N!), 超时无法通过所有用例
     *
     * @param start 起始位置与终点位置
     * @param graph 图结构, 值为权重
     *
     * @return
     */
    public int ans = Integer.MAX_VALUE;
    public int test_03(int start, int[][] graph) {
        int n = graph.length;


        return 0;
    }

    /**
     *
     * @param graph
     * @param visited
     * @param sum
     * @param index 当前访问城市下标
     * @param cnt 访问城市总数
     * @param n
     */
    public boolean[] visited;
    public void dfs(int[][] graph, boolean[] visited, int sum, int index, int cnt, int n) {
        if (sum >= ans) {
            return;
        }

        // 遍历完成
        if (cnt == n) {
            int res = sum + graph[index][0];
            ans = Math.min(ans, res);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            if (graph[index][i] != 0) {
                visited[i] = true;
                dfs(graph, visited, sum + graph[index][i], i, cnt + 1, n);
                visited[i] = false;
            }
        }
    }


    /**
     * 旅行商问题
     *
     * @description 动态规划, 状态压缩
     * @param start
     * @param graph
     * @return
     */
    public int TSP(int start, int[][] graph) {
        int n = graph.length;

        // 城市0为起点
        // 所以接下来 (n - 1) 城市的所有子集共有 2^(n - 1) 个
        int[][] dp = new int[n][1 << (n - 1)];

        // 初始化, 最后一步回到起点
        for (int i = 0; i < n; i++) {
            dp[i][0] = graph[i][0];
        }

        for (int j = 1; j < 1 << (n - 1); j++) {
            for (int i = 0; i < n; i++) {
                // 初始化 dp[i][j] = inf
                dp[i][j] = Integer.MAX_VALUE >> 1;

                // 当前城市已在集合中, 则跳过
                if (((j >> (i - 1)) & 1) == 1) {
                    continue;
                }

                for (int k = 1; k < n; k++) {
                    if (((j >> (k - 1)) & 1) == 0) {
                        continue;
                    }

                    dp[i][j] = Math.min(dp[i][j], dp[k][j ^ (1 << (k - 1))] + graph[k][i]);
                }
            }
        }

        return dp[0][(1 << (n - 1)) - 1];
    }


    /**
     * 根据动态规划的结果倒序找路径
     * @param dp
     * @param graph
     */
    public void path(int[][] dp, int[][] graph) {
        int n = graph.length;
        int S = 1 << (n - 1) - 1;
        int min = Integer.MAX_VALUE;

        boolean[] visited = new boolean[n];
        List<Integer> path = new ArrayList<>();

        path.add(0);
        int pre = 0;
        int tmp = 0;
        while (!isAllVisited(visited)) {
            for (int i = 1; i < n; i++) {
                if (!visited[i] && (S & (1 << (i - 1))) != 0) {
                    if (min > graph[i][pre] + dp[i][S ^ (1 << (i - 1))]) {
                        min = graph[i][pre] + dp[i][S ^ (1 << (i - 1))];
                        tmp = i;
                    }
                }
            }

            pre = tmp;
            path.add(pre);
            visited[pre] = true;
            S = S ^ (1 << (pre - 1));
            min = Integer.MAX_VALUE;
        }
    }


    /**
     * 判断是否所有城市都访问过一遍
     * @param visited
     * @return
     */
    public boolean isAllVisited(boolean[] visited) {
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }

        return true;
    }
}

/**
 * 技巧: Integer[] 与 int[] 相互转换
 *
 * Integer[] arr1 = Arrays.stream(arr).boxed.toArray(Integer::new);
 * int[] arr2 = Arrays.stream(arr1).mapToInt(Integer::valueOf).toArray();
 *
 * @param arr
 * @return
 */
