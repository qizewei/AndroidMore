/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

import java.util.LinkedList;

/**
 * 图-临接矩阵实现
 * 图的两种遍历：深度优先遍历，广度优先遍历
 * 图的最小生成树的两种算法：普利姆算法，克鲁斯卡尔算法
 */

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

  public int[] getVertexs() {
    return labels;
  }

  public void setVertexs(int[] vertexs) {
    this.labels = vertexs;
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

  //实现广度优先遍历1:
  public void broadFirstSearchF(int m) {
    MyQueue myQueue = new MyQueue();
    isVisited[m] = true;
    myQueue.enQueue(m);
    for (int i = 0; i < tables[m].length; i++) {
      if (tables[m][i] != 0 && tables[m][i] != MAX && !isVisited[i]) {
        isVisited[i] = true;
        myQueue.enQueue(i);
      }
    }
  }

  //实现广度优先遍历2:
  public void broadFirstSearchT(int m) {
    LinkedList<Object> myQueue = new LinkedList<>();
    isVisited[m] = true;
    myQueue.add(m);
    while (!myQueue.isEmpty()) {
      int p = (int) myQueue.removeFirst();
      int x = getFirstNeighbor(p);
      while (x != -1) {
        if (!isVisited[x]) {
          isVisited[x] = true;
          x = getNextNeighbor(x, p);
        }
      }
    }
  }

  /**
   * 最小生成树：
   * 普利姆算法：
   * 获取一个顶点A和它连接的顶点数组X，然后连接权值最小的顶点B，并将B连接的顶点数组合并到X，
   * 仍从X中找出权值最小的顶点连接，以此类推。
   */
  public static void prim(int m, int length, int[][] tables) {
    int[] repleData = tables[m];
    for (int i = 0; i < length; i++) {

      int small = MAX;
      for (int j = 0; j < length; j++) {
        if (repleData[j] != 0 && repleData[j] != MAX && repleData[j] < small) {
          System.out.print("节点：" + j + " 权重：" + small);
          small = tables[m][j];
          repleData[j] = 0;
        }
      }

      for (int j = 0; j < length; j++) {
        if (tables[small][j] != 0 && tables[small][j] != MAX && tables[small][j] < repleData[j]) {
          repleData[j] = tables[small][j];
        }
      }
    }
  }

  /**
   * 最小生成树
   * 克鲁斯卡尔算法：
   * Edge edge0 = new Edge(int begin,int end, int weight);
   * begin为边的起始顶点，end为边的结束顶点，weight为边的权重
   * 通过构建边的数组来进行计算。
   * 思想：按权重从小到大遍历，构成回环的边舍弃
   *
   * @param dataSize
   * @param datas Edge的集合，按权重从小到大排列
   */
  int[] results;

  public void Kruskal(int dataSize, Edge[] datas) {
    results = new int[dataSize];
    for (int i = 0; i < datas.length; i++) {
      int m = find(datas[i].begin);
      int n = find(datas[i].end);
      if (n != m) {
        results[m] = n;
      }
    }

  }

  //找到最终节点
  int find(int m) {
    while (results[m] != 0) {
      m = results[m];
    }
    return m;
  }

  class Edge {

    int begin;
    int end;
    int weight;

    public int getBegin() {
      return begin;
    }

    public void setBegin(int begin) {
      this.begin = begin;
    }

    public int getEnd() {
      return end;
    }

    public void setEnd(int end) {
      this.end = end;
    }

    public int getWeight() {
      return weight;
    }

    public void setWeight(int weight) {
      this.weight = weight;
    }

    public Edge(int begin, int end, int weight) {
      this.begin = begin;
      this.end = end;
      this.weight = weight;
    }
  }

  /**
   * 最短路径：
   * 迪杰斯特拉算法：
   * 获取一个顶点A和它连接的顶点数组X，然后连接最小权值(M)的顶点B，然后将B顶点的数组P整合到数组X(数组P相加M和数组X取最小值)
   */
  public void Dijkstra(int length, int[][] tables) {
    results = new int[length];
    results[0] = 0;
    isVisited[0] = true;
    results = tables[0];
    for (int i = 1; i < length; i++) {

      int min = MAX;
      int k = 0;
      for (int j = 0; j < tables[i].length; j++) {
        if (!isVisited[j] && tables[i][j] < min) {
          min = tables[i][j];
          k = j;
        }
      }

      for (int j = 0; j < length; j++) {
        if (!isVisited[j] && tables[k][j] + min < results[j]) {
          results[j] = min + tables[k][j];
        }
      }

    }
  }
}
