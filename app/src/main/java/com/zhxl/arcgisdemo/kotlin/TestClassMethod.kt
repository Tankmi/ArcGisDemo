package com.zhxl.arcgisdemo.kotlin

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


    fun main(args : Array<String>){
//        print
        println(userName)
    }
}
