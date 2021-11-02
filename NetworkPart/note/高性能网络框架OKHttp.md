# 一、 高性能网络框架OKHttp

Android 在6.0之后也将内部的HttpUrlConnection的默认实现替换成了OkHttp

## 1. 特点

- 1.同时支持**HTTP1.1**与支持**HTTP2.0**；
- 2.同时支持**同步与异步**请求；
- 3.同时具备HTTP与WebSocket功能；
- 4.拥有自动维护的socket连接池，减少握手次数；
- 5.拥有队列线程池，轻松写并发；
- 6.拥有Interceptors(拦截器)，轻松处理请求与响应额外需求(例：请求失败重试、响应内容重定向等等)；

> 图片和部分文字转载自https://www.songyubao.com/book/primary/network/OKHttp.html

![OkHttp](https://cdn.jsdelivr.net/gh/hairlyOwl/photo@master/OkHttp.60tgd7te3gg0.png)

## 2.准备阶段

### step1：添加网络访问权限

在AndroidManifest.xml中添加

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### step2：添加依赖

在`app/build.gradle`的`dependencies`中添加下面的依赖

```groovy
implementation("com.squareup.okhttp3:okhttp:4.9.0")

// 网络请求日志打印
implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
```

### step3：初始化

```kotlin
val client:OkHttpClient  = OkHttpClient.Builder()    //builder构造者设计模式
        .connectTimeout(10, TimeUnit.SECONDS) //连接超时时间
        .readTimeout(10, TimeUnit.SECONDS)    
        .writeTimeout(10, TimeUnit.SECONDS)  //写超时，也就是请求超时
        .build()
```

### Tips:

1.object对象的方法调用不用实例化，class对象的方法调用需要实例化

object对象在APP整个生命周期只有一份



## 3. GET请求

Android 分为`主线程` 和 `子线程`

`主线程`：APP一启动后，Android framework层会启动一个线程，主线程(UI线程)

`子线程` 例如 new Thread().start()

### 3.1同步GET请求

- 同步GET的意思是一直等待http请求, 直到返回了响应. 在这之间会阻塞线程, 所以**同步请求不能在**Android的**主线程**中执行, 否则会报错NetworkMainThreadException.

- 发送同步`GET`请求很简单：

1. 创建`OkHttpClient`实例`client`
2. 通过`Request.Builder`构建一个`Request`请求实例`request`
3. 通过`client.newCall(request)`创建一个`Call`的实例
4. `Call`的实例调用`execute`方法发送同步请求
5. 请求返回的`response`转换为`String`类型返回

```kotlin
object FirstOKHttp{
    val client:OkHttpClient = OkHttpClient.Builder()  //builder构造者设计模式
        .connectTimeout(10 , TimeUnit.SECONDS) //连接超时时间
        .readTimeout(10 , TimeUnit.SECONDS)    //读取超时
        .writeTimeout(10 , TimeUnit.SECONDS)   //写超时
        .build()

    //同步请求
    fun get(url : String){
        //子线程  为了不让等待响应时影响整个app 一般放在子线程里执行
        Thread(Runnable {
            //构建请求体
            val request:Request = Request.Builder().url(url).build()
            //构造请求对象
            val call: Call = client.newCall(request)
            //发起同步请求execute——同步执行
            val response = call.execute()
            //execute执行完后
            val body = response.body?.string()
            Log.e("OkHttp","get response ${body}")
        }).start()
    }

}
```

### 3.2异步请求

```kotlin
//异步请求  能够在主线程
fun getAsync(url : String){
    //构建请求体
    val request:Request = Request.Builder().url(url).build()
    //构造请求对象
    val call: Call = client.newCall(request)
    //发起异步请求enqueue——异步执行无返回值
    call.enqueue(object :Callback{
        //响应失败
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OkHttp","get response ${e.message}")
        }

        //响应成功
        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            Log.e("OkHttp","get response ${body}")
        }
    })
}
```

异步请求的步骤和同步请求类似，只是调用了`Call`的`enqueue`方法异步请求，结果通过回调`Callback`的`onResponse`方法及`onFailure`方法处理。

看了两种不同的Get请求，基本流程都是先创建一个`OkHttpClient`对象，然后通过`Request.Builder()`创建一个`Request`对象，`OkHttpClient`对象调用`newCall()`并传入`Request`对象就能获得一个`Call`对象。

而同步和异步不同的地方在于`execute()`和`enqueue()`方法的调用，

调用`execute()`为同步请求并返回`Response`对象；

调用`enqueue()`方法测试通过callback的形式返回`Response`对象。

> 注意：无论是同步还是异步请求，接收到`Response`对象时均在子线程中，`onFailure`，`onResponse`的回调是在子线程中的,我们需要切换到主线程才能操作UI控件

### Tips 

1.网络请求处理明文请求

在AndroidManifest.xml中添加

```groovy
 <application
       ......
        android:usesCleartextTraffic="true">
```

2.kotlin中想要实现一个接口的实例对象要使用关键字object

## 4.POST请求

POST请求与GET请求不同的地方在于`Request.Builder`的`post()`方法，`post()`方法需要一个`RequestBody`的对象作为参数

### 4.1 同步POST请求

```kotlin
//同步post请求 不能在主线程中使用
fun post(){
    //表单提交
    val body = FormBody.Builder()
        .add("userId","1600932269")
        .add("tagId","71")
        .build()
    val request:Request =
        Request.Builder().url("$BASE_URL/tag/toggleTagFollow")
            .post(body)
            .build()
    val call = client.newCall(request)

    Thread(Runnable {
        val response = call.execute()
        Log.e("OKHTTP POST" , "post response ${response.body?.string()}")
    }).start()
}
```

### 4.2 异步POST请求

#### 4.2.1异步表单提交

```kotlin
//异步POST 异步表单提交
fun postAsync(){
    //表单提交
    val body = FormBody.Builder()
        .add("userId","1600932269")
        .add("tagId","71")
        .build()
    val request:Request =
        Request.Builder().url("$BASE_URL/tag/toggleTagFollow")
            .post(body)
            .build()
    val call = client.newCall(request)

    val response = call.enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OKHTTP POST" , "post onFailure ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.e("OKHTTP POST" , "post onResponse ${response.body?.string()}")
        }
    })
}
```

#### 4.2.2 异步表单文件上传

> Android6.0及以后，读取外部存储卡的文件需要动态申请权限

```kotlin
fun postAsyncMultipart(context:Context){
    //外部存储文件对象
    //Android6.0及以后，读取外部存储卡的文件需要动态申请权限
    val file = File(Environment.getDownloadCacheDirectory(),"test.jpg")
    if(!file.exists()){
        Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show()
        return
    }
    
    val body = MultipartBody.Builder()
        .addFormDataPart("key1","value1")
        .addFormDataPart("key2","value2")
            //后台接受参数的名称  要上传文件的本地文件名称
        .addFormDataPart(
            "file" ,
            "file.jpg",
            RequestBody.create("application/octet-stream".toMediaType(),file)
        )
        .build()

    val request:Request =
        Request.Builder().url("接口也是需要支持文件上传才可以使用")
            .post(body)
            .build()

    val call = client.newCall(request)
    call.enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OKHTTP POST" , "postAsyncMultipart onFailure ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.e("OKHTTP POST" , "postAsyncMultipart onResponse ${response.body?.string()}")
        }
    })
}
```

#### 4.2.3  异步提交字符串

```kotlin
//传入的数据不是key,value 而是 字符串
fun postAsyncString(){
    //字符串可以是纯文本也可以是Json类型的字符串
    //JSON类型的字符串
    val applicationJSON = "application/json;charset=utf-8".toMediaType()
    val jsonObj = JSONObject()
    jsonObj.put("key1","value1")
    jsonObj.put("key2",120)
    //val body = RequestBody.create(applicationJSON,jsonObj.toString())

    //纯文本类型
    val textPlain = "text/plain;charset=utf-8".toMediaType()
    val textObj = "username:username;password:password"
    val body =
        RequestBody.create(textPlain,jsonObj.toString())


    val request = Request.Builder()
        .url("${BASE_URL}")
        .post(body)
        .build()
    val call = client.newCall(request)

    call.enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e("OKHTTP","postAsyncString onFailure ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.e("OKHTTP","postAsyncString onResponse ${response.body?.string()}")
        }

    })

}
```

## 5.拦截器

拦截器是OkHttp当中一个比较强大的机制，以解耦的形式可以监视、重写和重试调用请求

### 5.1 自定义拦截器LoggingInterceptor

```kotlin
class LoggingInterceptor : Interceptor{
    //拦截
    override fun intercept(chain: Interceptor.Chain): Response {
        val time_start = System.nanoTime() //发出请求的时间戳
        val request = chain.request() //当前请求的request对象
        val response = chain.proceed(request) //当前请求的response对象

        val buffer = Buffer()
        request.body?.writeTo(buffer)
        val requestBodyStr = buffer.readUtf8()
        //请求接口和请求接口值所携带的参数
        Log.e(
            "OKHTTP" ,
            String.format("Sending request %s with params %s",request.url,requestBodyStr)
        )

        //响应流里面的数据 一但使用response.body?.string() 今后就不可拿response读取它的响应流这是由于OKHttp的工作原理决定的
        val businessData = response.body?.string()?:"response body null"
        val mediaType = response.body?.contentType()
        val newBody = businessData.toResponseBody(mediaType)
        val newResponse = response.newBuilder().body(newBody).build()

        val time_end = System.nanoTime()

        //响应接口 响应时间(1e6是科学计数法) 响应流数据
        Log.e(
            "OKHTTP",
            String.format("Received response for %s in $.1fms >>> %s",request.url,(time_end-time_start)/1e6,businessData)
        )

        return newResponse
    }

}
```

使用

```kotlin
private val client:OkHttpClient = OkHttpClient.Builder()  //builder构造者设计模式
    .connectTimeout(10 , TimeUnit.SECONDS) //连接超时时间
    .readTimeout(10 , TimeUnit.SECONDS)    //读取超时
    .writeTimeout(10 , TimeUnit.SECONDS)   //写超时
    .addInterceptor(LoggingInterceptor()) //拦截器
    .build()
```

### 5.2 内置拦截器HttpLoggingInterceptor

```kotlin
private val client:OkHttpClient
init{
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    client= OkHttpClient.Builder()  //builder构造者设计模式
        .connectTimeout(10 , TimeUnit.SECONDS) //连接超时时间
        .readTimeout(10 , TimeUnit.SECONDS)    //读取超时
        .writeTimeout(10 , TimeUnit.SECONDS)   //写超时
        .addInterceptor(httpLoggingInterceptor) //拦截器
        .build()
}
```

tips:

- [System.currentTimeMillis()和System.nanoTime()的区别](https://www.cnblogs.com/andy-songwei/p/10784049.html)
- 一但使用`response.body?.string() `今后就不可拿response读取它的响应流这是由于OKHttp的工作原理决定的



## 6.使用Gson来解析网络请求响应

在`app/build.gradle`中添加以下依赖配置

```kotlin
dependencies {
  implementation 'com.google.code.gson:gson:2.8.6'
}
```

### 6.1  JSON与对象

将JSON转换为对象

```kotlin
val json ="{\"uid\":\"00001\",\"userName\":\"Freeman\",\"telNumber\":\"13000000000\"}"

//Gson对象啊
val gson = Gson()
val account = gson.fromJson<Account>(json,Account::class.java)
println(account.toString())
```

将对象转换为JSON

```kotlin
//1.2将对象转换为JSON
val accountJson = gson.toJson(account)
println("toJson : $accountJson")
```

### 6.2集合与JSON

将JSON转换成集合

```kotlin
//2.1将JSON转换成集合
val jsonList= "[{\"uid\":\"00001\",\"userName\":\"Freeman\",\"telNumber\":\"13000000000\"}" +
        ",{\"uid\":\"00002\",\"userName\":\"oooo\",\"telNumber\":\"12000000000\"}]"

val accountList:List<Account> =  gson.fromJson(jsonList , object :TypeToken<List<Account>>(){}.type)
println("fromJson to list : ${accountList.size}")
```

将集合转换成JSON

```kotlin
val accountJsonList = gson.toJson(accountList)
println("list to json : $accountJsonList")
```

Tips:

- **var** 关键字是 **variable** 的简称，表示该变量可以被修改，，这种声明变量的方式和java中声明变量的方式一样。
- **val** 关键字是 **value** 的简称，表示该变量一旦赋值后不能被修改，相当于java中的final变量。一个val创建的时候必须初始化，因为以后不能被改变。
- `{}`对象形式json字串      ` []`集合形式的json字串

#### JsonToKotlin 插件

settings--> plugins 搜索安装就可以
