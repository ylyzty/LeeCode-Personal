package CodeCatalog.BinaryTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Traversal {
    /**
     * 二叉树定义
     */
    static class TreeNode {
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

    /**
     * N叉树定义
     */
    static class NTreeNode {
        int val;
        List<NTreeNode> children;

        NTreeNode() {}
        NTreeNode(int val) { this.val = val; }
        NTreeNode(int val, List<NTreeNode> children) {
            this.val = val;
            this.children = children;
        }
    }

    /**
     * 递归法 先序遍历 二叉树
     * @param root 二叉树根节点
     * @return 返回遍历结果
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        preorderImpl(root, res);

        return res;
    }

    public void preorderImpl(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        res.add(root.val);
        preorderImpl(root.left, res);
        preorderImpl(root.right, res);
        return;
    }


    /**
     * 递归法 中序遍历 二叉树
     * @param root 二叉树根节点
     * @return 返回遍历结果
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        inorderImpl(root, res);
        
        return res;
    }

    public void inorderImpl(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        inorderImpl(root.left, res);
        res.add(root.val);
        inorderImpl(root.right, res);
        return;
    }


    /**
     * 递归法 后序遍历 二叉树
     * @param root 二叉树根节点
     * @return 返回遍历结果
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        postorderImpl(root, res);

        return res;
    }

    public void postorderImpl(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        postorderImpl(root.left, res);
        postorderImpl(root.right, res);
        res.add(root.val);
        return;
    }




    /**
     * 迭代法 先序遍历 二叉树
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                res.add(node.val);
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            node = node.right;
        }

        return res;
    }

    /**
     * 迭代法 中序遍历 二叉树
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }

        return res;
    }



    /**
     * 迭代法 后序遍历 二叉树
     */
    public List<Integer> postorderTraversalIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();

            // 如果右子树为空, 或右子树已经访问过
            if (root.right == null || root.right == prev) {
                res.add(root.val);
                prev = root;
                root = null;
            }
            else {
                stack.push(root);
                root = root.right;
            }
        }

        return res;
    }

    /**
     * 迭代法 后序遍历 二叉树
     * 前序遍历：中左右
     * 后续遍历：左右中
     * 
     * 调整前序遍历左右节点的顺序, 使其顺序转为：中右左
     * 然后将结果逆序就能得到后序遍历的结果
     */
    public List<Integer> postorderTraversalIterativeBasedInorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                res.add(root.val);
                stack.push(root);
                root = root.right;
            }
            root = stack.pop();
            root = root.left;
        }

        Collections.reverse(res);
        return res;
    }


    /**
     * Morris 先序遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversalMorris(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root, pre = null;

        while (cur != null) {
            pre = cur.left;
            if (pre != null) {
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }
    
                // 将左子树的最右节点与当前节点关联
                // 遍历完该节点后, 则取消关联, 遍历右子树
                if (pre.right == null) {
                    pre.right = cur;
                    res.add(cur.val);
                    cur = cur.left;
                    continue;
                }
                else {
                    pre.right = null;
                }
            }
            else {
                res.add(cur.val);
            }

            cur = cur.right;
        }

        return res;
    }

    /**
     * Morris 中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversalMorris(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root, pre = null;
        while (cur != null) {
            if (cur.left == null) {
                res.add(cur.val);
                cur = cur.right;
                continue;
            }

            pre = cur.left;
            while (pre.right != null && pre.right != cur) {
                pre = pre.right;
            }

            // 
            if (pre.right == null) {
                pre.right = cur;
                cur = cur.left;
            }
            else {
                pre.right = null;
                res.add(cur.val);
                cur = cur.right;
            }
        }

        return res;
    }


    /**
     * 层序遍历 二叉树
     * 队列
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);

        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> temp = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                temp.add(node.val);

                // 添加左右子节点
                if (node.left != null) {
                    deque.offer(node.left);
                }

                if (node.right != null) {
                    deque.offer(node.right);
                }
            }

            res.add(new ArrayList<>(temp));
        }

        return res;
    }


    /**
     * LeeCode 106 从中序和后序遍历序列构造二叉树
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTreeImpl(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    public TreeNode buildTreeImpl(int[] inorder, int inStart, int inEnd,
                                    int[] postorder, int postStart, int postEnd) {
        if (inStart >= inEnd) {
            return null;
        }

        int rootVal = postorder[postEnd - 1];
        TreeNode root = new TreeNode(rootVal);

        int rootInorderIndex = 0;
        for (int i = inStart; i < inEnd; i++) {
            if (inorder[i] == rootVal) {
                rootInorderIndex = i;
                break;
            }
        }

        int leftTreeSize = rootInorderIndex - inStart;
        root.left = buildTreeImpl(inorder, inStart, rootInorderIndex, postorder, postStart, postStart + leftTreeSize);
        root.right = buildTreeImpl(inorder, rootInorderIndex + 1, inEnd, postorder, postStart + leftTreeSize, postEnd - 1);

        return root;
    }


    /**
     * LeeCode 654 构建最大二叉树
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTreeImpl(nums, 0, nums.length);
    }

    public TreeNode constructMaximumBinaryTreeImpl(int[] nums, int start, int end) {
        if (start >= end) {
            return null;
        }

        // 构造根节点
        int rootVal = Integer.MIN_VALUE;
        int index = 0;
        for (int i = start; i < end; i++) {
            if (nums[i] > rootVal) {
                rootVal = nums[i];
                index = i;
            }
        }

        TreeNode root = new TreeNode(rootVal);
        root.left = constructMaximumBinaryTreeImpl(nums, start, index);
        root.right = constructMaximumBinaryTreeImpl(nums, index + 1, end);

        return root;
    }

    /**
     * N叉树的前序遍历
     */
    public List<Integer> preorderTraversalNTree(NTreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<NTreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            NTreeNode node = stack.pop();
            res.add(node.val);
            if (node.children != null) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    stack.push(node.children.get(i));
                }
            }
        }

        return res;
    }
    
    /**
     * 测试代码
     * @param args
     */
    public static void main(String[] args) {
        Traversal test = new Traversal();
        test.inorderTraversal(null);
    }
}
