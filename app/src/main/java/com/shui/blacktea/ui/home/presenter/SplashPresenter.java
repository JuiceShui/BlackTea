package com.shui.blacktea.ui.home.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.entity.SplashImgEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.home.contract.SplashContract;
import com.yeeyuntech.framework.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JuiceShui on 2017/8/9.
 * To strive,to seek,to find,and not to give up
 */

public class SplashPresenter extends BaseRxPresenter<SplashContract.View>
        implements SplashContract.Presenter {
    private int DELAY_TIME;

    @Inject
    public SplashPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getSplashImg() {
        Disposable disposable = mRetrofitHelper.getSplashImg()
                .compose(RxUtils.<SplashImgEntity>applySchedulers())
                .subscribe(new Consumer<SplashImgEntity>() {
                    @Override
                    public void accept(@NonNull SplashImgEntity splashImgEntity) throws Exception {
                        mView.showSplashImg(splashImgEntity);
                        doTimer(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doTimer(true);
                    }
                });
        addSubscribe(disposable);
    }

    private void doTimer(boolean isError) {
        if (isError) {
            DELAY_TIME = 1000;
        } else {
            DELAY_TIME = 2500;
        }
        Disposable disposable = Observable.timer(DELAY_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mView.jumpToMain();
                    }
                });
        addSubscribe(disposable);
    }
}