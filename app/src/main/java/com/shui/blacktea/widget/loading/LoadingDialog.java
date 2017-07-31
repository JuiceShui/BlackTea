/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shui.blacktea.R;
import com.yeeyuntech.framework.ui.ILoadingIndicator;

/**
 * Created by adu on 2017/6/2.
 */

public class LoadingDialog extends Dialog implements ILoadingIndicator {

    private Context mContext;
    private TextView mTvTips;

    public LoadingDialog(Context context) {
        super(context,0);
        this.mContext = context;
        init();
    }

    private void init() {
        Window window = getWindow();
        if (window != null) {
            // 避免loading使软键盘收起
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
        View view = LayoutInflater.from(mContext).inflate(0, null);
//        mTvTips = (TextView) view.findViewById(R.id.loading_tips);
        //设置加载的view
        setContentView(view);
        //设置点击外面不消失
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String message) {
        if (TextUtils.isEmpty(message)) {
            mTvTips.setVisibility(View.GONE);
        } else {
            mTvTips.setText(message);
            mTvTips.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void showLoading() {
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public void dismissLoading() {
        if (isShowing()) {
            dismiss();
        }
    }
}
