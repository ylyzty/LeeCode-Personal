package Daily;

public class KMP {
    public int searchSubstringIndex(String s, String pattern) {

        ListNode node;
        int[] next = new int[pattern.length()];
        next[0] = 0;

        for (int i = 1, j = 0; i < pattern.length(); i++) {
            while (j != 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }

            next[i] = j;
        }

        for (int i = 0, j = 0; i < s.length(); i++) {
            while (j != 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }

            if (j == pattern.length()) {
                return i - j + 1;
            }
        }

        return -1;
    }
}
