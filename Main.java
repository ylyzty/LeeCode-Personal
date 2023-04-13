
// We have imported the necessary tool classes.
// If you need to import additional packages or classes, please import here.

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] levels = new int[M];
        for (int i = 0; i < M; i++) {
            levels[i] = sc.nextInt();
        }

        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            map.put(i, new HashSet<>());
        }

        List<Integer>[] graph = new List[M];
        int[] cnts = new int[M];
        for (int i = 0; i < M; i++) {
            graph[i] = new ArrayList<>();

            cnts[i] = sc.nextInt();
            for (int j = 0; j < cnts[i]; j++) {
                int tmp = sc.nextInt();

                graph[i].add(tmp);
                if (levels[tmp] == levels[i]) {
                    map.get(levels[i]).add(tmp);
                }
            }
        }

        int[] ans = new int[N - 1];
        int[] starts = new int[N];
        int level = 0;
        starts[level++] = 0;
        for (int i = 1; i < M; i++) {
            if (levels[i] != levels[i - 1]) {
                starts[level++] = i;
            }
        }

        for (int j = N - 2; j >= 0; j--) {
            int left = starts[j];
            int right = starts[j + 1];

            Queue<Integer> queue = new ArrayDeque<>();
            for (int k = left; k < right; k++) {
                if (map.get(j).contains(k)) {
                    continue;
                }

                queue.offer(k);
            }

            while (!queue.isEmpty()) {
                int size = queue.size();
                if (size == 1) {
                    ans[j] = Math.max(ans[j], queue.peek());
                }

                Set<Integer> set = new HashSet<>();
                for (int k = 0; k < size; k++) {
                    int cur = queue.poll();
                    for (int m = 0; m < graph[cur].size(); m++) {
                        int next = graph[cur].get(m);
                        if (map.get(j).contains(next) && (!set.contains(next))) {
                            queue.offer(next);
                            set.add(next);
                        }
                    }
                }
            }
        }


        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
    }
}
