package HardLevelQues;

import java.util.HashMap;
import java.util.Map;

/**
 * 正则表达式匹配
 */
public class RegularMatch {
    private Map<String, Boolean> memSearch = new HashMap<>();

    public boolean regularMatch(String s, String pattern) {
        return dp(s, 0, pattern, 0);
    }

    /**
     * 匹配 s[i..] 与 pattern[j..]
     * @param s
     * @param i
     * @param pattern
     * @param j
     * @return
     */
    private boolean dp(String s, int i, String pattern, int j) {
        int m = s.length();
        int n = pattern.length();

        // 已经匹配到 模式串 的末尾, 检验文本是否匹配到末尾
        if (j == n) {
            return i == m;
        }

        // 已经匹配到 文本的 末尾, 检验之后的模式串是否能匹配空白串
        if (i == m) {
            // 剩下奇数个字符, 无法满足要求
            if ((n - j) % 2 == 1) {
                return false;
            }

            for (; j + 1 < n; j += 2) {
                if (pattern.charAt(j + 1) != '*') {
                    return false;
                }
            }

            return true;
        }

        // 记忆化存储已经搜索过的路径
        String key = i + "," + j;
        if (memSearch.containsKey(key)) {
            return memSearch.get(key);
        }

        /**
         * 情况1: s[i] == pattern[j] && pattern[j + 1] == '*', 匹配0次或多次
         * 情况2: s[i] == pattern[j] && pattern[j + 1] != '*', 正常匹配1次
         * 情况3: s[i] != pattern[j] && pattern[j + 1] == '*', 匹配0次
         * 其他情况: 无法匹配
         */
        boolean res = false;
        if (s.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '.') {
            if (j < n - 1 && pattern.charAt(j + 1) == '*') {
                // 通配符匹配0次, 或通配符匹配1次, 向下递归
                res = dp(s, i, pattern, j + 2) || dp(s, i + 1, pattern, j);
            }
            else {
                // 正常匹配1次
                res = dp(s, i + 1, pattern, j + 1);
            }
        }
        else {
            // 通配符匹配0次
            if (j < n - 1 && pattern.charAt(j + 1) == '*') {
                res = dp(s, i + 1, pattern, j + 2);
            }
        }

        memSearch.put(key, res);
        return res;
    }

    public static void main(String[] args) {
        RegularMatch r = new RegularMatch();
        System.out.println(r.regularMatch("a", "ab*"));
    }
}
