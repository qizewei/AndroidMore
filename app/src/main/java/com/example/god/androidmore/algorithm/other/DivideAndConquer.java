/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

public class DivideAndConquer {

  public static void treamQuestion(int num) {
    int[][] datas = new int[num][num];
    EightTream(datas, datas.length);
    printResult(datas);
  }

  public static void printResult(int datas[][]) {
    for (int i = 0; i < datas.length; i++) {
      for (int j = 0; j < datas.length; j++) {
        System.out.print(datas[i][j]);
      }
      System.out.print("\n");
    }
  }

  public static void EightTream(int[][] datas, int num) {
    if (num == 1) {
      datas[0][0] = 1;
    } else {
      int m = num / 2;
      EightTream(datas, m);

      for (int i = 0; i < m; i++) {
        for (int j = m; j < num; j++) {
          datas[i][j] = datas[i][j - m] + m;
        }
      }

      for (int i = m; i < num; i++) {
        for (int j = 0; j < m; j++) {
          datas[i][j] = datas[i - m][j] + m;
        }
      }

      for (int i = m; i < num; i++) {
        for (int j = m; j < num; j++) {
          datas[i][j] = datas[i - m][j - m];
        }
      }
    }
  }

}
