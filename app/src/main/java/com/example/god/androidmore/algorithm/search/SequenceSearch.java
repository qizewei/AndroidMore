/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.search;

public class SequenceSearch {

  /**
   * 线性查找
   * 传入查找数值和数组，返回最接近查找值的数组下标
   * 返回-1的意义是：数组所有的值都比要查找的数值大
   */
  public static int sequenceSearch(int[] src, int value) {
    int result = -1;
    int min = value;
    for (int i = 0; i < src.length; i++) {
      int n = value - src[i];
      if (n == 0) {
        return i;
      } else if (n > 0 && n < min) {
        min = n;
        result = i;
      }
    }
    return result;
  }
}
