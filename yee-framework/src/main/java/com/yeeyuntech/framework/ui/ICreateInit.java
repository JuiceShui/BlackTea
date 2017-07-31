package com.yeeyuntech.framework.ui;

import android.support.annotation.LayoutRes;


/**
 * Created by adu on 2017/5/20.
 * 界面初始化接口，activity和fragment实现此接口
 */
public interface ICreateInit {

    /**
     * 获得布局xml
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化界面跳转参数
     */
    void initParams();

    /**
     * 视图按需初始化
     */
    void initViews();

    /**
     * 初始化绑定事件
     */
    void initListeners();

    /**
     * 获取界面数据
     */
    void getData();
}