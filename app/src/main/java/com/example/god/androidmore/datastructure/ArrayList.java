/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 * 顺序存储方式线性表（ArrayList）：查找效率高，插入删除效率低（ArrayList删除元素后，后面元素会向前位移）
 * ArrayList的本质是维护了一个对象数组，对ArrayList的增删改查即对数组进行操作
 * 1. List接口中有sort方法，需要实现Comparator方法就能进行排序
 * 2. List接口继承Collection，Collection集成Iterable
 * 3. System.arraycopy  是用于复制数组的native方法
 * @param <T>
 */
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
