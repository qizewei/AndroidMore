/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

import static com.example.god.androidmore.algorithm.sort.BubbleSort.replaceNum;

import android.util.Log;

/**
 * 贪心算法
 */
public class Greedy {

  private static final String TAG = "Greedy";

  /**
   * 背包问题
   */
  public static void packageQuestion(int max, int[] weights, int[] values) {

    for (int i = 0; i < weights.length; i++) {
      for (int j = 0; j < (weights.length - i) - 1; j++) {

        float jx = (float) values[j] / (float) weights[j];
        float jxx = (float) values[j + 1] / (float) weights[j + 1];
        if (jx > jxx) {
          replaceNum(weights, j, j + 1);
          replaceNum(values, j, j + 1);
        }
      }
    }

    int backageNum = 0;
    int allPrice = 0;

    for (int i = weights.length; i > 0; i--) {
      if (backageNum + weights[i - 1] <= max) {
        backageNum = backageNum + weights[i - 1];
        allPrice = allPrice + values[i - 1];
        Log.d(TAG, "packageQuestion: 装入重量" + weights[i - 1]);
      }
    }

    Log.d(TAG, "packageQuestion: 最大重量" + backageNum);
    Log.d(TAG, "packageQuestion: 最大价值" + allPrice);
  }
}
