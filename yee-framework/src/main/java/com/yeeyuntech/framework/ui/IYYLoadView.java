package com.yeeyuntech.framework.ui;

/**
 * Created by adu on 2017/5/20.
 * MVP中的 View 层接口
 * 适用于有网络处理的界面
 */

public interface IYYLoadView extends IYYView {

    /**
     * 展示加载中的界面
     */
    void showLoadingView();


    /**
     * 展示加载错误的界面
     */
    void showLoadErrorView();


    /**
     * 展示加载成功的界面
     */
    void showMainView();


    /**
     * 处理网络请求返回的异常
     *
     * @param throwable 抛出的异常
     */
    void handleTokenException(Throwable throwable);
}
