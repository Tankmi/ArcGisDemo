package com.zhxl.lib

/**
 * @Title: TestExtension
 * @Package ArcGisDemo  com.zhxl.lib
 * @author zhutao
 * @date  2019-12-18   14:22
 * @version V1.0
 * @Description:扩展函数测试
 */
class TestExtension {

    fun MutableList<Int>.swap(num1: Int, num2: Int) {
        var temp = this[num1]
        this[num1] = this[num2]
        this[num2] = temp

    }


    public fun testExtension() {
//        val mutableList = mutableListOf<Int>(1,2,3)
//        mutableList.swap(1,2)
//        println(mutableList)
//
//        var (v1,v2, v3, v4) = mutableList
//        println("$v1  $v2 $v3 $v4")

    }

    fun nullTest() {

        //这是一个可空类型的集合
        val nullableList: List<Int?> = listOf(1, 2, null, 4)
        //过滤非空内容
        val intList: List<Int> = nullableList.filterNotNull()

        println(nullableList)
        println(intList)

        //:? Elvis表达式
        println(nullableList?.size ?: -1)

    }


    inner class TestLabel {
        //this@label
        var context = this@TestExtension    //指向外部类 TestExtension
        var context2 = this //指向最内层包含它的作用域 TestLabel
        fun test() {
            println(context)
            println(context2)
        }
    }
}

fun main(args: Array<String>) {

//    testExtension.testExtension()
    var testExtension = TestExtension()

    testExtension.nullTest()
//    testExtension.TestLabel().test()
}