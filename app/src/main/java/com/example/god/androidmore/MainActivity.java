/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore;

import static com.example.god.androidmore.algorithm.BubbleSort.BubbleSort;
import static com.example.god.androidmore.algorithm.QuickSort.QuickSort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    int[] src = {99, 2323, 9, 77, 44, 2, 87, 21, 32, 54};

    logOut("冒泡排序", BubbleSort(src));

    QuickSort(src, 0, src.length - 1);
    logOut("快速排序", src);
  }

  private void logOut(String method, int[] src) {
    Log.d(TAG, "logOut: " + method);
    for (int i = 0; i < src.length; i++) {
      Log.d(TAG, "logOut: " + src[i]);
    }
  }
}
