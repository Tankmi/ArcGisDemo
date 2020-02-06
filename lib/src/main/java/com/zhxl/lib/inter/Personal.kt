package com.zhxl.lib.inter

/**
 * @Title: Personal
 * @Package ArcGisDemo  com.zhxl.lib.inter
 * @author zhutao
 * @date  2019-12-13   11:09
 * @version V1.0
 * @Description:
 */
class Personal : A, B {
    override fun sex() {
        super<B>.sex()

    }

    //如果接口中对应的方法已有方法体；
    //方法名重名，需要用super关键字调用对应接口下的方法
    override fun like() {
        super<A>.like()
        super<B>.like()
    }

    fun log(){
        sex()
        like()
    }

    companion object{

        @JvmStatic
        fun main(args : Array<String>){
            var personal = Personal()
            personal.log()
        }
    }
}