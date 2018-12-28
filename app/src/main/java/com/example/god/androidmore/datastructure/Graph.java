/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

//图-临接矩阵实现
public class Graph {

  private int length;
  private int[] labels;
  private int[][] tables;
  private boolean[] isVisited;
  static int MAX = 8000;

  public Graph(int length) {
    this.length = length;
    labels = new int[length];
    tables = new int[length][length];
    isVisited = new boolean[length];

    for (int i = 0; i < length; i++) {
      labels[i] = i;
    }
  }

  /**
   * 入度
   */

//  public int[] getVertexs() {
////    return vertexs;
//  }
  public void setVertexs(int[] vertexs) {
//    this.vertexs = vertexs;
  }

  //获取某个节点的度
  public int getOutDegree(int m) {
    int result = 0;
    for (int i = 0; i < length; i++) {
      if (tables[m][length] != 0 && tables[m][length] != MAX) {
        result++;
      }
    }
    return result;
  }

  //获取某个节点的第一个临接节点
  public int getFirstNeighbor(int m) {
    for (int i = 0; i < length; i++) {
      if (tables[m][length] != 0 && tables[m][length] != MAX) {
        return tables[m][length];
      }
    }
    return -1;
  }

  //根据前一个邻接点的下标来取得下一个邻接点
  public int getNextNeighbor(int m, int x) {
    for (int i = x; i < length; i++) {
      if (tables[m][length] != 0 && tables[m][length] != MAX) {
        return tables[m][length];
      }
    }
    return -1;
  }

  //  图的深度优先遍历算法:深度优先遍历一个节点
  private void depthFirstSearch(int m) {
    isVisited[m] = true;
    int x = getFirstNeighbor(m);
    while (x != -1) {
      if (!isVisited[x]) {
        System.out.print("遍历到" + x);
        depthFirstSearch(x);
      }
      x = getNextNeighbor(m, x);
    }
  }

  // 遍历所有节点，防止出现断掉的顶点未遍历，
  // 然后重置isVisited数组以备下次遍历
  public void depthFirstSearch() {
    isVisited = new boolean[length];
    for (int i = 0; i < length; i++) {
      if (!isVisited[i]) {
        System.out.print("遍历到" + i);
        depthFirstSearch(i);
      }
    }
    isVisited = new boolean[length];
  }

  //实现广度优先遍历
  public void broadFirstSearch(int m) {

  }

}
