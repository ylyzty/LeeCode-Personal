package Reviews;

import java.util.*;;

/**
 * 美团笔试复盘
 */
public class Meituan {

    /**
     * 流星问题  差分数组
     * @param n
     * @param starts
     * @param ends
     */
    public void test_3(int n, int[] starts, int[] ends) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.put(starts[i], map.getOrDefault(starts[i], 0) + 1);
            map.put(ends[i], map.getOrDefault(ends[i], 0) - 1);
        }
        
        int[] times = new int[map.size()];
        Iterator<Integer> it = map.keySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            times[index++] = (Integer) it.next();
        }

        Arrays.sort(times);
        int maxNum = map.get(times[0]);
        int maxTime = 1;
        int cur = map.get(times[0]);

        for (int i = 1; i < times.length; i++) {
            cur += map.get(times[i]);
            if (cur == maxNum) {
                maxTime += times[i] - times[i - 1];
            }
            else if (cur > maxNum) {
                maxNum = cur;
                maxTime = 1;
            }
        }

        System.out.println(maxNum + " " + maxTime);
    }
}
