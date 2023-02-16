package DynamicProgramming;

import java.util.ArrayList;
import java.util.List;

public class DigitalDP {
    private static final int DIGITS = 15;
    public long[] getDigitLen(long digit, long[] dp, long[] mi) {
        long temp = digit;
        int len = 0;

        List<Integer> list = new ArrayList<>();
        list.add(-1);
        while (digit > 0) {
            ++len;
            list.add((int) digit % 10);
            digit /= 10;
        }

        long[] ans = new long[10];

        for (int i = len; i >= 1; i--) {
            // 计算 i - 1 位出现的 0~9
            for (int j = 0; j < 10; j++) {
                ans[j] += dp[i - 1] * list.get(i);
            }

            // 计算 i 位出现的 0~9, 未消除前导0
            for (int j = 0; j < list.get(i); j++) {
                ans[j] += mi[i - 1];
            }

            // 消除最高位
            temp -= mi[i - 1] * list.get(i);

            // 边界情况 987 的 9, 0~8均能满 2位即(000 ~ 899)
            // 而9 只能(900 ~ 987)
            ans[list.get(i)] += temp + 1;

            // 减去前导0
            ans[0] -= mi[i - 1];
        }

        return ans;
    }

    public void digitCount(long left, long right) {
        long[] dp = new long[DIGITS];
        long[] mi = new long[DIGITS];   // 10^0

        /**
         * dp转移公式: 表示满 i 位的所有数字中每个数字 (0~9) 出现的次数, 未处理前导0
         * dp[i] = 10 * dp[i - 1] + 10^(i - 1);
         */
        mi[0] = 1;
        for (int i = 1; i < DIGITS; i++) {
            dp[i] = dp[i - 1] * 10 + mi[i - 1];
            mi[i] = mi[i - 1] * 10;
        }

        long[] ans1 = getDigitLen(left - 1, dp, mi);
        long[] ans2 = getDigitLen(right, dp, mi);

        for (int i = 0; i < 10; i++) {
            System.out.println(ans2[i] - ans1[i]);
        }
    }
    
    public static void main(String[] args) {
        DigitalDP test = new DigitalDP();
        test.digitCount(1, 99);
    }
}
