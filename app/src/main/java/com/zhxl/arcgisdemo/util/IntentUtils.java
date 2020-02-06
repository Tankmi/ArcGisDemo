package com.zhxl.arcgisdemo.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.zhxl.arcgisdemo.context.PreferenceEntity;

import huitx.libztframework.utils.ToastUtils;

/**
 * @author zhutao
 * @version V1.0
 * @Title: IntentUtils
 * @Package Android_ServeOld02  com.zhxl.serve.intent
 * @date 2019-11-14   17:15
 * @Description:
 */
public class IntentUtils {
    static IntentUtils intentUtils;

    static {
        intentUtils = new IntentUtils();
    }

    public static IntentUtils getInstance() {    //双重检验锁，实现线程安全
        if (intentUtils == null) {
            synchronized (IntentUtils.class) {
                if (intentUtils == null) {
                    intentUtils = new IntentUtils();
                }
            }
        }
        return intentUtils;
    }

    //重新登录
    public boolean reLogin(Context actContext) {
        PreferenceEntity.isLogin = false;
        ToastUtils.showToast("登录信息异常，请重新登录");
//        readGo(actContext, GoodsDetailActivity.class, null);
        return true;
    }

    //首页
    public boolean intentHome(Context actContext) {
//        readGo(actContext, HomeActivity.class, null);

        return true;
    }

//    /**
//     * 跳转到商品详情
//     * <p>
//     * 商品ID，商品分类
//     */
//    public Bundle getIntentBundle(Long goodsId, int goodsType) {
//        Bundle bundle = new Bundle();
//
//        if (goodsType == 0) goodsType = ConstantsKTKt.getGOODSTYPE_JD();
//
//        bundle.putLong("goods_id", goodsId);
//        bundle.putInt("type", goodsType);
//        return bundle;
//    }
//
//    public Bundle getIntentBundles(Map<String, String> mapData) {
//        Bundle bundle = new Bundle();
//
//        for (Map.Entry<String, String> entry : mapData.entrySet()) {
//            bundle.putString(entry.getKey(), entry.getValue());
//        }
//
//        return bundle.size() > 0 ? bundle : null;
//    }


    public void readGo(Context actContext, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(actContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        actContext.startActivity(intent);
    }

    /****************
     *
     * 发起添加群流程。群号：中汇客服(483786113) 的 key 为： u8MfRZp9q5XtUXnlV0oKkvXjYhwuae4U
     * 调用 joinQQGroup(u8MfRZp9q5XtUXnlV0oKkvXjYhwuae4U) 即可发起手Q客户端申请加群 中汇客服(483786113)
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(Context actContext) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "u8MfRZp9q5XtUXnlV0oKkvXjYhwuae4U"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            actContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            ToastUtils.showToast("未安装手Q或安装的版本不支持");
            return false;
        }
    }


}
