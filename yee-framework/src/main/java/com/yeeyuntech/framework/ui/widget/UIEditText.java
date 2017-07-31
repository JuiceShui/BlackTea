package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yeeyuntech.framework.R;

public class UIEditText extends AppCompatEditText {

    private Drawable clearIcon;

    public UIEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIEditText);
        clearIcon = a.getDrawable(R.styleable.UIEditText_ui_clear_icon);
        a.recycle();
        if (clearIcon != null) {
            clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (hasFocus() && text.length() > 0) {
            showClearIcon();
        } else {
            hideClearIcon();
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getText().length() > 0) {
            showClearIcon();
        } else {
            hideClearIcon();
        }
    }

    private void showClearIcon() {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], clearIcon, getCompoundDrawables()[3]);
    }

    private void hideClearIcon() {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        if (clearIcon != null && getText().length() > 0 && x > getWidth() - getPaddingRight() - clearIcon.getIntrinsicWidth()) {
            setText("");
            return true;
        }
        return super.onTouchEvent(event);
    }
}