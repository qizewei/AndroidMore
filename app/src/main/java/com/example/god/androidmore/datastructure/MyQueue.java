/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 * LinkedList实现了Queue的接口
 * 先进先出。先出的一端为队头，另一端为队尾。
 */
public class MyQueue {

  private java.util.LinkedList list = new java.util.LinkedList();

  public void clear()//销毁队列
  {
    list.clear();
  }

  public boolean QueueEmpty()//判断队列是否为空
  {
    return list.isEmpty();
  }

  public void enQueue(Object o)//进队
  {
    list.addLast(o);
  }

  public Object deQueue()//出队
  {
    if (!list.isEmpty()) {
      return list.removeFirst();
    }
    return "队列为空";
  }

  public int QueueLength()//获取队列长度
  {
    return list.size();
  }

  public Object QueuePeek()//查看队首元素
  {
    return list.getFirst();
  }
}
