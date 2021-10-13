package com.example.firstapp.lesson

/**
 *@author: hairly owl
 *@time:2021/10/4 22:45
 *@version: 1.00
 *@description: kotlin 循环
 */

fun main() {
    val items = listOf("number", "list", "collection")

    //1.for 循环
    //for item in elements  elements可以是数组、集合
    //1.1 for-in
    println("for-in")
    for(item in items){
        print("$item ")
    }
    //1.2 forEach
    println("\nforEach")
    items.forEach{
        print("$it ")
    }
    //1.3 forEachIndexed
    println("\nforEachIndexed")
    items.forEachIndexed{index, item ->
        print("$index:$item   ")
    }



    //2.while 和 do-while
    //2.1 while 先判断再循环
    println("\n\nwhile")
    var index = 0
    while(index <items.size){
        print("$index:${items[index++]}  ")
    }
    //2.2 do-while 先执行一次循环体，再判断循环
    index = 0
    println("\ndo-while")
    do{
        print("$index:${items[index++]}  ")
    }while(index < items.size)



    //3.迭代区间和数列 常用
    //遍历区间，注意Kotlin的区间的包含或是闭合的
    println("\n\n遍历区间")
    for (i in 1..10) { //[1,10]
        print("$i ")
    }
    //for in-until 前闭后开
    println("\nfor in-until")
    for (i in 1 until 10) { //[1,10)
        print("$i ")
    }
    //for in downTo
    println("\nfor in downTo")
    for (i in 10 downTo 1) { //downTo 倒序遍历
        print("$i ")
    }
    //for in downTo step
    println("\nfor in step")
    for (i in 10 downTo 1 step 3) { //倒序 步长为3
        print("$i ")
    }
    //遍历数组时或list
    println("\nindices")
    val array = arrayOf(1,2,3)
    for (i in array.indices) {//遍历索引
        print(" "+array[i])
    }
    println("\nwithIndex方法")
    for ((index, value) in array.withIndex()) {
        print(" $index:$value   ")
    }
    println("\n\n")


    //4.break continue
    for (i in 1..12) {
        if (i % 2 == 0) continue // 如果 i 能整除于 2，跳出本次循环，进入下一个循环
        for (j in 1..12) {
            if (j > 5) break // 如果 j 小于 10 ，终止循环。
            print("$j ")
        }
        println()
    }
}

