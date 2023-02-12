package CodeCatalog.BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import CodeCatalog.BinaryTree.Traversal.TreeNode;

/**
 * 二叉搜索树
 * 左子树 < 根节点
 * 右子树 > 根节点
 * 中序遍历为递增序列
 */
public class BinarySearchTree {

    /**
     * LeeCode 700 二叉搜索树中的搜索
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (root.val < val) {
            return searchBST(root.right, val);
        }
        else if (root.val > val) {
            return searchBST(root.left, val);
        }
        else {
            return root;
        }
    }

    public TreeNode searchBSTIterative(TreeNode root, int val) {
        while (root != null) {
            if (root.val < val)
                root = root.right;
            else if (root.val > val)
                root = root.left;
            else
                return root;
        }

        return null;
    }


    /**
     * LeeCode 98 验证二叉搜索树
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();

        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            inorder.add(node.val);
            node = node.right;
        }

        // 判断中序遍历是否为升序序列
        for (int i = 0; i < inorder.size() - 1; i++) {
            if (inorder.get(i) >= inorder.get(i + 1)) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidBSTRecursive(TreeNode root) {
        return isValidBSTRecursiveImpl(root, Long.MAX_VALUE, Long.MIN_VALUE);
    }

    public boolean isValidBSTRecursiveImpl(TreeNode root, long max, long min) {
        if (root == null) {
            return true;
        }

        if (root.val <= min || root.val >= max) {
            return false;
        }

        return isValidBSTRecursiveImpl(root.left, root.val, min) && isValidBSTRecursiveImpl(root.right, max, root.val);
    }


    /**
     * LeeCode 530 二叉搜索树的最小绝对差
     * @param root
     * @return
     */
    public int getMinimumDifference(TreeNode root) {
        int max = 2 * (int)Math.pow(10, 5);
        int min = -(int)Math.pow(10, 5);
        return getMinimumDifferenceImpl(root, max, min);
    }

    public int getMinimumDifferenceImpl(TreeNode root, int max, int min) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }

        int cur = Math.min(Math.abs(root.val - max), Math.abs(root.val - min));
        return Math.min(Math.min(getMinimumDifferenceImpl(root.left, root.val, min), getMinimumDifferenceImpl(root.right, max, root.val)), cur);
    }


    /**
     * LeeCode 501 二叉搜索树中的众数
     * @param root
     * @return
     */
    public int[] findMode(TreeNode root) {
        // TODO 中序遍历得到升序序列, 计算众数 
        return null;
    }

    /**
     * LeeCode 235 二叉搜索树的最近公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode node = null;
        if (p.val < root.val && q.val < root.val) {
            node = lowestCommonAncestor(root.left, p, q);
        }
        else if(p.val > root.val && q.val > root.val) {
            node = lowestCommonAncestor(root.right, p, q);
        }
        else {
            node = root;
        }

        return node;
    }


    /**
     * LeeCode 701 二叉搜索树中的插入操作
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode insert = new TreeNode(val);
        if (root == null) {
            return insert;
        }

        TreeNode node = root;
        boolean flag = true;
        while (flag) {
            if (node.val < val) {
                if (node.right == null) {
                    node.right = insert;
                    flag = false;
                }
                else {
                    node = node.right;
                }
            }
            else {
                if (node.left == null) {
                    node.left = insert;
                    flag = false;
                }
                else {
                    node = node.left;
                }
            }
        }

        return root;
    }


    /**
     * LeeCode 450 删除二叉搜索树中的节点
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode virtualHead = new TreeNode((int)(Math.pow(10, 5) + 1));
        virtualHead.left = root;
        //virtualHead.right = root;

        TreeNode pre = virtualHead;
        TreeNode cur = root;

        while (cur != null) {
            if (cur.val > key) {
                pre = cur;
                cur = cur.left;
            }
            else if (cur.val < key) {
                pre = cur;
                cur = cur.right;
            }
            else {
                if (pre.val > key) {
                    if (cur.right != null && cur.left != null) {
                        pre.left = cur.right;
                        TreeNode node = cur.right;
                        while (node.left != null) {
                            node = node.left;
                        }
                        node.left = cur.left;
                    }
    
                    else if (cur.right != null) {
                        pre.left = cur.right;
                    }
    
                    else if (cur.left != null) {
                        pre.left = cur.left;
                    }
                    else {
                        pre.left = null;
                    }
                }
    
                if (pre.val < key) {
                    if (cur.right != null && cur.left != null) {
                        pre.right = cur.left;
                        TreeNode node = cur.left;
                        while (node.right != null) {
                            node = node.right;
                        }
                        node.right = cur.right;
                    }

                    else if (cur.right != null) {
                        pre.right = cur.right;
                    }
                    
                    else if (cur.left != null) {
                        pre.right = cur.left;
                    }
                    
                    else {
                        pre.right = null;
                    }
                }

                break;
            }
        }

        return virtualHead.left;
    }


    /**
     * LeeCode 669 修剪二叉搜索树
     * @param root
     * @param low
     * @param high
     * @return
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return root;
        }

        if (root.val < low) {
            return trimBST(root.right, low, high);
        }

        if (root.val > low) {
            return trimBST(root.left, low, high);
        }

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        return root;
    }


    /**
     * LeeCode 108 将有序数组转化为二叉搜索平衡树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTImpl(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBSTImpl(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = sortedArrayToBSTImpl(nums, left, mid - 1);
        node.right = sortedArrayToBSTImpl(nums, mid + 1, right);

        return node;
    }
}
