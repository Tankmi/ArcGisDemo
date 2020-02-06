package huitx.libztframework.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

/**
 * @author zhutao
 * @version V1.0
 * @Title: TextViewSpanUtils
 * @Package Android_ServeOld02  huitx.libztframework.utils
 * @date 2019-11-26   17:12
 * @Description: 文本编辑器
 */
public class TextViewSpanUtils {

    static final TextViewSpanUtils mTextViewSpanUtils = new TextViewSpanUtils();

    private TextView mTextView;

    public static TextViewSpanUtils getInstance() {
        return TextViewSpanUtils.mTextViewSpanUtils;
    }

    static SpannableString spanText;

    /**
     * init
     */
    public TextViewSpanUtils init(TextView view, String text) {
        this.mTextView = view;
        spanText = new SpannableString(text);
        return this;
    }

    public TextViewSpanUtils init(TextView view,String normalText, String text) {
        this.mTextView = view;
        this.mTextView.setText(normalText);
        spanText = new SpannableString(text);
        return this;
    }

    /**
     * 大小
     */
    public TextViewSpanUtils span_relativeSizeSpan(float percentage) {
        spanText.setSpan(new RelativeSizeSpan(percentage), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 字体样式 正常，斜体，粗体，斜体+粗体
     * public static final int NORMAL = 0;
     * public static final int BOLD = 1;
     * public static final int ITALIC = 2;
     * public static final int BOLD_ITALIC = 3;
     */
    public TextViewSpanUtils span_StyleSpan(int style) {
        //Typeface.BOLD_ITALIC:粗体+斜体
        spanText.setSpan(new StyleSpan(style), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return this;
    }

    /**
     * 变色
     */
    public TextViewSpanUtils span_foregroundColorSpan(int color) {
        spanText.setSpan(new ForegroundColorSpan(color), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 中划线
     */
    public TextViewSpanUtils span_strikethrough() {
        spanText.setSpan(new StrikethroughSpan(), 0, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * finish
     */
    public TextViewSpanUtils finish() {
        mTextView.append(spanText);
        return this;
    }

}
