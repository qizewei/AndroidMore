# Android高级工程师的成长之路

## 数据结构
1. 数据结构：数据之间相互存在的一种或多种特定关系的元素的集合。
2. 逻辑结构分类：集合结构，线性结构，树形结构，图形结构。
3. 物理结构分类：顺序存储，链式存储 。

**Android中主要数据结构的伪代码，注重思路和实现方式，笔记和重点在代码中有注释**<br/>
[ArrayList（顺序存储方式线性表）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/ArrayList.java)
<br/>
[LinkedList（链式存储方式线性表）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/LinkedList.java)
<br/>
[Queue（队列）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/MyQueue.java)
<br/>
[Stack（栈）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/MyStack.java)
<br/>
[HashMap（哈希表）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/HashMap.java)
<br/>
[LinkedHashMap（链式哈希表）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/LinkedHashMap.java)
<br/>
[Tree（树）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/Tree.java)
<br/>
[Graph（图-邻接表的实现）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/GraphLinekd.java)
<br/>
[Graph（图-邻接矩阵的实现）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/Graph.java)
<br/>
[Graph（图-深度优先遍历,广度优先遍历）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/Graph.java)
<br/>
[Graph（图-最小生成树的两种算法：普利姆算法，克鲁斯卡尔算法）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/Graph.java)
<br/>
[Graph（图-最短路径：迪杰斯特拉算法）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/Graph.java)
<br/>
[Graph（图-拓扑排序）](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/datastructure/GraphTopologic.java)
<br/>

## 算法：
算法优劣评定：时间复杂度（相同时间下执行的指令少），空间复杂度（消耗的内存大小），正确性，可读性，健壮性。
 
**各个算法的实现,均通过单元测试**<br/>
#### 排序算法
[冒泡排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/BubbleSort.java)
<br/>
[堆排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/HeapSort.java)
<br/>
[插入排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/InsertionSort.java)
<br/>
[归并排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/MergeSort.java)
<br/>
[快速排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/QuickSort.java)
<br/>
[选择排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/SelectionSort.java)
<br/>
[希尔排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/ShellSort.java)
<br/>
[基数排序](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/sort/redaixSort.java)
<br/>

#### 查找算法
[线性查找](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/search/SequenceSearch.java)
<br/>
[二分查找](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/search/BinarySearch.java)
<br/>
#### 算法思想及案例
[穷举思想案例：泊松分酒](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Exhaustion.java)
<br/>
[递归思想案例：汉诺塔问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Recursion.java)
<br/>
[递归思想案例：最大公约数问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Recursion.java)
<br/>
[动态规划法思想案例：最长公共子序列 + 最长公共子串问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/DynamicProgramming.java)
<br/>
[回溯法思想案例：八皇后问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/BackTracking.java)
<br/>
[分治法思想案例：球队比赛排列问题 + L型骨牌问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/DivideAndConquer.java)
<br/>
[贪心算法案例：背包问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Greedy.java)
<br/>

#### 其他算法
[约瑟夫问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Other.java)
<br/>
[大数相乘问题](https://github.com/QzwJuHao/AndroidMore/blob/master/app/src/main/java/com/example/god/androidmore/algorithm/other/Other.java)
<br/>

## Android
#### IPC部分（AIDL，Binder等）
[01-线程和进程](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/01-%E7%BA%BF%E7%A8%8B%E5%92%8C%E8%BF%9B%E7%A8%8B.md)
<br/>
[02-Android IPC](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/02-Android%20IPC.md)
<br/>
[03-Binder的原理](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/03-Binder%E7%9A%84%E5%8E%9F%E7%90%86.md)
<br/>
[04-Activity_知识点](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/04-Activity_%E7%9F%A5%E8%AF%86%E7%82%B9.md)
<br/>
[05-View的绘制](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/05-View%E7%9A%84%E7%BB%98%E5%88%B6.md)
<br/>
[06-消息队列](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/06-%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97.md)
<br/>
[07-Service难点](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/07-Service%E9%9A%BE%E7%82%B9.md)
<br/>
[08-Window相关](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/08-Window%E7%9B%B8%E5%85%B3.md)
<br/>
[09-AOP编程](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/09-AOP%E7%BC%96%E7%A8%8B.md)
<br/>
[10-RxJava工作原理](https://github.com/QzwJuHao/AndroidMore/blob/master/wiki/10-RxJava%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86.md)
<br/>


#### 联系方式
博客：https://blog.csdn.net/qizewei123<br/>
E-mail：qizewei@vip.qq.com<br/>
微信：779754469
