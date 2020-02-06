package com.zhxl.arcgisdemo.welcome.guide;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhxl.arcgisdemo.R;
import com.zhxl.arcgisdemo.base.BaseDialogFragment;
import com.zhxl.arcgisdemo.context.PreferenceEntity;
import com.zhxl.arcgisdemo.welcome.guide.bean.WelcomeGuideBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import huitx.libztframework.utils.EventBusMessage;
import huitx.libztframework.view.guide_view.GuideViewPager;
import huitx.libztframework.view.guide_view.indicator.DotIndicator;
import huitx.libztframework.view.guide_view.interf.GuideViewHolder;
import huitx.libztframework.view.guide_view.interf.GuideViewPagerPageListener;

/**
 *
 * 引导页
 */
public class GuidesDialogFragment extends BaseDialogFragment implements GuideViewPagerPageListener {

    static GuidesDialogFragment eyeHintFragment;

    private boolean isEnd;

    @BindView(R.id.gvp_welcome_guide)
    GuideViewPager mViewpager;
    @BindView(R.id.dot_welcome_guide)
    DotIndicator mDotView;
    @BindView(R.id.btn_welcome_guide)
    Button mBtn;

    private String[] images = {
            "https://images.unsplash.com/photo-1531256379416-9f000e90aacc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1513757378314-e46255f6ed16?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1506269085878-5c33839927e9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1543095414-e0660f5a1a5c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
    };

    public GuidesDialogFragment() {
        super(R.layout.dialog_fragment_guides);
        TAG = getClass().getSimpleName() + "     ";
    }

    public static GuidesDialogFragment getInstance(int state){
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
//        if(eyeHintFragment == null)
            eyeHintFragment = new GuidesDialogFragment();
        eyeHintFragment.setArguments(bundle);
        return eyeHintFragment;
    }

    @Override
    protected void initHead() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, mLayoutUtil.getWidgetHeight(677));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    @OnClick(R.id.btn_welcome_guide)void next(){
        if(isEnd) {
            dismiss();
            EventBus.getDefault().post(new EventBusMessage(PreferenceEntity.EVENT_BUS_REFRESH_SHOPCART, true));
        }
        else mViewpager.setCurrentItem(mViewpager.getCurrentItem()+1);

    }

    @Override
    protected void initLocation() {

    }

    @Override
    protected void initLogic() {
        mViewpager.setViewHolder(new BannerViewHolder());
        mViewpager.addIndicator(mDotView);
        mViewpager.setOnPageChangeListener(this);
        initData();
    }

    @Override
    public void currentItem(int sum, int position, boolean isEnd) {
//        LOG(sum + " " + position + " " + isEnd);
        this.isEnd = isEnd;
        if(isEnd) mBtn.setText("开始测试");
        else mBtn.setText("下一步");
    }

    private void initData(){
//        state = getArguments().getInt("state");
        List<WelcomeGuideBean> lists = new ArrayList<>();
            lists.add(new WelcomeGuideBean(R.drawable.iv_welcome_fine_new,"第一步：手机距被测试者眼睛25cm远"));
            lists.add(new WelcomeGuideBean(R.drawable.iv_welcome_fine_new,"第二步：手机与被测试者眼睛置同一水平位"));
            lists.add(new WelcomeGuideBean(R.drawable.iv_welcome_fine_new,"第三步：按缺口方向正确滑动"));
        mViewpager.setData(lists);


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public class BannerViewHolder implements GuideViewHolder<WelcomeGuideBean> {
        private boolean isRoundedCorner = true;

        public BannerViewHolder() {

        }

        public BannerViewHolder(boolean roundedCorner) {
            isRoundedCorner = roundedCorner;
        }

        @Override
        public View getView(Context context, final int position, WelcomeGuideBean data) {
            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_welcome_guide, null);
            ImageView imageView = inflate.findViewById(R.id.iv_item_welcome_guide);
            TextView textView = inflate.findViewById(R.id.tv_item_welcome_guide);
//            if (isRoundedCorner) {
//                Glide.with(imageView).load(list).transforms(new CenterCrop(), new RoundedCorners(dp2px(5))).disallowHardwareConfig().into(imageView);
//            } else {
                Glide.with(imageView).load(data.getImgId()).disallowHardwareConfig().into(imageView);
//            }

            textView.setText(data.getContent());

            imageView.setOnClickListener(view -> {
//                Intent intent_home = new Intent(mContext, WebViewActivity.class);
//                intent_home.putExtra("url", list.url);
//                intent_home.putExtra("title_name", list.title);
//                intent_home.putExtra("is_refresh", false);
//                mContext.startActivity(intent_home);
            });
            return inflate;
        }
    }

    @Override
    protected void pauseClose() {
    }

    @Override
    protected void destroyClose() {
        LOG("destroyClose");
    }

}
