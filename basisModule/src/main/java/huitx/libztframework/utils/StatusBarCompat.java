package huitx.libztframework.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 设置状态栏背景色
 *
 * @author ZhuTao
 * @date 2017/7/20
 * @params
 */
public class StatusBarCompat {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {
        compat(activity, statusColor, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor, boolean isFullScreen, boolean isDarkStyle) {
        setCompat(activity, statusColor, isFullScreen, isDarkStyle);
    }

    /**
     * @param activity
     * @param statusColor
     * @param isFullScreen 是否全屏显示
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor, boolean isFullScreen) {
        setCompat(activity, statusColor, isFullScreen, false);
    }

    public static void setCompat(Activity activity, int statusColor, boolean isFullScreen, boolean isDarkStyle) {
        Window window = activity.getWindow();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //6.0
//
//            // 设置状态栏底色白色
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(Color.WHITE);
//
//            // 设置状态栏字体黑色
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }


//        else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //当前手机版本为5.0及以上
            if (isFullScreen) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加  android6.0以后可以对状态栏文字颜色和图标进行修改
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                window.setStatusBarColor(Color.TRANSPARENT);  //设置状态栏的背景色
//                window.setNavigationBarColor(Color.TRANSPARENT);  //设置虚拟导航栏的背景色
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                window.setStatusBarColor(statusColor);
            }
                if (isDarkStyle) {
                    // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加  android6.0以后可以对状态栏文字颜色和图标进行修改
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            return;
        }

        //当前手机版本为4.4到5.0
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4
//            ViewGroup contentView = (ViewGroup) activity.getWindow().getDecorView();  //获取根视图
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  //绘制区域扩充到虚拟导航栏
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, lp);
        }


    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
