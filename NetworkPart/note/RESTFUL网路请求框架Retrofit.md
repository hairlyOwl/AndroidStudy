# 二、RESTFUL网路请求框Retrofit

`Retrofit`是一个高质量高效率的HTTP请求库。Retrofit内部依赖于OkHttp，它将OKHttp底层的代码和细节都封装了起来，功能上做了更多的扩展,比如返回结果的自动解析数据模型，网络引擎的切换，拦截器......

> restFul是符合rest架构风格的网络API接口,完全承认Http是用于标识资源。restFul URL是面向资源的，可以唯一标识和定位资源。 对于该URL标识的资源做何种操作是由Http方法决定的。 
>
> rest请求方法有4种，包括get,post,put,delete.分别对应获取资源，添加资源，更新资源及删除资源.
>
> 
>
> 作者：埋着头向前走寻找我自己
> 链接：https://www.jianshu.com/p/dfe3077ddbcd
> 来源：简书
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## 1. 注解

retrofit注解驱动型上层网络请求框架，使用注解来简化请求，大体分为以下几类：

- 用于标注**网络请求方式**的注解
- 标记**网络请求参数**的注解
- 用于标记网络**请求和响应格式**的注解

> 图片和部分文字转自https://www.songyubao.com/book/primary/network/Retrofit.html

![retrofit注解](https://cdn.jsdelivr.net/gh/hairlyOwl/photo@master/retrofit注解.72sby7k1xd00.png)

### 1.1请求方法注解

| 注解     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| @GET     | get请求                                                      |
| @POST    | post请求                                                     |
| @PUT     | put请求                                                      |
| @DELETE  | delete请求                                                   |
| @PATCH   | patch请求，该请求是对put请求的补充，用于更新局部资源         |
| @HEAD    | head请求                                                     |
| @OPTIONS | option请求                                                   |
| @HTTP    | 通用注解,可以替换以上所有的注解，其拥有三个属性：method，path，hasBody |

###  1.2 请求头注解

既可以标记在方法上面也可以标记在参数里面

| 注解     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| @Headers | 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在 |
| @Header  | 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头 |

### 1.3请求参数注解

| 名称      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| @Body     | 多用于post请求发送非表单数据,比如想要以post方式传递json格式数据 |
| @Filed    | 多用于post请求中表单字段,Filed和FieldMap需要FormUrlEncoded结合使用 |
| @FiledMap | 和@Filed作用一致，用于不确定表单参数                         |
| @Part     | 用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况 |
| @PartMap  | 用于表单字段,默认接受的类型是Map，可用于实现多文件上传       |
| @Path     | 用于url中的占位符                                            |
| @Query    | 用于Get中指定参数                                            |
| @QueryMap | 和Query使用类似                                              |
| @Url      | 指定请求路径                                                 |

### 1.4请求和响应格式注解

| 名称            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| @FormUrlEncoded | 表示请求发送编码表单数据，每个键值对需要使用@Field注解       |
| @Multipart      | 表示请求发送multipart数据，需要配合使用@Part                 |
| @Streaming      | 表示响应用字节流的形式返回.如果没使用该注解,默认会把数据全部载入到内存中.该注解在在下载大文件的特别有用 |

## 2 开始使用

### step1: 添加依赖

app/build.gradle

```groovy
implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.squareup.retrofit2:converter-gson:2.3.0' //json转换
```

### step2:  初始化

```kotlin
object FirstRetrofit {
    //OkHttpClient 对象
    private val okClient:OkHttpClient = OkHttpClient.Builder() //builder构建者设计模式
        .connectTimeout(10,TimeUnit.SECONDS)   //连接超时时间
        .readTimeout(10,TimeUnit.SECONDS)      //读取超时
        .writeTimeout(10,TimeUnit.SECONDS)     //写超时
        .addInterceptor(LoggingInterceptor())         //拦截器
        .build()

    //初始化
    private val retrofit:Retrofit = Retrofit.Builder()
        .client(okClient)    //配置OkHttp网络请求框架的对象
        .baseUrl("http://123.56.232.18:8080/serverdemo/")  //网络请求的域名
        .addConverterFactory(GsonConverterFactory.create())   //数据转换适配器
        .build()

    //发起网络请求
    //public <T> T create(final Class<T> service)  java类型
    fun <T> create(clazz: Class<T>):T{
        return retrofit.create(clazz)
    }
}

//网络请求接口
interface ApiService{
    //@Query 的 encoded发起网络请求是要不要对接口进行编码(防止中文乱码)
    //@Query(value = "userId", encoded = true) 是用来修饰 userId:String
    @GET(value = "user/query")
    fun queryUser(@Query(value = "userId", encoded = true) userId:String):Call<UserResponse>
}
```

### step3:调用

异步用enqueue()，同步用execute()

```kotlin
        val apiService = FirstRetrofit.create(ApiService::class.java)
        apiService.queryUser("1600933269").enqueue(object :Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.e("Retrofit",response.body()?.toString()?:"response is null")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Retrofit",t.message?:"unknown reason")
            }
        })
```

Tips:

> onResponse 和 onFailure 的回调都是在主线程里面
> 可以直接操控UI控件

### 请求方法定义

```kotlin
//网络请求接口
interface ApiService{
    //@Query 的 encoded发起网络请求是要不要对接口进行编码(防止中文乱码)
    //@Query(value = "userId", encoded = true) 是用来修饰 userId:String
    @GET(value = "user/query")
    fun queryUser(@Query(value = "userId", encoded = true) userId:String):Call<UserResponse>

    //使用@Headers添加多个请求头
    @Headers("User-Agent:android", "apikey:123456789")
    @GET(value = "user/query")
    fun queryUser1(@Query(value = "userId" , encoded = true) userId: String):Call<UserResponse>

    //多个参数的情况下可以使用@QueryMap，但只能用在GET请求上
    @GET(value = "user/query")
    fun queryUser2(@QueryMap(encoded = true) queryMap: Map<String?, String?>):Call<UserResponse>

    /**
     * 很多情况下，我们需要上传json格式的数据。当我们注册新用户的时候，因为用户注册时的数据相对较多
     * 并可能以后会变化，这时候，服务端可能要求我们上传json格式的数据。此时就要@Body注解来实现。
     * 直接传入实体,它会自行转化成Json, @Body只能用在POST请求上
     *
     * 字符串提交
     */
    @POST("user/update")
    fun userUpdate(@Body post: User):Call<UserResponse>

    /**
     * 表单提交（键值对提交）
     * 多用于post请求中表单字段,Filed和FieldMap需要FormUrlEncoded结合使用
     */
    @POST()
    @FormUrlEncoded
    fun executePost(@FieldMap map:Map<String , User>):Call<UserResponse>

    /**
     * 表单上传文件（键值对提交、同时上传文件）
     */
    @Multipart
    @FormUrlEncoded
    @POST("upload/upload")
    fun register(
        @Field("openId") openId:String,
        @PartMap map:Map<String? , MultipartBody.Part>
    ):Call<UserResponse>
    
}
```

