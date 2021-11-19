package com.example.componentlearn

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.componentlearn.databinding.ActivityMainBinding
import android.provider.MediaStore
import android.graphics.Bitmap







class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //activity生命周期
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)*/
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
/*        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )*/
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)

        /*
        * 创建文本视图
        * */
        val textView = TextView(this)
        textView.text = "MainActivity"
        textView.gravity = Gravity.CENTER
        setContentView(textView)

        /*
        * 显示启动
        * */
/*
        textView.setOnClickListener {
            //无参跳转
            //MainActivity@this : Context
            */
/*val intent = Intent(MainActivity@this , SecondActivity::class.java)
            startActivity(intent)*//*


            //有参跳转
            val intent = Intent(this , SecondActivity::class.java)
            intent.putExtra("extra_data","extra_data")
            intent.putExtra("extra_int_data",100)
        }

        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            val data = result.data
            val resultCode = result.resultCode
            val stringExtraResult = data?.getStringExtra("result_extra_string")
            val intExtraResult = data?.getIntExtra("result_extra_int",0)

            textView.text = "MainActivity result [${resultCode}] ${stringExtraResult} ---- ${intExtraResult}"
        }.launch(Intent(this,SecondActivity::class.java))
*/

        /*
        * 隐式启动
        * */
/*        textView.setOnClickListener {
            val intent = Intent()
            intent.action = "com.example.componentLearn.action.SECONDACTIVITY"
            intent.addCategory("com.example.componentLearn.category.SECONDACTIVITY")

            intent.putExtra("extra_data" , "extra_data")
            intent.putExtra("extra_int_data" , 111)
            startActivity(intent)
        }*/



        /*
        * 常见Activity
        * */
        textView.setOnClickListener {
            //拨打电话
            /*val uri: Uri = Uri.parse("tel: 11111")
            val intent = Intent(Intent.ACTION_DIAL,uri)
            startActivity(intent)*/

            //发送短信
            /*val uri:Uri = Uri.parse("smsto:1111")
            val intent = Intent(Intent.ACTION_SENDTO , uri)
            intent.putExtra("sms_body" , "发送短信")
            startActivity(intent)*/

            //打开网页
            /*val uri:Uri = Uri.parse("http://www.baidu.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)*/

            //多媒体
            /*val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse("file:///sdcard/foo.mp3")
            intent.setDataAndType(uri, "audio/mp3")
            startActivity(intent)*/
        }
        Log.e("MainActivity: ","onCreate")
    }


    override fun onStart() {
        super.onStart()
        Log.e("MainActivity: ","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity: ","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("MainActivity: ","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity: ","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity: ","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity: ","onRestart")
    }


}