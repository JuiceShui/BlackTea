package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.yeeyuntech.framework.R;

/**
 * Created by adu on 17-3-18.
 */
public class UIButton extends AppCompatButton {

    private Context context;

    // background
    private int borderWidth = 0;
    private int borderRadius = 0;
    private int borderColor = 0;
    private int backgroundColor = 0;
    private int backgroundColorPressed = 0;
    private int backgroundColorDisabled = 0;

    // text
    private int textColor = 0;
    private int textColorPressed = 0;
    private int textColorDisabled = 0;

    public UIButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    // init params
    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIView);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.UIView_ui_border_width, 0);
        borderRadius = typedArray.getDimensionPixelSize(R.styleable.UIView_ui_border_radius, 0);
        borderColor = typedArray.getColor(R.styleable.UIView_ui_border_color, 0);
        backgroundColor = typedArray.getColor(R.styleable.UIView_ui_background_color, 0);
        backgroundColorPressed = typedArray.getColor(R.styleable.UIView_ui_background_color_pressed, backgroundColor);
        backgroundColorDisabled = typedArray.getColor(R.styleable.UIView_ui_background_color_disabled, backgroundColor);
        typedArray.recycle();

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIButton);
        textColor = typedArray.getColor(R.styleable.UIButton_android_textColor, 0);
        textColorPressed = typedArray.getColor(R.styleable.UIView_ui_text_color_pressed, textColor);
        textColorDisabled = typedArray.getColor(R.styleable.UIView_ui_text_color_disabled, textColor);
        typedArray.recycle();

        update();
    }

    // update state and views
    private void update() {
        StateListDrawable backgroundDrawable = new StateListDrawable();
        backgroundDrawable.addState(new int[]{-android.R.attr.state_enabled},
                createGradientDrawable(backgroundColorDisabled, borderRadius, borderWidth, borderColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // create rippleDrawable
            RippleDrawable rippleDrawable = new RippleDrawable(
                    createColorStateList(backgroundColorPressed, backgroundColorPressed, backgroundColorDisabled),
                    createGradientDrawable(backgroundColor, borderRadius, borderWidth, borderColor),
                    null);
            backgroundDrawable.addState(new int[]{}, rippleDrawable);
        } else {
            backgroundDrawable.addState(new int[]{android.R.attr.state_pressed},
                    createGradientDrawable(backgroundColorPressed, borderRadius, borderWidth, borderColor));
            backgroundDrawable.addState(new int[]{},
                    createGradientDrawable(backgroundColor, borderRadius, borderWidth, borderColor));
        }
        setBackground(backgroundDrawable);

        ColorStateList colorStateList = createColorStateList(textColor, textColorPressed, textColorDisabled);
        setTextColor(colorStateList);
    }

    // create ColorStateList use colors
    private ColorStateList createColorStateList(int normal, int pressed, int disable) {
        int[] colors = new int[]{disable, pressed, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{-android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_pressed};
        states[2] = new int[]{};
        return new ColorStateList(states, colors);
    }

    // create GradientDrawable
    private GradientDrawable createGradientDrawable(int color, float cornerRadius, int borderWidth, int borderColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(cornerRadius);
        drawable.setStroke(borderWidth, borderColor);
        drawable.setColor(color);
        return drawable;
    }
}