package huitx.libztframework.base.util;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

/**
 * Created by gmx on 18/2/7.
 */

public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        sApplication = app;
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }
}
