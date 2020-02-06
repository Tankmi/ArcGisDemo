package com.zhxl.lib.accompanying

/**
 * @Title: AccompanyingTest
 * @Package ArcGisDemo  com.zhxl.lib.accompanying
 * @author zhutao
 * @date  2019-12-16   11:43
 * @version V1.0
 * @Description:
 */
class AccompanyingTest(name: String = "zhangsan", age: Int = 23) {

    var mutableList: MutableList<Int> = mutableListOf()
    fun log() {

        //集合
//        mutableList.add(1)
//        mutableList.add(2)
//        mutableList.add(3)
//
//        var lists : List<Int> = mutableList
//
//        for (i in mutableList.indices){
//            println(mutableList[i])
//        }
//
//        println("-----------")
//
//        mutableList.add(4)
//        for (i in lists.indices){
//            println(lists[i])
//        }

        //原生字符串、转义字符串
//        println("""
//                      |1
//                        2
//                3
//                4
//            """.trimIndent())
//
//        println("-------------\n1\n2\n")


    }

    //可变长参数
    fun <T> varargTest(vararg arg: T) {
        for (t in arg) {
            println(t)
        }

    }

    //嵌套函数
    fun foo() {
        println("outside")
        fun inside() {
            println("inside")
        }
        inside()
    }

    //范型函数
    fun <T> patternTest(item: T) {

    }

    //范型函数，带返回值
    fun <T> patternList(items: T): List<T> {

        var list: MutableList<T> = mutableListOf()
        list.add(items)

        for (item in list){
            println(item)
        }
        return list
    }

    //范型函数，带返回值
    fun <T> patternList(vararg  arg: T): List<T> {

        var list: MutableList<T> = mutableListOf()

        for (t in arg) {
            list.add(t)
        }

        for (item in list){
            println(item)
        }
        return list
    }

    //尾递归函数， 死循环
    tailrec fun countNum(count : Int):Int = if(count == 10) count else countNum(count - 1)

}


fun main(arags: Array<String>) {
//    var companinos = Accompanying()
//    companinos.normalMethod()

//    var  acText = AccompanyingTest(age = 33)
//    acText.log()

    var acText = AccompanyingTest(age = 33)
//    val a = arrayListOf(1, 2, 3);
//    acText.varargTest(1, 2, 3, "123123123", a)

//    acText.foo()

//    acText.patternList("爱慕微皮BOSS")
//    acText.patternList(1,3,"123")

    acText.countNum(1)
}