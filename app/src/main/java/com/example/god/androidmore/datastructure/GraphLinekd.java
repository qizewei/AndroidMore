/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

import static com.example.god.androidmore.datastructure.HashMap.hash;

import android.util.Log;

/**
 * 图中的元素称为顶点，顶点之间的连线叫做边，边分为无向边和有向边，有向边也称为弧，弧有弧头和弧尾。
 * 弧有与之对应的数字，成为权。
 * 任意两点之间是联通的，称为连通图。
 * 图中一个顶点可以连接无数个顶点
 * 无向图定点的边数叫度，有向图顶点的边数叫出度和入度。
 * 图的实现：1. 邻接矩阵（一个一维数组表示顶点信息，一个二维数组存储弧的信息）。
 * 图的实现：2.  邻接表（散链列表）
 *
 * 图的邻接表的实现
 */
public class GraphLinekd<K, T> {

  GNode[] table;
  int length;
  private static final String TAG = "GraphLinekd";

  /**
   * 构建一个table上的节点，该节点所连接的点 即为 该顶点在图中锁链的所有顶点
   */
  private GNode BuildNode(GNode<K, T> src, GNode<K, T>... linkedNode) {
    GNode<K, T> x = src;
    for (int i = 0; i < linkedNode.length; i++) {
      x.point = linkedNode[i];
      x = linkedNode[i];
    }
    x.point = null;
    return src;
  }

  private void addNode(GNode newNode) {
    GNode gNode = table[hash(newNode.k)];
    if (gNode != null) {
      Log.d(TAG, "addNode: 该节点已经存在");
      return;
    }
    gNode = newNode;
    length++;
  }

  private void removeNode(GNode oldNode) {
    GNode oldX = oldNode;
    while (oldX.point != null) {
      GNode<K, T> x = table[hash(oldX.point.k)].point;
      GNode<K, T> before = table[hash(oldX.point.k)];
      while (x != null) {
        if (x.k == oldX.point.k) {
          before.point = x.point;
          break;
        }
        before = x;
        x = x.point;
      }
      oldX = oldX.point;
    }
    table[hash(oldNode.k)] = null;
    length--;
  }

  private GNode getGNode(K k) {
    return table[hash(k)];
  }


  private static class GNode<K, T> {

    GNode<K, T> point;
    int weight;
    T v;
    K k;

    public GNode(GNode<K, T> point, int weight, T v) {
      this.point = point;
      this.weight = weight;
      this.v = v;
    }

    public GNode<K, T> getPoint() {
      return point;
    }

    public void setPoint(GNode<K, T> point) {
      this.point = point;
    }

    public int getWeight() {
      return weight;
    }

    public void setWeight(int weight) {
      this.weight = weight;
    }

    public T getV() {
      return v;
    }

    public void setV(T v) {
      this.v = v;
    }
  }
}
