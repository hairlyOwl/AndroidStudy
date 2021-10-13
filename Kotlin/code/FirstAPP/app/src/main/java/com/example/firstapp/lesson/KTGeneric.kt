package com.example.firstapp.lesson

import org.json.JSONObject

/**
 *@author: hairly owl
 *@time:2021/10/5 21:45
 *@version: 1.00
 *@description: 泛型
 */

fun main() {
    //1.1 泛型接口
    val drinkApple = DrinkApple()
    drinkApple.drink("apple")
    //1.2 泛型类
    val blueColor = BlueColor("blue")
    blueColor.printColor()

    //3.泛型方法
    fromJson<String>("{}",String::class.java) //class参数 String::class.java

    //4.泛型约束
    fromJson2<JSONObject>("{}",JSONObject::class.java)
    //4.2多种约束
    fromJson3<User>("{}",User::class.java)
}

//1.1泛型接口
interface Drink<T> {
    fun drink(t : T) //t泛型字段
}
//接口实现
class DrinkApple : Drink<String>{
    override fun drink(t: String) {
        println("Drink $t")
    }
}

//1.2 泛型类
abstract class Color<T>(val t:T){ //t是成员变量 t泛型字段
    abstract fun printColor()
}
class BlueColor(val color:String) : Color<String>(color){
    override fun printColor() {
        println("color is $color")
    }
}

//3.泛型方法
//json序列化的时候使用
fun<T> fromJson(json:String , tClass:Class<T>) : T?{ //T?返回值可空(应对json为空的时候)
    //声明一个实列，可能会抛出异常(IllegalAccessException, InstantiationException;) 要显式声明类型 T?
    val instance:T? = tClass.newInstance()
    return instance
}

//4.泛型约束 （常见）
//约束写法1
// T : xObject  所传递的类型T必须满足是xObject的子类 或xObject类
fun<T : JSONObject> fromJson2(json:String , tClass:Class<T>) : T?{ //T?返回值可空(应对json为空的时候)
    //声明一个实列，可能会抛出异常(IllegalAccessException, InstantiationException;) 要显式声明类型 T?
    val instance:T? = tClass.newInstance()
    return instance
}
//约束写法2
//所传递的类型T必须同时满足 where 子句的所有条件
//where T:类x,T:接口x , T必须满足类x或类x的子类，  同时T必须实现 接口x
fun<T> fromJson3(json:String , tClass:Class<T>) : T? where T:JSONObject,T:Comparable<T>{ //T?返回值可空(应对json为空的时候)
    //声明一个实列，可能会抛出异常(IllegalAccessException, InstantiationException;) 要显式声明类型 T?
    val instance:T? = tClass.newInstance()
    return instance
}
class User : JSONObject(),Comparable<User>{
    override fun compareTo(other: User): Int {
        TODO("Not yet implemented")
    }

}

//5.泛型中的out与in
open class Animal

class CatAnimal : Animal()
open class DogAnimal : Animal() //继承Animal
class whiteDogAnimal : DogAnimal()

fun animalFun(){
    val animal:Animal = DogAnimal()
    //5.1 out约束泛型参数的类型上限
    //不能做级别类型的类型强转
    // val animalList:ArrayList<Animal> = ArrayList<DogAnimal>() 报错类型错误
    //out  泛型参数的类型 允许传入T，以及T的子类
    //使用处使用out关键字声明——泛型上限
    //val animalList1:ArrayList<out Animal> = ArrayList<DogAnimal>()
    //在定义处使用out关键字声明
    //val animalList2:ArrayList<Animal> = ArrayList<DogAnimal>()

    //5.2in约束泛型参数的类型下限 允许传入的类型泛型参数可以是T以及T的父类
    //val animalList3:ArrayList<DogAnimal> = ArrayList<Animal>() //报错类型错误
    //使用处使用in关键字声明——泛型下限
    val animalList3:ArrayList<in DogAnimal> = ArrayList<Animal>()
    ////在定义处使用out关键字声明
    val animalList4:ArrayList<DogAnimal> = ArrayList<Animal>()

}
//在定义处使用out关键字声明，允许传入的泛型参数可以是T以及T的子类
/*class ArrayList<out T>{

}*/

//5.2in约束泛型参数的类型下限
class ArrayList<in T>{

}