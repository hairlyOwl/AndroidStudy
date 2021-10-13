package com.example.firstapp.lesson

//kotlin数据类型
fun main() {
    //1.如何声明一个基本数据类型的变量，有那些方式

    //1.1整型
    //编译器推断
    val number = 100  //不超过int的范围默认位int,超过则为long
    val bigNumber = 80000000000 //long型(编译器推断相判断是否超出int范围)
    val longNumber = 23L //自动推断为long型

    //声明时指定 val 变量名:类型
    val byteNumber:Byte = 2

    //1.2 浮点型
    val doubleNumber = 0.123456789101112 //默认为double类型
    val floatNumber = 0.123456789101112f //f或F都可以 float保留小数点后六位

    println("floatNumber  "+floatNumber)
    println("doubleNumber "+doubleNumber)

    //1.3字符类型
    val char:Char = 'A' //单引号

    //1.4布尔类型
    val isVisible:Boolean = true
    val isTrue = false

    //1.5字符串
    val str:String = "123456789" //从0开始
    val strNum1:Char = str[0]
    //1.5.1 字符串模板 $
    println("str = $str")
    println("str length = ${str.length}") //${}括起来的任意表达式
    println("I am"+10+"years old") //字符串串拼接
    println("测试换行\n") //转义字符
    val jsonText = "{\"key\":\"value\"}"
    val text = """
        "{"key":"value"}"
        "{\"key\":\"value\"}"
    """.trimIndent() //分界符 trimMargin() 函数去除前导空格：

    println("jsonTest $jsonText")
    println("text $text \n\n")


    //2.数据类型间的转换
    val number30:Int = 30
    println("转换成字符串 ${number30.toString()}")
    println("转换成Byte ${number30.toByte()}")

    //3.数据运算
    //3.1加减乘除
    val numberI:Int = 3 / 2
    println("numberI $numberI") // 输出 1
    val numberDouble:Double = 3 / 2.toDouble()
    println("numberDouble $numberDouble") // 输出 1.5
    println("乘法 ${numberDouble * numberDouble}") // 输出 2.25
    println("取余 ${numberDouble % 3} \n\n\n") // 输出 1.5

    //3.2位运算
    val vip= true
    val admin= false
    val result1:Boolean = vip.and(admin) //false 与运算and()
    val result2:Boolean = vip.or(admin) //true   或运算or()  非运算inv()
    val result3:Int = 8 ushr(2) // 无符号右移动  0000 1000----> 0000 0010



}