package Weekly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Day_2022_1029 {
    public String oddString(String[] words) {
        int length = words[0].length();
        int[] difference1 = new int[length - 1];
        int[] difference2 = new int[length - 1];
        char[] chArr1 = words[0].toCharArray();
        char[] chArr2 = words[1].toCharArray();

        for (int i = 0; i < length - 1; i++) {
            difference1[i] = chArr1[i + 1] - chArr1[i];
            difference2[i] = chArr2[i + 1] - chArr2[i];
        }

        if (isSame(difference1, difference2)) {
            for (int i = 2; i < words.length; i++) {
                char[] chArr = words[i].toCharArray();
                for (int j = 0; j < length - 1; j++) {
                    difference2[j] = chArr[j + 1] - chArr[j]; 
                }

                if (!isSame(difference1, difference2)) {
                    return words[i];
                }
            }
        }
        else {
            char[] chArr = words[2].toCharArray();
            int[] difference3 = new int[length - 1];
            for (int i = 0; i < length - 1; i++) {
                difference3[i] = chArr[i + 1] - chArr[i];
            }

            if (isSame(difference1, difference3)) {
                return words[1];
            }
            else {
                return words[0];
            }
        }

        return null;
    }


    /**
     * 辅助函数, 判断两个差值数组是否相同
     * @param arr1
     * @param arr2
     * @return
     */
    public boolean isSame(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }


    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> res = new ArrayList<>();
        for (String query : queries) {
            if (isSatisfy(query, dictionary)) {
                res.add(query);
            }
        }

        return res;
    }


    /**
     * 辅助函数, 判断单词是否满足两次以内
     * @param s
     * @param dictionary
     * @return
     */
    public boolean isSatisfy(String s, String[] dictionary) {
        for (String dict : dictionary) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != dict.charAt(i)) {
                    count += 1;
                }

                if (count > 2) {
                    break;
                }
            }

            if (count <= 2) {
                return true;
            }
        }

        return false;
    }


    public int destroyTargets(int[] nums, int space) {
        Map<Integer, int[]> map = new HashMap<>();

        for (int num : nums) {
            int temp = num % space;
            if (!map.containsKey(temp)) {
                map.put(temp, new int[]{1, num});
            }
            else {
                int[] arr = map.get(temp);
                arr[0] += 1;
                arr[1] = Math.min(arr[1], num);
                map.put(temp, arr);
            }
        }

        int maxCount = Integer.MIN_VALUE;
        int res= 0;
        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            int[] arr = entry.getValue();
            if (maxCount < arr[0]) {
                res = arr[1];
                maxCount = arr[0];
            }
            else if (maxCount == arr[0]) {
                res = Math.min(res, arr[1]);
            }
        }

        return res;
    }


    /**
     * 单调栈 + 小顶堆
     * 该方法也可以扩展到找第3,4,...,n 个更大元素
     * 只需要继续添加若干个小顶堆即可
     * @param nums
     * @return
     */
    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;

        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
            return nums[o1] - nums[o2];
        });
        
        for (int i = 0; i < n; ++i) {
            while (!queue.isEmpty() && nums[queue.peek()] < nums[i]) {
                ans[queue.peek()] = nums[i];
                queue.poll();
            }

            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                queue.offer(stack.pop());
            }

            stack.push(i);
        }

        return ans;
    }


    public static void main(String[] args) {
        int[] nums = {6, 2, 5};
        int space = 100;

        Day_2022_1029 test = new Day_2022_1029();
        System.out.println(test.destroyTargets(nums, space));
    }
}
