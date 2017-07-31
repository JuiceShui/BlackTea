package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yeeyuntech.framework.R;

import java.lang.reflect.Field;


/**
 * Description: 上方工具栏类
 * Created by adu on 17-3-20.
 * <p>
 * Description: 代码规范
 * Modify by Juice_ on 2017/5/22 上午10:45
 */

public class AppBar extends FrameLayout implements View.OnClickListener {

    // app bar 是否包含 status bar
    private boolean mStatusBarEnable = true;
    private View mRootView;
    private GradientDrawable mShadowDrawable;
    private TextView mTvTitle;
    private TextView mTvLeftText;
    private TextView mTvRightText;
    private AppCompatImageButton mIvLeftIcon;
    private AppCompatImageButton mIvRightIcon;
    private View mCustomView;
    private TypedArray mTypedArray;
    private Context mContext;
    private OnLeftClickListener mLeftClickListener;
    private OnRightClickListener mRightClickListener;
    private int mDefaultColor = Color.WHITE;

    public interface OnLeftClickListener {
        void onLeftClick();
    }

    public interface OnRightClickListener {
        void onRightClick();
    }

    public void setOnLeftClickListener(OnLeftClickListener listener) {
        this.mLeftClickListener = listener;
    }

    public void setOnRightClickListener(OnRightClickListener listener) {
        this.mRightClickListener = listener;
    }

    public AppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_app_bar, null);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AppBar);
        this.mContext = context;
        initAttrs();
        initEvents();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvLeftText = (TextView) findViewById(R.id.tv_left_text);
        mTvRightText = (TextView) findViewById(R.id.tv_right_text);
        mIvLeftIcon = (AppCompatImageButton) findViewById(R.id.iv_left_icon);
        mIvRightIcon = (AppCompatImageButton) findViewById(R.id.iv_right_icon);
    }

    private void initAttrs() {
        String leftText = null,
                rightText = null,
                title = null;
        Drawable background = null,
                leftIcon = null,
                rightIcon = null;
        int leftTextColor = 0,
                titleColor = 0,
                rightTextColor = 0;
        int customViewResId = 0;
        for (int i = 0, n = mTypedArray.getIndexCount(); i < n; i++) {
            int attr = mTypedArray.getIndex(i);
            if (attr == R.styleable.AppBar_app_bar_left_icon) {
                leftIcon = mTypedArray.getDrawable(attr);

            } else if (attr == R.styleable.AppBar_app_bar_right_icon) {
                rightIcon = mTypedArray.getDrawable(attr);

            } else if (attr == R.styleable.AppBar_app_bar_left_text) {
                leftText = mTypedArray.getString(attr);

            } else if (attr == R.styleable.AppBar_app_bar_right_text) {
                rightText = mTypedArray.getString(attr);

            } else if (attr == R.styleable.AppBar_app_bar_title) {
                title = mTypedArray.getString(attr);

            } else if (attr == R.styleable.AppBar_app_bar_left_text_color) {
                leftTextColor = mTypedArray.getColor(attr, mDefaultColor);

            } else if (attr == R.styleable.AppBar_app_bar_right_text_color) {
                rightTextColor = mTypedArray.getColor(attr, mDefaultColor);

            } else if (attr == R.styleable.AppBar_app_bar_title_text_color) {
                titleColor = mTypedArray.getColor(attr, mDefaultColor);

            } else if (attr == R.styleable.AppBar_app_bar_background) {
                background = mTypedArray.getDrawable(attr);

            } else if (attr == R.styleable.AppBar_app_bar_status_bar_enable) {
                mStatusBarEnable = mTypedArray.getBoolean(attr, true);

            } else if (attr == R.styleable.AppBar_app_bar_custom_view) {
                customViewResId = mTypedArray.getResourceId(attr, 0);

            }
        }
        mTypedArray.recycle();

        int statusBarHeight = 0;
        if (mStatusBarEnable) {
            statusBarHeight = getStatusBarHeight();
            mRootView.setPadding(
                    mRootView.getPaddingLeft(),
                    mRootView.getPaddingTop() + statusBarHeight,
                    mRootView.getPaddingRight(),
                    mRootView.getPaddingBottom());
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.app_bar_height) + statusBarHeight);
        addView(mRootView, layoutParams);

        initView();

        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(leftText)) {
            mTvLeftText.setText(leftText);
            mTvLeftText.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mTvRightText.setText(rightText);
            mTvRightText.setVisibility(VISIBLE);
        }
        if (leftIcon != null) {
            mIvLeftIcon.setImageDrawable(leftIcon);
            mIvLeftIcon.setVisibility(VISIBLE);
        }
        if (rightIcon != null) {
            mIvRightIcon.setImageDrawable(rightIcon);
            mIvRightIcon.setVisibility(VISIBLE);
        }
        if (background != null) {
            setBackground(background);
        }

        mTvLeftText.setTextColor(leftTextColor);
        mTvRightText.setTextColor(rightTextColor);
        mTvTitle.setTextColor(titleColor);

        if (customViewResId != 0) {
            mCustomView = inflate(mContext, customViewResId, null);
            removeView(mTvTitle);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(mCustomView, params);
        }
