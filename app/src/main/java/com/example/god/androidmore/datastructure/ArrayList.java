/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

import android.support.annotation.NonNull;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {

  private Object[] elementData;
  private int size;

  @Override
  public boolean isEmpty() {
    return elementData.length == 0;
  }

  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  @Override
  public boolean add(T t) {
    elementData[size++]=t;
    return true;
  }

  @Override
  public void clear() {
    for (int i = 0; i < elementData.length; i++) {
      elementData[i] = null;
    }
  }

  @Override
  public boolean remove(Object o) {
    int num = indexOf(o) - elementData.length - 1;
    System.arraycopy(elementData, indexOf(o) + 1, elementData, indexOf(o), num);
    return true;
  }

  @Override
  public boolean remove() {
    return false;
  }

  @Override
  public T get(int index) {
    if (index > elementData.length) {
      throw new IndexOutOfBoundsException(index + "越界");
    }
    return (T) elementData[index];
  }

  @Override
  public T set(int index, T t) {
    if (index > elementData.length) {
      throw new IndexOutOfBoundsException("index");
    }
    elementData[index] = t;
    return t;
  }

  @Override
  public int indexOf(Object o) {
    if (o == null) {
      for (int i = 0; i < elementData.length; i++) {
        if (elementData[i] == null) {
          return i;
        }
      }
    } else {
      for (int i = 0; i < elementData.length; i++) {
        if (o.equals(elementData[i])) {
          return i;
        }
      }
    }
    return -1;
  }
}
