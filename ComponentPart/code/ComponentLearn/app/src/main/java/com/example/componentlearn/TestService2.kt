package com.example.componentlearn

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log

/**
 *@author: hairly owl
 *@time:2021/11/11 21:56
 *@version: 1.00
 *@description: 绑定启动bindService() 的生命周期
 */

class TestService2 : Service(){

    private var TAG = "TestService2"
    private var count = 0
    private var quit = false //标记位 线程退出

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = Notification.Builder(applicationContext, "channel_id").build()
            startForeground(1,notification)
        }

        //线程开启
        Thread(Runnable {
            while(true){
                if(quit)
                    break
                Thread.sleep(1000)
                count++
            }
        }).start()
    }

    private val binder = MyBinder()

    //内部类 继承Binder类 ，Binder类实现IBinder
    inner class MyBinder : Binder(){
        fun getCount() : Int{
            return count
        }

    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.e(TAG,"onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG,"onUnbind")
        quit = true
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.e(TAG,"onDestroy")
        super.onDestroy()
    }
}