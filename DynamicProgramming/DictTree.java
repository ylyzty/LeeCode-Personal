package CodeCatalog.DynamicProgramming;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典树
 */
public class DictTree {
    public static void main(String[] args) {
        List<List<String>> res = new ArrayList<>();
        res.add(new ArrayList<>());

        List<String> list = new ArrayList<>();
        for (List<String> temp : res) {
            list.add(String.join(" ", temp));
        }

        System.out.println(list.get(0));
    }


    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        // 枚举终点, exclusive
        for (int i = 1; i <= s.length(); ++i) {
            // 枚举起点, inclusive
            for (int j = i - 1; j >= 0; --j) {
                String temp = s.substring(j, i);
                if (wordDict.contains(temp) && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }


    /**
     * 自底向上, DP
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak2(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        // 枚举终点, exclusive
        for (int i = 1; i <= s.length(); ++i) {
            // 枚举起点, inclusive
            for (int j = i - 1; j >= 0; --j) {
                String temp = s.substring(j, i);
                if (wordDict.contains(temp) && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        for (String word : wordDict) {
            wordSet.add(word);
        }

        List<String> res = new ArrayList<>();
        Deque<String> path = new ArrayDeque<>();
        dfs(s, s.length(), dp, path, res);

        return res;
    }


    /**
     * 从后完前深搜, 根据 dp 数组判断是否需要回溯
     * @param s
     * @param index: 前缀子串长度
     * @param dp
     * @param path
     * @param res
     */
    public void dfs(String s, int index, boolean[] dp, Deque<String> path, List<String> res) {
        if (index == 0) {
            res.add(String.join(" ", path));
            return;
        }

        // 枚举子串起点
        for (int i = index - 1; i >= 0; i--) {
            String temp = s.substring(i, index);
            if (wordSet.contains(temp) && dp[i]) {
                path.addFirst(temp);
                dfs(s, i, dp, path, res);
                path.removeFirst();
            }
        }
    }


    /**
     * 记忆化搜索
     * @param s
     * @param wordDict
     * @return
     */
    Map<Integer, List<List<String>>> map = new HashMap<>();
    Set<String> wordSet = new HashSet<>();
    public List<String> wordBreak3(String s, List<String> wordDict) {
        for (String word : wordDict) {
            wordSet.add(word);
        }

        List<List<String>> wordBreaks = dfs(s, 0);
        List<String> res = new ArrayList<>();
        for (List<String> wordBreak : wordBreaks) {
            res.add(String.join(" ", wordBreak));
        }

        return res;
    }

    public List<List<String>> dfs(String s, int index) {
        if (!map.containsKey(index)) {
            List<List<String>> wordBreaks = new ArrayList<>();

            if (index == s.length()) {
                wordBreaks.add(new ArrayList<>());
            }

            for (int i = index + 1; i <= s.length(); i++) {
                String word = s.substring(index, i);
                if (wordSet.contains(word)) {
                    List<List<String>> nextWordBeaks = dfs(s, i);
                    for (List<String> nextWordBreak : nextWordBeaks) {
                        List<String> wordBreak = new ArrayList<>(nextWordBreak);
                        wordBreak.add(0, word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }

            map.put(index, wordBreaks);
        }

        return map.get(index);
    }
}