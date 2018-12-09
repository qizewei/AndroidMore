/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm;

/**
 * 冒泡排序
 * 第一个数从头到尾和数组的每个数比较，得出最值放入底部
 * 仍从头开始，依次循环
 */
public class BubbleSort {

  public static int[] BubbleSort(int[] src) {
    for (int i = 0; i < src.length; i++) {
      for (int j = 0; j < (src.length - i) - 1; j++) {
        if (src[j] > src[j + 1]) {
          replaceNum(src, j, j + 1);
        }
      }
    }
    return src;
  }

  public static void replaceNum(int[] src, int star, int end) {
    int x = src[star];
    src[star] = src[end];
    src[end] = x;
  }

}
