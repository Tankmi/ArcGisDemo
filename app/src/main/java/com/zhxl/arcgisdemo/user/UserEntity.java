/**
 *
 */
package com.zhxl.arcgisdemo.user;


import com.zhxl.arcgisdemo.context.ApplicationData;
import com.zhxl.arcgisdemo.context.PreferenceEntity;

import huitx.libztframework.utils.PreferencesUtils;

/**
 * @Description: TODO(用户登录信息实体类)
 * @author ZhuTao
 * @date 2015年3月23日 下午1:49:19
 * @version V1.0
 */
public class UserEntity {

    public int code;
    public String cost;
    public String msg;
    public Data data;

    public static class Data {
        /** 个人中心，修改头像地址 */
//        public String header;
        public String head;
        public String id;
        public String phone;
        public String status;
        /** 0,未注册，1，已注册 */
        public String isRegist;
        /** 1男，2女 */
        public String sex;
        /**0,未设置，1，已设置 */
        public String isPwdSet;
        /** 支付密码 */
        public String password;
        /** 用户名 */
        public String niceName;

        //微信登录
        /** :type=2时返回以下数据（直接登录） type=1时进入绑定手机号页面 */
        public String type;
        /** 微信唯一标识 */
        public String unionId;

        public String answerOne;
        public String answerTwo;
        public String questionOne;
        public String questionTwo;
        /** 余额 */
        public String balance;
        /** 积分 */
        public String integral;
        public String birthday;
        public String email;
        public String imei;
//        public String name;
        public String newPassword;
    }

    public static UserEntity.Data getUserInfo() {
        UserEntity.Data mData = new UserEntity.Data();
        mData.phone = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_ACCOUNT, "");
        mData.head = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_HEADER,"");
        mData.niceName = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_NICK,"");
        mData.email = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_EMAIL,"");
        mData.sex = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_SEX,"1");
        mData.birthday = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_BIR,"");
        mData.balance = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_BANLANCE,"");
        mData.integral = PreferencesUtils.getString(ApplicationData.context, PreferenceEntity.KEY_USER_INTEGRAL,"");
        return mData;
    }

}
