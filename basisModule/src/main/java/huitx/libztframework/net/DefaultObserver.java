package huitx.libztframework.net;

import java.net.ConnectException;

import huitx.libztframework.R;
import huitx.libztframework.base.util.Utils;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.NetUtils;
import huitx.libztframework.utils.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DefaultObserver<T> implements Observer<T> {
    private Disposable mDisposable;
    private String ERROR_TOAST ="";

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        onSubscart(d);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if(!NetUtils.isAPNType()){
            ToastUtils.showToast("" + Utils.getApp().getResources().getString(R.string.netstate_error));
            onError("");
            onFinish();
        } else if (e instanceof ConnectException){
            LOGUtils.LOG("连接超时，请稍后重试！");
            ERROR_TOAST="连接超时，请稍后重试！";
            ToastUtils.showToast(ERROR_TOAST);
            onError(ERROR_TOAST);
            onFinish();
        }else {
            onError(e + e.getMessage());
            onFinish();
        }
    }

    @Override
    public void onComplete() {
        if (!mDisposable.isDisposed()) mDisposable.dispose();  //取消订阅
    }

    public void onSubscart(Disposable d){

    }

    public abstract void onSuccess(T data);

    public abstract void onError(String error);

    public abstract void onFinish();
}
