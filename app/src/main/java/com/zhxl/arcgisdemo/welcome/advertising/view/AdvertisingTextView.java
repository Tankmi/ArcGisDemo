package com.zhxl.arcgisdemo.welcome.advertising.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zhxl.arcgisdemo.R;

import huitx.libztframework.base.util.Utils;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.WidgetSetting;

/**
 * @author zhutao
 * @version V1.0
 * @Title: CountDownTextView
 * @Package ATong  com.jemer.atong.fragment.user.login.view
 * @date 2019-06-05   15:48
 * @Description: 自定义倒计时TextView
 */
public class AdvertisingTextView extends AppCompatTextView {
    TimeCount mTimeCount;

    private long futureMillis;

    public AdvertisingTextView(Context context) {
        super(context);
        init();
    }

    public AdvertisingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.count_down_text);
        futureMillis = attribute.getInt(R.styleable.count_down_text_future_millis_length,60000);
        init();
    }

    public AdvertisingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTimeCount = new TimeCount(futureMillis, 1000);// 构造CountDownTimer对象
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) mListener.coundDownFinish();
            break;
            case MotionEvent.ACTION_MOVE:
            break;
            case MotionEvent.ACTION_CANCEL:
            break;
            case MotionEvent.ACTION_UP:
            break;
            default:
                break;
        }
        return true;
    }

    public void destory() {
        if (mTimeCount != null) mTimeCount.cancel();
    }


    public void startCountDown() {
        mTimeCount.start();
//        setFocusable(true);
//        setFocusableInTouchMode(false);
//        setEnabled(true);
    }

    /* 定义一个倒计时的内部类 */
    protected class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
//            setText("获取验证码");
//            setEnabled(true);
            if (mListener != null) mListener.coundDownFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            setEnabled(false);
//            setText("获取中");
//            updateView(millisUntilFinished);
                        setText("跳过" + millisUntilFinished / 1000 + "s");
        }

    }

    protected void updateView(long millisUntilFinished){
        WidgetSetting.setIdenticalLineTvColor(this, Utils.getApp().getResources().getColor(R.color.red),
                1.0f, "(" + millisUntilFinished / 1000 + ")", false);
    }

    OnCountDownTextViewListener mListener;

    public void setOnCountDownListener(OnCountDownTextViewListener mListener) {
        this.mListener = mListener;
    }

    public interface OnCountDownTextViewListener {
        void coundDownFinish();
    }
}
