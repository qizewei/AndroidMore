/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

public class BackTracking {

  /**
   * 八皇后问题_result = 92
   * 先获取单列内可放置的集合，递归遍历所有可能的
   */
  private static int MAX_QUEEN = 8;
  private static int num = 0;
  private static int[] allResult = new int[MAX_QUEEN];

  public static void queueQuestion() {
    getLineQueens(0);
  }

  public static void getLineQueens(int lineCount) {
    boolean[] lineResult = new boolean[MAX_QUEEN];
    for (int j = 0; j < lineCount; j++) {
      lineResult[allResult[j]] = true;
      int x = lineCount - j;
      if (allResult[j] - x >= 0) {
        lineResult[allResult[j] - x] = true;
      }
      if (allResult[j] + x < MAX_QUEEN) {
        lineResult[allResult[j] + x] = true;
      }
    }

    for (int i = 0; i < MAX_QUEEN; i++) {
      if (!lineResult[i]) {
        allResult[lineCount] = i;
        if (lineCount < MAX_QUEEN - 1) {
          getLineQueens(lineCount + 1);
        } else {
          num++;
          PrintQueen(num, allResult);
        }
      }
    }
  }

  /**
   * 打印结果
   */
  static void PrintQueen(int num, int[] result) {
    char[][] results = new char[MAX_QUEEN][MAX_QUEEN];
    System.out.print("第" + num + "种排列:\n");
    for (int i = 0; i < result.length; i++) {
      results[i][result[i]] = 'X';
    }

    for (int i = 0; i < MAX_QUEEN; i++) {
      for (int j = 0; j < MAX_QUEEN; j++) {
        if (results[i][j] == 'X') {
          System.out.print("X");
        } else {
          System.out.print("0");
        }
      }
      System.out.print("\n");
    }
  }
}
