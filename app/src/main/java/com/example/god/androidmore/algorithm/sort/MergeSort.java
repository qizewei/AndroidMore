/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

import static com.example.god.androidmore.algorithm.sort.InsertionSort.insertionSort;

/**
 * 归并排序：二路归并排序
 * 将数组分每两个元素分成一组，然后两两排序并合并
 */
public class MergeSort {

  public static void mergeSort(int[] src, int start, int end) {
    if (end-start == 1) {
      if(src[start]>src[end])
        replaceNum(src,start,end);
      return;
    }
    int m =(start+end)>>1;
    mergeSort(src, start, m);
    mergeSort(src, m , end);
    insertionSort(src);
  }

  public static void replaceNum(int[] src, int star, int end) {
    int x = src[star];
    src[star] = src[end];
    src[end] = x;
  }
}
