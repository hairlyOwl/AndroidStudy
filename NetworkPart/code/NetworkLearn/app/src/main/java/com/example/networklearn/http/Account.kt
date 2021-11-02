package com.example.networklearn.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *@author: hairly owl
 *@time:2021/10/29 15:48
 *@version: 1.00
 *@description: 练习Gson处理json数据
 */

fun main() {
    /*
    * json与对象
    * */
    //1.1将JSON转换为对象
    val json ="{\"uid\":\"00001\",\"userName\":\"Freeman\",\"telNumber\":\"13000000000\"}"
    //Gson对象
    val gson = Gson()
    val account = gson.fromJson<Account2>(json,Account2::class.java)
    println("fromJson : ${account.toString()}")

    //1.2将对象转换为JSON
    val accountJson = gson.toJson(account)
    println("toJson : $accountJson")

    /*
    *json与集合
    * */
    //2.1将JSON转换成集合
    val jsonList= "[{\"uid\":\"00001\",\"userName\":\"Freeman\",\"telNumber\":\"13000000000\"}" +
            ",{\"uid\":\"00002\",\"userName\":\"oooo\",\"telNumber\":\"12000000000\"}]"

    val accountList:List<Account> =  gson.fromJson(jsonList , object :TypeToken<List<Account>>(){}.type)
    println("fromJson to list : ${accountList.size}")

    //2.2将集合转换成JSON
    val accountJsonList = gson.toJson(accountList)
    println("list to json : $accountJsonList")

    val userModelJson = "{\"status\":200,\"message\":\"成功\",\"data\":{\"data\":{\"id\":3117,\"userId\":1600932269,\"name\":\"lovelychubby\",\"avatar\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com//ajsdfksjakfjasklfjkasfas_54757db023a4c2f%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20200214204431.jpg\",\"description\":\"更多android进阶课程请在慕课搜索lovelychubby\",\"likeCount\":985,\"topCommentCount\":200,\"followCount\":100,\"followerCount\":10,\"qqOpenId\":\"A8747C32A5D614281E65DA5B473D1F31\",\"expires_time\":1640266383000,\"score\":1000,\"historyCount\":10,\"commentCount\":3,\"favoriteCount\":0,\"feedCount\":10,\"hasFollow\":false}}}"

    val userResponse = gson.fromJson<UserResponse>(userModelJson, UserResponse::class.java)
    println("userResponse : $userResponse ")
}


class Account {
    private var uid:String="";
    private var userName:String="Freeman";
    private var password:String="password";
    private var telNumber:String="13000000000";
    override fun toString(): String {
        return "Account(uid='$uid', userName='$userName', password='$password', telNumber='$telNumber')"
    }
}

data class Account2(val uid:String , val userName:String , val password:String , val telNumber:String)

data class UserResponse(
    val data: Data,
    val message: String,
    val status: Int
)

data class Data(
    val data: User
)

data class User(
    val avatar: String,
    val commentCount: Int,
    val description: String,
    val expires_time: Long,
    val favoriteCount: Int,
    val feedCount: Int,
    val followCount: Int,
    val followerCount: Int,
    val hasFollow: Boolean,
    val historyCount: Int,
    val id: Int,
    val likeCount: Int,
    val name: String,
    val qqOpenId: String,
    val score: Int,
    val topCommentCount: Int,
    val userId: Int
)