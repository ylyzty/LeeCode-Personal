package Weekly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day_2022_1030 {
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        Map<String, List<String>> map = new HashMap<>();

        long maxValue = 0;
        for (int i = 0; i < creators.length; ++i) {
            if (!map.containsKey(creators[i])) {
                maxValue = Math.max(maxValue, views[i]);
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(views[i]));
                list.add(String.valueOf(views[i]));
                list.add(ids[i]);

                map.put(creators[i], list);
            }
            else {
                List<String> list = map.get(creators[i]);

                long sum = Long.valueOf(list.get(0));
                int max = Integer.valueOf(list.get(1));
                
                sum += views[i];
                maxValue = Math.max(maxValue, sum);
                if (max < views[i]) {
                    list.set(2, ids[i]);
                    max = views[i];
                }
                else if (max == views[i]) {
                    if (list.get(2).compareTo(ids[i]) > 0) {
                        list.set(2, ids[i]);
                    }
                }
                list.set(0, String.valueOf(sum));  // 总体流量总和
                list.set(1, String.valueOf(max));  // 单个作者最大值

                map.put(creators[i], list);
            }
        }

        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            if (Long.valueOf(value.get(0)) == maxValue) {
                List<String> temp = new ArrayList<>();
                temp.add(key);
                temp.add(value.get(2));

                res.add(temp);
            }
        }

        return res;
    }


    public long makeIntegerBeautiful(long n, int target) {
        List<Integer> list = new ArrayList<>();
        int bitSum = 0;
        while (n > 0) {
            int temp = (int) (n % 10);
            n /= 10;

            bitSum += temp;
            list.add(temp);
        }

        if (bitSum <= target) {
            return 0;
        }

        int index = 0;
        long ans = 0;
        while (bitSum > target) {
            int bit = list.get(index);
            if (bit == 0) {
                index += 1;
                continue;
            }

            ans = ans + (long) Math.pow(10, index) * (10 - bit);
            index += 1;

            int temp = index;
            while (temp < list.size() && list.get(temp) == 9) {
                list.set(temp, 0);
                bitSum -= 9;
                temp += 1;
            }

            if (temp == list.size()) {
                list.add(1);
            }
            else {
                list.set(temp, list.get(temp) + 1);
            }
            bitSum = bitSum - bit + 1;
        }

        return ans;
    }


    private Map<TreeNode, Integer> map = new HashMap<>();
    private int[] res;
    public int[] treeQueries(TreeNode root, int[] queries) {
        dfs(root);
        map.put(null, 0);
        
        res = new int[map.size() + 1];
        dfs2(root, -1, 0);

        for (int i = 0; i < queries.length; ++i) {
            queries[i] = res[queries[i]];
        }

        return queries;
    }


    /**
     * 第一次DFS: 获取每棵节点的高度
     * @param node
     * @return
     */
    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int height =  1 + Math.max(dfs(node.left), dfs(node.right));
        map.put(node, height);

        return height;
    }


    /**
     * 第二次DFS: 删除以当前节点为根的子树后, 剩余子树的最大高度
     * @param node: 当前节点
     * @param height: 当前节点高度
     * @param residue: 去除以当前节点为根的子树的高度
     */
    private void dfs2(TreeNode node, int height, int residue) {
        if (node == null) {
            return;
        }

        height += 1;
        res[node.val] = residue;

        dfs2(node.left, height, Math.max(residue, height + map.get(node.right)));
        dfs2(node.right, height, Math.max(residue, height + map.get(node.left)));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
