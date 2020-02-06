/*
package com.example.administrator.huimai.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.huimai.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnBinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFrgamentLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(getActivity(), view);
        initView(view);
        loadData();
        return view;
    }

    protected abstract void initView(View view);

    @Override
    public void onStart() {
        loadData();
        super.onStart();
    }

    protected abstract void loadData();

    protected abstract int getFrgamentLayoutId();

    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();
    }
}
*/
package huitx.libztframework.base;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.Unbinder;
import huitx.libztframework.view.dialog.DialogUIUtils;

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnBinder;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false;
    protected boolean isDataInit = false;
    protected boolean isViewInit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFrgamentLayoutId(), container, false);
        initView(view);
//        loadData();
        return view;
    }

    @Override
    public void onStart() {
//       loadData();
        super.onStart();
    }

    protected abstract void initView(View view);

    protected abstract void loadData();

    protected abstract int getFrgamentLayoutId();

    @Override
    public void onDestroy() {
        if (mUnBinder != null)
            mUnBinder.unbind();
        super.onDestroy();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && isViewInit && !isDataInit) {
            isDataInit = true;
            lazyLoad();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        // loadData();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected void lazyLoad() {
    }

    ;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        isViewInit = true;
        if (isVisible && !isDataInit) {
            isDataInit = true;
            lazyLoad();
        }

    }
    /*
     * 加载框
     * */

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
                mBuildDialog = DialogUIUtils.showLoading(getActivity(), data, true, true, false, true).show();
            else mBuildDialog.show();
        } else if (mBuildDialog != null) mBuildDialog.dismiss();
    }

}
