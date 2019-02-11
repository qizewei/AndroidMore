/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore;

import static com.example.god.androidmore.algorithm.other.Other.multiplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    multiplication("789654","789654");

//    L型骨牌问题
//    ChessBoardQuestion(4);

//    球队比赛排列问题
//    TreamQuestion(8);

//    八皇后问题
//    queueQuestion();

//    贪心背包问题
//    int MAX_WEIGHT = 150;
//    int[] weight = {35, 30, 60, 50, 40, 10, 25};
//    int[] value = {10, 40, 30, 50, 35, 40, 30};
//    packageQuestion(MAX_WEIGHT, weight, value);

//    int[] src = {99, 2323, 9, 77, 44, 2, 87, 21, 32, 54};
//    heapSort(src);
//    logOut("堆排序", src);

//    bubbleSort(src);
//    logOut("冒泡排序", src);
//
//    quickSort(src, 0, src.length - 1);
//    logOut("快速排序", src);
//
//    selsctionSort(src);
//    logOut("选择排序", src);

//    insertionSort(src);
//    logOut("插入排序", src);

//    shellSort(src, src.length);
//    logOut("希尔排序", src);

//    int i = sequenceSearch(src, 123);
//    logOut("线性查找", src[i]);

//    int searchValue = 0;
//    int i = binarySearch(src, searchValue, 0, src.length-1);
//    logOut("二分查找", i, searchValue);

//    汉诺塔问题
//    hanoi(3, "A", "B", "C");

//    Log.d(TAG, "onCreate: 最大公约数为" + maxDivisor(9, 4));

//    泊松分酒
//    putDrive(12, 0, 0, 6, 12, 8, 5);
//
//    mergeSort(src,0,src.length-1);
//    logOut("归并排序", src);

//    redaixSort(src);
//    logOut("基数排序", src);

//    约瑟夫问题
//    josephus(12,4);

//    mDP("android","ahiroi");
  }

  private void logOut(String method, int[] src) {
    for (int i = 0; i < src.length; i++) {
      Log.d(TAG, method + src[i]);
    }
  }

  private void logOut(String method, int src, int value) {
    Log.d(TAG, method + value + "的位置在数组的第" + src + "前");
  }
}
