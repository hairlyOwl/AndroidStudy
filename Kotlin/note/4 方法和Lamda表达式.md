# 四、Kotlin/方法与Lambda表达式

[视频链接](https://www.imooc.com/video/23356)         [视频老师博客链接](https://www.songyubao.com/book/primary/kotlin/kotlin-lambda.html)

## 1.kotlin的方法

### 1.1方法声明

#### 1.1.1 成员方法

成员方法需要构建实列对象，才能访问成员方法

```kotlin
//成员方法
class Person{
    fun test(){
        println("Person test方法")
    }
}

fun main() {
    //成员方法需要构建实列对象，才能访问成员方法
    //示例对象的构建只需要在类名后面加上() !!!不需要new
    Person().test()
}
```

#### 1.1.2 类方法

① companion object 实现的类方法 ②静态类 ③ 全局静态

- companion object 实现的类方法

​        当想要在class声明的类里面，想要定义一个静态方法的话使用伴生类

```kotlin
//成员方法
class Person{
    //伴生类
    companion object{
        fun test2(){
            println("伴生类的方法")
        }
    }
}

fun main() {
    //伴生类的方法调用
    Person.test2()
}
```

- 静态类
  如果我们想实现一个工具util时，可以借助关键字object创建一个静态类

```kotlin
//静态类
object NumUtil{
    fun plus(num:Int):Int{
        return num+1
    }
}

fun main() {
    //静态类方法调用，无须构建实例
    val n = NumUtil.plus(0)
    println("静态类方法 $n")
}
```

- 全局静态

  直接新建一个 Kotlin file 然后定义一些常量、方法。

#### 1.1.3 单表达式方法

```kotlin
//单表达式方法
fun double(x:Int):Int = x*2

fun main() {
    val double = double(2)
    println("单表达式方法 $double")
}
```

当返回值类型可由编译器推断时，显式声明返回类型是可选的：

```kotlin
fun double(x: Int) = x * 2
```



###  1.2 方法参数

#### 1.2.1默认参数

```kotlin
//2.1默认参数
//方法参数可以有默认值，当省略相应的参数时使用默认值。与其Java相比，这可以减少重载数量：
fun read(offset:Int=0,start:Int){
    println("offset $offset, start $start")
}

//函数调用
read(8,1)
```

#### 1.2.2具名参数

如果一个默认参数在一个无默认值的参数之前，那么该默认值只能通过使用具名参数调用该方法来使用

```kotlin
//bar: Int = 0 默认值为0
//baz: Int 无默认值
//第三个参数是一个方法，参数名为 qux 如果参数的类型是() ,说明参数是一个方法类型
//方法参数的返回值使用 ->Unit（无返回值）  ->String （返回值的类型是String）
fun foo(bar: Int = 0, baz: Int, qux: () -> String) {
    val qux1 = qux()
    println(qux1)
}


//函数调用
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
```

#### 1.2.3可变数量的参数

> 可变参数的要求：

- 只有一个参数可以标注为 vararg；
- 如果 vararg 参数不是列表中的最后一个参数， 可以使用具名参数语法传递其后的参数的值，或者，如果参数具有方法类型，则通过在括号外部传一个 lambda。

```kotlin
////2.3可变数量参数
fun append(vararg str:Char,num:Int):String {
    val res = StringBuffer()
    str.forEach {
        res.append(it)
    }
    return res.toString()
}

//函数调用
    val append = append('1', '2', '3', 'a',num = 1)
    println("append 可变数量参数 $append")

    val world = charArrayOf('w', 'o', 'r', 'l', 'd')
    val result = append('h', 'e', 'l', 'l', 'o',' ', *world,num = 1) //*world，依次添加world字符数组中的元素
    println("append 可变数量参数 $result")
```

### 1.3方法作用域

方法可以在文件顶层声明

除了顶层方法，Kotlin 中方法也可以声明在局部作用域、作为成员方法以及扩展方法

#### 1.3.1局部方法

一个方法在另一个方法内部

局部方法可以访问外部方法（即闭包）的局部变量

```kotlin
fun magic(): Int {
    //foo是局部方法
    fun foo(v: Int): Int {
        return v * v
    }

    val v1 = (0..100).random()
    return foo(v1)
}
```

## 2.Lambda表达式

### 2.1 Lambda表达式的特点

- 是匿名方法
- 二是可传递

### 2.2 Lambda语法

- 无参数

```kotlin
val/var 变量名 = {操作代码}
```

- 有参数

```kotlin
val/var 变量名:(参数类型，参数类型....) -> 返回的类型={参数1，参数2...... -> 操作参数的代码}
```

```kotlin
// 此种写法：即表达式的返回值类型会根据操作的代码自推导出来。
val/var 变量名 = { 参数1 ： 类型，参数2 : 类型, ... -> 操作参数的代码 }
```

### 2.3 Lambda实践

```kotlin
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
```

调用

```kotlin
val number:Array<Int> = arrayOf(1,2,3,4)
change(number){ index, element ->
    index * element //方法体最后一句是return
}
```

#### 2.3.1 隐式参数 it

- it并不是Kotlin中的一个关键字(保留字)
- it是在当一个高阶方法中Lambda表达式的参数只有一个的时候可以使用it来使用此参数
- it可表示为单个参数的隐式名称，是Kotlin语言约定的

调用时

```kotlin
number.forEach {
    print("$it ")
}
println()
```

多个参数就指明参数名类型

```kotlin
//forEachIndexed 参数是下标和元素
number.forEachIndexed{index: Int, element: Int ->
    println("forEachIndexed $index : $element")
}
//显示调用
/*number.forEachIndexed(action = {index: Int, element: Int ->
    println("forEachIndexed $index : $element")
})*/
```

#### 2.3.2 下划线_

在使用Lambda表达式的时候，可以用下划线(_)表示未使用的参数，表示不处理这个参数

```kotlin
val map = mapOf("key1" to "1", "key2" to "2", "key3" to "3")
map.forEach { (key, value) ->
    println("$key \t $value")
}

// 不需要key的时候
map.forEach { (_, value) ->
    println(value)
}
```

