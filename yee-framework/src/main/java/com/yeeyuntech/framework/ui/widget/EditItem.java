package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yeeyuntech.framework.R;
import com.yeeyuntech.framework.ui.widget.databinding.BindingText;


/**
 * Description: 自定义的编辑框
 * Created by adu on 17-3-27.
 * <p>
 * Description: 代码规范
 * Modify by Juice_ on 2017/5/22 上午10:58
 */

public class EditItem extends LinearLayout implements BindingText {

    private String mOldStr = "";
    private Context mContext;
    private TextView mTvLabel;
    private EditText mEtEditText;

    public EditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initViews();
        initAttr(attrs);
    }

    private void initViews() {
        inflate(mContext, R.layout.widget_edit_item, this);
        mTvLabel = (TextView) findViewById(R.id.tv_edit_item_label);
        mEtEditText = (EditText) findViewById(R.id.et_edit_item_edit_text);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EditItem);
        String label = typedArray.getString(R.styleable.EditItem_edit_item_label);
        String hint = typedArray.getString(R.styleable.EditItem_edit_item_hint);
        String text = typedArray.getString(R.styleable.EditItem_edit_item_text);
        int minLabelWidth = typedArray.getDimensionPixelOffset(R.styleable.EditItem_edit_item_label_min_width, 0);
        int labelGravity = typedArray.getInt(R.styleable.EditItem_edit_item_label_gravity, 0);
        int inputType = typedArray.getInt(R.styleable.EditItem_android_inputType, EditorInfo.TYPE_NULL);
        boolean singleLine = typedArray.getBoolean(R.styleable.EditItem_edit_item_single_line, true);
        int imeOptions = typedArray.getInt(R.styleable.EditItem_android_imeOptions, EditorInfo.IME_NULL);
        typedArray.recycle();
        if (!TextUtils.isEmpty(label)) {
            mTvLabel.setText(label);
        }
        if (!TextUtils.isEmpty(hint)) {
            mEtEditText.setHint(hint);
        }
        if (!TextUtils.isEmpty(text)) {
            mEtEditText.setText(text);
        }
        if (minLabelWidth != 0) {
            mTvLabel.setMinWidth(minLabelWidth);
        }
        LayoutParams params = (LayoutParams) mTvLabel.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        switch (labelGravity) {
            case 0:
                params.gravity = Gravity.TOP;
                break;
            case 1:
                params.gravity = Gravity.CENTER;
                break;
            case 2:
                params.gravity = Gravity.BOTTOM;
                break;
        }
        mTvLabel.setLayoutParams(params);
        // singleLine 应该在 inputType之前设置,否则会导致InputType的password特性失效
        mEtEditText.setSingleLine(singleLine);
        if (inputType != EditorInfo.TYPE_NULL) {
            mEtEditText.setInputType(inputType);
        }
        if (imeOptions != EditorInfo.IME_NULL) {
            mEtEditText.setImeOptions(imeOptions);
        }
    }

    public EditText getEditText() {
        return mEtEditText;
    }

    public void setLabel(@StringRes int resId) {
        mTvLabel.setText(resId);
    }

    public void setLabel(CharSequence text) {
        mTvLabel.setText(text);
    }

    public void setText(@StringRes int resId) {
        mEtEditText.setText(resId);
    }

    @Override
    public View getBindingView() {
        return this;
    }

    @Override
    public void setBindingText(String text) {
        if (text == null || text.equals(mEtEditText.getText().toString())) {
            return;
        }
        mEtEditText.setText(text);
    }

    @Override
    public String getBindingText() {
        return mEtEditText.getText().toString();
    }

    @Override
    public void addTextWatch(TextWatcher textWatcher) {
        mEtEditText.addTextChangedListener(textWatcher);
    }

    @Override
    public void delTextWatch(TextWatcher textWatcher) {
        mEtEditText.removeTextChangedListener(textWatcher);
    }


}
