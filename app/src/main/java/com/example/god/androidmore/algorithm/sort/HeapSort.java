/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.sort;

import static com.example.god.androidmore.algorithm.sort.BubbleSort.replaceNum;

/**
 * 堆排序
 * 对数组进行堆的构建（使用递归），然后从构建好的大堆中依次拿出数据。
 */
public class HeapSort {

  public static void heapSort(int[] datas) {

    buildMaxHeap(datas);
    for (int i = datas.length - 1; i >= 1; i--) {
      replaceNum(datas, 0, i);
      maxHeap(datas, i, 0);
    }
  }

  //从树的底部向上遍历
  private static void buildMaxHeap(int[] datas) {
    int m = (datas.length - 1) / 2;
    for (int i = m; i >= 1; i--) {
      maxHeap(datas, m, 0);
    }
  }

  //构建每个大堆，递归构建该大堆下的所有节点
  private static void maxHeap(int[] datas, int length, int x) {
    int left = 2 * x + 1;
    int right = 2 * x + 2;
    int lagest = x;
    if (left < length && datas[left] > datas[x]) {
      replaceNum(datas, left, x);
      lagest = left;
      maxHeap(datas, length, lagest);
    }

    if (right < length && datas[right] > datas[x]) {
      replaceNum(datas, right, x);
      maxHeap(datas, length, lagest);
    }

  }
}
