package com.example.componentlearn

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast

/**
 *@author: hairly owl
 *@time:2021/11/14 15:38
 *@version: 1.00
 *@description: 定义一个广播接收者
 */
class TestBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        //intent事件类型
        val action = intent?.action?:return //如果为空则不处理
        if(action == ConnectivityManager.CONNECTIVITY_ACTION){
            val connectivityManager:ConnectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo //获取网络连接类型
            Log.e("TestBroadcastReceiver","${info.toString()}")
            if(info!=null && info.isAvailable){
                //有网络连接的
                val typeName =info.typeName
                Toast.makeText(context , "当前网络连接类型${typeName}" ,  Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context , "当前无网络连接" ,  Toast.LENGTH_LONG).show()
            }
        }else if(action == "com.example.componentLearn.TEST_BROADCAST_RECEIVER"){
            Log.e("action","TEST_BROADCAST_RECEIVER")
            Toast.makeText(context , "静态注册广播，接受到了自定义的事件" ,  Toast.LENGTH_LONG).show()
        }
    }
}