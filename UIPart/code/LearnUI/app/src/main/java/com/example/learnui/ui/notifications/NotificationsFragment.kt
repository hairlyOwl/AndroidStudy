package com.example.learnui.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.learnui.R
import com.example.learnui.databinding.FragmentDashboardBinding
import com.example.learnui.databinding.FragmentNotificationsBinding
import com.example.learnui.databinding.ItemViewGridBinding

//瀑布流
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    //Fragment被解析为view对象时会回调这个方法
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 几列 方向
        FragmentNotificationsBinding.bind(view).recyclerView.layoutManager =
            StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL)
        FragmentNotificationsBinding.bind(view).recyclerView.adapter = MyAdapter()
    }

    //内部类可以访问外部类的对象 为了拿到context
    inner class  MyAdapter: RecyclerView.Adapter<MyViewHolder>(){
        //创建对应的ViewHolder对象
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            //资源文件id ViewGroup attachToRoot
            val itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_view_grid, parent, false)
            return MyViewHolder(itemView)
        }

        //完成数据的绑定
        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //载入图片的三种方式
            /*holder.itemView.item_image.setImageResource(R.drawable.android_icon)
            holder.itemView.item_image.setImageDrawable(ContextCompat.getDrawable(context!!,R.drawable.android_icon))
            holder.itemView.item_image.setImageBitmap(BitmapFactory.decodeResource(context!!.resources , R.drawable.android_icon))*/

            with(holder){
                binding.itemImage.setImageResource(R.drawable.android_icon)
                //改变部分数据的高度
                if(position == 0 || position == 4 || position == 3 ||position == 7 || position == 9 ){
                    binding.itemMessage.setSingleLine(false) //不单行
                }else{
                    binding.itemMessage.setSingleLine(true) //单行
                }

                binding.itemTitle.text = "这是第 ${position+1} 个标题"
                binding.itemMessage.text = "这是第 ${position+1} 个内容，这是第 ${position} 个内容，这是第 ${position} 个内容，这是第 ${position} 个内容"
            }
        }

        //告诉列表有多少数据
        override fun getItemCount(): Int {
            return 20
        }
    }

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val binding = ItemViewGridBinding.bind(view)
    }
}