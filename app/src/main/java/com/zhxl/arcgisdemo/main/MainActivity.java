package com.zhxl.arcgisdemo.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.base.BaseFragmentActivity;
import com.zhxl.arcgisdemo.context.ApplicationData;
import com.zhxl.arcgisdemo.gis.GISMapFragment;
import com.zhxl.arcgisdemo.gis.GISSceneFragment;
import com.zhxl.arcgisdemo.kotlin.KotlinTestFragmentNew;
import com.zhxl.arcgisdemo.main.net.HomeNetEntity;
import com.zhxl.arcgisdemo.main.net.HomeNetPresenter;
import com.zhxl.arcgisdemo.main.net.HomeNetView;
import com.zhxl.arcgisdemo.util.VersionTools;
import com.zhxl.arcgisdemo.welcome.advertising.AdvertisingFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import huitx.libztframework.utils.ToastUtils;
import huitx.libztframework.utils.WidgetSetting;
import huitx.libztframework.view.FragmentSwitchTool;
import huitx.libztframework.view.dialog.DialogUIUtils;

import static huitx.libztframework.utils.LOGUtils.LOG;


/**
 * @author : Zhutao
 * @version 创建时间：@2016年11月23日
 * @Description: 主页
 * @params：
 */
public class MainActivity extends BaseFragmentActivity implements OnClickListener, DialogInterface.OnDismissListener
        , HomeNetView<HomeNetEntity.Data>
        , AdvertisingFragment.OnAdvertisingListener {

    HomeNetPresenter homeNetPresenter;

    //    protected HomeFragment homeFragment;
//    HomeGoodsClassifyFragment homeGoodsClassifyFragment;
//    private MyFragment myFragment;
//    private HomeMallFragment homeMallFragment;
//    private ShopCartFragment shopCarFragment;
//
    protected FragmentSwitchTool mFragmentSwitch;
    private final int VERSION_UPDATE = 100;
    /**
     * 记录页面退出时间
     */
    private long exitTime = 0;
    private String androidMsg;
    private int newVersion;

    public MainActivity() {
        super(R.layout.activity_home);
        TAG = getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
    }

    @Override
    protected void initHead() {
        setStatusBarColor(true, true, mContext.getResources().getColor(R.color.transparency));
//        iv_title_status_bar_fill.setBackgroundResource(0x00000000);
        if (mHandler == null) mHandler = new MyHandler(this);
    }

    @Override
    protected void initLocation() {
        mLayoutUtil.setIsFullScreen(true);
        lin_tab_home.setMinimumHeight(mLayoutUtil.getWidgetHeight(100));
    }

    @Override
    protected void initLogic() {
        //test
        initFragment();

//        showOrHideAdvertisingFragment(NetUtils.isAPNType());
    }

    //广告播放监听
    @Override
    public void onAdvertisingClose(long bufferTime) {
        showOrHideAdvertisingFragment(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LOG("RankingListActivity onActivityResult requestCode" + requestCode);
    }

    private Runnable getVersionRunnable = new Runnable() {
        public void run() {
            getVersion();
        }
    };

    //保存页面的缓存信息，在onCreate方法中可以进行数据的初始化
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putString("home_datas", "非正常退出！");
    }

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTran;
    AdvertisingFragment mAdvertisingFragment;

    protected void showOrHideAdvertisingFragment(boolean isShow) {
        if (mAdvertisingFragment == null) mAdvertisingFragment = new AdvertisingFragment();
        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
        fragmentTran = fragmentManager.beginTransaction();
        if (isShow) {
            if (mAdvertisingFragment.isAdded()) fragmentTran.show(mAdvertisingFragment);
            else fragmentTran.replace(R.id.fl_home_advertising, mAdvertisingFragment, "ShopCart");
        } else {
            fragmentTran.hide(mAdvertisingFragment);
        }
        fragmentTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTran.commitAllowingStateLoss();

        if(!isShow){
            mHandler.post(getVersionRunnable);
            initFragment();
        }

    }

    GISMapFragment gisMapFragment;
    GISSceneFragment gisSceneFragment;
    KotlinTestFragmentNew kotlinTestFragmentNew;

    private void initFragment() {
        gisMapFragment = new GISMapFragment();
        gisSceneFragment = new GISSceneFragment();

        kotlinTestFragmentNew = new KotlinTestFragmentNew();

//        homeMallFragment = new HomeMallFragment();
//        shopCarFragment = new ShopCartFragment();
//        myFragment = new MyFragment();
//        GISMapFragment gisMapFragment = new GISMapFragment();
//        homeGoodsClassifyFragment = HomeGoodsClassifyFragment.getInstance(true, -1);

        mFragmentSwitch = new FragmentSwitchTool(getSupportFragmentManager(), R.id.fl_home);
        mFragmentSwitch.setClickableViews(lin_tab01, lin_mall, lin_tab_home_information, lin_tab03, lin_tab04);
        mFragmentSwitch.addSelectedViews(new View[]{iv_tab_home_choiceness})
                .addSelectedViews(new View[]{iv_tab_home_dynamic})
                .addSelectedViews(new View[]{iv_tab_home_information})
                .addSelectedViews(new View[]{iv_tab_home_history})
                .addSelectedViews(new View[]{iv_tab_home_settings});

        mFragmentSwitch.setFragments(
                kotlinTestFragmentNew.getClass(),
                gisSceneFragment.getClass(),
                gisMapFragment.getClass(),
                gisMapFragment.getClass(),
                gisMapFragment.getClass()
        );

        Bundle bundle = new Bundle();
        bundle.putBoolean("is_homeView", true);
        mFragmentSwitch.setBundles(null, bundle);

        mFragmentSwitch.changeTag(lin_tab01);
    }

//
//    @Override
//    public void loadNews() {
//        mFragmentSwitch.changeTag(lin_tab_home_information);
//    }

    /**
     * 获取服务端缓存的版本号，判断是否需要更新 0
     */
    protected void getVersion() {
        if (homeNetPresenter == null) {
            homeNetPresenter = new HomeNetPresenter();
            homeNetPresenter.attachView(this);
        }
        homeNetPresenter.getVerisionName();

    }


    protected MyHandler mHandler;

    @Override
    public void onVerisionData(boolean state, HomeNetEntity.Data data) {
        int versionCode = VersionTools.getVersionCode(mContext);
        String normalUpdateUrl = "https://www.pgyer.com/7tS0";

        newVersion = WidgetSetting.filtrationStringbuffer(data.android + "", -1);
        androidMsg = WidgetSetting.filtrationStringbuffer("更新内容：\n" + data.androidMessage + "\n", "");
        int constraintVersion = WidgetSetting.filtrationStringbuffer(data.androidUnder + "", -1);

        //test
//        constraintVersion = 20;
//        newVersion = 22;
//        list.androidUrl="http://39.97.172.44:8080/getUploadHtml";

        LOG("当前系统版本：" + versionCode
                + "; 强制更新版本：" + constraintVersion
                + "; 最新版本：" + newVersion
                + "; 更新内容:" + androidMsg);

        if (newVersion > versionCode) {
            if (constraintVersion > versionCode) {    //最低强制更新版本高于当前版本号，需要强制更新
                initDialogView(WidgetSetting.filtrationStringbuffer(data.androidUrl, normalUpdateUrl), 1);
            } else {
                initDialogView(WidgetSetting.filtrationStringbuffer(data.androidUrl, normalUpdateUrl), 0);
            }
        }
    }

    @Override
    public void loadingShow() {

    }

    @Override
    public void loadingDissmis() {

    }

    @Override
    public void loginOut() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    protected class MyHandler extends Handler {

        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<Activity> mActivity;

        protected MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: // 获取版本号

                    break;
            }
        }

    }

    @Override
    protected void pauseClose() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2500) {
                ToastUtils.showToast("再按一次后退键退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ApplicationData.getInstance().finishApp();
//				android.os.Process.killProcess(android.os.Process.myPid());
//                finish();
            }
            return true;
        }
        return super.onKeyDown(event.getKeyCode(), event);
    }

    @Override
    protected void destroyClose() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        if (homeNetPresenter != null) {
            homeNetPresenter.detachView();
            homeNetPresenter = null;
        }
    }

    @Override
    public void onClick(View view) {
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        LOG("onDismiss");
    }

    private View mDialogView;
    protected Dialog dialog;
    private ImageView iv_dialog_update_affirm;
    private TextView tv_dialog_update_affirm, tv_versionName;
    private Button btn_dia_update_cancel;
    private Button btn_dia_update_affirm;

    /**
     * 更新提示
     *
     * @param isUpdate 1：强制更新
     */
    protected void initDialogView(final String url, int isUpdate) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (mDialogView == null) {
            mDialogView = View.inflate(this, R.layout.dialog_update_affirm, null);
            iv_dialog_update_affirm = findViewByIds(mDialogView, R.id.iv_dialog_update_affirm);
            tv_dialog_update_affirm = findViewByIds(mDialogView, R.id.tv_dialog_update_affirm);
            tv_versionName = findViewByIds(mDialogView, R.id.tv_versionName);
            btn_dia_update_cancel = findViewByIds(mDialogView, R.id.btn_dia_update_cancel);
            btn_dia_update_affirm = findViewByIds(mDialogView, R.id.btn_dia_update_affirm);

            btn_dia_update_cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        if (isUpdate == 1) {
                            ApplicationData.getInstance().exit();
                        } else {
                            DialogUIUtils.dismiss(dialog);
                        }
                    }
                }
            });

            btn_dia_update_affirm.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        DialogUIUtils.dismiss(dialog);
                    }
                }
            });
        }

        tv_versionName.setText(" V " + newVersion);
        tv_dialog_update_affirm.setText(androidMsg + "");
        iv_dialog_update_affirm.setVisibility(View.VISIBLE);

        if (dialog == null) {
            dialog = DialogUIUtils.showCustomAlert(this, mDialogView, Gravity.TOP, true, false).show();
            dialog.setCancelable(false);
        } else dialog.show();

        dialog.setOnDismissListener(this);

    }


    @BindView(R.id.rl_home_main)
    protected RelativeLayout rel_home_main;
    @BindView(R.id.lin_tab_home)
    protected LinearLayout lin_tab_home;
    @BindView(R.id.lin_tab_home_choiceness)
    protected LinearLayout lin_tab01;
    @BindView(R.id.iv_tab_home_choiceness)
    protected ImageView iv_tab_home_choiceness;
    @BindView(R.id.tv_tab_home_choiceness)
    protected TextView tv_tab_home_choiceness;
    @BindView(R.id.lin_tab_home_dynamic)
    protected LinearLayout lin_mall;
    @BindView(R.id.iv_tab_home_dynamic)
    protected ImageView iv_tab_home_dynamic;
    @BindView(R.id.lin_tab_home_information)
    protected LinearLayout lin_tab_home_information;
    @BindView(R.id.iv_tab_home_information)
    protected ImageView iv_tab_home_information;
    @BindView(R.id.tv_tab_home_dynamic)
    protected TextView tv_tab_home_dynamic;
    @BindView(R.id.lin_tab_home_history)
    protected LinearLayout lin_tab03;
    @BindView(R.id.iv_tab_home_history)
    protected ImageView iv_tab_home_history;
    @BindView(R.id.tv_tab_home_history)
    protected TextView tv_tab_home_history;
    @BindView(R.id.lin_tab_home_settings)
    protected LinearLayout lin_tab04;
    @BindView(R.id.iv_tab_home_settings)
    protected ImageView iv_tab_home_settings;
    @BindView(R.id.tv_tab_home_settings)
    protected TextView tv_tab_home_settings;

}
