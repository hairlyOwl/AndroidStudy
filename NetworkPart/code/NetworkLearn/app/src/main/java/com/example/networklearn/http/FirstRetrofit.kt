package com.example.networklearn.http

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.POST





/**
 *@author: hairly owl
 *@time:2021/10/30 22:59
 *@version: 1.00
 *@description: Retrofit 练习
 */
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