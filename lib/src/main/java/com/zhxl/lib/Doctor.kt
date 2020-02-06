package com.zhxl.lib

/**
 * @Title: Doctor
 * @Package ArcGisDemo  com.zhxl.lib
 * @author zhutao
 * @date  2019-12-12   17:08
 * @version V1.0
 * @Description:
 */
//子类实现主构造函数，基类在主构造器中需要立即实现
open class Doctor constructor(name : String, age : Int, address : String) : Personal(name, age){

    init {
        println("医生：姓名$name 地址： $address" )
    }


}