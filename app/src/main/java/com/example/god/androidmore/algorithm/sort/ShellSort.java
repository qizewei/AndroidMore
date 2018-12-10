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
    if (x == 1) {
      return;
    }
    x = x >>> 1;
    int i = 0;
    while (i + x < src.length) {

      if (src[i] > src[i + x]) {
        replaceNum(src, i, i + x);
      }

      i++;
    }
    shellSort(src, x);
  }

  public static void replaceNum(int[] src, int star, int end) {
    int x = src[star];
    src[star] = src[end];
    src[end] = x;
  }
}
