package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.yeeyuntech.framework.R;

/**
 * Created by adu on 2017/3/28.
 */

public class UITextView extends AppCompatTextView {

    private Context context;

    int textColor = 0;
    int textColorPressed = 0;
    int textColorDisabled = 0;

    public UITextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
    }

    // init params
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UITextView);
        textColor = typedArray.getColor(R.styleable.UITextView_android_textColor, 0);
        textColorPressed = typedArray.getColor(R.styleable.UITextView_ui_text_color_pressed, textColor);
        textColorDisabled = typedArray.getColor(R.styleable.UITextView_ui_text_color_disabled, textColor);
        typedArray.recycle();

        update();
    }

    // update state and views
    private void update() {
        ColorStateList color = createColorStateList(textColor, textColorPressed, textColorDisabled);
        setTextColor(color);
    }

    // create ColorStateList
    private ColorStateList createColorStateList(int normal, int pressed, int disable) {
        int[] colors = new int[]{pressed, disable, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{-android.R.attr.state_enabled};
        states[2] = new int[]{};
        return new ColorStateList(states, colors);
    }
}
