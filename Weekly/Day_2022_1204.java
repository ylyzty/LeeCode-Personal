package Weekly;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;;

public class Day_2022_1204 {
    public boolean isCircularSentence(String sentence) {
        String[] tokens = sentence.split(" ");

        for (int i = 1; i < tokens.length; i++) {
            if (tokens[i].charAt(0) != tokens[i - 1].charAt(tokens[i - 1].length() - 1)) {
                return false;
            }
        }

        int len = tokens[tokens.length - 1].length();
        return tokens[tokens.length - 1].charAt(len - 1) == tokens[0].charAt(0);
    }


    public long dividePlayers(int[] skill) {
        int n = skill.length;
        int sum = Arrays.stream(skill).sum();
        int target = sum / (n / 2);

        Arrays.sort(skill);
        int left = 0;
        int right = n - 1;
        long ans = 0;
        while (left < right) {
            if (skill[left] + skill[right] != target) {
                return -1;
            }

            ans += skill[left] * skill[right];
            left += 1;
            right -= 1;
        }

        return ans;
    }


    public int minScore(int n, int[][] roads) {
        // Build Graph
        List<int[]>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int src = road[0];
            int tgr = road[1];
            int dis = road[2];

            graph[src].add(new int[]{tgr, dis});
            graph[tgr].add(new int[]{src, dis});
        }

        int ans = Integer.MAX_VALUE;
        Deque<Integer> deque = new ArrayDeque<>();
        Set<Integer> set = new HashSet<>();

        deque.offer(1);
        set.add(1);

        while (!deque.isEmpty()) {
            int cur = deque.poll();
            for (int[] next : graph[cur]) {
                if (!set.contains(next[0])) {
                    set.add(next[0]);
                    deque.offer(next[0]);
                }
            }
        }

        for (int[] road : roads) {
            if (set.contains(road[0]) && set.contains(road[1])) {
                ans = Math.min(ans, road[2]);
            }
        }

        return ans;
    }


    private int ans = Integer.MAX_VALUE;
    public int minScore2(int n, int[][] roads) {
        // Build Graph
        List<int[]>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            int src = road[0];
            int tgr = road[1];
            int dis = road[2];

            graph[src].add(new int[]{tgr, dis});
            graph[tgr].add(new int[]{src, dis});
        }

        boolean[] visited = new boolean[n + 1];
        minScoreDfs(graph, 1, visited);

        return ans;
    }


    public void minScoreDfs(List<int[]>[] graph, int cur, boolean[] visited) {
        visited[cur] = true;
        for (int[] next : graph[cur]) {
            ans = Math.min(ans, next[1]);
            if (!visited[next[0]]) {
                minScoreDfs(graph, next[0], visited);
            }
        }
    }


    /**
     * 将节点分成尽可能多的组
     * 
     * 1. 判断二分图
     * 2. 枚举每个连通块的所有起点
     * @param n
     * @param edges
     * @return
     */
    private List<Integer>[] graph;
    private List<Integer> nodes = new ArrayList<>();
    private int[] color;
    public int magnificentSets(int n, int[][] edges) {
        // Build Graph
        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        color = new int[n + 1];
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (color[i] != 0) {
                continue;
            }

            nodes.clear();

            if (!isBipartite(i, 1)) {
                return -1;
            }

            int depth = 0;
            for (int x : nodes) {
                depth = Math.max(getMaxDepth(x, n), depth);
            }

            ans += depth;
        }

        return ans;
    }

    public boolean isBipartite(int x, int c) {
        nodes.add(x);
        color[x] = c;

        for (int next : graph[x]) {
            if (color[next] == c || color[next] == 0 && !isBipartite(next, -c)) {
                return false;
            }
        }

        return true;
    }


    /**
     * 从 start 出发连通块的最大深度
     * @param start
     * @param n
     * @return
     */
    public int getMaxDepth(int start, int n) {
        int step = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];

        deque.add(start);
        visited[start] = true;

        while (!deque.isEmpty()) {
            int size = deque.size();

            for (int i = 0; i < size; i++) {
                int cur = deque.poll();
                for (int next : graph[cur]) {
                    if (!visited[next]) {
                        deque.add(next);
                        visited[next] = true;
                    }
                }
            }

            step += 1;
        }

        return step;
    }
}
