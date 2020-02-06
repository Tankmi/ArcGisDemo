package com.zhxl.lib

import java.sql.DriverManager.println

/**
 * @Title: TestClassMethod
 * @Package ArcGisDemo  com.zhxl.arcgisdemo.kotlin
 * @author zhutao
 * @date  2019-12-12   10:25
 * @version V1.0
 * @Description:
 */
class TestClassMethod constructor(name : String, age : Int){
    var userName:String="zhangsan"
        get() = "lisi"
        set(value){
            if(value.equals("zhangsan")){
                field = "zhangyi"
            }else{
                field = value
            }
        }

    companion object {
        @JvmStatic
        fun main(args : Array<String>){
        println(123)
        }
    }


}
