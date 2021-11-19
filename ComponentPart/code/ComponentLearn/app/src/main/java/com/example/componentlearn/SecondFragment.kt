package com.example.componentlearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 *@author: hairly owl
 *@time:2021/11/7 21:47
 *@version: 1.00
 *@description: Fragment 的生命周期 动态添加
 */
class SecondFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("SecondFragment","onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SecondFragment","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textview = TextView(context)
        textview.text = "secondFragment"
        textview.gravity = Gravity.CENTER
        Log.e("SecondFragment","onCreateView")

        return textview
    }

    /*
    * 取传给fragment的值
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        val intValue = arguments?.getInt("int_extra")
        val strValue = arguments?.getString("string_extra")*/

        //view是onCreateView return的view
        val textview = view as TextView
        textview.text = "${arguments?.getString("tab")}"

        textview.setOnClickListener {
            val intent = Intent(context ,
                TestBroadcastReceiverActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("SecondFragment","onStart")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //当且仅当activity存在多个fragment ， 并且我们调用了show - hide
        //hidden 当前fragment 不可见的时候为true
        //hidden 当前fragment 可见的时候为false
        Log.e("SecondFragment" , "onHiddenChanged : ${arguments?.getString("tab")}-- $hidden")
    }

    override fun onResume() {
        super.onResume()
        Log.e("SecondFragment","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("SecondFragment","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("SecondFragment","onStop")
    }

    override fun onDestroyView() {
        //不是每次都能触发
        //onCreateView返回的对象被销毁的时候 会执行这个回调
        super.onDestroyView()
        Log.e("SecondFragment","onDestroyView")
    }

    override fun onDestroy() {
        //fragment被销毁的时候
        super.onDestroy()
        Log.e("SecondFragment","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("SecondFragment","onDetach")
    }


}