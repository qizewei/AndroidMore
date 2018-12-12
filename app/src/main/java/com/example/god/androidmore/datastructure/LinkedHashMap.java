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
 * 迭代HashMap的顺序并不是HashMap放置的顺序。
 * next是用于维护HashMap指定table位置上连接的Entry的顺序的，before、After是用于维护Entry插入的先后顺序的。
 * 该循环双向链表的头部存放的是最久访问的节点或最先插入的节点，尾部为最近访问的或最近插入的节点，迭代器遍历方向是从链表的头部开始到链表尾部结束，在链表尾部有一个空的header节点，该节点不存放key-value内容，为LinkedHashMap类的成员属性，循环双向链表的入口。
 * 注意这个header，hash值为-1，其他都为null，也就是说这个header不放在数组中，就是用来指示开始元素和标志结束元素的。
 * header的目的是为了记录第一个插入的元素是谁，在遍历的时候能够找到第一个元素。
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
    LinkedHashMapEntry tBefore = tail.before;
    put(p.key, p.value);
    tBefore.next = new LinkedHashMapEntry(p.hash, p.key, p.value, null);
    if (length >= load_factor * length) {
      unLinkedNodeList();
    }
  }

  private void unLinkedNodeList() {
    LinkedHashMapEntry hAfter = header.after;
    header = hAfter;
    tail.next = header;
  }

  private void unLinkedNode(Object key) {
    LinkedHashMapEntry x = header;
    while (x.after != null) {
      if (x.getKey() == key) {
        LinkedHashMapEntry hAfter = x.after;
        LinkedHashMapEntry hBefore = x.before;
        hBefore.after = x.after;
        hAfter.before = x.before;
        return;
      }
      x = x.after;
    }
  }

  private LinkedHashMapEntry getNode(Object key) {
    LinkedHashMapEntry result = getNode(key);
    if (accessOrder) {
      unLinkedNode(key);
      LinkedNodeList(result);
    }
    return result;
  }

  void newNode(int hash, K k, V v, Node<K, V> next) {
    LinkedHashMapEntry linkedHashMapEntry = new LinkedHashMapEntry(hash, k, v, next);
    LinkedNodeList(linkedHashMapEntry);
  }
}
