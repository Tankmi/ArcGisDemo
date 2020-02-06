package com.zhxl.arcgisdemo.kotlin

import android.content.Context
import android.os.Handler
import android.os.Message
import com.zhxl.arcgisdemo.R
import com.zhxl.arcgisdemo.base.BaseFragment
import com.zhxl.arcgisdemo.kotlin_cooperation.Cooperation
import huitx.libztframework.utils.EventBusMessage
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * @Title: KotlinTestFragmentNew
 * @Package ArcGisDemo  com.zhxl.arcgisdemo.kotlin
 * @author zhutao
 * @date  2019-12-20   11:35
 * @version V1.0
 * @Description:
 */

class KotlinTestFragmentNew : BaseFragment(R.layout.fragment_main_kotlin) {

    var mHandler: MyHandler? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBusSelPhoto(data: EventBusMessage<*>?) {

    }

    override fun initHead() {
        TAG = javaClass.simpleName
        if (mHandler == null) mHandler = MyHandler(mContext)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun initLocation() {
    }

    override fun initLogic() {

//        LOG(" start ${Thread.currentThread().name}")
//
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(1000)
//            LOG("coroutinue ${Thread.currentThread().name}")
//        }
//
//        LOG(" end ${Thread.currentThread().name}")


        var cooperation = Cooperation()

        //顺序执行
//        GlobalScope.launch {
//            var value1 = cooperation.request1()
//            var value2 = cooperation.request2()
//            var value3 = cooperation.request3()
//
//            cooperation.updateUI(value1, value2, value3)
//        }
        //并发执行
        GlobalScope.launch(Dispatchers.IO) {
            var value1 = async { cooperation.request1() }
            var value2 = async { cooperation.request2() }
            var value3 = async { cooperation.request3() }

            cooperation.updateUI(value1.await(), value2.await(), value3.await())
        }

    }

     val MSG_OrderMessage = 100

    inner class MyHandler constructor(activity: Context) : Handler() {
        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        val mActivity: WeakReference<Context>

        init {
            mActivity = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_OrderMessage -> {
                }
            }
        }
    }


    override fun pauseClose() {

    }


    override fun destroyClose() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

}