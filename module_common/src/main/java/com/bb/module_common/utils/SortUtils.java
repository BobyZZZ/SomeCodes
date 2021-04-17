package com.bb.module_common.utils;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/31
 * Time: 13:10
 */
public class SortUtils {
    /**
     * 快速排序
     *
     * @param arr  需要排序的数组
     * @param low  开始位置
     * @param high 结束位置
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int index = swapAndGetIndex(arr, low, high);
            quickSort(arr, low, index - 1);
            quickSort(arr, index + 1, high);
        }
    }

    private static int swapAndGetIndex(int[] arr, int low, int high) {
        if (low < high) {
            int temp = arr[low];
            while (low < high) {
                while (low < high && arr[high] >= temp) {
                    high--;
                }
                if (low < high) {
                    arr[low++] = arr[high];
                }

                while (low < high && arr[low] < temp) {
                    low++;
                }
                if (low < high) {
                    arr[high--] = arr[low];
                }
            }
            arr[low] = temp;
        }
        return low;
    }

    /**
     * 冒泡排序
     * @param arr   目标数组
     */
    public static void bubbleSort(int[] arr) {
        int length = arr.length;
        if (length <= 1) {
            return;
        }
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * @param arr   目标数组
     */
    public static void selectSort(int[] arr) {
        int length = arr.length;
        if (length <= 1) {
            return;
        }
        for (int i = 0; i < length; i++) {
            int minIndex = i;
            for (int j = i; j < length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
