package BinaryTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import BinaryTree.Traversal.TreeNode;

/**
 * 二叉树应用
 */
public class Usage {
    /**
     * 翻转二叉树, 左右子树相互交换
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        stack.push(node);

        while (!stack.isEmpty()) {
            node = stack.pop();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) {
                stack.push(node.left);
            }

            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return root;
    }


    /**
     * LeeCode 101 对称二叉树
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root.left);
        deque.offer(root.right);

        while (!deque.isEmpty()) {
            TreeNode node1 = deque.poll();
            TreeNode node2 = deque.poll();

            if (node1 == null && node2 == null) {
                continue;
            }

            else if(node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            // 外侧元素
            deque.offer(node1.left);
            deque.offer(node2.right);

            // 内侧元素
            deque.offer(node1.right);
            deque.offer(node2.left);
        }

        return true;
    }

    /**
     * 递归法 对称二叉树
     * @param node1
     * @param node2
     * @return
     */
    public boolean compare(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }

        else if (node1 == null || node2 == null || node1.val != node2.val) {
            return false;
        }

        return compare(node1.left, node2.right) && compare(node1.right, node2.left);
    }

    
    /**
     * 完全二叉树的节点个数
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = 0, right = 0;
        TreeNode node1 = root.left;
        TreeNode node2 = root.right;
        while (node1 != null) {
            left += 1;
            node1 = node1.left;
        }

        while (node2 != null) {
            right += 1;
            node2 = node2.right;
        }

        if (left == right) {
            return (1 << (left + 1)) - 1;
        }

        return countNodes(root.left) + countNodes(root.right) + 1;
    }


    /**
     * LeeCode 110 平衡二叉树
     * 自顶向下判断：重复计算多
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        return Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    /**
     * LeeCode 110 平衡二叉树
     * 自底向上判断: 先序遍历
     * @param root
     * @return
     */
    public int recur(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = recur(root.left);
        if (left == -1) {
            return -1;
        }

        int right = recur(root.right);
        if (right == -1) {
            return -1;
        }

        if (Math.abs(left - right) <= 1) {
            return Math.max(left, right) + 1;
        }

        return -1;
    }


    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        binaryTreePathsImpl(root, sb, res);
        return res;
    }

    public void binaryTreePathsImpl(TreeNode root, StringBuilder sb, List<String> res) {
        if (root.left == null && root.right == null) {
            sb.append(root.val);
            res.add(sb.toString());
            return;
        }

        if (root.left != null) {
            int size = sb.length();
            sb.append(root.val + "->");
            binaryTreePathsImpl(root.left, sb, res);
            sb.delete(size, sb.length());
        }

        

        if (root.right != null) {
            int size = sb.length();
            sb.append(root.val + "->");
            binaryTreePathsImpl(root.right, sb, res);
            sb.delete(size, sb.length());
        }

        return;
    }


    /**
     * LeeCode 404 左叶子之和
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        return getSum(root, false);
    }

    public int getSum(TreeNode root, boolean flag) {
        if (root == null) {
            return 0;
        }

        if (flag && root.left == null && root.right == null) {
            return root.val;
        }

        return getSum(root.left, true) + getSum(root.right, false);
    }


    /**
     * LeeCode 513 找树左下角的值
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int res = 0;

        while (!deque.isEmpty()) {
            res = deque.peekFirst().val;
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                if (node.left != null) {
                    deque.offer(node.left);
                }

                if (node.right != null) {
                    deque.offer(node.right);
                }
            }
        }

        return res;
    }


    /**
     * LeeCode 112 路径总和
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        return hasPathSumImpl(root, targetSum);
    }

    public boolean hasPathSumImpl(TreeNode root, int target) {
        if (root.left == null && root.right == null && root.val == target) {
            return true;
        }

        if(root.left == null && root.right == null) {
            return false;
        }

        if (root.left != null) {
            target = target - root.val;
            if (hasPathSumImpl(root.left, target)) {
                return true;
            }
            target = target + root.val;
        }

        if (root.right != null) {
            target = target - root.val;
            if (hasPathSumImpl(root.right, target)) {
                return true;
            }
            target = target + root.val;
        }

        return false;
    }


    /**
     * LeeCode 113 路径总和II
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        List<Integer> temp = new ArrayList<>();
        pathSumImpl(root, targetSum, temp, res);

        return res;
    }

    public void pathSumImpl(TreeNode root, int target, List<Integer> temp, List<List<Integer>> res) {
        if (root.left == null && root.right == null) {
            if (target == root.val) {
                temp.add(root.val);
                res.add(new ArrayList<>(temp));
                temp.remove(temp.size() - 1);
            }
            return;
        }

        if (root.left != null) {
            temp.add(root.val);
            pathSumImpl(root.left, target - root.val, temp, res);
            temp.remove(temp.size() - 1);
        }

        if (root.right != null) {
            temp.add(root.val);
            pathSumImpl(root.right, target - root.val, temp, res);
            temp.remove(temp.size() - 1);
        }

        return;
    }



    /**
     * LeeCode 617 合并二叉树
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }

        TreeNode root = new TreeNode(root1.val + root2.val);
        root.left = mergeTrees(root1.left, root2.left);
        root.right = mergeTrees(root1.right, root2.right);
        return root;
    }


    /**
     * LeeCode 236 二叉树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        boolean flag = true;
        while (flag) {
            if (isChild(node.left, p) && isChild(node.left, q)) {
                node = node.left;
            }
            else if (isChild(node.right, p) && isChild(node.right, q)) {
                node = node.right;
            }
            else {
                flag = false;
            }
        }

        return node;
    }

    public boolean isChild(TreeNode root, TreeNode node) {
        if (root == null) {
            return false;
        }

        if (root == node) {
            return true;
        }

        return isChild(root.left, node) || isChild(root.right, node);
    }


    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        return root;
    }


    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);
        node1.left = node2;
        node1.right = node3;

        Usage usage = new Usage();
        System.out.println(usage.findBottomLeftValue(node1));
    }
}
