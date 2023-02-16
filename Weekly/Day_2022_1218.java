package Weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day_2022_1218 {
    
    public int similarPairs(String[] words) {
        int ans = 0;

        for (int i = 0; i < words.length; i++) {
            Set<Integer> set1 = new HashSet<>();
            for (char ch : words[i].toCharArray()) {
                if (!set1.contains(ch - 'a')) {
                    set1.add(ch - 'a');
                }
            }

            for (int j = i + 1; j < words.length; j++) {
                Set<Integer> set2 = new HashSet<>();
                for (char ch : words[j].toCharArray()) {
                    if (!set2.contains(ch - 'a')) {
                        set2.add(ch - 'a');
                    }
                }

                if (help(set1, set2)) {
                    ans += 1;
                }
            }
        }

        return ans;
    }

    public boolean help(Set<Integer> set1, Set<Integer> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }

        for (int num : set1) {
            if (!set2.contains(num)) {
                return false;
            }
        }

        return true;
    }


    public int smallestValue(int n) {
        while (n > assist(n)) {
            n = assist(n);
        }

        return n;
    }

    public int assist(int n) {
        int tmp = 0;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                while (n % i == 0) {
                    tmp += i;
                    n /= i;
                }
            }
        }

        if (n != 1) {
            tmp += n;
        }

        return tmp;
    }


    public boolean isPossible(int n, List<List<Integer>> edges) {
        // Build graph
        List<Integer>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (List<Integer> edge : edges) {
            graph[edge.get(0)].add(edge.get(1));
            graph[edge.get(1)].add(edge.get(0));
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (graph[i].size() % 2 != 0) {
                list.add(i);
            }
        }

        if (list.size() == 0) {
            return true;
        }

        if (list.size() == 2) {
            int src = list.get(0);
            int tgr = list.get(1);

            for (int node : graph[src]) {
                if (node == tgr) {
                    return false;
                }
            }

            return true;
        }

        else if (list.size() == 4) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (j == i) {
                        continue;
                    }

                    // 判断
                    boolean flag = true;
                    for (int node : graph[list.get(i)]) {
                        if (node == list.get(j)) {
                            flag = false;
                            break;
                        }
                    }

                    if (!flag) {
                        continue;
                    }
                    else {
                        int[] arr = new int[2];
                        int pos = 0;
                        for (int k = 0; k < list.size(); k++) {
                            if (k != i && k != j) {
                                arr[pos] = k;
                                pos += 1;
                            }
                        }

                        for (int node : graph[list.get(arr[0])]) {
                            if (node == list.get(arr[1])) {
                                flag = false;
                                break;
                            }
                        }

                        if (!flag) {
                            continue;
                        }
                        else {
                            return true;
                        }
                    }

                }
            }
        }

        return false;
    }


    public static void main(String[] args) {
        Day_2022_1218 test = new Day_2022_1218();
        test.similarPairs(new String[]{"zgtzytjkre","jjzdbxyutj","umghhnlihq","mdxjukhqsm","mqdplhuvqr","xpdhateywu","ugedwkxapc","vjpryhictr"});
    }

}
