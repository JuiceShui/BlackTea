package com.shui.blacktea.ui.news.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.YYApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.response.YYResponse;
import com.shui.blacktea.data.retrofit.DefaultErrorConsumer;
import com.shui.blacktea.entity.WeiBoEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.news.contract.NewsContract;
import com.yeeyuntech.framework.utils.RxUtils;

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
    private int mType = YYApi.TYPE_WEIBO_ENTERTAINMENT;
    private String mSpace = YYApi.TYPE_WEIBO_SPACE_DAY;

    @Inject
    public NewsPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getNewsList(int type, final boolean isLoadMore) {
        if (!(mType == type)) {
            mPage = 1;
            mType = type;
        }
        if (isLoadMore) {
            mPage += 1;
        } else {
            mPage = 1;
        }
        Disposable disposable = mRetrofitHelper.getWeiBoNew(mType, mSpace, mPage)
                .compose(RxUtils.<YYResponse<WeiBoEntity>>applySchedulers())
                .subscribe(new Consumer<YYResponse<WeiBoEntity>>() {
                    @Override
                    public void accept(@NonNull YYResponse<WeiBoEntity> weiBoEntityYYResponse) throws Exception {
                        mView.showNewsResult(weiBoEntityYYResponse.getShowapi_res_body().getPagebean().getContentlist(), isLoadMore);
                    }
                }, new DefaultErrorConsumer(mView));
        addSubscribe(disposable);
    }
}