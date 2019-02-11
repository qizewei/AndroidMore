/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

/**
 * 快速排序
 * 确定一个基数，将数组分成大小的两组，依次递归。不稳定排序
 */
public class QuickSort {

  public static void quickSort(int[] src, int star, int end) {

    if (star < end) {
      int index = oneSort(src, star, end);
      quickSort(src, star, index - 1);
      quickSort(src, index + 1, end);
    }
  }

  public static int oneSort(int[] datas, int start, int end) {
    int temp = datas[start];

    while (start < end) {
      while (start < end && datas[end] >= temp) {
        end--;
      }
      datas[start] = datas[end];
      while (start < end && datas[start] <= temp) {
        start++;
      }
      datas[end] = datas[start];
    }
    datas[start] = temp;
    return start;
  }
}
