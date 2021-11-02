package com.example.networklearn

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.networklearn.databinding.ActivityMainBinding
import com.example.networklearn.http.ApiService
import com.example.networklearn.http.FirstOKHttp
import com.example.networklearn.http.FirstRetrofit
import com.example.networklearn.http.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        //FirstOKHttp.get("https://www.baidu.com")
        //FirstOKHttp.getAsync("https://www.baidu.com")
        //FirstOKHttp.post()
        //FirstOKHttp.postAsync()
        //FirstOKHttp.postAsyncMultipart(context = ...)
        //FirstOKHttp.postAsyncString()

        //onResponse 和 onFailure 的回调都是在主线程里面
        val apiService = FirstRetrofit.create(ApiService::class.java)
        apiService.queryUser("1600933269").enqueue(object :Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.e("Retrofit",response.body()?.toString()?:"response is null")
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Retrofit",t.message?:"unknown reason")
            }
        })


    }
}