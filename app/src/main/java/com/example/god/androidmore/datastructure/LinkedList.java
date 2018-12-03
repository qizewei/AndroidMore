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

public class LinkedList<T> implements List<T> {

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @NonNull
  @Override
  public Iterator iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public boolean add(T t) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<? extends T> c) {
    return false;
  }

  @Override
  public void clear() {

  }

  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int haseCode() {
    return 0;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public T get(int index) {
    return null;
  }

  @Override
  public T set(int index) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }
}
