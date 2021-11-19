package com.example.componentlearn

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 *@author: hairly owl
 *@time:2021/11/14 16:04
 *@version: 1.00
 *@description: 运行时动态注册广播接收事件
 */
class TestBroadcastReceiverActivity : AppCompatActivity(){

    private lateinit var receiver: TestBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        * 动态注册
        * */
/*        receiver = TestBroadcastReceiver()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver , intentFilter)  //动态注册*/

        /*
        * 自定义广播 全局广播
        * */
/*        val intent = Intent()
        intent.action = "com.example.componentLearn.TEST_BROADCAST_RECEIVER"
        // 下面这一行在Android 7.0及以下版本不是必须的，但是Android 8.0或者更高版本，发送广播的条件更加严苛，必须添加这一行内容。
        // 创建的ComponentName实例化对象有两个参数，第1个参数是指接收广播类的包名，第2个参数是指接收广播类的完整类名。
        intent.component = ComponentName(packageName,"com.example.componentlearn.TestBroadcastReceiver")
        sendBroadcast(intent)*/


        /*
        * 应用内自定义广播
        * */
        receiver = TestBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.componentLearn.TEST_BROADCAST_RECEIVER")
        //自定义广播的注册
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver , intentFilter)
        //自定义广播的发送
        Handler().postDelayed(Runnable {
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("com.example.componentLearn.TEST_BROADCAST_RECEIVER"))
        },2000) //延迟发送确保广播已注册

    }

    override fun onDestroy() {
        super.onDestroy()
        // 必须要在onDestroy时反注册，否则会内存泄漏
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }
}