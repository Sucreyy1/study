package sort;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 排序相关
 */
public class Sort {

    public static void main(String[] args) {

    }


    /**
     * 冒泡排序
     *
     * @param a
     * @return
     */
    public int[] bubble(int[] a) {
        int length = a.length;
        int temp;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }

    /**
     * 简单选择排序
     * @param a
     * @return
     */
    public int[] select(int[] a) {
        int length = a.length;
        for (int i = 0; i < length; i++) {
            int value = a[i];
            int position = i;
            for (int j = i + 1; j < length ; j++) {
                if (a[j] < value) {
                    value = a[j];
                    position = j;
                }
            }
            a[position] = a[i];
            a[i] = value;
        }
        return a;
    }

}
