package Others;

/**
 * 1. 排序算法的稳定性
 * 
 * 假设在数组中存在 nums[i] == nums[j]
 * 若排序之前nums[i]在nums[j]前面;并且排序之后nums[i]依然在nums[j]前面, 则该排序算法是稳定的
 */
public class SortAlgorithms {

    /**
     * 冒泡排序
     * @param nums
     */
    public void bubbleSort(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {

            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    flag = true;
                }
            }

            // 此时数组元素已经有序
            if (!flag) {
                return;
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * 快速排序
     * @param nums
     * @return
     */
    public void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int temp = nums[left];
        int L = left;
        int R = right;
        while (left < right) {
            while (left < right && nums[right] > temp) {
                right -= 1;
            }

            if (left < right) {
                nums[left] = nums[right];
                left += 1;
            }

            while (left < right && nums[left] <= temp) {
                left += 1;
            }

            if (left < right) {
                nums[right] = nums[left];
                right -= 1;
            }
        }

        nums[left] = temp;
        quickSort(nums, L, left - 1);
        quickSort(nums, left + 1, R);
    }


    /**
     * 归并排序
     * @param nums
     */
    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    public void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int index = 0;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[index++] = nums[i++];
            }
            else {
                temp[index++] = nums[j++];
            }
        }

        while (i <= mid) {
            temp[index++] = nums[i++];
        }
        while (j <= right) {
            temp[index++] = nums[j++];
        }

        for (int k = 0; k < index; k++) {
            nums[k + left] = temp[k];
        }
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 2, 4, 6, 5, 9, 8, 7};

        SortAlgorithms test = new SortAlgorithms();
        // test.bubbleSort(nums);
        // test.quickSort(nums, 0, nums.length - 1);
        test.mergeSort(nums, 0, nums.length - 1);

        // print
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}