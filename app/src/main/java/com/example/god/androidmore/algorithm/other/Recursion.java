/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

import android.util.Log;

/**
 * 递归算法
 */
public class Recursion {

  private static final String TAG = "Recursion";

  /**
   * 汉诺塔问题
   */
  public static void Hanoi(int count, String from, String mid, String to) {
    if (count == 1) {
      Log.d(TAG, "Hanoi: 1 " + from + "到" + to);
    } else {
      Hanoi(count - 1, from, to, mid);
      Log.d(TAG, "Hanoi: " + count + " " + from + "到" + to);
      Hanoi(count - 1, mid, from, to);
    }

  }

  /**
   * 最大公约数
   */
  public static int MaxDivisor(int big, int small) {

    if (small == 0) {
      return big;
    }
    return MaxDivisor(small, big % small);
  }
}
