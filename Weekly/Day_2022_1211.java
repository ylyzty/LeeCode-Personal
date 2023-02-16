package Weekly;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Day_2022_1211 {
    public int deleteGreatestValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            Arrays.sort(grid[i]);
        }

        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            int temp = 0;
            for (int j = 0; j < m; j++) {
                temp = Math.max(temp, grid[j][i]);
            }

            ans += temp;
        }

        return ans;
    }

    public int longestSquareStreak(int[] nums) {
        Set<Long> set = new HashSet<>();
        for (int num : nums) {
            set.add((long) num);
        }

        int ans = 0;
        for (long num : set) {
            int count = 0;

            for (; set.contains(num); num *= num) {
                count += 1;
            }

            ans = Math.max(ans, count);
        }

        return ans == 1 ? -1 : ans;
    }


    public int[] maxPoints(int[][] grid, int[] queries) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int[] ans = new int[queries.length];
        
        // id 按照查询值的大小排列
        Integer[] ids = new Integer[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ids[i] = new Integer(i);
        }
        Arrays.sort(ids, (o1, o2) -> {
            return queries[o1] - queries[o2];
        });


        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            return o1[0] - o2[0];
        });

        priorityQueue.offer(new int[]{grid[0][0], 0, 0});
        grid[0][0] = 0;
        int count = 0;

        for (int id : ids) {
            int query = queries[id];
            while (!priorityQueue.isEmpty() && priorityQueue.peek()[0] < query) {
                count += 1;
                int[] cur = priorityQueue.poll();

                for (int k = 0; k < directions.length; k++) {
                    int nx = cur[1] + directions[k][0];
                    int ny = cur[2] + directions[k][1];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 0) {
                        priorityQueue.offer(new int[]{grid[nx][ny], nx, ny});
                        grid[nx][ny] = 0;
                    }
                }
            }

            ans[id] = count;
        }

        return ans;
    }
}


class Allocator {
    private int[] arr;

    public Allocator(int n) {
        this.arr = new int[n];
    }
    
    public int allocate(int size, int mID) {
        int count = 0;
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != 0) {
                count = 0;
            }
            else {
                count += 1;
                if (count == size) {
                    for (int k = 0; k < size; k++) {
                        this.arr[i - k] = mID;
                    }

                    return i - size + 1;
                }
            }
        }

        return -1;
    }
    
    public int free(int mID) {
        int count = 0;
        for (int i = 0; i < this.arr.length; i++) {
            
            if (this.arr[i] == mID) {
                count += 1;
                this.arr[i] = 0;
            }
        }

        return count;
    }
}
