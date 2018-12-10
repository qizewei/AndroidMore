/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

/**
 * 选择排序
 * 数组中找最小的（和第一位比较），然后在剩下的数据中找最小的放第二位，以此类推
 */
public class SelectionSort {

  public static void selsctionSort(int[] src) {
    for (int i = 0; i < src.length; i++) {
      int min = i;
      for (int j = i; j < src.length; j++) {
        if (src[min] > src[j]) {
          min = j;
        }
      }
      replaceNum(src, min, i);
    }
  }

  public static void replaceNum(int[] src, int star, int end) {
    int x = src[star];
    src[star] = src[end];
    src[end] = x;
  }
}
