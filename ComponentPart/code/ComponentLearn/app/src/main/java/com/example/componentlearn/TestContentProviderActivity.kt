package com.example.componentlearn

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.componentlearn.databinding.ActivityContentProviderBinding


/**
 *@author: hairly owl
 *@time:2021/11/17 18:58
 *@version: 1.00
 *@description: ContentProvider 运行时动态申请权限
 */
class TestContentProviderActivity : AppCompatActivity(){

    private lateinit var binging : ActivityContentProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = ActivityContentProviderBinding.inflate(layoutInflater)
        val view = binging.root
        setContentView(view)

        initView()

        requestPermission()
    }


    /*
    * 按钮的点击事件
    * */
    private fun initView() {
        binging.getContact.setOnClickListener {
            getContacts()
        }

        binging.updateContact.setOnClickListener {
            updateContact()
        }

        binging.insertContact.setOnClickListener {
            insertContact()
        }

        binging.deleteContact.setOnClickListener {
            deleteContact()
        }

        binging.getMessage.setOnClickListener {
            getMessage()
        }
    }

    /*
    * 获取通讯录的权限
    * */
    private fun requestPermission() {
        val readable = ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        val writeable = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
        val readMsg = ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED

        //判断读取通讯录的允许
        if (!readable||writeable||readMsg) {
    /*            //如果用户选择 don`t ask again
                if(ActivityCompat.shouldShowRequestPermissionRationale(actionBar , android.Manifest.permission.READ_CONTACTS)){
                    //在这里弹窗引导用户 去设置页打开联系人的权限
                }*/
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_SMS),
                100
            )
        } else {
            //getContacts() //读取联系人
        }
    }

    /*
    * 通讯录的增删改查
    * */
    //读取联系人
    @SuppressLint("Range")
    private fun getContacts() {
        //查询对象
        val resolver = contentResolver
        //格式化一个uri
        val uri = Uri.parse("content://com.android.contacts/data/phones")
        //得到一个游标
        val cursor = resolver.query(uri, null, null, null, null)?:return

        while (cursor.moveToNext()) {
            //获取联系人名字和号码
            val displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            Log.e("ContentProvider", "姓名:$displayName")
            Log.e("ContentProvider", "号码:$phoneNumber")
            Log.e("ContentProvider", "======================")
        }
        cursor.close() //游标关闭！！
    }
    //插入联系人
    private fun insertContact() {
        /*
         * 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获得系统返回的rawContactId
         * 这时后面插入data表的数据，才能使插入的联系人在通讯录里面可见
         */
        val resolver = contentResolver
        val values = ContentValues()
        val rawContactUri = contentResolver!!.insert(ContactsContract.RawContacts.CONTENT_URI, values)!!
        val rawContactId = ContentUris.parseId(rawContactUri)

        //往data表里写入姓名数据
        values.clear() //先把可能存在的数据清空
        values.put(ContactsContract.Data.RAW_CONTACT_ID , rawContactId)
        values.put(ContactsContract.Data.MIMETYPE , CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE) //内容类型
        values.put(CommonDataKinds.StructuredName.GIVEN_NAME , "some_name")
        resolver.insert(ContactsContract.Data.CONTENT_URI , values)

        //往data表里写入电话数据
        values.clear() //先把可能存在的数据清空
        values.put(ContactsContract.Data.RAW_CONTACT_ID , rawContactId)
        values.put(ContactsContract.Data.MIMETYPE , CommonDataKinds.Phone.CONTENT_ITEM_TYPE) //内容类型
        values.put(CommonDataKinds.Phone.NUMBER , "1231231234")
        values.put(CommonDataKinds.Phone.TYPE , CommonDataKinds.Phone.TYPE_MOBILE) //手机号
        resolver.insert(ContactsContract.Data.CONTENT_URI , values)

        //往data表里写入Email的数据
        values.clear() //先把可能存在的数据清空
        values.put(ContactsContract.Data.RAW_CONTACT_ID , rawContactId)
        values.put(ContactsContract.Data.MIMETYPE , CommonDataKinds.Email.CONTENT_ITEM_TYPE) //内容类型
        values.put(CommonDataKinds.Email.DATA , "some_name@xx.com")
        values.put(CommonDataKinds.Email.TYPE , CommonDataKinds.Email.TYPE_HOME) //家庭邮箱
        resolver.insert(ContactsContract.Data.CONTENT_URI , values)
    }
    //更新联系人
    private fun updateContact() {
        val contractId = getContactIdByPhone(234524)
        if(contractId == null){
            Toast.makeText(this , "联系人不存在，无法更新" , Toast.LENGTH_LONG).show()
            return
        }

        val values = ContentValues()
        values.put(ContactsContract.Data.MIMETYPE , CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        values.put(CommonDataKinds.StructuredName.GIVEN_NAME, "new_name")
        contentResolver.update(
            ContactsContract.Data.CONTENT_URI,
            values,
            "${ContactsContract.Data.CONTACT_ID}=?",
            arrayOf(contractId)
        )

    }
    //删除联系人
    private fun deleteContact() {
        //根据姓名删除 返回的是行号
        val ret = contentResolver.delete(
            ContactsContract.RawContacts.CONTENT_URI,
            CommonDataKinds.Phone.DISPLAY_NAME + "=?",
            arrayOf("some_name")
        )
        if(ret>0){
            Toast.makeText(this,"删除成功", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show()
        }
    }
    //读取短信
    private fun getMessage() {
        val uri = Uri.parse("content://sms/")
        val resolver = contentResolver
        //查询短信的  发件人地址  日期 	短信类型  短信具体内容
        val cursor = resolver.query(uri, arrayOf("address", "address", "type", "body"), null, null, null)?:return
        while (cursor.moveToNext()){
            val address = cursor.getString(0)
            val data = cursor.getString(1)
            val type = cursor.getString(2)
            val body = cursor.getString(3)

            Log.e("ContentProvider","收件人 $address")
            Log.e("ContentProvider","类型时间 [$type]:$data")
            Log.e("ContentProvider","短信内容 $body")
            Log.e("ContentProvider","-----------------------------")
        }

        cursor.close() //关闭游标！！
    }

    //通过电话号获得contactId
    @SuppressLint("Range")
    fun getContactIdByPhone(phone : Long) : String?{
        val uri = Uri.parse("content://com.android.contacts/data/phones/filter/$phone")
        val resolver = contentResolver
        val cursor = resolver.query(uri, arrayOf(ContactsContract.Data.CONTACT_ID), null, null, null)?:return null

        if (cursor.moveToNext()){
            val contractId = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
            return contractId
        }

        return null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==100){
            if(permissions[0]==Manifest.permission.READ_CONTACTS){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){ //表示授予权限
                    //getContacts()
                    Toast.makeText(this,"读取通讯录授权成功",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"读取通讯录的权限被拒绝，程序将无法继续工作",Toast.LENGTH_LONG).show()
                }
            }else if(permissions[1]==Manifest.permission.WRITE_CONTACTS){
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){ //表示授予权限
                    //getContacts()
                    Toast.makeText(this,"写入通讯录授权成功",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"写入通讯录的权限被拒绝，程序将无法继续工作",Toast.LENGTH_LONG).show()
                }
            }else if(permissions[2]==Manifest.permission.READ_SMS){
                if (grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "读取信息授权成功", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "读取信息的权限被拒绝，程序将无法继续工作", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}