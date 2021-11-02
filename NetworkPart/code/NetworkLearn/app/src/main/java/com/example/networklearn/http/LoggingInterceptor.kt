package com.example.networklearn.http

import android.util.Log
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

/**
 *@author: hairly owl
 *@time:2021/10/29 11:33
 *@version: 1.00
 *@description: 自定义拦截器  统一打印网络拦截日志
 */

class LoggingInterceptor : Interceptor{
    //拦截
    override fun intercept(chain: Interceptor.Chain): Response {
        val time_start = System.nanoTime() //发出请求的时间戳
        val request = chain.request() //当前请求的request对象
        val response = chain.proceed(request) //当前请求的response对象

        val buffer = Buffer()
        //method 请求方法  cacheControl缓存策略  isHttps 等等都可以自定义打印输出
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