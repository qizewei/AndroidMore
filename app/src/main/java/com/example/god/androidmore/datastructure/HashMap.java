/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/**
 * Hashmap默认初始化大小16，负载因子0.75
 * @param <K>
 * @param <V>
 */
public class HashMap<K, V> {

  private Node<K, V>[] table;
  private int length;

  static final int hash(Object key) {
    //通过hashCode来算出hash值，hash值对应table上的位置
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  static class Node<K, V> implements Map.Entry<K, V> {

    final int hash;
    final K key;
    V value;
    Node<K, V> next;

    Node(int hash, K key, V value, Node<K, V> next) {
      this.hash = hash;
      this.key = key;
      this.value = value;
      this.next = next;
    }

    public final K getKey() {
      return key;
    }

    public final V getValue() {
      return value;
    }

    public final String toString() {
      return key + "=" + value;
    }

//    public final int hashCode() {
//      return Objects.hashCode(key) ^ Objects.hashCode(value);
//    }

    public final V setValue(V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }

  public void put(K key,V value) {

    int hash = hash(key);
    Node node = new Node(hash,key,value,null);

    if (table[hash] == null) {
      table[hash] = node;
    } else {
      Node<K, V> x = table[hash];
      while (x != null) {
        if ((x.hash == hash && (x.key == node.key ))) {
          table[hash]=node;
          break;
        }
        if (x.next == null) {
          x.next = node;
          break;
        }
        x = x.next;
      }
    }
    length++;
  }

  public Node<K, V> remove(K key) {

    int hash = hash(key);
    Node<K, V> x = table[hash];

    if (table[hash] == null) {
      return null;
    }
    if (table[hash].key == key) {
      if (x.next == null) {
        table[hash] = null;
      } else {
        table[hash] = x.next;
      }
      return x;
    }

    while (x != null) {
      if (x.key == key) {
        Node<K, V> node = x;
        x = x.next;
        return node;
      }
      x = x.next;
    }
    return null;
  }

  public void set(Object key, Object value) {
    Node<Object, Object> newNode = new Node<>(hash(key), key, value, null);
    Node<K, V> x = table[newNode.hash];
    if (x == null) {
      table[newNode.hash] = (Node<K, V>) newNode;
      return;
    } else {
      while (x != null) {
        if (x.key == key) {
          return;
        }
        x = x.next;
      }
      x.next = (Node<K, V>) newNode;
    }
  }

  public V get(Object key) {
    int hash = hash(key);
    if (table[hash]==null) {
      return null;
    }else {
      Node<K,V> x = table[hash];
      while (x!=null){
        if(x.key==key)
          return x.value;
        x=x.next;
      }
      return null;
    }
  }
}
