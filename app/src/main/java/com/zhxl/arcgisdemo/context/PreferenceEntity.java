package com.zhxl.arcgisdemo.context;

import com.zhxl.arcgisdemo.user.UserEntity;

import java.util.HashMap;

import huitx.libztframework.context.LibPreferenceEntity;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.NewWidgetSetting;
import huitx.libztframework.utils.PreferencesUtils;
import huitx.libztframework.utils.StringUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class
PreferenceEntity extends LibPreferenceEntity {
	/** 标记是否初始化过icon */
	public static boolean isInitIcons;

//	/** 本地缓存地址 mnt/sdcard/huidf_doc */
//	public static final String KEY_CACHE_PATH = "/huidf_slimming";
	// 清除文件夹里面的文件！
	// boolean file = DelectFileUties.delAllFile("mnt/sdcard/menmen/");
	// if (file) {
	// ToastUtils.show(ApplicationData.context, "清除缓存成功！");
	// }else {
	// ToastUtils.show(ApplicationData.context, "清除缓存失败");
	// }

	/** 是否同步用户id（H5 LocalStorage缓存）  */
	public static boolean isSyncUserDatas = false;

	/** 判断是否已经登录 */
	public static boolean isLogin(){
		String user_id = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_ID, "");
		String user = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_ACCOUNT, "");
//		String imei = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_IMEI, "");
//		if(imei.equals(ApplicationData.imei) && user_id !=null && !user_id.equals("") && user !=null && !user.equals("")) return true;
//		if(!NewWidgetSetting.filtrationStringbuffer(imei,"").equals("") &&!NewWidgetSetting.filtrationStringbuffer(imei,"").equals("") && user_id !=null && !user_id.equals("") && user !=null && !user.equals("")) return true;
		if(user_id !=null && !user_id.equals("") && user !=null && !user.equals("")) return true;
		else return false;
	}

	public static void setUserEntity(UserEntity.Data data) {
		clearData();
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ID, "" + data.id);
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ACCOUNT, "" + data.phone);
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_HEADER, NewWidgetSetting.getInstance().filtrationStringbuffer(data.head, ""));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_NICK, NewWidgetSetting.getInstance().filtrationStringbuffer(data.niceName, ""));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_EMAIL, NewWidgetSetting.getInstance().filtrationStringbuffer(data.email, ""));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_SEX, NewWidgetSetting.getInstance().filtrationStringbuffer(data.sex, "1"));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_BIR, NewWidgetSetting.getInstance().filtrationStringbuffer(data.birthday, "1"));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ISREGISTER, NewWidgetSetting.getInstance().filtrationStringbuffer(data.isRegist, "1"));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ISSETPSW, NewWidgetSetting.getInstance().filtrationStringbuffer(data.isPwdSet, "0"));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_BANLANCE, NewWidgetSetting.getInstance().filtrationStringbuffer(data.balance, ""));
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_INTEGRAL, NewWidgetSetting.getInstance().filtrationStringbuffer(data.integral, ""));
	}

	/** 清空用户信息 */
	public static void clearData() {
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ID, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ACCOUNT, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_HEADER, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_NICK, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_EMAIL, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_SEX, "");
//		PreferencesUtils.putString(ApplicationData.context, KEY_USER_AGE, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_BIR, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ISREGISTER, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_ISSETPSW, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_BANLANCE, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_USER_INTEGRAL, "");

		PreferencesUtils.putString(ApplicationData.context, KEY_ADDRESS_USER_NAME, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_ADDRESS_AREA, "");
		PreferencesUtils.putString(ApplicationData.context, KEY_ADDRESS_ID, "");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getLoginData() {
		HashMap ha = new HashMap();
		String user = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_ACCOUNT, "");
		ha.put("account", user);// 保存用户加密后的密码

		return ha;
	}


	public  static Request getLoginParamsForOkHttp(Request request){
		HashMap map = PreferenceEntity.getLoginData();
//		LOGUtils.LOG("PreferenceEntity 请求参数：id  " + (String) map.get("id"));
//		LOGUtils.LOG("PreferenceEntity 请求参数：user  " + (String) map.get("user"));
//		LOGUtils.LOG("PreferenceEntity 请求参数：imei  " + (String) map.get("imei"));
		return request.newBuilder()
				.addHeader("account", (String) map.get("account"))
				.build();
	}

	public  static HashMap getQueryBodyParamsForOkHttp(){
		HashMap map = PreferenceEntity.getLoginData();
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
		HashMap map = PreferenceEntity.getLoginData();
		return request.url().newBuilder()
				.addQueryParameter("phone", (String) map.get("account"))
				.build();
	}

	// ***********************************用户信息
	/** 记录用户的id，long型 */
	public static final String KEY_USER_ID = "user_id";
	/** 签名 */
