package com.zhxl.arcgisdemo.main.net;

import com.google.gson.Gson;
import com.zhxl.arcgisdemo.net.UrlConstant;

import huitx.libztframework.context.ContextConstant;
import huitx.libztframework.net.DefaultObserver;
import huitx.libztframework.net.RxJavaDataImpl;
import huitx.libztframework.net.inter.BasePresenter;
import huitx.libztframework.utils.LOGUtils;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：ZhuTao
 * 创建时间：2019/3/26 : 15:28
 * 描述：
 */
public class HomeNetPresenter implements BasePresenter<HomeNetView> {

    private HomeNetView mView;
    CompositeDisposable compositeDisposable;


    public HomeNetPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(HomeNetView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if(compositeDisposable != null){
            LOGUtils.LOG("所有请求已取消" + compositeDisposable.size());
            compositeDisposable.clear();
        }
        if(mView != null) mView = null;
    }

    public String getVerisionName() {
        mView.loadingShow();
//        mModel.getVersionName(new BaseHttpEntity<ResponseBody>() {
        RxJavaDataImpl.postData(UrlConstant.API_GETVERSION, null, new DefaultObserver<ResponseBody>() {
            @Override
            public void onSubscart(Disposable d) {
                super.onSubscart(d);
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ResponseBody data) {
                mView.loadingDissmis();
                Gson gson = new Gson();
                HomeNetEntity mEntity;
                String str;
                try {
//                    str = StringUtils.replaceJson(list.string());
                    mEntity = gson.fromJson(data.string(), HomeNetEntity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (mEntity.code == ContextConstant.RESPONSECODE_200) {
                    mView.onVerisionData(true,mEntity.data);
                } else if (mEntity.code == ContextConstant.RESPONSECODE_310) {    //登录信息过时跳转到登录页
                    mView.loginOut();
                } else {
                    mView.onVerisionData(false,null);
//                    ToastUtils.showToast(NewWidgetSetting.getInstance().filtrationStringbuffer(mEntity.msg, "接口信息异常！"));
                }
            }

            @Override
            public void onError(String error) {
                mView.loadingDissmis();
            }

            @Override
            public void onFinish() {
                mView.loadingDissmis();

            }
        });
        return null;
    }



}
