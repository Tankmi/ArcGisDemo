package huitx.libztframework.context;

import android.os.Build;

import java.util.HashMap;

import huitx.libztframework.base.util.Utils;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.PreferencesUtils;
import huitx.libztframework.utils.StringUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static huitx.libztframework.utils.LOGUtils.LOG;

public class LibPreferenceEntity {
    /**
     * 本地缓存地址  目录1
     *  LOG("目录：   " + Environment.getExternalStorageDirectory() + PreferenceEntity.KEY_CACHE_PATH);
     *  LOG("目录2：   " + "mnt/sdcard/" + PreferenceEntity.KEY_CACHE_PATH);
     */
    public static final String KEY_CACHE_PATH = "/huidf_slimming";      //创建新的项目时注意修改，每个项目的缓存地址都是独立的
    // 清除文件夹里面的文件！
    // boolean file = DelectFileUties.delAllFile("mnt/sdcard/menmen/");
    // if (file) {
    // ToastUtils.show(LibApplicationData.context, "清除缓存成功！");
    // }else {
    // ToastUtils.show(LibApplicationData.context, "清除缓存失败");
    // }

    /** 是否登录  */
    public static boolean isLogin = false;

    /**
     * 标记是不是第一次打开APP
     */
    public static String KEY_IS_FIRST_OPEN = "";

    //屏幕信息
    public static int screenWidth;
    public static int screenHeight;
    /** 状态栏的高度  */
    public static float ScreenTop;
    /** 虚拟键盘的高度  */
    public static float ScreenTitle_navigationBarHeight;
    /** 是否显示虚拟键*/
    public static boolean hasNavigationBar = false;

    //sp缓存信息
    public static final String KEY_SCREEN_WIDTH = "screen_width";
    public static final String KEY_SCREEN_HEIGHT = "screen_height";
    /** 状态栏的高度  */
    public static final String KEY_SCREEN_TOP = "screen_top";
    /**  虚拟键盘的高度  */
    public static final String KEY_SCREEN_NAVIGATIONBAR_H = "screen_screennavigationbar_height";
    /** 判断是否显示虚拟键盘 boolean */
    public static final String KEY_SCREEN_HASNAVIGATION = "screen_has_navigationbar";
    /** 是否有屏幕信息 boolean */
    public static final String KEY_SCREEN_ISSAVE = "screen_is_save";
    //屏幕信息

    public static void initScreenView(){
        screenWidth = PreferencesUtils.getInt(LibApplicationData.context,KEY_SCREEN_WIDTH);
        screenHeight = PreferencesUtils.getInt(LibApplicationData.context,KEY_SCREEN_HEIGHT);
        ScreenTop = PreferencesUtils.getFloat(LibApplicationData.context,KEY_SCREEN_TOP);
        ScreenTitle_navigationBarHeight = PreferencesUtils.getFloat(LibApplicationData.context,KEY_SCREEN_NAVIGATIONBAR_H);
        hasNavigationBar = PreferencesUtils.getBoolean(LibApplicationData.context,KEY_SCREEN_HASNAVIGATION);

        LOG("状态栏的高度：" + ScreenTop
                + ",屏幕的宽度:"  + screenWidth + ",屏幕的高度:"
                +screenHeight + "虚拟键盘的高度：" + ScreenTitle_navigationBarHeight + "是否有虚拟键盘：" + hasNavigationBar);
    }

    public static void saveScreenView(){
        PreferencesUtils.putInt(LibApplicationData.context,KEY_SCREEN_WIDTH,screenWidth);
        PreferencesUtils.putInt(LibApplicationData.context,KEY_SCREEN_HEIGHT,screenHeight);
        PreferencesUtils.putFloat(LibApplicationData.context,KEY_SCREEN_TOP,ScreenTop);
        PreferencesUtils.putFloat(LibApplicationData.context,KEY_SCREEN_NAVIGATIONBAR_H,ScreenTitle_navigationBarHeight);
        PreferencesUtils.putBoolean(LibApplicationData.context,KEY_SCREEN_HASNAVIGATION,hasNavigationBar);
        PreferencesUtils.putBoolean(LibApplicationData.context,KEY_SCREEN_ISSAVE,true);
    }

    //User
    /** 帐号 */
    public static final String KEY_USER_ACCOUNT = "user_account";

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static HashMap getLoginData() {
        HashMap ha = new HashMap();
        String user = PreferencesUtils.getString(Utils.getApp(), KEY_USER_ACCOUNT, "");
        ha.put("account", user);// 保存用户加密后的密码

        return ha;
    }

    public  static Request getLoginParamsForOkHttp(Request request){
        HashMap map = getLoginData();
//		LOGUtils.LOG("PreferenceEntity 请求参数：id  " + (String) map.get("id"));
//		LOGUtils.LOG("PreferenceEntity 请求参数：user  " + (String) map.get("user"));
//		LOGUtils.LOG("PreferenceEntity 请求参数：imei  " + (String) map.get("imei"));
        return request.newBuilder()
                .addHeader("account", (String) map.get("account"))
                .build();
    }

    public  static HashMap getQueryBodyParamsForOkHttp(){
        HashMap map = getLoginData();
        LOGUtils.LOG("map.get(\"account\") : " + map.get("account"));
        if(map.get("account") == null || StringUtils.isEmpty((String) map.get("account")) ){
            LOGUtils.LOG("map.get(\"account\") == null ");
            return null;
        }
        HashMap ha = new HashMap();
        ha.put("phone", (String) map.get("account"));// 公共请求参数，账号

        return ha;
    }

    public  static HttpUrl getUrlParamsForOkHttp(Request request){
        HashMap map = getLoginData();
        return request.url().newBuilder()
                .addQueryParameter("phone", (String) map.get("account"))
                .build();
    }

}
