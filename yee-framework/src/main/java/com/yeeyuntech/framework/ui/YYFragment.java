package com.yeeyuntech.framework.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeeyuntech.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by adu on 2017/5/2.
 */

public abstract class YYFragment extends SupportFragment implements IYYLoadView, ICreateInit, Handler.Callback {


    protected final String TAG = getClass().getSimpleName();

    private List<IYYPresenter> mPresenters = new ArrayList<>();

    // Handler实例
    protected Handler mHandler;
    // 保存的activity引用
    protected Activity mActivity;
    private YYActivity mYYVMActivity;
    protected boolean isInit = false;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mYYVMActivity = (YYActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inject
        initInject();
        mHandler = new Handler(this);

        IYYPresenter presenter = getPresenter();
        if (presenter != null) {
            mPresenters.add(presenter);
        }
        List<IYYPresenter> presenters = getExtraPresenters();
        if (presenters != null && presenters.size() > 0) {
            mPresenters.addAll(presenters);
        }

        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter p : mPresenters) {
                p.attachView(this);
                p.onViewCreate();
            }
        }
        // data binding
        View view = initBinding();
        initParams();
        initViews();
        initListeners();
        isInit = true;
        return view;
    }


    /**
     * 懒加载初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getData();
    }


    @Override
    public void initParams() {
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void getData() {
    }


    @Override
    public void showLoadingIndicator() {
        mYYVMActivity.showLoadingIndicator();
    }

    @Override
    public void hideLoadingIndicator() {
        mYYVMActivity.hideLoadingIndicator();
    }


    /**
     * 处理消息
     *
     * @param msg
     * @return 是否处理该消息
     */
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    @Override
    public void showToast(@StringRes int resId) {
        ToastUtils.showToast(resId);
    }

    @Override
    public void showToast(String toast) {
        ToastUtils.showToast(toast);
    }

    @Override
    public void safeRun(Runnable action) {
        mHandler.post(action);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewPause();
            }
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewDestroy();
            }
        }
        super.onDestroyView();
    }

    @Override
    public void showLoadingView() {
    }

    @Override
    public void showLoadErrorView() {
    }

    @Override
    public void showMainView() {
    }

    protected abstract void initInject();

    protected abstract View initBinding();

    protected abstract IYYPresenter getPresenter();

    protected abstract List<IYYPresenter> getExtraPresenters();
}