//        setFloat(true);
    }


    private void initEvents() {
        mTvLeftText.setOnClickListener(this);
        mTvRightText.setOnClickListener(this);
        mIvLeftIcon.setOnClickListener(this);
        mIvRightIcon.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_left_text || i == R.id.iv_left_icon) {
            if (mLeftClickListener != null) {
                mLeftClickListener.onLeftClick();
            }

        } else if (i == R.id.tv_right_text || i == R.id.iv_right_icon) {
            if (mRightClickListener != null) {
                mRightClickListener.onRightClick();
            }

        }
    }

//    public void setFloat(boolean enabled) {
//        if (enabled) {
//            if (mShadowDrawable == null) {
//                mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
//                        new int[]{Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")});
//            }
//            InsetDrawable insetDrawable = new InsetDrawable(mShadowDrawable, 0, getHeight(), 0, 0);
//            int mShadowHeight = (int) (8 * getResources().getDisplayMetrics().density);
//            insetDrawable.setBounds(getLeft(),
//                    getTop(),
//                    getRight(),
//                    getBottom() + mShadowHeight);
//            setBackgroundResource(R.drawable.bg_avatar_stroke);
//        } else {
//            setBackground(null);
//        }
//    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        Paint paint = new Paint();  //定义一个Paint
//        Shader mShader = new LinearGradient(getWidth() / 2, getHeight() - 16, getWidth() / 2, getHeight(), new int[]{Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")}, null, Shader.TileMode.REPEAT);
////新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
//
//        paint.setShader(mShader);
//        paint.setStrokeWidth(getWidth());
//        canvas.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, getHeight()+16, paint);
//    }

    public void setLeftIcon(Drawable icon) {
        mIvLeftIcon.setImageDrawable(icon);
    }

    public void setTitle(@StringRes int resId) {
        mTvTitle.setText(resId);
        mTvTitle.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
        mTvTitle.setVisibility(VISIBLE);
    }

    public void setCustomView(View view) {
        mCustomView = view;
        removeView(mTvTitle);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(mCustomView, params);
    }

    public void setRightText(String s) {
        mTvRightText.setText(s);
        mTvRightText.setVisibility(VISIBLE);
    }

    public void setRightTextDrawable(int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        mTvRightText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setRightTextSize(int size) {
        mTvRightText.setTextSize(size);
    }

    public void setText(String s) {

    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            //反射出该对象中status_bar_height字段所对应的在R文件的id值
            //该id值由系统工具自动生成,文档描述如下:
            //The desired resource identifier, as generated by the aapt tool.
            int id = Integer.parseInt(field.get(object).toString());
            //依据id值获取到状态栏的高度,单位为像素
            statusBarHeight = getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
