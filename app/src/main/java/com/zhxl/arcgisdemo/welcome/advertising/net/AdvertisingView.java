package com.zhxl.arcgisdemo.welcome.advertising.net;


import huitx.libztframework.net.inter.BaseView;

/**
 * 作者：ZhuTao
 * 创建时间：2019/3/26 : 15:05
 * 描述：动态 mvp
 */
public interface AdvertisingView<T> extends BaseView {

    void onAdvertisingData(boolean state, T data);

}
