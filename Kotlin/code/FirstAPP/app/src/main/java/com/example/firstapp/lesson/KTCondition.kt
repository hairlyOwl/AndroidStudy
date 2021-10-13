package com.example.firstapp.lesson

/**
 *@author: hairly owl
 *@time:2021/10/4 21:56
 *@version: 1.00
 *@description: 条件控制
 */

fun main() {
    //1.if
    //1.1带返回值 if 表达式
    println("maxOf ${maxOf(3,1)}")
    //1.3 多级 if 表达式
    println("eval ${eval(1.22)} \n")


    //2.when 表达式   (代替java中的switch case)
    println("eval ${eval2(99f)}")

    //3.when 表达式的功能增强
    eval3()
}

//1.if
//1.1带返回值 if 表达式
fun maxOf(a:Int,b:Int):Int{ //比较两个值的大小
    if(a>b){
        return a
    }else{
        return b
    }
}
//1.2 if 表达式替代三目运算符
fun maxOf2(a:Int,b:Int):Int{ //maxOf简写版
    return if (a > b) a else b
}
//1.3 多级 if 表达式
fun eval(number: Number) {
    if (number is Int) {
        println("this is int number")
    } else if (number is Double) {
        println("this is double number")
    } else if (number is Byte) {
        println("this is byte number")
    } else if (number is Short) {
        println("this is Short number")
    } else {
        throw IllegalArgumentException("invalid argument")
    }
}


//2.when 表达式   (代替java中的switch case)
//when 将它的参数与所有的分支条件顺序比较，直到某个分支满足条件
fun eval2(number: Any):String = when(number){
    1 -> "one"
    "hello" -> "hello word"
    is Int -> "int"
    is Double,Float -> "Double 或 Float" ////多个分支条件放在一起，用逗号分隔
    is String -> {
        println("多行when")
        "String"
    }

    else  -> "invalid number"
}


//3.when 表达式的功能增强
// kotlin1.3版本后 when(value) value可以动态赋值如方法的返回结果
fun eval3() :String = when (val value = getValue()) {
       //when表达式条件直接是一个表达式，并用value保存了返回值, 实际上相当于把外部那一行缩进来写
        is Int -> "This is Int Type, value is $value".apply(::println)
        is String -> "This is String Type, value is $value".apply(::println)
        is Double -> "This is Double Type, value is $value".apply(::println)
        is Float -> "This is Float Type, value is $value".apply(::println)
        else -> "unknown type".apply(::println)
}

fun getValue(): Any {
    return 100F
}
