package com.zhxl.arcgisdemo.welcome.advertising.net;

import com.google.gson.Gson;
import com.zhxl.arcgisdemo.net.UrlConstant;

import huitx.libztframework.context.ContextConstant;
import huitx.libztframework.net.DefaultObserver;
import huitx.libztframework.net.RxJavaDataImpl;
import huitx.libztframework.net.inter.BasePresenter;
import huitx.libztframework.utils.StringUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：ZhuTao
 * 创建时间：2019/3/26 : 15:28
 * 描述：
 */
public class AdvertisingPresenter implements BasePresenter<AdvertisingView> {

    private AdvertisingView mView;
    CompositeDisposable compositeDisposable;

    @Override
    public void attachView(AdvertisingView view) {
        this.mView = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        if (mView != null) mView = null;
    }

    public void getNetData() {
        mView.loadingShow();

        RxJavaDataImpl.postData(UrlConstant.API_GETADVERTISING, null, new DefaultObserver<ResponseBody>() {

            @Override
            public void onSubscart(Disposable d) {
                super.onSubscart(d);
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ResponseBody data) {
                Gson gson = new Gson();
                AdvertisingEntity mEntity;
                String str;
                try {
                    str = StringUtils.replaceJson(data.string());
                    mEntity = gson.fromJson(str, AdvertisingEntity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (mEntity.code == ContextConstant.RESPONSECODE_200) {
                    mView.onAdvertisingData(true,mEntity.data);
                } else if (mEntity.code == ContextConstant.RESPONSECODE_310) {    //登录信息过时跳转到登录页
                    mView.loginOut();
                } else {
                    mView.onAdvertisingData(false,null);
                }
            }

            @Override
            public void onError(String error) {
                mView.onAdvertisingData(false,null);
            }

            @Override
            public void onFinish() {
                mView.loadingDissmis();
            }
        });
    }




}
