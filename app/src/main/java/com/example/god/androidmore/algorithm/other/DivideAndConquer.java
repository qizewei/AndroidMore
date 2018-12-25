/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

public class DivideAndConquer {

  /**
   * 球队比赛排列问题
   * @param num
   */
  public static void TreamQuestion(int num) {
    int[][] datas = new int[num][num];
    EightTream(datas, datas.length);
    PrintResult(datas);
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

  // L型骨牌问题

  static int type = 0;

  public static void ChessBoardQuestion(int num) {
    int[][] datas = new int[num][num];
    ChessBoard(1, 0, 0, 0, datas.length, datas);
    PrintResult(datas);
  }

  public static void ChessBoard(int spcR, int spcC, int starR, int starC, int num, int[][] data) {

    if (num != 1) {
      int m = num / 2;
      type = type % 9 + 1;
      int n = type;

      //左上角
      if (spcR < starR + m && spcC < starC + m) {
        ChessBoard(spcR, spcC, starR, starC, m, data);
      } else {
        data[starR + m - 1][starC + m - 1] = n;
        ChessBoard(starR + m - 1, starC + m - 1, starR, starC, m, data);
      }

      //左下角
      if (spcR < starR + m && spcC >= starC + m) {
        ChessBoard(spcR, spcC, starR, starC + m, m, data);
      } else {
        data[starR + m - 1][starC + m] = n;
        ChessBoard(starR + m - 1, starC + m, starR, starC + m, m, data);
      }

      //右上角
      if (spcR >= starR + m && spcC < starC + m) {
        ChessBoard(spcR, spcC, starR + m, starC, m, data);
      } else {
        data[starR + m][starC + m - 1] = n;
        ChessBoard(starR + m, starC + m - 1, starR + m, starC, m, data);
      }

      //右下角
      if (spcR >= starR + m && spcC >= starC + m) {
        ChessBoard(spcR, spcC, starR + m, starC + m, m, data);
      } else {
        data[starR + m][starC + m] = n;
        ChessBoard(starR + m, starC + m, starR + m, starC + m, m, data);
      }
    }
  }

  /**
   * 打印结果
   * @param datas
   */
  public static void PrintResult(int datas[][]) {
    for (int i = 0; i < datas.length; i++) {
      for (int j = 0; j < datas.length; j++) {
        System.out.print(datas[i][j]);
      }
      System.out.print("\n");
    }
  }
}
