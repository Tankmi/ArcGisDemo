package com.zhxl.arcgisdemo.welcome.advertising;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.base.BaseFragment;
import com.zhxl.arcgisdemo.context.PreferenceEntity;
import com.zhxl.arcgisdemo.util.IntentUtils;
import com.zhxl.arcgisdemo.welcome.advertising.net.AdvertisingEntity;
import com.zhxl.arcgisdemo.welcome.advertising.net.AdvertisingPresenter;
import com.zhxl.arcgisdemo.welcome.advertising.net.AdvertisingView;
import com.zhxl.arcgisdemo.welcome.advertising.view.AdvertisingTextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.LayoutUtil;

public class AdvertisingFragment extends BaseFragment implements AdvertisingView<AdvertisingEntity.Data> {

//    protected MyHandler mHandler;
    protected AdvertisingPresenter mPresenter;

    @BindView(R.id.cl_advertising)
    ConstraintLayout mMainView;
    @BindView(R.id.tv_welcome_advertising)
    AdvertisingTextView tv_welcome_advertising;

    public AdvertisingFragment() {
        super(R.layout.fragment_advertising);
        TAG = getClass().getSimpleName() + "     ";
    }


    @Override
    protected void initHead() {
        if (mPresenter == null) {
            mPresenter = new AdvertisingPresenter();
        }
        mPresenter.attachView(this);
//        tv_welcome_advertising.setClickable(true);

        tv_welcome_advertising.setOnClickListener(v -> {

            LOGUtils.LOG("Advertising onclick");
            mAdvertisingListener.onAdvertisingClose(0);
        });

        tv_welcome_advertising.setOnCountDownListener(() -> {

            LOGUtils.LOG("Advertising OnCountDow");
            if (mAdvertisingListener != null) {
                mAdvertisingListener.onAdvertisingClose(20);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Activity context) {
        super.onAttach(context);
        try {
            mAdvertisingListener = (OnAdvertisingListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnGridViewSelectedListener");
        }
    }


    public OnAdvertisingListener mAdvertisingListener;

    public void setJumpListener(OnAdvertisingListener onAdvertisingListener) {
        this.mAdvertisingListener = onAdvertisingListener;
    }

    public interface OnAdvertisingListener {
        /**
         * @param bufferTime 缓冲时长，单位毫秒
         */
        void onAdvertisingClose(long bufferTime);
    }

    @Override
    protected void initLocation() {
        LayoutUtil.getInstance().drawViewRBConstraintLayout(tv_welcome_advertising, -1, -1,
                0, 40, (int) PreferenceEntity.ScreenTop + 8, -1);
    }

    @Override
    protected void initLogic() {
//        initBack();
        mPresenter.getNetData();
    }


    @Override
    protected void onVisibile() {

    }

    @Override
    public void loadingShow() {
//        setLoading(true, "");
    }

    @Override
    public void loadingDissmis() {
//        setLoading(false, "");
    }

    @Override
    public void loginOut() {
//        reLoading();
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    private void initBack(){
        float mScreenRatio = 1.0f * PreferenceEntity.screenWidth / PreferenceEntity.screenHeight;
        if (mScreenRatio > 0.5f)
            mMainView.setBackgroundResource(R.drawable.iv_welcome_wide_new);
        else
            mMainView.setBackgroundResource(R.drawable.iv_welcome_fine_new);
    }

    protected static final int MSG_OrderMessage = 100;

    @Override
    public void onAdvertisingData(boolean state, AdvertisingEntity.Data data) {
        if (state && data.isShow == 1) {
            float mScreenRatio = 1.0f * PreferenceEntity.screenWidth / PreferenceEntity.screenHeight;
            LOG("宽高比：" + mScreenRatio);
            SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    mMainView.setBackground(resource);
                }
            };

            if (mScreenRatio > 0.5f) {
                LOG("宽平：" + mScreenRatio);
                Glide.with(this).load(data.imgUrl1).placeholder(R.drawable.iv_welcome_wide_new).error(R.drawable.icon_loading).into(simpleTarget);
            } else {
                LOG("细屏：" + mScreenRatio);
                Glide.with(this).load(data.imgUrl2).placeholder(R.drawable.iv_welcome_fine_new).error(R.drawable.icon_loading).into(simpleTarget);
            }

            tv_welcome_advertising.setVisibility(View.VISIBLE);
            tv_welcome_advertising.startCountDown();

        } else {
            LOG("没有内容或者网络请求失败");
            try {
                mAdvertisingListener.onAdvertisingClose(10);
            }catch (Exception e){
                e.printStackTrace();
                IntentUtils.getInstance().intentHome(getActivity());
                getActivity().finish();
            }

        }
    }

    protected class MyHandler extends Handler {

        private final WeakReference<Context> mActivity;

        protected MyHandler(Context activity) {
            mActivity = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OrderMessage:
                    break;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void pauseClose() {
    }

    @Override
    protected void destroyClose() {
        if(tv_welcome_advertising != null)tv_welcome_advertising.destory();
//        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
//        if (EventBus.getDefault().isRegistered(this)) {
//            LOGUtils.LOG("解除EventBus 注册");
//            EventBus.getDefault().unregister(this);
//        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }


}
