package com.yeeyuntech.framework.ui;

import android.support.annotation.StringRes;

/**
 * Created by adu on 2017/5/20.
 * MVP中的 View 层接口
 */

public interface IYYView {

    /**
     * 显示loading
     */
    void showLoadingIndicator();


    /**
     * 隐藏loading
     */
    void hideLoadingIndicator();


    /**
     * 显示一个toast
     *
     * @param resId
     */
    void showToast(@StringRes int resId);


    /**
     * 显示一个toast
     *
     * @param toast
     */
    void showToast(String toast);

    /**
     * 在主线程执行
     *
     * @param action
     */
    void safeRun(Runnable action);
}
