package com.shui.blacktea.ui.news.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.TXApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.response.TXResponse;
import com.shui.blacktea.data.retrofit.DefaultErrorConsumer;
import com.shui.blacktea.entity.NewsEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.news.contract.NewsContract;
import com.yeeyuntech.framework.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JuiceShui on 2017/8/1.
 * To strive,to seek,to find,and not to give up
 */

public class NewsPresenter extends BaseRxPresenter<NewsContract.View>
        implements NewsContract.Presenter {
    private int mPage = 1;
    private String mCate = TXApi.SOCIAL;

    @Inject
    public NewsPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getNewsList(String cate, final boolean isLoadMore) {
        if (!mCate.equals(cate)) {
            mPage = 1;
            mCate = cate;
        }
        if (isLoadMore) {
            mPage += 1;
        } else {
            mPage = 1;
        }
        System.out.println("page------:" + mCate + "----" + mPage);
        Disposable disposable = mRetrofitHelper.getTXNews(mCate, TXApi.PAGE_SIZE, mPage)
                .compose(RxUtils.<TXResponse<List<NewsEntity>>>applySchedulers())
                .subscribe(new Consumer<TXResponse<List<NewsEntity>>>() {
                    @Override
                    public void accept(@NonNull TXResponse<List<NewsEntity>> listTXResponse) throws Exception {
                        mView.showNewsResult(listTXResponse.getNewslist(), isLoadMore);
                    }
                }, new DefaultErrorConsumer(mView));
        addSubscribe(disposable);
    }
}