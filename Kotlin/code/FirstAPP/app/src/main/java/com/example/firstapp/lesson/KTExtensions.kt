package com.example.firstapp.lesson

/**
 *@author: hairly owl
 *@time:2021/10/12 22:16
 *@version: 1.00
 *@description: kotlin扩展 extensions
 */

fun main() {
    //2.扩展方法的使用
    Jump().test()
    Jump().doubleJump()

    //4.泛型扩展方法
    val list = mutableListOf<Int>(1,2,3,4)
    list.swap(3,1)
    for(number in list){
        print("$number ")
    }

    //5.扩展属性
    val str = "evening"
    val strLastChar = str.lastChar
    println("\nlastChar: $strLastChar")

    //6.为伴生对象添加扩展
    Eat.print("为伴生对象添加扩展")

    //7.Kotlin中常用的扩展
    //7.1  let扩展
    testLet("ddd")

    //7.2 run扩展
    val j = Jump()
    println(testRun(j))

    //7.3 apply扩展
    testApply()

}

class Jump{
    fun test(){
        print("jump test ")
    }
}
//2.扩展方法的使用
//扩展函数的定义，就是在方法的前面加上类前缀
//针对无法直接修改类的时候，如想要修改一个jar包种的类
fun Jump.doubleJump():String{
    return "Jump.doubleJump()"
}

//4.泛型扩展方法
fun <T> MutableList<T>.swap(index1:Int , index2: Int){
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

//5.扩展属性
//对String类添加一个lastChar属性 （在kotlin里任何字段都有get set方法）
val String.lastChar:Char get() = this.get(this.length - 1)

//6.为伴生对象添加扩展
class Eat {
    companion object {}
}
fun Eat.Companion.print(str: String) {
    println("Eat.Companion"+str)
}

//7.Kotlin中常用的扩展
//7.1  let扩展 let扩展函数的实际上是一个作用域函数
// 类名后面加上? 代表参数可能为空，使用的时候 注意判空
fun testLet(str: String?){
/*    str.let {//it:String?
        val str2 = "morning"
        println(str2 + it)
    }*/

    //判空用法，当str为空时不会触发闭包里面的逻辑
    str?.let{
        val str2 = "morning"
        println("$str2  $it")
    }
}

//7.2 run扩展
//在run函数中可以**直接访问实例的公有属性和方法**
fun testRun(jump: Jump) : String{
    jump.run {
        test() //jump.test()
        return "run函数"
    }
}

//7.3 apply扩展
fun testApply() {
    arrayListOf<String>().apply {
        add("111")
        add("22222")
    }.let {
        println(it)
    }
}


