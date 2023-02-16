package GraphTheory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphBasics {
    
    private int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * 广度优先搜索
     * @param image
     * @param sr
     * @param sc
     * @param color
     * @return
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int m = image.length;
        int n = image[0].length;

        int oldColor = image[sr][sc];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{sr, sc});

        boolean[][] visited = new boolean[m][n];
        image[sr][sc] = color;
        visited[sr][sc] = true;
        while (!deque.isEmpty()) {
            int[] cur = deque.poll();
            for (int i = 0; i < directions.length; i++) {
                int nextRow = cur[0] + directions[i][0];
                int nextCol = cur[1] + directions[i][1];

                if (isValid(nextRow, nextCol, m, n) && !visited[nextRow][nextCol] && image[nextRow][nextCol] == oldColor) {
                    visited[nextRow][nextCol] = true;
                    image[nextRow][nextCol] = color;
                    deque.offer(new int[]{nextRow, nextCol});
                }
            }
        }

        return image;
    }

    public boolean isValid(int row, int col, int m, int n) {
        if (row >= 0 && row < m && col >= 0 && col < n) {
            return true;
        }

        return false;
    }


    /**
     * 岛屿的数量
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    ans += 1;
                    dfs(grid, i, j);
                }
            }
        }

        return ans;
    }

    public void dfs(char[][] grid, int row, int col) {
        if (grid[row][col] == '1') {
            grid[row][col] = '0';
            for (int i = 0; i < directions.length; i++) {
                int nextRow = row + directions[i][0];
                int nextCol = col + directions[i][1];

                if (nextRow >= 0 && nextRow < grid.length && nextCol >= 0 && nextCol < grid[0].length) {
                    dfs(grid, nextRow, nextCol);
                }
            }
        }
    }


    /**
     * 封闭岛屿的数目
     * 思路：即连通的0不能处于四条最外围边
     * 
     * 空间优化, 将访问过的节点修改为-1
     * @param grid
     * @return
     */
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    boolean flag = true;
                    
                    Deque<int[]> deque = new ArrayDeque<>();
                    deque.offer(new int[]{i, j});

                    while (!deque.isEmpty()) {
                        int[] cur = deque.poll();
                        if (cur[0] == 0 || cur[0] == m - 1 || cur[1] == 0 || cur[1] == n - 1) {
                            flag = false;
                        }

                        grid[cur[0]][cur[1]] = -1;
                        for (int k = 0; k < directions.length; k++) {
                            int nextX = i + directions[i][0];
                            int nextY = i + directions[i][1];

                            if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && grid[nextX][nextY] == 0) {
                                deque.offer(new int[]{nextX, nextY});
                            }
                        }
                    }
                    
                    ans += flag ? 1 : 0;
                }
            }
        }

        return ans;
    }


    public int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    ans = Math.max(ans, dfsMaxArea(grid, i, j));
                }
            }
        }

        return ans;
    }

    public int dfsMaxArea(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == 0) {
            return 0;
        }

        grid[x][y] = 0;
        return 1 + dfsMaxArea(grid, x - 1, y) + dfsMaxArea(grid, x + 1, y) + dfsMaxArea(grid, x, y - 1) + dfsMaxArea(grid, x, y + 1);
    }


    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            if (grid[i][0] == 1) {
                dfsNumEnclaves(grid, i, 0);
            }

            if (grid[i][n - 1] == 1) {
                dfsNumEnclaves(grid, i, n - 1);
            }
        }

        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) {
                dfsNumEnclaves(grid, 0, j);
            }

            if (grid[m - 1][j] == 1) {
                dfsNumEnclaves(grid, m - 1, j);
            }
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans += grid[i][j] == 1 ? 1 : 0;
            }
        }

        return ans;
    }

    public void dfsNumEnclaves(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == 0) {
            return;
        }

        grid[x][y] = 0;
        dfsNumEnclaves(grid, x - 1, y);
        dfsNumEnclaves(grid, x + 1, y);
        dfsNumEnclaves(grid, x, y - 1);
        dfsNumEnclaves(grid, x, y + 1);
    }


    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length;
        int n = grid1[0].length;

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1 && dfsCountSubIslands(grid1, grid2, i, j)) {
                    ans += 1;
                }
            }
        }

        return ans;
    }

    public boolean dfsCountSubIslands(int[][] grid1, int[][] grid2, int x, int y) {
        if (x < 0 || x >= grid1.length || y < 0 || y >= grid1[0].length || grid2[x][y] == 0) {
            return true;
        }

        grid2[x][y] = 0;
        boolean temp1 = dfsCountSubIslands(grid1, grid2, x + 1, y);
        boolean temp2 = dfsCountSubIslands(grid1, grid2, x - 1, y);
        boolean temp3 = dfsCountSubIslands(grid1, grid2, x, y + 1);
        boolean temp4 = dfsCountSubIslands(grid1, grid2, x, y - 1);


        if (grid1[x][y] != 1) {
            return false;
        }

        return temp1 && temp2 && temp3 && temp4;
    }
    
    
    /**
     * 多源最短路径
     * @param grid
     * @return
     */
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        int INF = 1000000;
        int[][] dis = new int[n][n];
        Deque<int[]> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dis[i][j] = INF;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dis[i][j] = 0;
                    deque.offer(new int[]{i, j});
                }
            }
        }

        while (!deque.isEmpty()) {
            int[] cur = deque.poll();
            for (int i = 0; i < directions.length; i++) {
                int nx = cur[0] + directions[i][0];
                int ny = cur[1] + directions[i][1];

                if (nx >= 0 && nx < n && ny >= 0 && ny < n && dis[nx][ny] > dis[cur[0]][cur[1]] + 1) {
                    dis[nx][ny] = dis[cur[0]][cur[1]] + 1;
                    deque.offer(new int[]{nx, ny});
                }
            }
        }

        int ans = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    ans = Math.max(ans, dis[i][j]);
                }
            }
        }

        return ans == INF ? -1 : ans;
    }


    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            dfsPacificAtlantic(heights, pacific, i, 0);
            dfsPacificAtlantic(heights, atlantic, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            dfsPacificAtlantic(heights, pacific, 0, j);
            dfsPacificAtlantic(heights, atlantic, m - 1, j);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    tmp.add(j);

                    ans.add(tmp);
                }
            }
        }

        return ans;
    }

    public void dfsPacificAtlantic(int[][] heights, boolean[][] visited, int x, int y) {
        if (visited[x][y]) {
            return;
        }

        visited[x][y] = true;
        for (int i = 0; i < directions.length; i++) {
            int nx = x + directions[i][0];
            int ny = y + directions[i][1];

            if (nx >= 0 && nx < heights.length && ny >= 0 && ny < heights[0].length && heights[nx][ny] >= heights[x][y]) {
                dfsPacificAtlantic(heights, visited, nx, ny);
            }
        }
    }


    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] ans = new int[m][n];

        Deque<int[]> deque = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    deque.offer(new int[]{i, j});
                }
            }
        }

        int step = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            step += 1;
            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();

                for (int k = 0; k < directions.length; k++) {
                    int nx = cur[0] + directions[k][0];
                    int ny = cur[1] + directions[k][1];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && mat[nx][ny] == 1) {
                        ans[nx][ny] = step;
                        mat[nx][ny] = 0;
                        deque.offer(new int[]{nx, ny});
                    }
                }
            }
        }

        return ans;
    }


    /**
     * 单源最短路径
     * @param grid
     * @return
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;

        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) {
            return -1;
        }

        if (n == 1) {
            return 1;
        }


        int[][] directions = {{-1, -1}, {-1 , 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int ans = -1;

        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{0, 0});
        grid[0][0] = 1;
        int step = 1;

        while (!deque.isEmpty()) {
            int size = deque.size();

            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();

                for (int k = 0; k < directions.length; k++) {
                    int nx = cur[0] + directions[k][0];
                    int ny = cur[1] + directions[k][1];

                    if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                        if (nx == n - 1 && ny == n - 1) {
                            return step + 1;
                        }

                        grid[nx][ny] = 1;
                        deque.offer(new int[]{nx, ny});
                    }
                }
            }

            step += 1;
        }

        return ans;
    }


    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];

        for (int i = 0; i < n; i++) {
            if (color[i] != 0) {
                continue;
            }

            if (!dfsIsBipartite(i, 1, color, graph)) {
                return false;
            }
        }

        return true;
    }

    public boolean dfsIsBipartite(int start, int c, int[] color, int[][] graph) {
        color[start] = c;
        for (int next : graph[start]) {
            if (color[next] == c || color[next] == 0 && !dfsIsBipartite(next, -c, color, graph)) {
                return false;
            }
        }

        return true;
    }


    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length;
        int n = maze[0].length;
        int x = entrance[0];
        int y = entrance[1];
        maze[x][y] = '+';

        int step = 0;
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{x, y});

        while (!deque.isEmpty()) {
            int size = deque.size();

            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();

                for (int k = 0; k < directions.length; k++) {
                    int nx = cur[0] + directions[k][0];
                    int ny = cur[1] + directions[k][1];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && maze[nx][ny] == '.') {
                        if (nx == 0 || nx == m - 1 || ny == 0 || ny == n - 1) {
                            return step + 1;
                        }

                        deque.offer(new int[]{nx, ny});
                        maze[nx][ny] = '+';
                    }
                }
            }

            step += 1;
        }

        return -1;
    }


    public int shortestBridge(int[][] grid) {
        int n = grid.length;

        // 第一次DFS遍历, 找到一个岛
        Deque<int[]> deque = new ArrayDeque<>();
        boolean flag = false;
        for (int i = 0; i < n && !flag; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dfsShortestBridge(grid, i, j, deque);
                    flag = true;
                    break;
                }
            }
        }

        // 第二次BFS遍历, 求最小距离
        int step = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();

                for (int k = 0; k < directions.length; k++) {
                    int nx = cur[0] + directions[k][0];
                    int ny = cur[1] + directions[k][1];

                    if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                        if (grid[nx][ny] == 1) {
                            return step;
                        }

                        if (grid[nx][ny] == 0) {
                            deque.offer(new int[]{nx, ny});
                            grid[nx][ny] = 2;
                        }
                    }
                }
            }

            step += 1;
        }

        return 0;
    }

    public void dfsShortestBridge(int[][] grid, int x, int y, Deque<int[]> deque) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid.length || grid[x][y] != 1) {
            return;
        }

        if (grid[x][y] == 1) {
            grid[x][y] = 2;
            deque.offer(new int[]{x, y});
            dfsShortestBridge(grid, x - 1, y, deque);
            dfsShortestBridge(grid, x + 1, y, deque);
            dfsShortestBridge(grid, x, y - 1, deque);
            dfsShortestBridge(grid, x, y + 1, deque);
        }
    }


    private List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> allPathSourceTarget(int[][] graph) {
        List<Integer> temp = new ArrayList<>();
        temp.add(0);

        dfsAllPathSourceTarget(graph, 0, temp);
        return ans;
    }

    public void dfsAllPathSourceTarget(int[][] graph, int cur, List<Integer> temp) {
        if (cur == graph.length - 1) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int next : graph[cur]) {
            temp.add(next);
            dfsAllPathSourceTarget(graph, next, temp);
            temp.remove(temp.size() - 1);
        } 
    }


    private Set<Integer> set = new HashSet<>();
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        dfsCanVisitAllRooms(rooms, 0);
        return set.size() == rooms.size();
    }

    public void dfsCanVisitAllRooms(List<List<Integer>> rooms, int cur) {
        set.add(cur);
        for (int next : rooms.get(cur)) {
            if (!set.contains(next)) {
                dfsCanVisitAllRooms(rooms, next);
            }
        }
    }


    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                ans += 1;
                dfsFindCircleNum(isConnected, i, visited);
            }
        }
        
        return ans;
    }

    public void dfsFindCircleNum(int[][] isConnected, int cur, boolean[] visited) {
        visited[cur] = true;
        for (int i = 0; i < isConnected.length; i++) {
            if (isConnected[cur][i] == 1 && !visited[i]) {
                dfsFindCircleNum(isConnected, i, visited);
            }
        }
    }


    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }

        // Build Graph
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] connection : connections) {
            int src = connection[0];
            int tgr = connection[1];

            graph[src].add(tgr);
            graph[tgr].add(src);
        }
        boolean[] visited = new boolean[n];

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                ans += 1;
                dfsMakeConnected(graph, i, visited);
            }
        }

        return ans;
    }

    public void dfsMakeConnected(List<Integer>[] graph, int cur, boolean[] visited) {
        visited[cur] = true;
        for (int next : graph[cur]) {
            if (!visited[next]) {
                dfsMakeConnected(graph, next, visited);
            }
        }
    }


    public boolean canReach(int[] arr, int start) {
        if (arr[start] == 0) {
            return true;
        }


        int n = arr.length;
        boolean[] visited = new boolean[n];

        Deque<Integer> deque = new ArrayDeque<>();
        deque.offer(start);
        visited[start] = true;

        while (!deque.isEmpty()) {
            int cur = deque.poll();

            if (cur - arr[cur] >= 0 && !visited[cur - arr[cur]]) {
                if (arr[cur - arr[cur]] == 0) {
                    return true;
                }
                
                visited[cur - arr[cur]] = true;
                deque.offer(cur - arr[cur]);
            }

            if (cur + arr[cur] < n && !visited[cur + arr[cur]]) {
                if (arr[cur + arr[cur]] == 0) {
                    return true;
                }

                visited[cur + arr[cur]] = true;
                deque.offer(cur + arr[cur]);
            }
        }

        return false;
    }


    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        if (x == 0) {
            return 0;
        }

        int num = Arrays.stream(forbidden).max().getAsInt();
        int bound = Math.max(num + a + b, x + b);
        boolean[] visited = new boolean[bound + 1];
        for (int pos : forbidden) {
            visited[pos] = true;
        }

        /**
         * 0: 右跳
         * 1: 左跳
         */
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{0, 0});
        visited[0] = true;

        int step = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                int[] cur = deque.poll();
                int pos = cur[0];
                int dir = cur[1];

                if (dir == 1) {
                    if (pos + a <= bound && !visited[pos + a]) {
                        if (pos + a == x) {
                            return step + 1;
                        }

                        deque.offer(new int[]{pos + a, 0});
                        visited[pos + a] = true;
                    }
                }
                else {
                    if (pos - b >= 0 && !visited[pos - b]) {
                        if (pos - b == x) {
                            return step + 1;
                        }

                        deque.offer(new int[]{pos - b, 1});
                        visited[pos - b] = true;
                    }

                    if (pos + a <= bound && !visited[pos + a]) {
                        if (pos + a == x) {
                            return step + 1;
                        }

                        deque.offer(new int[]{pos + a, 0});
                        visited[pos + a] = true;
                    }
                }
            }

            step += 1;
        }

        return -1;
    }
}


/**
 * 并查集模板
 * 
 * parent: 记录当前节点所在组的根节点
 * size: 记录当前节点所在组的个数
 * count: 当前分组数目
 * n: 节点总数
 */
class UnionFind {
    int[] parent;
    int[] size;
    int n;
    int count;

    public UnionFind(int n) {
        this.n = n;
        this.count = n;
        this.parent = new int[n];
        this.size = new int[n];

        Arrays.fill(size, 1);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * 路径压缩
     * 
     * 在向上查找当前节点所在组的根节点时，通过递归一层一层调用并赋值实现
     * 下一次查找组内任意一个节点时，可以一步定位到根节点，缩短查找路径
     * @param x
     * @return
     */
    public int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }


    /**
     * 按秩合并
     * 
     * 在合并的时候，将节点数量少的分支向节点数量多的分支进行合并
     * @param x
     * @param y
     * @return
     */
    public boolean merge(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return false;
        }

        if (size[x] < size[y]) {
            int temp = x;
            x = y;
            y = temp;
        }

        count -= 1;
        parent[y] = x;
        size[x] += size[y];
        
        return true;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
