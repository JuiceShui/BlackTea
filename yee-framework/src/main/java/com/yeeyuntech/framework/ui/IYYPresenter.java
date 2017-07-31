package com.yeeyuntech.framework.ui;


/**
 * Created by adu on 2017/5/20.
 * MVP中的 Presenter 层接口
 * 适用于有网络处理的界面
 */

public interface IYYPresenter<T extends IYYLoadView> {

    /**
     * 绑定View
     * 设置View引用
     *
     * @param view
     */
    void attachView(T view);


    /**
     * 解除View
     * 释放View引用
     */
    void detachView();


    /**
     * presenter初始化需要执行的操作
     */
    void start();

    void onViewResume();

    void onViewPause();

    void onViewCreate();

    void onViewDestroy();
}
