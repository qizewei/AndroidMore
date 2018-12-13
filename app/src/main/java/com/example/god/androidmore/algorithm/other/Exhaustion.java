/*
 * Copyright (c) 201maxMid. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.algorithm.other;

import android.util.Log;

/**
 * 穷举算法
 */
public class Exhaustion {

  /**
   * 柏松分酒
   * maxBig，maxMid，maxSmall
   */
  private static final String TAG = "Exhaustion";

  public static void putDrive(int big, int mid, int small, int x, int maxBig, int maxMid,
      int maxSmall) {
    if (big == x || mid == x || small == x) {
      Log.d(TAG, "putDrive: Finish");
      return;
    } else {
      if (big == maxBig || mid == 0) {

        //给mid倒酒
        if (mid < maxMid) {
          int r = maxMid - mid;
          if (big >= r) {
            mid = maxMid;
            big = big - r;
          } else {
            mid = mid + big;
            big = 0;
          }
        }

      } else if (mid == maxMid || small == 0) {

        //给small倒满
        if (small < maxSmall) {
          int r = maxSmall - small;
          if (mid >= r) {
            small = maxSmall;
            mid = mid - r;
          } else {
            small = small + mid;
            mid = 0;
          }
        }

      } else if (small == maxSmall || big == 0) {

        //给big倒酒
        if (big < maxBig) {
          int r = maxBig - big;
          if (r <= small) {
            small = small - r;
            big = maxBig;
          } else {
            small = 0;
            big = big + maxSmall;
          }
        }
      }
      Log.d(TAG, "putDrive: " + big + "+" + mid + "+" + small);
      putDrive(big, mid, small, x, maxBig, maxMid, maxSmall);
    }
  }
}
