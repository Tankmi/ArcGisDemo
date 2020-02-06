package com.zhxl.lib.pattern

/**
 * out是消费者
 * out修饰T 类型，只能消费T类型，不能返回T类型
 */
class PatternOut<out T> (t : T) {

    fun testStr(str : PatternOut<String>){
        var objects : PatternOut<String> = str
        var anyObj : PatternOut<Any> = str
    }

    fun  testAny(str : PatternOut<Any>){
        //报错 string不能接受不了any类型
//        var objects : PatternOut<String> = str
        var anyObj : PatternOut<Any> = str
    }

}