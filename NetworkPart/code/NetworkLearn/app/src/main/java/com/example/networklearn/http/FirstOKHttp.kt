package com.example.networklearn.http

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 *@author: hairly owl
 *@time:2021/10/27 20:09
 *@version: 1.00
 *@description: 学习OkHttp get,post请求
 */

object FirstOKHttp{
    private val BASE_URL = "http://123.56.232.18:8080/serverdemo"
    //加载时首先调用这个init
    /*private val client:OkHttpClient
    init{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client= OkHttpClient.Builder()  //builder构造者设计模式
            .connectTimeout(10 , TimeUnit.SECONDS) //连接超时时间
            .readTimeout(10 , TimeUnit.SECONDS)    //读取超时
            .writeTimeout(10 , TimeUnit.SECONDS)   //写超时
            .addInterceptor(httpLoggingInterceptor) //拦截器
            .build()
    }*/

    private val client:OkHttpClient = OkHttpClient.Builder()  //builder构造者设计模式
        .connectTimeout(10 , TimeUnit.SECONDS) //连接超时时间
        .readTimeout(10 , TimeUnit.SECONDS)    //读取超时
        .writeTimeout(10 , TimeUnit.SECONDS)   //写超时
        .addInterceptor(LoggingInterceptor()) //拦截器
        .build()

    /*
    * 1get请求
    * */
    //1.1同步请求  不能放在主线程里
    fun get(url : String){
        //子线程  为了不让等待响应时影响整个app 一般放在子线程里执行
        Thread {
            //构建请求体
            val request: Request = Request.Builder().url(url).build()
            //构造请求对象
            val call: Call = client.newCall(request)
            //发起同步请求execute——同步执行
            val response = call.execute()
            //execute执行完后
            val body = response.body?.string()
            Log.e("OkHttp", "get response $body")
        }.start()
    }

    //1.2异步请求  能够在主线程
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
                Log.e("OkHttp","get response $body")
            }
        })
    }


    /*、
    *2post请求
    * */
    //2.1同步post请求 不能在主线程中使用
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

        Thread {
            val response = call.execute()
            Log.e("OKHTTP POST", "post response ${response.body?.string()}")
        }.start()
    }

    //2.2异步POST
    // 2.2.1异步表单提交
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

        call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP POST" , "post onFailure ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("OKHTTP POST" , "post onResponse ${response.body?.string()}")
            }
        })
    }

    //2.2.2异步表单文件上传
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
                file.asRequestBody("application/octet-stream".toMediaType())
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

    //2.2.3 异步提交字符串
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
            jsonObj.toString().toRequestBody(textPlain)


        val request = Request.Builder()
            .url("$BASE_URL")
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



}