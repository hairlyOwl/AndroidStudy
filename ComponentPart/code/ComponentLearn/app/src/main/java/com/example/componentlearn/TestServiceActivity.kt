package com.example.componentlearn

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.componentlearn.databinding.ActivityTestServiceBinding

/**
 *@author: hairly owl
 *@time:2021/11/11 22:06
 *@version: 1.00
 *@description: 启动service 的 Activity
 */
class TestServiceActivity : Activity(){

    private lateinit var binding : ActivityTestServiceBinding
    private var connection: ServiceConnection?=null
    private var myBinder: TestService2.MyBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        connection = object:ServiceConnection{
            //Activity与Service连接成功时回调该方法
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.e("TestService2","--------------Service  Connected--------------")
                myBinder = service as TestService2.MyBinder
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                //Activity与Service断开连接时回调该方法
                Log.e("TestService2","--------------Service  Disconnected--------------")
            }

        }*/

        //绑定service
/*        val intent = Intent(this , TestService2::class.java)
        bindService(intent , connection!! , Context.BIND_AUTO_CREATE)*/

        //延时操作
        val intent = Intent(this , TestService2::class.java)
        Handler().postDelayed(Runnable{
            //startService(Intent(this,TestService2::class.java))
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //安卓版本高于8.0   26或者Build.VERSION_CODES.O
                startForegroundService(intent)
            }else{
                startService(intent)
            }
        } , 70*1000)

        //启动服务
        binding.startService.setOnClickListener {
/*            val intent = Intent(this , TestService1::class.java)
            startService(intent)*/

            Log.e("TestService2","*********** getCount:=${myBinder?.getCount()} ***********")
        }
        //停止服务
        binding.stopService.setOnClickListener {
            //普通启动starService()
/*            val intent = Intent(this , TestService1::class.java)
            stopService(intent)*/

            //绑定启动bindService()
            unbindService(connection!!)

        }
    }

    //否则Activity会发生内存泄露
    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection!!)
    }
}