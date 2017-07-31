package com.yeeyuntech.framework.ui.widget.databinding;

import android.text.TextWatcher;
import android.view.View;


/**
 * 自定义双向数据绑定接口
 * <p>
 * Created by lsy on 2017/5/8.
 */

public interface BindingText {

    View getBindingView();

    void setBindingText(String text);

    String getBindingText();

    void addTextWatch(TextWatcher textWatcher);

    void delTextWatch(TextWatcher textWatcher);
}
