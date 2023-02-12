package CodeCatalog.Daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Month_01 {
    Map<String, PriorityQueue<String>> map = new HashMap<>();
    List<String> result = new ArrayList<>();

    // Hierholzer算法求解欧拉路径
    public List<String> findItinerary(List<List<String>> tickets) {
        
        // 记录边信息
        for (List<String> ticket : tickets) {
            String src = ticket.get(0);
            String dst = ticket.get(1);

            if (!map.containsKey(src)) {
                map.put(src, new PriorityQueue<String>());
            }

            map.get(src).offer(dst);
        }

        dfsAssist("JFK");

        Collections.reverse(result);
        return result;
    }

    public void dfsAssist(String cur) {
        while (map.containsKey(cur) && map.get(cur).size() > 0) {
            String temp = map.get(cur).poll();
            dfsAssist(temp);
        }

        result.add(cur);
    }


    Set<String> visited = new HashSet<>();
    StringBuilder ans = new StringBuilder();

    public String crackSafe(int n, int k) {
        if (n == 1 && k == 1) {
            return "0";
        }


        String start = String.join("", Collections.nCopies(n - 1, "0"));
       
        dfsCrackSafe(start, k);
        ans.append(start);
        
        return ans.toString();
    }

    public void dfsCrackSafe(String cur, int k) {
        for (int x = 0; x < k; x++) {
            String next = cur + x;
            if (!visited.contains(next)) {
                visited.add(next);
                
                dfsCrackSafe(next.substring(1), k); 
                ans.append(x);
            }
        }
    }


    /**
     * Day11: LeeCode 2283
     * @param num
     * @return
     */
    public boolean digitCount(String num) {
        int n = num.length();

        Map<Integer, Integer> map = new HashMap<>();
        for (char c : num.toCharArray()) {
            if (c - '0' >= n) {
                return false;
            }

            map.put(c - '0', map.getOrDefault(c - '0', 0) + 1);
        }

        boolean flag = true;

        for (int i = 0; i < n; i++) {
            if (map.getOrDefault(i, 0) != num.charAt(i) - '0') {
                flag = false;
                break;
            }
        }

        return flag;
    }


    /**
     * Day12: LeeCode 1807
     * @param s
     * @param knowledge
     * @return
     */
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();
        for (List<String> tmp : knowledge) {
            String key = tmp.get(0);
            String val = tmp.get(1);

            map.put(key, val);
        }


        StringBuilder ans = new StringBuilder();
        StringBuilder sub = new StringBuilder();

        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                sub.setLength(0);
                flag = true;
            }

            else if (s.charAt(i) == ')') {
                ans.append(map.getOrDefault(sub.toString(), "?"));
                flag = false;
            }

            else {
                if (flag) {
                    sub.append(s.charAt(i));
                }
                else {
                    ans.append(s.charAt(i));
                }
            }
        }


        return ans.toString();
    }


    /**
     * 单调栈练习题: LeeCode 496
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(nums2[0]);

        for (int i = 1; i < nums2.length; i++) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                int cur = stack.pop();
                map.put(cur, nums2[i]);
            }

            stack.push(nums2[i]);
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }

        return nums1;
    }


    /**
     * 滑动窗口练习题: LeeCode 713 
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int ans = 0;
        int left = 0;
        int cur = 1;

        for (int right = 0; right < nums.length; right++) {
            cur *= nums[right];
            while (cur >= k && left <= right) {
                cur /= nums[left];
                left += 1;
            }

            ans += right - left + 1;
        }

        return ans;
    }


    /**
     * Day13: LeeCode 2287
     * @param s
     * @param target
     * @return
     */
    public int rearrangeCharacters(String s, String target) {
        int[] countS = new int[26];
        int[] countT = new int[26];

        for (char c : s.toCharArray()) {
            countS[c - 'a'] += 1;
        }

        for (char c : target.toCharArray()) {
            countT[c - 'a'] += 1;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (countT[i] > 0) {
                ans = Math.min(ans, countS[i] / countT[i]);
            }
        }

        return ans;
    }


    public int gcd(int a, int b) {
        if (b % a == 0) {
            return a;
        }

        return gcd(b % a, a);
    }

    /**
     * Day14: LeeCode 1819
     * @param nums
     * @return
     */
    public int countDifferentSubsequenceGCDs(int[] nums) {
        int ans = 0;

        int maxVal = Arrays.stream(nums).max().getAsInt();
        boolean[] flags = new boolean[maxVal + 1];

        for (int num : nums) {
            flags[num] = true;
        }

        for (int i = 1; i <= maxVal; i++) {
            int subGCD = 0;
            for (int j = i; j <= maxVal; j += i) {
                if (flags[j]) {
                    if (subGCD == 0) {
                        subGCD = j;
                    }
                    else {
                        subGCD = gcd(j, subGCD);
                    }

                    if (subGCD == j) {
                         ans += 1;
                         break;
                    }
                }
            }
        }

        return ans;
    }


    /**
     * Day 17: LeeCode 1814
     * @param nums
     * @return
     */
    public static final int MOD = (int) (1e9 + 7);
    public int countNicePairs(int[] nums) {
        int ans = 0;

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int tmp = num;
            int recNum = 0;

            while (tmp > 0) {
                recNum = recNum * 10 + tmp % 10;
                tmp /= 10;
            }

            ans = (ans + map.getOrDefault(num - recNum, 0)) % MOD;
            map.put(num - recNum, map.getOrDefault(num - recNum, 0) + 1);
        }

        return ans;
    }


    public static void main(String[] args) {
        Month_01 test = new Month_01();

        System.out.println(test.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100));
    }
}
