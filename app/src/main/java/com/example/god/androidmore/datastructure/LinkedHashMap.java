/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 * 线程不安全
 * 每个节点添加头尾指针，构成双向链表
 * LruCache的实现（Linkedhashmap默认插入排序，需要配置一个布尔值accessOrder来设置调用排序）。
 */

public class LinkedHashMap<K, V> extends HashMap<K, V> {

  static class LinkedHashMapEntry<K, V> extends HashMap.Node<K, V> {

    LinkedHashMapEntry before, after;

    LinkedHashMapEntry(int hash, K key, V value, Node<K, V> next) {
      super(hash, key, value, next);
    }
  }

  LinkedHashMapEntry<K, V> header;
  LinkedHashMapEntry<K, V> tail;

  boolean accessOrder = false;

  public void setAccessOrder(boolean accessOrder) {
    this.accessOrder = accessOrder;
  }

  private void LinkedNodeList(LinkedHashMapEntry<K, V> p) {

  }

//  private V get(Object key){
//
//  }
}
