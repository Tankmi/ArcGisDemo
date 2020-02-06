package com.zhxl.arcgisdemo.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.context.ApplicationData;
import com.zhxl.arcgisdemo.context.PreferenceEntity;
import com.zhxl.arcgisdemo.main.MainActivity;
import com.zhxl.arcgisdemo.welcome.advertising.AdvertisingFragment;
import com.zhxl.arcgisdemo.welcome.guide.GuidesDialogFragment;

import java.lang.ref.WeakReference;

import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.PreferencesUtils;

public class WelcomeActivity extends WelcomeBaseActivity implements AdvertisingFragment.OnAdvertisingListener {
    private MyHandler mHandler;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

            //测试条目
            mHandler.sendEmptyMessageDelayed(0, 500);  //直接进首页
//            mHandler.sendEmptyMessageDelayed(2, 20);  //判断是否显示广告
//            showGuideView(-1);    //加载开机欢迎页

            //生产条目
//            mHandler.sendEmptyMessageDelayed(2, 2000);  //判断是否显示广告

//        if (PreferenceEntity.isLogin()) {
//            mHandler.sendEmptyMessageDelayed(1, 2000);
//        } else {
//            mHandler.sendEmptyMessageDelayed(0, 2000);
//        mHandler.sendEmptyMessageDelayed(2, 2000);
//        }
//		}else{	//第一次进来，跳转到引导页
//			mHandler.sendEmptyMessageDelayed(2, 200);
//		}
        }
    }

    private class MyHandler extends Handler {

        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<Activity> mActivity;

        protected MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Intent intent_home = null;
            switch (msg.what) {
                case 0:    //没有登录成功
                    intent_home = new Intent(mContext, MainActivity.class);
                    break;
                case 1:    //登录成功
                    String isall = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_ISSETPSW, "");
//                    if (isall.equals("0")) {
//                        setPayPswFragment();
//                        return;
//                    } else {
//                        intent_home = new Intent(mContext, HomeActivity.class);
//                    }
                    break;
                case 2:    //获取广告
//                    showAdvertisingFragment();
                    return;
                default:
                    break;
            }

            startActivity(intent_home);
            toFinish();

            //----------Test
//            IntentUtils.getInstance().goMallFraAct_4(WelcomeActivity.this, 421, "");

//            PreferencesUtils.putString(ApplicationData.context, PreferenceEntity.KEY_USER_ACCOUNT, "15652675982");
//            intent_home = new Intent(mContext, ShopCarFragmentActivity.class);
//            intent_home = new Intent(mContext, GoodsDetailActivity.class);
//            intent_home = new Intent(mContext, MyOrderActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putLong("goods_id", 57284);
//            bundle.putInt("type", 2);
//            intent_home.putExtras(bundle);
//            startActivity(intent_home);
//            toFinish();
        }
    }

    FragmentManager fragmentManager;
    protected String PayPswTAG = "pay_psw";


    FragmentTransaction fragmentTran;
//    AdvertisingFragment mWelcomeFragment;
//
//    protected void showAdvertisingFragment() {
//        if (mWelcomeFragment == null) mWelcomeFragment = new AdvertisingFragment();
//        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
//        fragmentTran = fragmentManager.beginTransaction();
//        if (mWelcomeFragment.isAdded()) fragmentTran.show(mWelcomeFragment);
//        else fragmentTran.replace(R.id.fl_welcome, mWelcomeFragment, "ShopCart");
//        fragmentTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        fragmentTran.commitAllowingStateLoss();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        mgetNetData = GetNetData.getInstance();
        mContext = ApplicationData.context;
        setStatusBarColor(true, false, 0);

        View main = getLayoutInflater().inflate(R.layout.activity_welcome, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);  //隐藏activity_welcome.xml虚拟键盘，自适应
        setContentView(main);

        rl_welcome = findViewById(R.id.rl_welcome);

        if (mHandler == null) mHandler = new MyHandler(this);
//        getParameterByIntent();
        //        initXGPush();
//        selIntent();
    }

    @Override
    public void onAdvertisingClose(long bufferTime) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(0, bufferTime);
        }
    }


    private void showGuideView(int state) {
        GuidesDialogFragment guidesDialogFragment = GuidesDialogFragment.getInstance(state);
        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
        guidesDialogFragment.show(fragmentManager, "123");
    }


    public void getParameterByIntent() {
        Intent mIntent = this.getIntent();
        intent_state = mIntent.getIntExtra("intent_state", -1);
        LOGUtils.LOG("组件化启动，传来的值" + intent_state);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
    }

    protected void toFinish() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        setContentView(R.layout.view_null);
        finish();
        System.gc();
    }


}
