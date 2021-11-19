package com.example.componentlearn

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 *@author: hairly owl
 *@time:2021/11/11 21:56
 *@version: 1.00
 *@description: 普通启动startService() 的生命周期
 */
class TestService1 : Service(){

    private var TAG = "TestService1"

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate")
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.e(TAG,"onBind")
        return null
    }

    /*
    *     对于使用startService的方式而言，`onStartCommand`就是我们用于做后台任务的地方，
    * 如果我们多次点击startService按钮，会直接调`onStartCommand`,而不再回调`onCreate`
    *
    *     普通启动startService()，它的生命周期和应用程序的生命周期一样长，只要应用程序不被杀死，
    * 服务就会一直运行 ，除非我们使用stopService()
    * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.e(TAG,"onDestroy")
        super.onDestroy()
    }
}