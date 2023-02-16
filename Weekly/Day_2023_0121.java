package Weekly;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day_2023_0121 {

    public int getCommon(int[] nums1, int[] nums2) {
        int a = 0, b = 0;

        while (a < nums1.length && b < nums2.length) {
            if (nums1[a] < nums2[b]) {
                a += 1;
            }
            else if (nums1[a] > nums2[b]) {
                b += 1;
            }
            else {
                return nums1[a];
            }
        }

        return -1;
    }


    public long minOperations(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;

        long addTimes = 0;
        long subTimes = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(nums1[i] - nums2[i]) % k != 0) {
                return -1;
            }

            int tmp = nums1[i] - nums2[i];
            if (tmp < 0) {
                addTimes += (-tmp) / k;
            }
            else {
                subTimes += tmp / k;
            }
        }

        if (addTimes != subTimes) {
            return -1;
        }

        return addTimes;
    }


    public long ans = 0;
    public long sum = 0;
    public List<Integer> list = new ArrayList<>();
    
    public long maxScore(int[] nums1, int[] nums2, int k) {
        maxScoreTrack(k, 0, 0, nums1, nums2);
        return ans;
    }

    public void maxScoreTrack(int k, int cur, int count, int[] nums1, int[] nums2) {
        if (count == k) {
            List<Integer> tmp = new ArrayList<>(list);
            tmp.sort((Integer o1, Integer o2) -> {
                return o1 - o2;
            });

            ans = Math.max(ans, sum * tmp.get(0));
            return;
        }

        // 选择当前下标
        sum += nums1[cur];
        list.add(nums2[cur]);
        maxScoreTrack(k, cur + 1, count + 1, nums1, nums2);
        sum -= nums1[cur];
        list.remove(list.size() - 1);
        maxScoreTrack(k,cur + 1, count, nums1, nums2);
    }


    // public boolean isReachable(int targetX, int targetY) {
    //     Set<int[]> set = new HashSet<>();
    //     set.add(new int[]{1, 1});

    //     Queue<int[]> queue = new ArrayDeque<>();
    //     queue.offer(new int[]{1, 1});

    //     while (!queue.isEmpty()) {
    //         int[] cur = queue.poll();

    //     }
    // }
}