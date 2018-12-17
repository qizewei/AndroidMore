/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

/**
 * 基数排序
 * 个位，十位，百位依次遍历进行排序
 */
public class redaixSort {

  public static void redaixSort(int[] src) {
    int x = 10;
    while (true) {
      int c = 0;
      for (int i = 0; i < src.length; i++) {
        for (int j = i; j > 0; j--) {
          int m = src[j] % x;
          int n = src[j - 1] % x;
          if (m < n) {
            replaceNum(src, j, j - 1);
          }
        }
        if (src[i] / x != 0) {
          c++;
        }
      }

      if (c == 0) {
        return;
      }

      x = x * 10;
    }
  }

  public static void replaceNum(int[] src, int star, int end) {
    int x = src[star];
    src[star] = src[end];
    src[end] = x;
  }

}
