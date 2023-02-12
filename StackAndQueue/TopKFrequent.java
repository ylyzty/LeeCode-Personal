package CodeCatalog.StackAndQueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;


/**
 * 优先级队列
 */
public class TopKFrequent {
    public int[] solution(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }


        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>() {

            @Override
            public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
                // TODO Auto-generated method stub
                return o1.getValue() - o2.getValue();
            }
            
        });

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());

        for (int i = 0; i < list.size(); i++) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(list.get(i));
            }
            else {
                if (priorityQueue.peek().getValue() < list.get(i).getValue()) {
                    priorityQueue.poll();
                    priorityQueue.offer(list.get(i));
                }
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < priorityQueue.size(); i++) {
            res[i] = priorityQueue.poll().getKey();
        }
        
        return res;
    }
}
