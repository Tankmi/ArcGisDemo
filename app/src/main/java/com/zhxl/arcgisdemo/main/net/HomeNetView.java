package com.zhxl.arcgisdemo.main.net;


import huitx.libztframework.net.inter.BaseView;

/**
 * 作者：ZhuTao
 * 创建时间：2019/3/26 : 15:05
 * 描述：动态 mvp
 */
public interface HomeNetView<T> extends BaseView {

    void onVerisionData(boolean state, T data);

}
