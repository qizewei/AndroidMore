/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 *链式存储方式线性表（linkedList）：插入效率高，查询效率低
 * @param <T>
 */
public class LinkedList<T> implements List<T> {

  private Node<T> heard;
  private Node<T> end;

  @Override
  public boolean isEmpty() {
    return heard.element == null;
  }

  @Override
  public boolean add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    addLast(t);
    return true;
  }

  public void addLast(T t) {
    Node<T> node = end;
    Node<T> newLast = new Node<>(t, end, null);
    node.next = newLast;
    end = newLast;
  }

  @Override
  public void clear() {
    heard.element = null;
    heard.next = null;
  }

  @Override
  public boolean remove() {
    removeLast();
    return true;
  }

  private void removeLast() {
    Node<T> l = end.pre;
    l.next = null;
  }

  private T getFirst() {
    return heard.element;
  }

  private T getLast() {
    return end.element;
  }

  @Override
  public int indexOf(Object o) {
    int index = 0;
    for (Node<T> i = heard; i != null; i = i.next) {
      if (i.element.equals(o)) {
        return index;
      }
      index++;
    }
    return -1;
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

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public T set(int index, T t) {
    return null;
  }


  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }
}
