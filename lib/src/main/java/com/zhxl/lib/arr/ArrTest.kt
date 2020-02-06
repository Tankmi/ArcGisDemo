package com.zhxl.lib.arr

/**
 * @Title: ArrTest
 * @Package ArcGisDemo  com.zhxl.lib.arr
 * @author zhutao
 * @date  2019-12-19   16:34
 * @version V1.0
 * @Description:
 */

class arrTest{

    var ar1 = arrayOf(1, 1, 1, 1, 2)

    //创建一个长度为 6 的空数组
    var ar2 = arrayOfNulls<Int>(6)

    var list : List<Int?> = ar1.asList()

    fun iteArry(){
        for (value in list.iterator()){
            println("值 $value")
        }
    }



}

fun main(args : Array<String>){
    var arrTest = arrTest()
    arrTest.iteArry()
}