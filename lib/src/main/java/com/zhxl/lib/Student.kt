package com.zhxl.lib

/**
 * @Title: Student
 * @Package ArcGisDemo  com.zhxl.lib
 * @author zhutao
 * @date  2019-12-12   16:59
 * @version V1.0
 * @Description:
 */
class Student : Personal {

    //子类继承父类，子类如果没有实现主构造函数；
    //子类需要在二级构造函数中使用 super 关键字初始化基类
    constructor(name : String, age : Int, school : String) : super(name, age) {
        println("$name")
    }

    companion object{
        @JvmStatic
        fun main(args : Array<String>){
            var student = Student("小明", 22, "清华")
            var doctor = Doctor("小明哥", 22, "中医院")

        }
    }

}

