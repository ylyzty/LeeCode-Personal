package Weekly;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Day_2023_0226 {

    public int[] leftRigthDifference(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];

        for (int i = 0; i < n - 1; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        for (int i = n - 1; i > 0; i--) {
            suffix[i - 1] = suffix[i] + nums[i];
        }

        for (int i = 0; i < n; i++) {
            nums[i] = Math.abs(prefix[i] - suffix[i]);
        }

        return nums;
    }


    public int[] divisibilityArray(String word, int m) {
        int n = word.length();
        int num = 0;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            num = num * 10 % m + (word.charAt(i) - '0');
            if (num % m == 0) {
                ans[i] = 1;
            }
            else {
                ans[i] = 0;
            }
        }

        return ans;
    }


    /**
     * 二分法
     * @param nums
     * @return
     */
    public int maxNumOfMarkedIndices(int[] nums) {
        Arrays.sort(nums);

        int left = 0;
        int right = nums.length / 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid)) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }

        return right * 2;
    }

    public boolean check(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            if (2 * nums[i] > nums[nums.length - k + i]) {
                return false;
            }
        }

        return true;
    }


    public int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * 堆 + BFS
     * @param grid
     * @return
     */
    public int minimumTime(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (grid[0][1] > 1 && grid[1][0] > 1) {
            return -1;
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            return o1[0] - o2[0];
        });
        queue.offer(new int[]{0, 0, 0});
        boolean[][] visited = new boolean[m][n];

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int time = cur[0];
            int curX = cur[1];
            int curY = cur[2];
            if (curX == m - 1 && curY == n - 1) {
                return time;
            }

            if (visited[curX][curY]) {
                continue;
            }

            visited[curX][curY] = true;
            for (int i = 0; i < directions.length; i++) {
                int nextX = curX + directions[i][0];
                int nextY = curY + directions[i][1];

                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n) {
                    if (grid[nextX][nextY] <= time + 1) {
                        queue.offer(new int[]{time + 1, nextX, nextY});
                    }
                    else if ((grid[nextX][nextY] - time) % 2 == 1) {
                        queue.offer(new int[]{grid[nextX][nextY], nextX, nextY});
                    }
                    else {
                        queue.offer(new int[]{grid[nextX][nextY] + 1, nextX, nextY});
                    }
                }
            }
        }

        return -1;
    }

    
    public static void main(String[] args) {
        int[][] grid = {{0, 1, 3, 2}, {5, 1, 2, 5}, {4, 3, 8, 6}};
        Day_2023_0226 test = new Day_2023_0226();
        System.out.println(test.minimumTime(grid));
    }
}
