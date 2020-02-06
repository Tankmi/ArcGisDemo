package com.zhxl.lib.accompanying

/**
 * @Title: Accompanying
 * @Package ArcGisDemo  com.zhxl.lib.accompanying
 * @author zhutao
 * @date  2019-12-16   11:39
 * @version V1.0
 * @Description:
 */
class Accompanying {

    companion object {
    }

    fun normalMethod(){
        Accompanying.acp()
        println("accompanying 属性扩展 ${Accompanying.acpData}")
    }

    //伴生对象函数扩展
    fun Accompanying.Companion.acp() {
        println("伴生对象扩张")
    }

    //伴生对象属性扩展
    val Accompanying.Companion.acpData: Int
        get() = 10



}

