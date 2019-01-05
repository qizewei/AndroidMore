/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.god.androidmore.datastructure;

/**
 * 每个节点最多只有一个父节点。
 * 节点拥有子树数称为节点的度。
 * 度为0的节点称为叶子节点。
 * 树的度是树内部个节点度的最大值。
 * 节点的层次从根开始定义，根为第一层。总层数即为树的深度 。
 * 森林是不相交的树的集合
 * **满二叉树**：每个节点都有个子节点，所有叶子都在同一层上
 * **完全二叉树**： 完全二叉树是由满二叉树而引出来的。对于深度为K的，有n个结点的二叉树，当且仅当其每一个结点都与深度为K的满二叉树中编号从1至n的结点一一对应时称之为完全二叉树。
 * 前序遍历（根节点-左节点-右节点） ，中序遍历（左节点-根节点-右节点），后序遍历（左节点-右节点-根节点）。
 * 除了迭代，栈也可以实现二叉树的遍历
 * **搜索（查找）二叉树**：任何一个结点，它的左子树的所有结点都比这个根结点要小，它的右子树的所有结点都比这个根结点要大。
 * 如果一棵二叉树按照中序遍历得到的序列时有序的，那么这棵二叉树一定是搜索二叉树。
 *
 * TreeSet:
 * 内部填装数据需要继承Comparable<>方法，并实现compareto方法。
 * compareto方法返回1，表示新插入的元素比被比较元素大，会插在树的右侧;如果为0表示相同，会舍弃该值。
 * TreeMap:
 * 本质为红黑树
 */
public class Tree {

}
