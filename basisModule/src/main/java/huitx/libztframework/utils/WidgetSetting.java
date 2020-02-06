package huitx.libztframework.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import huitx.libztframework.R;

/**
 * @author 作者 E-mail: ZT
 * @version 创建时间：2016年9月19日 上午10:42:41
 * 控件管理类
 */
public class WidgetSetting {

    private static WidgetSetting mWidgetSetting;

    public static WidgetSetting getInstance() {
        synchronized (WidgetSetting.class) {
            if (mWidgetSetting == null) {
                mWidgetSetting = new WidgetSetting();
            }
        }
        return mWidgetSetting;
    }


    /**
     * 过滤空字符串 如果为空或者null返回指定默认值
     */
    public static String filtrationStringbuffer(String text, String normal) {
        return text == null ? "" + normal : text.equals("") ? "" + normal : text.equals("null") ? "" + normal : text;
    }

    /**
     * 过滤空字符串 如果为空或者null返回指定默认值
     */
    public static int filtrationStringbuffer(String text, int normal) {
        return text == null ? normal : text.equals("") ? normal : text.equals("null") ? normal : Integer.parseInt(text);
    }

    /**
     * 为文本框设置内容，过滤null字段
     *
     * @param view   视图对象
     * @param text   文本
     * @param normal 文本对象为空时的默认值
     */
    public static void setViewText(TextView view, String text, String normal) {
        view.setText(text == null ? "" + normal : text.equals("") ? "" + normal : text.equals("null") ? "" + normal : text);
    }

    /**
     * 针对ellipsize属性，不能使用append，对换行文本进行拼接
     */
    public static String appendViewTextString(String text1, String text2, String normal, boolean feedLine) {

        String text = getStr(text1, normal);
        if (feedLine) text = text + ("\n");
        text = text + getStr(text2, normal);
        return text;
    }

    private static String getStr(String text, String normal) {
        return text == null ? "" + normal : text.equals("") ? "" + normal : text.equals("null") ? "" + normal : text;
    }


    /**
     * 为文本框设置内容，过滤null字段
     *
     * @param view  视图对象
     * @param text1 默认文本
     * @param text  文本
     * @param def   文本的默认值（不是默认文本）
     * @param state true，默认文本在前
     */
    public static void setViewText(TextView view, String text1, String text, String def, boolean state) {
        if (state) {
            view.setText(text1 + (text == null ? def : text.equals("") ? def : text));
        } else {
            view.setText((text == null ? def : text.equals("") ? def : text) + text1);
        }

    }

    /**
     * 判断文本框内容是否为空
     *
     * @param view
     * @param hint 为空时的提示语句
     * @return 为空返回false
     */
    public static boolean notNull(TextView view, String hint) {

        if (view.getText().toString().trim().length() <= 0) {
            ToastUtils.showToast(hint);
            return false;
        }

        return true;

    }

    public static boolean notNull(TextView view) {
        if (view.getText().toString().trim().length() <= 0) return false;
        return true;
    }

    /**
     * 文本框连接特殊字体(颜色，大小)
     *
     * @param view       文本控件
     * @param color      特殊字体颜色
     * @param percentage 相对大小倍数
     * @param text       添加内容
     * @param feedLine   是否换行
     */
    public static void setIdenticalLineTvColor(TextView view, int color, float percentage, String text, boolean feedLine) {
        SpannableString spanText = new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(color), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new RelativeSizeSpan(percentage), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        if (feedLine) view.append("\n");
        view.append(spanText);
    }


    /**
     * 设置文本点击事件
     *
     * @param mContext
     * @param view
     * @param text     对应的文本
     * @param color    文本颜色
     * @param intent
     */
    public static void setTvClick(final Context mContext, TextView view, String text, final int color, final Intent intent) {
        SpannableString spanText = new SpannableString(text);
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanText);

        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View arg0) {
                if (intent != null) mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(color); // 设置文本颜色
                ds.setFakeBoldText(true);
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, 0, text.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.append(ssb);
    }

    /**
     * 关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return
     */
    public static void matcherSearchTitle(TextView view, int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        view.append(s);
    }

    /**
     * 多个关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字数组
     * @return
     */
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * 调整指定图片的大小
     * eg：为文本框设置图片 tv.setCompoundDrawables（）
     */
    public static Drawable getWeightDrawable(Context context, int id, int width, int height) {
        LayoutUtil mLayoutUtil = LayoutUtil.getInstance();
        Drawable drawable = null;
        drawable = context.getResources().getDrawable(id);
        if (drawable != null) {
            drawable.setBounds(0, 0, mLayoutUtil.getWidgetWidth(width), mLayoutUtil.getWidgetHeight(height));
        }
        return drawable;
    }
}
