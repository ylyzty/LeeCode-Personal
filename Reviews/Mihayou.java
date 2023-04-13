package Reviews;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Mihayou {
    public Set<Character> set = new HashSet<>();
    public static void main(String[] args) {
        Mihayou test = new Mihayou();
        test.set.add('m');
        test.set.add('h');
        test.set.add('y');

        System.out.println(test.test_2("mabchy", "mhaybcmhmy"));
    }


    /**
     * 哈希映射 + 双指针
     * 字符串之差为 mhy 的整数倍，并保证非 mhy 字母的相对顺序
     * @param s
     * @param t
     * @return
     */
    public boolean test_2(String s, String t) {
        if (Math.abs(s.length() - t.length()) % 3 != 0) {
            return false;
        }

        Map<Character, Integer> mapS = new HashMap<>();
        Map<Character, Integer> mapT = new HashMap<>();
        int i = 0;
        int j = 0;

        while (i < s.length() && j < t.length()) {
            while (i < s.length() && set.contains(s.charAt(i))) {
                mapS.put(s.charAt(i), mapS.getOrDefault(s.charAt(i), 0) + 1);
                i += 1;
            }

            while (j < t.length() && set.contains(t.charAt(j))) {
                mapT.put(t.charAt(j), mapT.getOrDefault(t.charAt(j), 0) + 1);
                j += 1;
            }

            if (i < s.length() && j < t.length()) {
                if (s.charAt(i) == t.charAt(j)) {
                    i += 1;
                    j += 1;
                }
                else {
                    return false;
                }
            }
        }

        while (i < s.length()) {
            if (set.contains(s.charAt(i))) {
                mapS.put(s.charAt(i), mapS.getOrDefault(s.charAt(i), 0) + 1);
                i += 1;
            }
            else {
                return false;
            }
        }

        while (j < t.length()) {
            if (set.contains(t.charAt(j))) {
                mapT.put(t.charAt(j), mapT.getOrDefault(t.charAt(j), 0) + 1);
                j += 1;
            }
            else {
                return false;
            }
        }

        int target = (s.length() - t.length()) / 3;
        for (char c : set) {
            if ((mapS.get(c) - mapT.get(c)) != target) {
                return false;
            }
        }
        
        return true;
    }
}
