package com.example.firstapp.lesson

import org.json.JSONObject

/**
 *@author: hairly owl
 *@time:2021/9/25 15:37
 *@version: 1.00
 *@description: kotlin容器
 */

fun main() {
    //1.kotlin数组
    //1.1 arrayOf() 数组初始化是必须指定数组的元素 不定长，可以是任意类型
    val arrayInt = arrayOf(1, 2, 3, 4)
    //kotlin的Any等价于java中的Object
    val array = arrayOf(1, true, "2")  // 集合中的元素可以是任意类型

    //1.2 arrayOfNulls() 必指定元素类型、指定数组长度
    val arrayOfNulls = arrayOfNulls<String>(4) //类型? 表示这个对象是可空的
    arrayOfNulls[0] = "element0"  //数据填充
    arrayOfNulls[1] = "element1"
    arrayOfNulls[2] = null
    arrayOfNulls[3] = "element3"

    //1.3 动态创建数组（不常用）
    //利用Array的构造函数动态的创建
    //数组创建的时候，会循环5次，i就是数组的下标
    // ->右边的表达式的结果，就是数组中当前下标的元素
    val asc = Array(5){i -> (i*i).toString()} //{"0","1","4","9","16"}
    //asc.forEach { print(it+"  ") }

    //1.4字节数组
    val byteA = ByteArray(5)
    byteA[0] = 0

    //1.5使用IntArray创建整型数组
    val intA1 = IntArray(5) //长度为5的空IntArray
    intA1[0] = 2
    val intA2 = IntArray(5){100} //长度为5每个元素都是100 {100,100,100,100,100}
    val intA3 = IntArray(5){it*2} //{0,2,4,6,8} it是数组的下标  {i -> i * 2} 等价 {it*2}

    //1.6数组循环遍历
    //依次取出数组中的元素——for-in的形式
    for(item in intA3){
        print("$item ")
    }
    println()
    //根据下标再取出对应的元素 for-in
    for(i in intA3.indices){
        print(i.toString()+"->"+ intA3[i]+" ")
    }
    println()
    //带索引和元素 for-in
    for((index,item) in intA3.withIndex()){
        print("$index->$item ")
    }
    println("\n")
    //forEach会依次回调给我们数组中的元素
    intA3.forEach {
        print("$it ") //此时it是数组元素
    }
    println()
    //forEach增强版 ,依次回调下标和元素
    intA3.forEachIndexed{index, item ->
        print("$index:$item ")
    }
}