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

public interface Collection<T> extends Iterable{

  boolean isEmpty();

  boolean contains(Object o);

  @NonNull
  Iterator iterator();

  Object[] toArray();

  boolean add(T t);

  boolean containsAll(Collection<?> c);

  boolean addAll(Collection<? extends T> c);

  boolean removeAll(Collection<? extends T> c);

  void clear();

  boolean equals(Object o);

}
