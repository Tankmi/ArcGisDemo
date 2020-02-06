package com.zhxl.arcgisdemo.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.context.ApplicationData;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import huitx.libztframework.context.ContextConstant;
import huitx.libztframework.context.LibPreferenceEntity;
import huitx.libztframework.utils.LOGUtils;
import huitx.libztframework.utils.LayoutUtil;
import huitx.libztframework.utils.NetUtils;
import huitx.libztframework.utils.TransitionTime;
import huitx.libztframework.view.dialog.DialogUIUtils;

/**
 * dialogFragment
 *
 * @author ZhuTao
 * @date 2017/7/12
 * @params
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected View Mview; // 当前界面的根
    private int MlayoutId; // 当前界面对应的布局
    public Context mContext;    //上下文
    /**
     * 获取当前类名
     */
    public String TAG;

    private Unbinder unbinder;

    /**
     * 软键盘的处理
     * imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);	//显示软键盘
     * imm.hideSoftInputFromWindow(et_sendmessage.getWindowToken(), 0); //强制隐藏键盘
     */
    public InputMethodManager imm;    //软键盘的处理


    /**
     * 时间展示格式转换工具
     */
    public TransitionTime tranTimes;

    protected LayoutUtil mLayoutUtil;

    public BaseDialogFragment(int layoutId) {
        super();
        this.MlayoutId = layoutId;
        mContext = ApplicationData.context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //设置样式
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        //设置无标题
        window.requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        //设置背景为透明
        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
        int dialogHeight = getContextRect(getActivity());
        //设置弹窗大小为会屏
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        //去除阴影
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = 0.0f;
        window.setAttributes(layoutParams);

    }

    //获取内容区域
    private int getContextRect(Activity activity){
        //应用区域
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        return outRect1.height();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Mview = View.inflate(getActivity(), MlayoutId, null);
        unbinder = ButterKnife.bind(this, Mview);

        init(); // 初始化头中的各个控件,以及公共控件ImageLoader
        initHead(); // 初始化设置当前界面要显示的头状态
        initLocation(); // 初始化空间位置
        initLogic(); // 初始化逻辑
        return Mview;
    }


    /**
     * 初始化头中的各个控件,以及公共控件ImageLoader
     */
    protected void init() {
        mLayoutUtil = LayoutUtil.getInstance();
        tranTimes = TransitionTime.getInstance();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);//初始化软键盘处理的方法
    }

    protected Dialog mBuildDialog;

    protected void setLoading(boolean isShowLoading) {
        setLoading(isShowLoading, "");
    }

    /**
     * 显示或者隐藏，正在加载弹窗
     */
    protected void setLoading(boolean isShowLoading, String data) {
        if (isShowLoading) {
            if (mBuildDialog == null)
                mBuildDialog = DialogUIUtils.showLoading(getActivity(), data, true, true, false, true).show();
            else mBuildDialog.show();
        } else if (mBuildDialog != null) mBuildDialog.dismiss();
    }

    /**
     * 判断是否登录
     *
     * @return 登录返回true，否则返回false
     */
    public boolean isLogin() {
        return LibPreferenceEntity.isLogin;
    }

    protected abstract void initHead();

    /**
     * 初始化控件位置
     */
    protected abstract void initLocation();

    /**
     * 初始化逻辑
     */
    protected abstract void initLogic();

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        pauseClose();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(unbinder!=null)unbinder.unbind();
        destroyClose();
    }

    /**
     * pause关闭方法
     */
    protected abstract void pauseClose();

    /**
     * destroy关闭方法
     */
    protected abstract void destroyClose();

    /**
     * 避免每次都进行强转
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T findViewByIds(int viewId) {
        return (T) Mview.findViewById(viewId);
    }

    public <T> T findViewByIds(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }


    /**
     * 打印日志
     *
     * @param data 需要打印的内容
     */
    public void LOG(String data) {
        LOG(TAG, data);
    }

    public void LOG(String tag, String data) {
        LOGUtils.LOG(tag + data);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LOG("setUserVisibleHint: " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
//        if(getUserVisibleHint()){
        if (isVisibleToUser) {
            onVisibile();
        } else {
            onInVisibile();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    /**
     * fragment可见时的操作
     */
    protected void onVisibile() {
    }

    /**
     * fragment不可见时的操作
     */
    protected void onInVisibile() {
    }

    protected void error(String msg, int type) {
        NetUtils.isAPNType(mContext);
        setLoading(false);
        if (msg.equals(ContextConstant.HTTPOVERTIME)) {
            LOG("请求超时");
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        // 这里把原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }

}
