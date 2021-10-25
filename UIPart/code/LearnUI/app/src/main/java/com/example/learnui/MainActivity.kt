package com.example.learnui

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.learnui.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))*/

        //setupActionBarWithNavController(navController, appBarConfiguration) //因为主题设置没有状态栏
        navView.setupWithNavController(navController)





        //MaterialButtonToggleGroup 事件和布局文件的绑定
/*        setContentView(R.layout.comps_material_button)
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener(object :MaterialButtonToggleGroup.OnButtonCheckedListener{
            override fun onButtonChecked(
                group: MaterialButtonToggleGroup?,
                checkedId: Int,
                isChecked: Boolean
            ) {
                Log.e("Main" , "$checkedId , $isChecked")
            }
        })*/



    }
}