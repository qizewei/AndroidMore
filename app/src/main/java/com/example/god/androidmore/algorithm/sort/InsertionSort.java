/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

/**
 * 插入排序
 * 遍历元素，将遍历的元素插入前面已经排好序的数组
 */
public class InsertionSort {

  public static void insertionSort(int[] src) {
    for (int i = 0; i < src.length; i++) {
      for (int j = 0; j < i; j++) {
        if (src[i] <= src[j]) {
          int m = src[i];
          System.arraycopy(src, j, src, j + 1, i - j);
          src[j] = m;
          break;
        }
      }
    }
  }
}
