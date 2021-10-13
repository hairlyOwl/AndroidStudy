package com.example.firstapp.lesson

import androidx.collection.arraySetOf

/**
 *@author: hairly owl
 *@time:2021/9/29 15:33
 *@version: 1.00
 *@description: kotlin集合
 */
/*
* List: 是一个有序集合，可通过索引（反映元素位置的整数）访问元素。元素可以在 list 中出现多次。
*       列表的一个示例是一句话：有一组字、这些字的顺序很重要并且字可以重复。
*  Set: 是唯一元素的集合。它反映了集合（set）的数学抽象：一组无重复的对象。
*      一般来说 set 中元素的顺序并不重要。例如，字母表是字母的集合（set）。
*  Map: （或者字典）是一组键值对。键是唯一的，每个键都刚好映射到一个值，值可以重复。
* */

fun main() {
    //2.1列表的创建方式
    //2.1.1 mutableList<>() 可变列表
    val arrayString = mutableListOf<String>()
    arrayString.add("1")  //直接添加元素
    arrayString.add("2")
    arrayString.add(2,"3") //指定下标添加元素

    val arrayString2 = mutableListOf<String>("1","2","3","4") //初始化添加元素
    arrayString2.add("5")

    //2.1.2 listOf<>() 不可变列表  必须指定元素类型，必须初始化数据元素
    val arrayList = listOf<Int>(1, 2, 3)

    //2.2 map字典创建
    //2.2.1 mutableMapOf<>() 可变字典
    val arrayMap = mutableMapOf<String, Int>()
    arrayMap["1"] = 1
    arrayMap["2"] = 2
    arrayMap["3"] = 1
    arrayMap["4"] = 1 //此时，会覆盖上面的value

    //使用Pair指定集合中初始化的元素
    val arrayMap2 = mutableMapOf<String, Int>(Pair("1",6))

    //2.2.2 mapOf<>() 不可变字典 不可动态添加删除元素
    val arrayMap3 = mapOf<Int, String>(Pair(1, "2"))
    val arrayMap4 = mapOf<Int, String>() //空字典且不可添加元素

    //2.3 Set集合创建  元素唯一
    //2.3.1 mutableSetOf<>()  可变集合元素，唯一
    val set = mutableSetOf<Int>()
    set.add(1)
    set.add(2)
    set.add(2) //无法添加
    for(item in set){
        print("$item ")
    }
    println()

    val set2 = mutableSetOf<Int>(1,2,3,4)
    set.add(1) //无法添加
    set.add(2) //无法添加


    //2.3.2 setOf<>() 不可变
    val set3 = setOf<Int>(1, 3, 2)

    //2.4 集合的操作 同样适用与map,set,list
    val arrayExamples = mutableListOf<String>("1", "2", "3","4","5","3","7")
    println("isEmpty ${arrayExamples.isEmpty()}")
    println("contains ${arrayExamples.contains("7")}") //Boolean
    println("get ${arrayExamples[1]}")
    println("indexOf ${arrayExamples.indexOf("3")}") //元素出现第一次对应的下标
    println("indexOf ${arrayExamples.lastIndexOf("3")}") //元素出现(右到左)第一次对应的下标

    arrayExamples[0] = "0" //修改下标对应的元素
    arrayExamples.add(1,"7") //指定位置插入元素
    arrayExamples.removeAt(0)
    //迭代器
    val iterator = arrayExamples.iterator()
    iterator.forEach { it->
        print("$it ")
    }
    println()
    arrayExamples.clear() //清除集合
    println("isEmpty ${arrayExamples.isEmpty()}")
    println("size ${arrayExamples.size}")

    //2.5变换操作
    val numbers = mutableListOf<Int>(1, 2, 3, 4)
    numbers.reverse()
    print("reverse：")
    numbers.forEach { it->
        print("$it ")
    }
    println()

    numbers.shuffle() //随机排列元素 不常用
    print("shuffle：")
    numbers.forEach { it->
        print("$it ")
    }
    println()

    numbers.sort() //小到大排序 常用
    print("sort：")
    numbers.forEach { it->
        print("$it ")
    }
    println()

    numbers.sortDescending() //大到排序 常用
    print("sortDescending：")
    numbers.forEach { it->
        print("$it ")
    }
    println()
}