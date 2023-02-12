package CodeCatalog.Weekly;

import java.util.Arrays;


public class Day_2022_1023 {

    public boolean haveConflict(String[] event1, String[] event2) {
        // 比较 Unicode 字符, 使用 compareTo 函数
        if (event1[1].compareTo(event2[0]) < 0 || event2[1].compareTo(event1[0]) < 0) {
            return false;
        }
        
        return true;
    }


    /**
     * 求两个数的最大公因数
     * @param a
     * @param b
     * @required a <= b
     * @return
     */
    public int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }

        return gcd(b % a, a);
    }

    public int subarrayGCD(int[] nums, int k) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % k != 0) {
                continue;
            }

            int temp = nums[i];
            for (int j = i; j < nums.length; j++) {
                if (nums[j] % k != 0) {
                    break;
                }

                temp = gcd(temp, nums[j]);
                if (temp % k != 0) {
                    break;
                }

                if (temp == k) {
                    res += 1;
                }
            }
        }

        return res;
    }


    public long minCost(int[] nums, int[] cost) {
        int[][] pairs = new int[nums.length][2];

        long counts = 0;
        for (int i = 0; i < nums.length; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = cost[i];

            counts += cost[i];
        }

        Arrays.sort(pairs, (o1, o2) -> {
            return o1[0] - o2[0];
        });

        counts /= 2;
        int index = -1;
        while (counts > 0) {
            index += 1;
            counts -= pairs[index][1];
        }

        long res = 0;
        for (int i = 0; i < pairs.length; i++) {
            if (i == index) {
                continue;
            }

            long diff = Math.abs(pairs[i][0] - pairs[index][0]);
            long times = pairs[i][1];
            res += diff * times;
        }

        return res;
    }


    public long makeSimilar(int[] nums, int[] target) {
        Arrays.sort(nums);
        Arrays.sort(target);

        long res = 0;

        // index[0] 表示 target 当前指向的偶元素位置
        // index[1] 表示 target 当前指向的奇元素位置
        int[] index = new int[2];

        for (int num : nums) {
            int temp = num % 2;
            while (target[index[temp]] % 2 != temp) {
                index[num % 2] += 1;
            }

            res += Math.abs(num - target[index[temp]]);
            index[temp] += 1;
        }
        
        return res / 4;
    }


    public static void main(String[] args) {
        Day_2022_1023 test = new Day_2022_1023();

        int[] nums = {1, 3, 5, 2};
        int[] cost = {2, 3, 1, 14};
        System.out.println(test.minCost(nums, cost));
    }
}
