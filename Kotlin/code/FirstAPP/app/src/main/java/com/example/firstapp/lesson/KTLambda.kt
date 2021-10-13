package com.example.firstapp.lesson

/**
 *@author: hairly owl
 *@time:2021/9/29 20:19
 *@version: 1.00
 *@description: 方法和Lambda
 */

fun main() {
    /*
    * 1.方法声明
    * */
    //成员方法需要构建实列对象，才能访问成员方法
    //示例对象的构建只需要在类名后面加上() !!!不需要new
/*    Person().test()

    //静态类方法调用，无须构建实例
    val n = NumUtil.plus(0)
    println("静态类方法 $n")

    //伴生类的方法调用
    Person.test2()

    val double = double(2)
    println("单表达式方法 $double")*/

    /*
    * 2.方法参数
    * */
    //2.1 默认参数
    read(8,1)
    //2.2具名参数
    read(start = 3) //如果一个无默认值的参数在默认值参数后，该默认值只能通过使用具名参数调用该方法
    foo(1,2 ,qux={
        //方法体里面的最后一行，就是该方法的返回值
        val res: Int = 3 * 4
        "res $res，括号内使用具名参数，传递action参数"
    })
    //当且仅当最后的参数是一个方法时才可以写在花括号外面
    foo(baz=1){
        "括号外传递action参数"
    }
    //2.3可变数量参数
    val append = append('1', '2', '3', 'a',num = 1)
    println("append 可变数量参数 $append")

    val world = charArrayOf('w', 'o', 'r', 'l', 'd')
    val result = append('h', 'e', 'l', 'l', 'o',' ', *world,num = 1) //*world，依次添加world字符数组中的元素
    println("append 可变数量参数 $result")



    /*
    *3.Lambda
    * */
    val number:Array<Int> = arrayOf(1,2,3,4)
    change(number){ index, element ->
        index * element //方法体最后一句是return
    }

    //3.1隐形参数it
    //只有一个参数默认时it
    number.forEach {
        print("$it ")
    }
    println()

    //多个参数的时候
    //forEachIndexed 参数是下标和元素
    number.forEachIndexed{index: Int, element: Int ->
        println("forEachIndexed $index : $element")
    }
    //显示调用
    /*number.forEachIndexed(action = {index: Int, element: Int ->
        println("forEachIndexed $index : $element")
    })*/

    //3.2下划线_
    val map = mapOf("key1" to "1", "key2" to "2", "key3" to "3")
    map.forEach { (key, value) ->
        println("$key \t $value")
    }

    // 不需要key的时候
    map.forEach { (_, value) ->
        println(value)
    }
}

/*
* 1.方法声明
* */
//成员方法
/*class Person{
    fun test(){
        println("Person test方法")
    }

    //伴生类
    //当想要在class声明的类里面，想要定义一个静态方法的话使用伴生类
    companion object{
        fun test2(){
            println("伴生类的方法")
        }
    }
}
//静态类
//如果我们想实现一个工具util时，可以借助关键字object创建一个静态类
object NumUtil{
    fun plus(num:Int):Int{
        return num+1
    }
}
//单表达式方法
fun double(x:Int):Int = x*2*/


/*
* 2.方法参数
* */
//2.1默认参数
//方法参数可以有默认值，当省略相应的参数时使用默认值。与其Java相比，这可以减少重载数量：
fun read(offset:Int=0,start:Int){
    println("offset $offset, start $start")
}

//bar: Int = 0 默认值为0
//baz: Int 无默认值
//第三个参数是一个方法，参数名为 qux 如果参数的类型是() ,说明参数是一个方法类型
//方法参数的返回值使用 ->Unit（无返回值）  ->String （返回值的类型是String）
fun foo(bar: Int = 0, baz: Int, qux: () -> String) {
    val qux1 = qux()
    println(qux1)
}

////2.3可变数量参数
fun append(vararg str:Char,num:Int):String {
    val res = StringBuffer()
    str.forEach {
        res.append(it)
    }
    return res.toString()
}

/*
* 3.Lambda
* */
//array int数组
//action方法，两个Int 参数，返回值是Int
fun change(array:Array<Int>,action:(index:Int,element:Int)->Int){
    for(index in array.indices){ //indices是数组下标
        val newValue = action(index,array[index])
        array[index] = newValue
        print("${array[index]} ")
    }
    println()
}