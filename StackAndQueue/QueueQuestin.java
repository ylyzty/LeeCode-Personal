package StackAndQueue;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class QueueQuestin {
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != map.get(c)) {
                    return false;
                }
                stack.pop();
            }
            else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty() || c != stack.peek()) {
                stack.push(c);
            }
            else {
                stack.pop();
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        Set<String> set = new HashSet<>();    /* 存储操作符 */
        set.add("+");
        set.add("-");
        set.add("*");
        set.add("/");

        for (String token : tokens) {
            if (!set.contains(token)) {
                stack.push(Integer.valueOf(token));
            }
            else {
                int data1 = stack.pop();
                int data2 = stack.pop();

                switch(token) {
                    case "+":
                        stack.push(data2 + data1);
                        break;
                    case "-":
                        stack.push(data2 - data1);
                        break;
                    case "*":
                        stack.push(data2 * data1);
                        break;
                    case "/":
                        stack.push(data2 / data1);
                        break;
                    default:
                        System.out.println("Unknown Condition!");
                }
            }
        }

        return stack.pop();
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k >= nums.length) {
            // return max(nums);
        }

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < k; i++) {
            while(!deque.isEmpty() && nums[deque.getLast()] <= nums[i]) {
                deque.removeLast();
            }

            deque.add(i);
        }

        int[] res = new int[nums.length - k + 1];
        res[0] = nums[deque.getFirst()];
        int index = 1;

        for (int i = k; i < nums.length; i++) {
            while(!deque.isEmpty() && nums[deque.getLast()] <= nums[i]) {
                deque.removeLast();
            }
            deque.add(i);
            while (deque.getFirst() <= i - k) {
                deque.removeFirst();
            }

            res[index++] = nums[deque.getFirst()];
        }

        return res;
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        /**
         * 自定义排序规则
         * 按照数组第2个元素值从小到大排序
         */
        Comparator<int[]> comparator = new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                // TODO Auto-generated method stub
                return o1[1] - o2[1];
            }
            
        }; 
        // 小顶堆的实现
        PriorityQueue<int[]> queue = new PriorityQueue<>(comparator);
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int value = entry.getValue();

            if (queue.size() < k) {
                queue.offer(new int[]{num, value});
            }
            else {
                if (queue.peek()[1] < value) {
                    queue.poll();
                    queue.offer(new int[]{num, value});
                }
            }
        }

        int[] res = new int[k];
        int index = 0;
        while (index < k) {
            res[index++] = queue.poll()[0];
        }

        return res;
    }

}
