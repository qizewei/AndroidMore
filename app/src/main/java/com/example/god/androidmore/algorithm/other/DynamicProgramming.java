/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

import android.util.Log;

public class DynamicProgramming {

  private static final String TAG = "DynamicProgramming";

  /**
   * 最长公共子序列（Longest Common Subsequence,LCS）
   */
  public static void LCS(String strO, String strT) {
    char[] str1 = strO.toCharArray();
    char[] str2 = strT.toCharArray();
    int[][] res = new int[str1.length][str2.length];
    int count = 0;
    if (str1[0] == str2[0]) {
      count = count + 1;
      res[0][0] = 1;
    } else {
      res[0][0] = count;
    }

    for (int i = 0; i < str1.length; i++) {
      boolean hasAdd = false;
      if (str2.length > i) {
        for (int j = i; j < str2.length; j++) {
          if (str1[i] == str2[j] && !hasAdd) {
            count = count + 1;
            hasAdd = true;
          }
          res[i][j] = count;
        }
      }

      if (str2.length > i) {
        for (int m = i; m < str1.length; m++) {
          if (str1[m] == str2[i] && !hasAdd) {
            count = count + 1;
            hasAdd = true;
          }
          res[m][i] = count;
        }
      }


    }
    Log.d(TAG, "LCS: " + res[str1.length - 1][str2.length - 1]);

  }

  /**
   * 最长公共子串
   */
  public static void DP() {

  }
}
