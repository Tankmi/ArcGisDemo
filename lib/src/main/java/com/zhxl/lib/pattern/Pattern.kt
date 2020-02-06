package com.zhxl.lib.pattern

/**
 * @Title: Pattern
 * @Package ArcGisDemo  com.zhxl.lib.pattern
 * @author zhutao
 * @date  2019-12-16   15:00
 * @version V1.0
 * @Description:
 */
class Pattern<T> (t : T){
    var value = t

    var data = if(1>2) {
        1+1
        print(t)
    } else {
        2
    }

    //定义无返回值
    fun student(strs : Pattern<T>) : Unit{
    }

    fun <T : Comparable<T>> sort(list: List<T>) {
        // ……
    }

    fun test(strs: Pattern<Any>) {
        //不报错
        var objects: Pattern<in String> = strs
    }

    //返回值也可以什么都不写
    fun studentClass(){

    }
    //有返回值
    fun hasStudent(t:T): T{

        return  t;
    }

}

fun main(args: Array<String>) {
    var boxInt = Pattern<Int>(10)
    var boxString = Pattern<String>("Runoob")

    //范型的数据类型如果可以判断出来，调用范型函数时，可以省略参数类型
    var boxboolean = Pattern(false)

    println(boxInt.value)
    println(boxString.value)
    println(boxboolean.value)
}