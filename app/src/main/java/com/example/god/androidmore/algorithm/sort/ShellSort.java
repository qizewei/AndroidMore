/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

/**
 * 希尔排序
 * 定义间距，每隔一间距取数组成数组比较，减小间距后依次循环
 */
public class ShellSort {

  public static void shellSort(int[] src, int x) {
    int l = src.length;
    if (x == 1) {
      return;
    }
    x = x >>> 1;

    for (int i = 0; i < x; i++) {
      for (int j = i + x; j < l; j = j + x) {
        int m = src[j];
        int n;
        for (n = (j - x); n >= 0 && m < src[n]; n = n - x) {
          src[n + x] = src[n];
        }
        src[n + x] = m;
      }
    }

    shellSort(src, x);
  }
}
