/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;


import java.util.Stack;

public class MyStack<T> {

  private Node<T> top;
  private Node<T> bottom;

  public boolean push(T t) {
    Node<T> l = top;
    Node<T> newNode = new Node<>(t, null, top);
    l.pre = newNode;
    top = newNode;
    return true;
  }

  public T pop() {
    Node<T> l = bottom.pre;
    T v = bottom.element;
    l.next = null;
    return v;
  }

  public T peek(){
    return top.element;
  }

  public void remove() {
    Node<T> l = top.next;
    l.pre = null;
  }


  private static class Node<E> {

    E element;
    Node<E> pre;
    Node<E> next;

    public Node(E element, Node<E> pre, Node<E> next) {
      this.element = element;
      this.pre = pre;
      this.next = next;
    }
  }
}