//	public static final String KEY_USER_SIGNATURE = "user_signature";
//	public static final String KEY_USER_IMEI = "user_imei";
//	public static final String KEY_USER_MONEY = "user_money";
//	public static final String KEY_USER_DOCID = "user_docid";
	public static final String KEY_USER_NICK = "user_nick";
	public static final String KEY_USER_HEADER = "user_header";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_APP_UPDATE_VERSION= "app_version";
	public static final String KEY_APP_WX_QQ_UNIONID = "wx_qq_unionId";
	public static final String KEY_APP_UPDATE_URL = "app_url";
	/** 是否补全用户信息 */
	public static final String KEY_USER_ISALL = "isall";
	public static final String KEY_USER_SEX = "user_sex";
	public static final String KEY_USER_BIR = "user_bir";
	public static final String KEY_USER_AGE = "user_age";
	/** 帐号 */
	public static final String KEY_USER_ACCOUNT = "user_account";
	/** 密码 */
	public static final String KEY_USER_PSW = "user_psw";
	public static final String KEY_USER_ISREGISTER = "user_is_register";
	public static final String KEY_USER_ISSETPSW = "user_is_setpsw";
	public static final String KEY_USER_PAYPSW = "user_pay_psw";
	public static final String KEY_USER_BANLANCE = "user_balance";
	public static final String KEY_USER_INTEGRAL = "user_integral";


	public static boolean isLogin=false;	//是否登录
	public static boolean isInitHomeData=false;	//欢迎页面是否已经加载了首页数据

	public static boolean isRefreshHomeData = true;	//是否刷新首页数据
	/** 清空SP缓存 */
	public static String KEY_IS_CLEAR_SP = "key_is_clear_sp";

	// ***********************************用户信息

	//首页数据缓存
	public static String KEY_CACHE_BANNER = "key_cache_banner";
	public static String KEY_CACHE_HOME = "key_cache_home";
	//提交订单
	public static String KEY_ADDRESS_USER_NAME = "key_address_user_name";
	public static String KEY_ADDRESS_AREA = "key_address_area";
	public static String KEY_ADDRESS_ID = "key_address_id";
	public static String KEY_ADDRESS_FLAG = "key_address_flag";

	public static String KEY_CACHE_INFORMATION_HOME = "key_cache_information_home";
	public static String KEY_CACHE_INFORMATION = "key_cache_information";
	public static String KEY_CACHE_INFORMATION_SEARCH = "key_cache_information_search";
	public static String KEY_LOCAION_ADDRESS = "key_location_address";

	public static String KEY_ISSHOPCART_INITSYNCDATA = "key_isshopcart_initsyncdata";	//是否判断过要将离线数据同步至服务端
	public static String KEY_ISSHOPCART_SYNCDATA = "key_isshopcart_syncdata";	//是否需要将离线数据库同步至服务端
	/** 是否迁移购物车数据库 1.0 -> 2.0 */
	public static String KEY_ISSHOPCART_MIGRATION1_2 = "key_isshopcart_migration1_2";

	//EventBus
	public static String EVENT_BUS_REFRESH_USERINFO = "refreshUserData";	//更新用户信息
	public static String EVENT_BUS_REFRESH_SHOPCART = "refreshShopCar";	//更新购物车

}
