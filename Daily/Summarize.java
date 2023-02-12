package CodeCatalog.Daily;

public class Summarize {
    // 递归快速幂
    public int quickPow1(int a, int n) {
        if (n == 0) {
            return 1;
        }

        else if (n % 2 == 1) {
            return quickPow1(a, n / 2) * a;
        }

        else {
            int tmp = quickPow1(a, n / 2);
            return tmp * tmp;
        }
    }
    // 非递归快速幂 a^n
    public int quickPow2(int a, int n) {
        int ans = 1;

        while (n > 0) {
            if ((n & 1) == 1) {
                ans *= a;
            }

            a *= a;
            n = n >> 1;
        }

        return ans;
    }
}
