package com.yeeyuntech.framework.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;

import com.yeeyuntech.framework.YYApplication;
import com.yeeyuntech.framework.ui.widget.AppBar;
import com.yeeyuntech.framework.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by adu on 2017/4/21.
 * 不使用Presenter的Activity直接继承该类
 */

public abstract class YYActivity extends SupportActivity implements IYYLoadView, ICreateInit, Handler.Callback {

    protected final String TAG = getClass().getSimpleName();

    private List<IYYPresenter> mPresenters = new ArrayList<>();

    // YYApplication实例
    private YYApplication mYYApp;
    // Handler实例
    protected Handler mHandler;
    // activity引用
    protected Activity mActivity;

    // loading
    private ILoadingIndicator mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mYYApp = YYApplication.getInstance();
        mYYApp.addActivity(this);
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

        // data binding, this method will call setContentView()
        initBinding();
        // view created
        onViewCreated();
        // init
        initParams();
        initViews();
        initListeners();
        getData();
    }

    protected void setAppBar(AppBar appBar) {
        appBar.setOnLeftClickListener(new AppBar.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                backAction();
            }
        });
    }

    /**
     * called after initInject()
     */
    protected void onViewCreated() {
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.attachView(this);
                presenter.onViewCreate();
            }
        }
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
        if (mLoadingDialog == null) {
            mLoadingDialog = getLoadingDialog();
        }
        if (mLoadingDialog == null) {
            throw new RuntimeException("the return value of getLoadingDialog() can not be null");
        }
        mLoadingDialog.showLoading();
    }

    @Override
    public void hideLoadingIndicator() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismissLoading();
        }
    }


    @Override
    public void safeRun(Runnable action) {
        mHandler.post(action);
    }

    /**
     * 返回上一页的响应动作
     */
    public void backAction() {
        super.onBackPressedSupport();
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

    /**
     * 重写返回方法
     */
    @Override
    public final void onBackPressedSupport() {
        backAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewPause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenters != null && mPresenters.size() > 0) {
            for (IYYPresenter presenter : mPresenters) {
                presenter.onViewDestroy();
            }
        }
        super.onDestroy();
        mActivity = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        mYYApp.removeActivity(this);
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
    public void showLoadingView() {
    }

    @Override
    public void showLoadErrorView() {
    }

    @Override
    public void showMainView() {
    }

    protected abstract ILoadingIndicator getLoadingDialog();

    protected abstract void initInject();

    protected abstract void initBinding();

    protected abstract IYYPresenter getPresenter();

    protected abstract List<IYYPresenter> getExtraPresenters();
}
