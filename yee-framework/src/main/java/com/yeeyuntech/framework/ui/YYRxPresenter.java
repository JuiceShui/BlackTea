package com.yeeyuntech.framework.ui;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public abstract class YYRxPresenter<T extends IYYLoadView> implements IYYPresenter<T> {

    protected T mView;

    private CompositeDisposable mCompositeDisposable;

    // 在请求数量，为0时隐藏loading
    private int mLoadingNum;

    public YYRxPresenter() {
    }

    /**
     * 需要显示loading时，使用此方法处理
     */
    protected <O> ObservableTransformer<O, O> bindLoading() {    //compose简化线程
        return new ObservableTransformer<O, O>() {
            @Override
            public Observable<O> apply(Observable<O> observable) {
                return observable.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        safeRun(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingNum++;
                                setLoading();
                            }
                        });
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        safeRun(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingNum--;
                                setLoading();
                            }
                        });
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        safeRun(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingNum--;
                                setLoading();
                            }
                        });
                    }
                }).doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        safeRun(new Runnable() {
                            @Override
                            public void run() {
                                mLoadingNum--;
                                setLoading();
                            }
                        });
                    }
                });
            }
        };
    }

    /**
     * 不需要显示loading标志时，使用此方法管理订阅
     *
     * @param subscription
     */
    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 暂不开放本方法
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 根据loading计数设置loading状态
     */
    private void setLoading() {
        if (mLoadingNum > 0) {
            mView.showLoadingIndicator();
        } else {
            mView.hideLoadingIndicator();
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        // 取消订阅时，需要设置loading，因此需要在mView置空之前
        unSubscribe();
        this.mView = null;
    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewPause() {

    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestroy() {

    }

    protected void safeRun(Runnable action) {
        if (mView == null) {
            return;
        }
        mView.safeRun(action);
    }
}