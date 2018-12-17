/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.search;

/**
 * 二分查找
 */
public class BinarySearch {

  public static int binarySearch(int[] src, int value, int left, int right) {
    int result = -1;
    int mid = (right + left) >> 1;

    if (mid == left || mid == right || value == src[mid]) {
      if (value <= src[left] && value <= src[right]) {
        return left;
      } else if (value <= src[right] && value >= src[left]) {
        return right;
      } else {
        return right + 1;
      }
    }

    if (value > src[mid]) {
      result = binarySearch(src, value, mid + 1, right);
    }
    if (value < src[mid]) {
      result = binarySearch(src, value, left, mid - 1);
    }
    return result;
  }

}
