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
   * 先求出第一行和第一列的值，然后根据 对应的二维数组的 性质求出剩余值。
   */
  public static void LCS(String strO, String strT) {
    char[] str1 = strO.toCharArray();
    char[] str2 = strT.toCharArray();
    int m = str1.length;
    int n = str2.length;
    int[][] res = new int[m][n];
    boolean hasAdd = false;
    int count = 0;
    for (int i = 0; i < m; i++) {
      if (str1[i] == str2[0] && !hasAdd) {
        count = count + 1;
        hasAdd = true;
      }
      res[i][0] = count;
    }

    hasAdd = false;
    count = 0;
    for (int i = 0; i < n; i++) {
      if (str1[0] == str2[i] && !hasAdd) {
        count = count + 1;
        hasAdd = true;
      }
      res[0][i] = count;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (str1[i] == str2[j]) {
          res[i][j] = res[i - 1][j - 1] + 1;
        } else {
          res[i][j] = Math.max(res[i][j - 1], res[i - 1][j]);
        }
      }
    }

    Log.d(TAG, "LCS: " + res[m - 1][n - 1]);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(res[i][j]);
      }
      System.out.print("\n");
    }
  }

  /**
   * 最长公共子串
   */
  public static void DP(String str1, String str2) {
    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    int m = chars1.length;
    int n = chars2.length;
    int[][] results = new int[m][n];

    for (int i = 0; i < m; i++) {
      if (chars1[i] == chars2[0]) {
        results[i][0] = 1;
      } else {
        results[i][0] = 0;
      }
    }

    for (int i = 0; i < n; i++) {
      if (chars2[i] == chars1[0]) {
        results[0][i] = 1;
      } else {
        results[0][i] = 0;
      }
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (chars1[i] == chars2[j]) {
          results[i][j] = results[i - 1][j - 1] + 1;
        } else {
          results[i][j] = 0;
        }
      }
    }

    int max = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (results[i][j] > max) {
          max = results[i][j];
        }
      }
    }

    Log.d(TAG, "DP: " + max);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(results[i][j]);
      }
      System.out.print("\n");
    }
  }
}
