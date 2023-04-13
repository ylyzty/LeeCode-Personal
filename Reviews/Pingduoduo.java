package Reviews;

import java.util.PriorityQueue;

/**
 * 拼多多笔试复盘
 */
public class Pingduoduo {

    /**
     * 最小费用最大流
     * 
     */
    public void test_3() {

    }

    /**
     * 平均数  中位数
     * @param n
     * @param arr
     */
    public void test_4(int n, int[] arr) {
        int[] avg = new int[n];
        int[] mid = new int[n];

        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            avg[i] = (int) (sum / (i + 1) + (sum % (i + 1) >= (i + 2) / 2 ? 1 : 0));
        }


        /**
         * 大顶堆: 保存中位数中左边的数
         * 小顶堆: 保存中位数中右边的数
         */
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>((o1, o2) -> {
            return o2 - o1;
        });
        PriorityQueue<Integer> minQueue = new PriorityQueue<>((o1, o2) -> {
            return o1 - o2;
        });

        for (int i = 0; i < n; i++) {
            if (maxQueue.isEmpty() || arr[i] <= maxQueue.peek()) {
                maxQueue.offer(arr[i]);

                if (maxQueue.size() > minQueue.size() + 1) {
                    minQueue.offer(maxQueue.poll());
                }
            }
            else {
                minQueue.offer(arr[i]);

                if (minQueue.size() > maxQueue.size() + 1) {
                    maxQueue.offer(minQueue.poll());
                }
            }

            if (maxQueue.size() > minQueue.size()) {
                mid[i] = maxQueue.peek();
            }
            else {
                mid[i] = (maxQueue.peek() + minQueue.peek()) / 2;
            }
        }
    }
}
