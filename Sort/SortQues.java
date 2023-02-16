package Sort;

import java.util.Random;

public class SortQues {
    
    Random random = new Random();

    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, k);
    }


    public int quickSelect(int[] arr, int left, int right, int k) {
        int q = randomPartition(arr, left, right);
        if (q == k - 1) {
            return arr[q];
        }

        else {
            return q < k - 1 ? quickSelect(arr, q + 1, right, k) : quickSelect(arr, left, q - 1, k);
        }
    }

    public int randomPartition(int[] arr, int left, int right) {
        int mid = random.nextInt(right - left + 1) + left;
        swap(arr, mid, left);
        return partitionSort(arr, left, right);
    }


    public int partitionSort(int[] arr, int left, int right) {
        int select = arr[left];

        int i = left, j = right;
        while (i < j) {
            while (i < j && arr[j] < select) {
                j -= 1;
            }

            if (i < j) {
                arr[i] = arr[j];
                i += 1;
            }

            while (i < j && arr[i] >= select) {
                i += 1;
            }

            if (i < j) {
                arr[j] = arr[i];
                j -= 1;
            }
        }

        arr[i] = select;

        return i;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
