package com.zhxl.lib.pattern

/**
 * in是生产者
 * 用 in修饰T，只能返回T类型，不能消费T类型
 */
class PatternIn<in T>(data: T) {

    fun testStr(str: PatternIn<String>) {
        var objects: PatternIn<String> = str
        //报错,in类型，向上兼容，String 不支持any类型
        //in是生产者，可以生产它兼容的类型，但是String不兼容any，所以会报错
//        var anyObj : PatternInOut<Any> = str
    }

    fun testAny(str : PatternIn<Any>){
        var objects : PatternIn<String> = str
        var strObj : PatternIn<Any> = str
    }

}

fun main(args: Array<String>) {

}