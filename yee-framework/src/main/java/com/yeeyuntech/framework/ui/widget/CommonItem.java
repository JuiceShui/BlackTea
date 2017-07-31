package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yeeyuntech.framework.R;


/**
 * Description: 自定义常用的 item布局
 * Created by lsy on 2017/3/24.
 * <p>
 * Description: 代码规范
 * Modify by Juice_ on 2017/5/22 上午10:54
 */

public class CommonItem extends LinearLayout {

    private Context mContext;
    private String mLeftText;
    private String mLeftTip;
    private String mRightText;
    private String mMessageTip;
    private Drawable mLeftIcon;
    private Drawable mRightIcon;
    private int mLeftTextColor;
    private int mRightTextColor;
    private int mLeftTipTextColor;
    private boolean mHasBg = true;
    private TypedArray mTypedArray;
    private View mRootView;
    private ImageView mIvLeftIcon;
    private TextView mTvLeftText;
    private TextView mTvLeftTip;
    private TextView mTvRightText;
    private TextView mTvMessageTip;
    private ImageView mIvRightIcon;
    private int mLeftTextSize;
    private int mRightTextSize;

    public CommonItem(Context context) {
        this(context, null);
    }

    public CommonItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_common_item, this, true);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonItem);
        initAttrs();
        initView();
        assignmentView();
    }

    private void initAttrs() {
        mLeftText = mTypedArray.getString(R.styleable.CommonItem_common_item_left_text);
        mLeftTip = mTypedArray.getString(R.styleable.CommonItem_common_item_left_tip);
        mRightText = mTypedArray.getString(R.styleable.CommonItem_common_item_right_text);
        mMessageTip = mTypedArray.getString(R.styleable.CommonItem_common_item_message_tip);
        mLeftTextSize = mTypedArray.getInt(R.styleable.CommonItem_common_item_left_text_size, 15);
        mRightTextSize = mTypedArray.getInt(R.styleable.CommonItem_common_item_right_text_size, 15);
        mLeftIcon = mTypedArray.getDrawable(R.styleable.CommonItem_common_item_left_icon);
        mRightIcon = mTypedArray.getDrawable(R.styleable.CommonItem_common_item_right_icon);

        mLeftTextColor = mTypedArray.getColor(R.styleable.CommonItem_common_item_left_text_color,
                ContextCompat.getColor(mContext, R.color.text_strong));
        mRightTextColor = mTypedArray.getColor(R.styleable.CommonItem_common_item_right_text_color,
                ContextCompat.getColor(mContext, R.color.text_weak));
        mLeftTipTextColor = mTypedArray.getColor(R.styleable.CommonItem_common_item_left_tip_text_color,
                ContextCompat.getColor(mContext, R.color.text_weak));
        mHasBg = mTypedArray.getBoolean(R.styleable.CommonItem_common_item_has_bg, true);
        mTypedArray.recycle();
    }

    private void initView() {
        mIvLeftIcon = (ImageView) mRootView.findViewById(R.id.iv_left_icon);
        mTvLeftText = (TextView) mRootView.findViewById(R.id.tv_left_text);
        mTvLeftTip = (TextView) mRootView.findViewById(R.id.tv_left_tip);
        mTvRightText = (TextView) mRootView.findViewById(R.id.tv_right_text);
        mTvMessageTip = (TextView) mRootView.findViewById(R.id.tv_message_tip);
        mIvRightIcon = (ImageView) mRootView.findViewById(R.id.iv_right_icon);

    }

    private void assignmentView() {
        if (mHasBg) {
            mRootView.setBackgroundResource(R.drawable.ripple);
        }
        if (mLeftIcon != null) {
            mIvLeftIcon.setVisibility(VISIBLE);
            mIvLeftIcon.setImageDrawable(mLeftIcon);
        } else {
            mIvLeftIcon.setVisibility(GONE);
        }
        if (mRightIcon != null) {
            mIvRightIcon.setVisibility(VISIBLE);
            mIvRightIcon.setImageDrawable(mRightIcon);
        } else {
            mIvRightIcon.setVisibility(GONE);
        }
        if (TextUtils.isEmpty(mLeftText)) {
            mTvLeftText.setVisibility(GONE);
        } else {
            mTvLeftText.setVisibility(VISIBLE);
            mTvLeftText.setText(mLeftText);
        }
        if (TextUtils.isEmpty(mLeftTip)) {
            mTvLeftTip.setVisibility(GONE);
        } else {
            mTvLeftTip.setVisibility(VISIBLE);
            mTvLeftTip.setText(mLeftTip);
        }
        if (TextUtils.isEmpty(mRightText)) {
            mTvRightText.setVisibility(GONE);
        } else {
            mTvRightText.setVisibility(VISIBLE);
            mTvRightText.setText(mRightText);
        }
        if (TextUtils.isEmpty(mMessageTip)) {
            mTvMessageTip.setVisibility(GONE);
        } else {
            mTvMessageTip.setVisibility(VISIBLE);
            mTvMessageTip.setText(mMessageTip);
        }
        mTvLeftText.setTextColor(mLeftTextColor);
        mTvRightText.setTextColor(mRightTextColor);
        mTvLeftTip.setTextColor(mLeftTipTextColor);
        mTvLeftText.setTextSize(mLeftTextSize);
        mTvRightText.setTextSize(mRightTextSize);
    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }


    public void setmRightTextColor(@ColorRes int resId) {
        mTvRightText.setTextColor(ContextCompat.getColor(mContext, resId));
    }

    public CharSequence getmLeftText() {
        return mTvLeftText.getText();
    }

    public void setmLeftText(String s) {
        mTvLeftText.setVisibility(VISIBLE);
        mTvLeftText.setText(s);
    }

    public void setmMessageTip(String s) {
        mTvMessageTip.setVisibility(VISIBLE);
        mTvMessageTip.setText(s);
    }

    public void setMessageVisible(boolean b) {
        if (b) {
            mTvMessageTip.setVisibility(VISIBLE);
        } else {
            mTvMessageTip.setVisibility(GONE);
        }
    }

    public void setmRightText(String s) {

        mTvRightText.setVisibility(VISIBLE);
        mTvRightText.setText(s);
    }

    public CharSequence getmRightText() {
        return mTvRightText.getText();
    }

    public void setmLeftTip(String s) {
        mTvLeftTip.setVisibility(VISIBLE);
        mTvLeftTip.setText(s);
    }

    public void setmLeftIcon(int resid) {
        mIvLeftIcon.setVisibility(VISIBLE);
//        ivLeftIcon.setImageDrawable(ContextCompat.getDrawable(BaseApplication.getInstance(), resid));
        mIvLeftIcon.setImageResource(resid);
    }

    public void setmRightIcon(int resid) {
        if (resid == 0) {
            mIvRightIcon.setVisibility(GONE);
        }
        mIvRightIcon.setVisibility(VISIBLE);
//        ivRightIcon.setImageDrawable(ContextCompat.getDrawable(BaseApplication.getInstance(), resid));
        mIvRightIcon.setImageResource(resid);
    }
}
