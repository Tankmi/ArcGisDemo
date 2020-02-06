package com.zhxl.arcgisdemo.welcome;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.context.PreferenceEntity;

import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.PreferencesUtils;
import huitx.libztframework.utils.StatusBarCompat;

import static huitx.libztframework.utils.LOGUtils.LOG;

/**
 * 只负责屏幕参数的测量和网络交互
 */
public class WelcomeBaseActivity extends FragmentActivity {

//    AdvertisingPresenter mPresenter;

    RelativeLayout rl_welcome;
    protected boolean isInitWindowParams = false;
    protected Context mContext;    //上下文
    protected int intent_state = -1;


    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        LOGUtils.LOG("screenHeight * 2 / 3   " + screenHeight * 2 / 3);
        LOGUtils.LOG("rect.bottom   " + rect.bottom);
        return screenHeight * 2 / 3 > rect.bottom;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
//		super.onWindowFocusChanged(hasFocus);

        if (isInitWindowParams) return;
        if (hasFocus) {
            boolean isSave = PreferencesUtils.getBoolean(this, PreferenceEntity.KEY_SCREEN_ISSAVE, false);
            if (!isSave) {
//            if (isSave) {
                LOGUtils.LOG("初始化屏幕信息");
                //获取屏幕尺寸，不包括虚拟功能高度<br><br>
                int widowHeight = getWindowManager().getDefaultDisplay().getHeight();
//                Rect rect = new Rect();
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//                int top = rect.top; // 状态栏的高度
//                LOGUtils.LOG("状态栏的高度" + top);

                Resources resources = WelcomeBaseActivity.this.getResources();
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                int top2 = view.getTop(); // 状态栏与标题栏的高度
                int width = view.getWidth(); // 视图的宽度
                int height = view.getHeight(); // 视图的高度
                int navigationBarHeight = resources.getDimensionPixelSize(resourceId);    //虚拟按键的高度

//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int mewidth = metric.widthPixels;  // 屏幕宽度（像素）
//        int meheight = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        LOGUtils.LOG("屏幕密度: " + density + "; 屏幕密度DPI: " + densityDpi);

                float status_bar_height = 0;
                int resourceId_status = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId_status > 0) {
                    status_bar_height = this.getResources().getDimensionPixelSize(resourceId_status);
                    LOGUtils.LOG("状态栏高度: " + status_bar_height);
                }

                PreferenceEntity.ScreenTop = status_bar_height;
                PreferenceEntity.ScreenTitle_navigationBarHeight = navigationBarHeight;
                PreferenceEntity.screenWidth = width;
                PreferenceEntity.screenHeight = height + top2;


                boolean hasNavigationBar = false;
                if ((height + top2) - widowHeight > 0) hasNavigationBar = true;
                else hasNavigationBar = false;
                LOG("  虚拟键盘 hasNavigationBar =  " + (!hasNavigationBar ? "没显示" : "显示了"));
                PreferenceEntity.hasNavigationBar = hasNavigationBar;

                LOGUtils.LOG("状态栏的高度：" + status_bar_height + ",标题栏与状态栏的高度:" + top2 + ",标题栏与状态栏的高度占比:"
                        + PreferencesUtils.getFloat(this, "ScreenTitle")
                        + ",视图的宽度:" + width + ",视图的高度(不包括状态栏的高度):" + height + "屏幕高度（不包括虚拟功能高度）： " + widowHeight + ",屏幕的宽度:"
                        + PreferenceEntity.screenWidth + ",屏幕的高度:"
                        + PreferenceEntity.screenHeight + "虚拟键盘的高度：" + navigationBarHeight + "手机厂商：" + Build.MANUFACTURER);
                PreferenceEntity.saveScreenView();
                isInitWindowParams = true;
            } else {
                LOG("屏幕信息已保存，初始化");
                PreferenceEntity.initScreenView();
                isInitWindowParams = true;
            }
            float mScreenRatio = 1.0f * PreferenceEntity.screenWidth / PreferenceEntity.screenHeight;
            LOG("宽高比：" + mScreenRatio);
            if (mScreenRatio > 0.5f) {
                LOG("宽平：" + mScreenRatio);
                rl_welcome.setBackgroundResource(R.drawable.iv_welcome_wide_new);
//                rl_welcome.setBackgroundResource(R.drawable.iv_welcome_wide);
            } else {
                LOG("细屏：" + mScreenRatio);
                rl_welcome.setBackgroundResource(R.drawable.iv_welcome_fine_new);
//                rl_welcome.setBackgroundResource(R.drawable.iv_welcome_fine);
            }

        }

    }

    /**
     * 设置状态栏颜色，默认透明
     *
     * @param isFit: 是否是全屏显示	5.0以上的系统需要此属性判断状态栏的属性设置
     * @param isSet: 是否设置特殊颜色，否的话，第三个参数可以设置为0
     */
    public void setStatusBarColor(boolean isFit, boolean isSet, int color) {
        if (isSet) StatusBarCompat.compat(this, color, isFit);
        else
            StatusBarCompat.compat(this, mContext.getResources().getColor(R.color.status_bar_default_bg), isFit);
    }

}

