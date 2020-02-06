package com.zhxl.lib.field

import java.lang.reflect.Field

/**
 * @Title: TestFailed
 * @Package ArcGisDemo  com.zhxl.lib.field
 * @author zhutao
 * @date  2019-12-19   15:21
 * @version V1.0
 * @Description:测试反射
 */
class TestFailed {

    var student = Student("张三", 16, "康杰中学")

    fun testDeclaredField(){
        //通过Class.getDeclaredField(String name)获取类或接口的指定已声明字段。
//        var f1:Field = student.javaClass.getDeclaredField("name")
//        println(f1)

        //通过Class.getDeclaredFields()获取类或接口的指定已声明字段列表。
        var fs : Array<Field> = student.javaClass.getDeclaredFields()
        for (i in fs.iterator()){
            println(i)
        }

        //通过Class.getField(String name)返回一个类或接口的指定公共成员字段，私有成员报错。
        //获取不到
//        var f2 : Field = student.javaClass.getField("name")
        /*
        报错
        Exception in thread "main" java.lang.NoSuchFieldException: name
	at java.lang.Class.getField(Class.java:1703)
	at com.zhxl.lib.field.TestFailed.testDeclaredField(TestFailed.kt:29)
	at com.zhxl.lib.field.TestFailedKt.main(TestFailed.kt:38)
         */
//        println(f2)

        //通过Class.getField()，返回 Class 对象所表示的类或接口的所有可访问公共字段。
        //获取不到
//        var fs1 : Array<Field> = student.javaClass.getFields()
//        for (i in fs1.iterator()){
//            i.isAccessible
//            println(i)
//        }

    }
}

fun main(args : Array<String>){
    var testField = TestFailed()
    testField.testDeclaredField()
}