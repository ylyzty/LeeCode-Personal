package Others;

import java.util.Arrays;
import java.util.Scanner;

public class AcmPatternReadInputs {
    public static void main(String[] args) {
        // Scanner类默认的分割符为 空格
        Scanner sc = new Scanner(System.in);

        /**
         * 多行输入
         * 输入包括两个整数a,b, 输入数据包括多组
         */
        while (sc.hasNext()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println(a + b);
        }


        /**
         * 多行输入
         * 第一行为数据组数t
         * 接下来每行包括两个正整数a,b
         */
        int t = sc.nextInt(); 
        while (t > 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            System.out.println(a + b);
            t -= 1;
        }


        /**
         * 多行输入
         * 输入包括两个整数a,b, 输入数据包括多组, 以[0, 0]结束
         */
        while (sc.hasNext()) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println(a + b);
        }


        /**
         * 多行输入
         * 每行第一个数字n为该行数字的个数, n为0结束输入
         * 接下来n个正整数,即需要求和的每个正整数
         */
        while (sc.hasNext()) {
            int n = sc.nextInt();
            
            if (n == 0) {
                break;
            }
            
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += sc.nextInt();
            }
            System.out.println(sum);
        }


        /**
         * 多行输入
         * 输入的第一行包括一个正整数t(1 <= t <= 100), 表示数据组数。
         * 接下来t行, 每行一组数据。
         * 每行的第一个整数为整数的个数n(1 <= n <= 100)
         * 接下来n个正整数, 即需要求和的每个正整数
         */
        t = sc.nextInt();
        while (t > 0) {
            int n = sc.nextInt();
            
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += sc.nextInt();
            }
            System.out.println(sum);
            t -= 1;
        }


        /**
         *
         * 输入数据有多组, 每行表示一组输入数据
         * 每行的第一个整数为整数的个数n(1 <= n <= 100)
         * 接下来n个正整数, 即需要求和的每个正整数
         */
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += sc.nextInt();
            }
            System.out.println(sum);
        }


        /**
         * 输入数据有多组, 每行表示一组输入数据
         * 每行不定有n个整数，空格隔开(1 <= n <= 100)
         */
        while (sc.hasNextLine()) {
            String[] nums = sc.nextLine().split(" ");
            
            int sum = 0;
            for (String num : nums) {
                sum += Integer.valueOf(num);
            }
            
            System.out.println(sum);
        }


        /**
         * 输入有两行，第一行n
         * 第二行是n个字符串，字符串之间用空格隔开
         * 
         * 输出一行排序后的字符串，空格隔开，无结尾空格
         */
        int n = sc.nextInt();
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = sc.next();
        }
        
        Arrays.sort(array, (o1, o2) -> {
            return o2.compareTo(o1);
        });
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i != n - 1) {
                System.out.print(" ");
            }
        }


        /**
         * 多个测试用例，每个测试用例一行
         * 每行通过空格隔开，有n个字符，n＜100
         * 
         * 对于每组测试用例，输出一行排序过的字符串，每个字符串通过空格隔开
         */
        while (sc.hasNextLine()) {
            String[] array2 = sc.nextLine().split(" ");
            Arrays.sort(array2);
            
            for (int i = 0; i < array2.length; i++) {
                System.out.print(array2[i] + " ");
            }
            System.out.println();
        }


        /**
         * 多个测试用例，每个测试用例一行
         * 每行通过,隔开，有n个字符，n＜100
         * 
         * 对于每组用例输出一行排序后的字符串，用','隔开，无结尾空格
         */
        while (sc.hasNextLine()) {
            String[] array2 = sc.nextLine().split(",");
            Arrays.sort(array2);
            
            for (int i = 0; i < array2.length; i++) {
                System.out.print(array2[i]);
                if (i != array2.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }
}
