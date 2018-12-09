/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 * Collection集合创建了一些接口，Collection可以存储相同的对象，Map用来存储不相同的对象
 */
public interface Collection<T> {

  boolean isEmpty();

  boolean contains(Object o);

  boolean add(T t);

  void clear();

  boolean equals(Object o);

}
