package com.zhxl.lib

/**
 * @Title: lambdaTest
 * @Package ArcGisDemo  com.zhxl.lib
 * @author zhutao
 * @date  2019-12-17   17:47
 * @version V1.0
 * @Description:
 */
class lambdaTest {

    /*
    表达式包含在花括号里；
    参数在->前面
    函数体在->后面
     */
    open var sum = { x: Int, y: Int -> x + y }
}

fun main(args : Array<String>){
    //调用lambda表达式
   var lams = lambdaTest()
    println(lams.sum(1,3))
}