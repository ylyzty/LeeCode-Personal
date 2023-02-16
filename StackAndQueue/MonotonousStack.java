package StackAndQueue;

import java.util.Stack;

public class MonotonousStack {

    /**
     * 寻找以 arr[i] 为最小值的子数组个数
     * 1. arr[i] 左边第一个小于它的数的距离 left
     * 2. arr[i] 右边第一个小于等于它的数的距离 right
     * 3. count(arr[i]) = left * right
     * @param arr
     * @return
     */
    private static final int MOD = 1000000007;
    public int sumSubArrayMins(int[] arr) {
        int n = arr.length;
        int[] leftDis = new int[n];
        int[] rightDis = new int[n];

        Stack<Integer> stackLeft = new Stack<>();
        Stack<Integer> stackRight = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stackLeft.empty() && arr[stackLeft.peek()] >= arr[i]) {
                stackLeft.pop();
            }

            if (stackLeft.empty()) {
                leftDis[i] = i + 1;
            }
            else {
                leftDis[i] = i - stackLeft.peek();
            }

            stackLeft.push(i);
        }

        for (int j = n - 1; j >= 0; j--) {
            while (!stackRight.empty() && arr[stackRight.peek()] > arr[j]) {
                stackRight.pop();
            }

            if (stackRight.empty()) {
                rightDis[j] = n - j;
            }
            else {
                rightDis[j] = stackRight.peek() - j;
            }

            stackRight.push(j);
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            res = (res + (long) leftDis[i] * rightDis[i] * arr[i]) % MOD;
        }

        return (int)res;
    }


    public int sumSubArrayMins2(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();

        int[] dp = new int[n];
        long ans = 0;

        for (int i = 0; i < n; i++) {
            while (!stack.empty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }

            int k = 0;
            if (!stack.empty()) {
                k = i - stack.peek();
            }
            else {
                k = i + 1;
            }

            dp[i] = arr[i] * k + (stack.empty() ? 0 : dp[i - k]);
            ans = (ans + dp[i]) % MOD;
            stack.push(i);
        }

        return (int) ans;
    }
}
