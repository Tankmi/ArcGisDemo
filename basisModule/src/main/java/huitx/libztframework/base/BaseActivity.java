package huitx.libztframework.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import huitx.libztframework.R;
import huitx.libztframework.context.LibApplicationData;
import huitx.libztframework.view.dialog.DialogUIUtils;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();
    private FragmentManager mManager;
    private Unbinder mUnBinder;
    protected Activity mContext;//通用上下文
//    protected EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

//        SpUtils.config(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        eventBus = EventBus.getDefault();
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            initByBundle(extras);
        }
        LibApplicationData.getInstance().addActivity(this);

        //----------沉浸式状态栏-----------
        setLightMode();

       /* //----------适配底部虚拟导航栏----------
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }*/
        //--------------------
        setContentView(getActivityLayouId());
        mUnBinder = ButterKnife.bind(this);

//        MyApplication.mContext = this;
        mManager = getSupportFragmentManager();
//        CrashHandler.getInstance().init(this);//初始化全局异常管理
        initView();
        loadData();
    }

    protected abstract int getActivityLayouId();

    protected abstract void initView();

    protected abstract void loadData();


    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
//        eventBus.unregister(this);
        super.onDestroy();
    }

    //----------加载框-----------
    protected Dialog mBuildDialog;
    protected Boolean isShowLoading = false;

    protected void setLoading(boolean isShowLoading) {
        setLoading(isShowLoading, "");
    }

    /**
     * 显示或者隐藏，正在加载弹窗
     */
    protected void setLoading(boolean isShowLoading, String data) {
        this.isShowLoading = isShowLoading;
        if (isShowLoading) {
            if (mBuildDialog == null)
                mBuildDialog = DialogUIUtils.showLoading(this, data, true, true, false, true).show();
            else mBuildDialog.show();
        } else if (mBuildDialog != null) mBuildDialog.dismiss();
    }


    //----------自定义状态栏-----------
    protected void backWithTitle(String text) {//自定义toolBar返回键
        TextView tv = (TextView) findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(text) && tv != null) {
            tv.setText(text);
        }
        getLeftIV().setOnClickListener(this);
    }

    protected ImageView getLeftIV() {// 显示返回按钮并取得引用
        ImageView iv = (ImageView) findViewById(R.id.iv_left);
        if (iv != null) {
            iv.setVisibility(View.VISIBLE);
        }
        return iv;
    }

    protected ImageView getRightIV(int resId) {// 显示右侧按钮并取得引用
        ImageView iv = (ImageView) findViewById(R.id.iv_right);
        if (iv != null) {
            iv.setImageResource(resId);
            iv.setVisibility(View.VISIBLE);
        }
        return iv;
    }


    protected TextView getRightTV(int resId) {// 显示右侧按钮（文本）并取得引用
        return getRightTV(getString(resId));
    }

    protected TextView getRightTV(String text) {// 显示右侧按钮（文本）并取得引用
        TextView tv = (TextView) findViewById(R.id.tv_right);
        if (!TextUtils.isEmpty(text) && tv != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
        return tv;
    }

    /**
     * 获取 bundle 数据
     *
     * @param extras
     */
    protected abstract void initByBundle(Bundle extras);

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /* *
     * Android 6.0 以上设置状态栏颜色
     */
    private void setLightMode() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);   //设置状态栏颜色透明
//            window.setNavigationBarColor(Color.TRANSPARENT); //设置导航栏颜色透明
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色白色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);

            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /*  *//**
     * Android 6.0 以上设置状态栏颜色
     *//*
    protected boolean transStatebar() {
        return false;
    }
    private void setLightMode() {
        //状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 21) {
            Window window = getWindow();
            //改变带有fitssystemwindow 的主题
//            setTheme(R.style.Fit_Window);
            window.getDecorView().setFitsSystemWindows(true);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            if (transStatebar()) {
                tintManager.setStatusBarTintResource(R.color.transparents);
            } else {
                tintManager.setStatusBarTintResource(R.color.white);
            }
        } else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Window window = getWindow();
            window.getDecorView().setFitsSystemWindows(true);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (transStatebar()) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.transparents));
                } else {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色白色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);

            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
*/

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }
/*
    protected void hasEventComming(EventCenter eventCenter) {

    }

    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            hasEventComming(eventCenter);
        }
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left) {
            finish();
        }
    }
}
