package Daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Month_09 {
    
    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            temp.add(nums[i]);
        }
        Collections.sort(temp, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                if (map.get(o1) == map.get(o2)) {
                    return o2 - o1;
                }

                return map.get(o1) - map.get(o2);
            }
            
        });

        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp.get(i);
        }

        return nums;
    }


    /**
     * LeeCode 698: 划分为 k 个相等的子集
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int n = nums.length;

        for (int num : nums) {
            sum += num;
        }

        if (n < k || sum % k != 0) {
            return false;
        }

        int per = sum / k;
        Arrays.sort(nums);
        if (nums[n - 1] > per) {
            return false;
        }

        boolean[] dp = new boolean[1 << n];
        int[] curSum = new int[1 << n];
        dp[0] = true;

        for (int i = 0; i < (1 << n); i++) {
            if (!dp[i]) {
                continue;
            }

            for (int j = 0; j < n; j++) {
                if (curSum[i] + nums[j] > per) {
                    break;
                }

                if (((i >> j) & 1) == 0) {
                    int next = i | (1 << j);
                    if (!dp[next]) {
                        curSum[next] = (curSum[i] + nums[j]) % per;
                        dp[next] = true;
                    }
                }
            }
        }

        return dp[1 << n - 1];
    }


    public boolean makesquare(int[] matchsticks) {
        int n = matchsticks.length;
        int sum = 0;
        for (int matchstick : matchsticks) {
            sum += matchstick;
        }

        if (n < 4 || sum % 4 != 0) {
            return false;
        }

        int per = sum / 4;
        Arrays.sort(matchsticks);
        if (matchsticks[n - 1] > per) {
            return false;
        }

        boolean[] dp = new boolean[1 << n];
        int[] curSum = new int[1 << n];
        dp[0] = true;

        for (int i = 0; i < (1 << n); i++) {
            if (!dp[i]) {
                continue;
            }

            for (int j = 0; j < n; j++) {
                if (curSum[i] + matchsticks[j] > per) {
                    break;
                }

                if (((i >> j) & 1) == 0) {
                    int next = i | (1 << j);
                    if (!dp[next]) {
                        curSum[next] = (curSum[i] + matchsticks[j]) % per;
                        dp[next] = true;
                    }
                }
            }
        }

        return dp[(1 << n) - 1];
    }


    int res;
    public int KSimilarity(String s1, String s2) {
        res = Integer.MAX_VALUE;
        dfs(0, s1, s2, 0);
        return res;
    }

    public void dfs(int index, String s1, String s2, int step) {
        while (index < s1.length() && s1.charAt(index) == s2.charAt(index)) {
            index++;
        }

        if (index == s1.length()) {
            res = Math.min(res, step);
            return;
        }

        for (int i = index + 1; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(index)) {
                String s1Next = swap(s1, index, i);
                dfs(index + 1, s1Next, s2, step + 1);
            }
        }
    }

    public String swap(String s, int index1, int index2) {
        char[] array = s.toCharArray();
        char temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;

        return new String(array);
    }


    public int[] decrypt(int[] code, int k) {
        int length = code.length;
        int[] res = new int[length];

        if (k == 0) {
            return res;
        }

        if (k > 0) {
            int sum = 0;
            for (int i = 0; i < k; i++) {
                sum += code[i];
            }

            int left = 0;
            int right = k - 1;

            for (int i = 0; i < code.length; i++) {
                sum -= code[left];
                left += 1;
                if (left >= length) {
                    left = 0;
                }

                right += 1;
                if (right >= length) {
                    right = 0;
                }

                sum += code[right];

                res[i] = sum;
                System.out.println(sum);
            }
        }
        else {
            int sum = 0;
            for (int i = length - 1; i >= length - k; i--) {
                sum += code[i];
            }

            int left = length - k;
            int right = length - 1;

            for (int i = length - 1; i >= 0; i--) {
                left -= 1;
                if (left < 0) {
                    left = length - 1;
                }
                sum += code[left];

                sum -= code[right];
                right -= 1;
                if (right < 0) {
                    right = length - 1;
                }

                res[i] = sum;
            }
        }

        return res;
    }


    public int[] missingTwo(int[] nums) {
        int xor_val = 0;
        for (int i = 1; i < nums.length + 2; i++) {
            xor_val ^= i;
        }

        for (int num : nums) {
            xor_val ^= num;
        }

        int classification = xor_val & -xor_val;
        int type1 = 0, type2 = 0;
        for (int num : nums) {
            if ((num & classification) != 0) {
                type1 ^= num;
            }
            else {
                type2 ^= num;
            }
        }

        return new int[]{type1, type2};
    }


    public boolean CheckPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        int n = s1.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(s1.charAt(i), map.getOrDefault(s1.charAt(i), 0) + 1);
            map.put(s2.charAt(i), map.getOrDefault(s2.charAt(i), 0) - 1);
        }

        Set<Map.Entry<Character, Integer>> entrySet = map.entrySet();
        for(Map.Entry<Character, Integer> entry : entrySet) {
            if (entry.getValue() != 0) {
                return false;
            }
        }

        return true;
    }


    public boolean isFlipedString(String s1, String s2) {
        return s1.length() == s2.length() && (s1 + s1).contains(s2);
    }


    public void setZeros(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] rows = new boolean[m];
        boolean[] cols = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rows[i] = true;
                    cols[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rows[i] || cols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }


    public boolean canTranform(String start, String end) {
        // 特殊情况考虑
        if (start.length() != end.length()) {
            return false;
        }

        int n = start.length();
        int i = 0, j = 0;
        while (i < n && j < n) {
            while (i < n && start.charAt(i) == 'X') {
                i += 1;
            }
            while (j < n && end.charAt(j) == 'X') {
                j += 1;
            }

            if (i < n && j < n) {
                if (start.charAt(i) != end.charAt(j)) {
                    return false;
                }

                if (start.charAt(i) == 'L' && i < j) {
                    return false;
                }
                else if (start.charAt(i) == 'R' && i > j) {
                    return false;
                }
            }

            i += 1;
            j += 1;
        }

        while (i < n) {
            if (start.charAt(i) != 'X') {
                return false;
            }
            i += 1;
        }

        while (j < n) {
            if (end.charAt(j) != 'X') {
                return false;
            }
            j += 1;
        }

        return true;
    }


    

    


    public static void main(String[] args) {
        Month_09 test = new Month_09();
        System.out.println(test.toString());
    }
}
