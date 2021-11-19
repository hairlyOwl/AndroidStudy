package com.example.componentlearn

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.componentlearn.databinding.ActivitySecondBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import java.lang.IllegalStateException

/**
 *@author: hairly owl
 *@time:2021/11/3 16:25
 *@version: 1.00
 *@description: Activity组件学习
 */
class SecondActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //改变选中按钮的颜色
        binding.toggleGroup.addOnButtonCheckedListener { group:MaterialButtonToggleGroup, checkedId:Int , isChecked:Boolean ->
            val childCount = group.childCount
            var selectIndex = 0
            for (index in 0 until childCount){
                val button = group.getChildAt(index) as MaterialButton
                if(button.id == checkedId){
                    //选中的按钮变红
                    selectIndex = index
                    button.setTextColor(Color.CYAN)
                    button.iconTint = ColorStateList.valueOf(Color.CYAN)
                } else {
                    button.setTextColor(Color.BLACK)
                    button.iconTint = ColorStateList.valueOf(Color.BLACK)
                }
            }
            switchFragment(selectIndex)
        }
        binding.toggleGroup.check(binding.tab1.id)


/*        val fragment  = SecondFragment()
        //给Fragment传递数据
        val bundle = Bundle()
        bundle.putInt("int_extra" , 100)
        bundle.putString("string_extra" , "string_extra")
        fragment.arguments = bundle
        //事务操作对象
        val ft:FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.container , fragment)
        ft.commitAllowingStateLoss()*/



        //activity生命周期
/*        val stringExtra = intent.getStringExtra("extra_data")
        val intExtra = intent.getIntExtra("extra_int_data" , 0 )

        *//*
        * 创建文本视图
        * *//*
        val textView = TextView(this)
        textView.text = "SecondActivity ${stringExtra} ---- ${intExtra}"
        textView.gravity = Gravity.CENTER
        setContentView(textView)

        textView.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("result_extra_string" , "result_extra_string")
            resultIntent.putExtra("result_extra_int" , 1000)
            setResult(Activity.RESULT_OK , resultIntent)
            finish() //关闭SecondActivity 等同于点击返回键
        }*/

        Log.e("SecondActivity: ","onCreate")
    }

    private var tab1Fragment:SecondFragment?=null
    private var tab2Fragment:SecondFragment?=null
    private var tab3Fragment:SecondFragment?=null
    private var shownFragment:Fragment ?= null //当前正在显示的Fragment

    //切换Fragment
    private fun switchFragment(selectIndex: Int) {
        //根据选中tab的id返回fragment
         val fragment = when(selectIndex){
            0->{
                if(tab1Fragment == null){
                    tab1Fragment = SecondFragment()
                    val bundle = Bundle()
                    bundle.putString("tab" , "tab1")
                    tab1Fragment!!.arguments = bundle
                }
                tab1Fragment
            }1->{
                if(tab2Fragment == null){
                    tab2Fragment = SecondFragment()
                    val bundle = Bundle()
                    bundle.putString("tab" , "tab2")
                    tab2Fragment!!.arguments = bundle
                }
                tab2Fragment
            }2->{
                if(tab3Fragment == null){
                    tab3Fragment = SecondFragment()
                    val bundle = Bundle()
                    bundle.putString("tab" , "tab3")
                    tab3Fragment!!.arguments = bundle
                }
                tab3Fragment
            }else -> {
                throw IllegalStateException("下标不符合预期")
            }
         }?:return


        val ft = supportFragmentManager.beginTransaction()
        if(!fragment.isAdded){
            ft.add(R.id.container , fragment)
        }
        ft.show(fragment)
        //避免出现多个fragment重叠
        if(shownFragment !=null){
            ft.hide(shownFragment!!)
        }
        shownFragment = fragment
        ft.commitAllowingStateLoss()
    }


    override fun onStart() {
        super.onStart()
        Log.e("SecondActivity: ","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("SecondActivity: ","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("SecondActivity: ","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("SecondActivity: ","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SecondActivity: ","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("SecondActivity: ","onRestart")
    }

}