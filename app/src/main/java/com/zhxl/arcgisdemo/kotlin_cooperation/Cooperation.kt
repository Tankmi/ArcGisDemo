package com.zhxl.arcgisdemo.kotlin_cooperation

import android.text.format.DateUtils
import huitx.libztframework.utils.LOGUtils
import huitx.libztframework.utils.TestUtils
import huitx.libztframework.utils.TransitionTime
import kotlinx.coroutines.*

/**
 * @Title: Cooperation
 * @Package ArcGisDemo  com.zhxl.arcgisdemo.kotlin_coperation
 * @author zhutao
 * @date  2019-12-20   10:21
 * @version V1.0
 * @Description: 协程学习
 */
class Cooperation {

    fun testCooperation() {

        GlobalScope.launch {
            //            delay(1000)
            //设置执行协程运行线程
            withContext(Dispatchers.IO) {
                LOGUtils.LOG("withContext ${Thread.currentThread().name}")
            }

            LOGUtils.LOG("coroutinue ${Thread.currentThread().name}")
        }
    }

    var nums: Int = 1;

    suspend fun request1(): String {
        LOGUtils.LOG("request 1")
        nums++

        return "request1  ${Thread.currentThread().name}  time: ${TransitionTime.getInstance().convert(System.currentTimeMillis(), "HH:mm:ss")}   ${nums} "
    }

    suspend fun request2(): String {
        LOGUtils.LOG("request 2")
        nums++

        var testUtils = TestUtils()
        testUtils.simulateDelay()

        return "request2  ${Thread.currentThread().name}  time: ${TransitionTime.getInstance().convert(System.currentTimeMillis(), "HH:mm:ss")}   ${nums}"
    }

    suspend fun request3(): String {
        LOGUtils.LOG("request 3")
        nums++
        return "request3  ${Thread.currentThread().name}  time: ${TransitionTime.getInstance().convert(System.currentTimeMillis(), "HH:mm:ss")}   ${nums}"
    }

    fun updateUI(vararg vars: String) {

        for (t in vars) {
            LOGUtils.LOG(t)
        }
    }

}

fun main(args: Array<String>) {
    LOGUtils.LOG(" start ${Thread.currentThread().name}")

    var cooperation = Cooperation()
//    GlobalScope.launch(Dispatchers.IO) {
//        LOGUtils.LOG("Hello ${Thread.currentThread().name}")
//        cooperation.testCooperation()
//        LOGUtils.LOG("End ${Thread.currentThread().name}")
//    }

//    cooperation.testCooperation()

//    GlobalScope.launch {
//        delay(1000)
//        LOGUtils.LOG("coroutinue ${Thread.currentThread().name}")
//    }

    //顺序执行
//    GlobalScope.launch {
//        var value1 = cooperation.request1()
//        var value2 = cooperation.request2()
//        var value3 = cooperation.request3()
//
//        cooperation.updateUI(value1, value2, value3)
//    }

    //并发执行
    GlobalScope.launch(Dispatchers.IO) {
        var value1 = async { cooperation.request1() }
        var value2 = async { cooperation.request2() }
        var value3 = async { cooperation.request3() }

        cooperation.updateUI(value1.await(), value2.await(), value3.await())
    }




    LOGUtils.LOG(" end ${Thread.currentThread().name}")
}