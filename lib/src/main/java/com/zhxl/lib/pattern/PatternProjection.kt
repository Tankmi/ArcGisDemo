package com.zhxl.lib.pattern

/**
 * 泛型投影
 */
class PatternProjection <T> {

    fun projectionOut(data : PatternProjection<Any>){
        //报错
//        var objects1 : PatternProjection<String> = data

        //in是生产者者，可以进行类型转换。可以返回指定类型，不能消费类型
        //相当于Java的<? super String>，指定范围下限
        var objects : PatternProjection<in String> = data
        var anyObj : PatternProjection<Any> = data
    }

    fun projectionIn(data : PatternProjection<String>){
        //报错
//        var anyNorObj : PatternProjection<Any> = data

        //out是消费者，可以消费泛型的类型，不能返回类型
        //相当于java的<? extends Object>，指定范围上限
        var anyObj : PatternProjection<out Any> = data
        var strObj : PatternProjection<String> = data

    }

}