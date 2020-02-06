package com.zhxl.lib.enum

/**
 * @Title: EnumTest
 * @Package ArcGisDemo  com.zhxl.lib.enum
 * @author zhutao
 * @date  2019-12-17   15:58
 * @version V1.0
 * @Description:
 */
class EnumTest {
    inline fun <reified T : Enum<T>> printAllValues() {
        print(enumValues<T>().joinToString { it.name })
    }


}