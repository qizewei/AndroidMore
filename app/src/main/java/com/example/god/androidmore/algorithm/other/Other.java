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
import android.widget.Toast;

public class Other {

  private static final String TAG = "Other";

  /**
   * 约瑟夫问题
   * 创建头尾相连的链表解决问题
   */
  public static void josephus(int total, int count) {
    class Node {

      private int n;
      private Node next;

      private Node(int n) {
        this.n = n;
      }
    }

    Node header = new Node(1);
    Node x = header;
    for (int i = 2; i <= total; i++) {
      x.next = new Node(i);
      x = x.next;
    }
    x.next = header;

    Node m = header;
    int p = 1;
    while (m.n != m.next.n) {
      if (p == count - 1) {
        Log.d(TAG, "josephus: 枪毙" + m.next.n);
        m.next = m.next.next;
        p = 0;
      }
      m = m.next;
      p = p + 1;
    }
    Log.d(TAG, "josephus: 剩余" + m.n);
  }

  /**
   * 大数相乘算法
   */
  public static void multiplication(String str, String str2) {

    char[] intOne = str.toCharArray();
    char[] intTwo = str2.toCharArray();
    int oneLength = intOne.length;
    int twoLength = intTwo.length;
    int[] results = new int[oneLength + twoLength + 3];

    trun(intOne);
    trun(intTwo);
    calculation(results, intOne, intTwo, oneLength, twoLength);
    carry(results);
    String result = getResult(results);
    System.out.print("结果："+ result);
  }

  //去0，翻转，输出
  private static String getResult(int[] results) {
    int m = results.length;
    for (int i = results.length - 1; i > 0; i--) {
      if (results[i] != 0) {
        m = i;
        break;
      }
    }

    //得到结果
    StringBuilder sResult = new StringBuilder();
    for (int i = m; i >= 0; i--) {
      sResult.append(String.valueOf(results[i]));
    }
    return sResult.toString();
  }

  //进位
  private static void carry(int[] results) {
    for (int i = 0; i < results.length - 1; i++) {
      int m = results[i] % 10;
      int n = results[i] / 10;
      if (results[i] > 10) {
        results[i + 1] = results[i + 1] + n;
      }
      results[i] = m;
    }
  }

  //计算
  private static void calculation(int[] results, char[] intOne, char[] intTwo, int oneLength,
      int twoLength) {
    for (int i = 0; i < results.length; i++) {
      results[i] = 0;
    }

    for (int i = 0; i < oneLength; i++) {
      for (int j = 0; j < twoLength; j++) {
        results[i + j] = results[i + j] + Integer.parseInt(String.valueOf(intOne[i])) * Integer
            .parseInt(String.valueOf(intTwo[j]));
      }
    }
  }

  //翻转int
  private static void trun(char[] data) {
    int m = data.length / 2;
    for (int i = 0; i < m; i++) {
      replaceNum(data, i, data.length - 1 - i);
    }
  }

  private static void replaceNum(char[] src, int star, int end) {
    char x = src[star];
    src[star] = src[end];
    src[end] = x;
  }
}
