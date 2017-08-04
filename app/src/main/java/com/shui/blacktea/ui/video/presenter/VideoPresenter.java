package com.shui.blacktea.ui.video.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.VideoApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.retrofit.DefaultErrorConsumer;
import com.shui.blacktea.entity.VideoChoiceEntity;
import com.shui.blacktea.entity.VideoEntertainmentEntity;
import com.shui.blacktea.entity.VideoEntity;
import com.shui.blacktea.entity.VideoFunEntity;
import com.shui.blacktea.entity.VideoHotEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.video.contract.VideoContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JuiceShui on 2017/8/4.
 * To strive,to seek,to find,and not to give up
 */

public class VideoPresenter extends BaseRxPresenter<VideoContract.View>
        implements VideoContract.Presenter {
    private int mPage = 1;
    private String mCate = VideoApi.VIDEO_HOT_ID;

    @Inject
    public VideoPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getVideoList(String cate, final boolean isLoadMore) {
        if (!mCate.equals(cate)) {
            mPage = 1;
            mCate = cate;
        }
        if (isLoadMore) {
            mPage += 1;
        } else {
            mPage = 1;
        }
        Observable observable = mRetrofitHelper.getHotVideoList(mPage);
        switch (mCate) {
            case VideoApi.VIDEO_HOT_ID:
                observable = mRetrofitHelper.getHotVideoList(mPage);
                break;
            case VideoApi.VIDEO_ENTERTAINMENT_ID:
                observable = mRetrofitHelper.getEntertainmentVideoList(mPage);
                break;
            case VideoApi.VIDEO_FUN_ID:
                observable = mRetrofitHelper.getFunVideoList(mPage);
                break;
            case VideoApi.VIDEO_CHOICE_ID:
                observable = mRetrofitHelper.getChoiceVideoList(mPage);
                break;
        }
        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        List<VideoEntity> list = new ArrayList<VideoEntity>();
                        if (o instanceof VideoHotEntity) {
                            list = ((VideoHotEntity) o).getV9LG4B3A0();
                        } else if (o instanceof VideoEntertainmentEntity) {
                            list = ((VideoEntertainmentEntity) o).getV9LG4CHOR();
                        } else if (o instanceof VideoFunEntity) {
                            list = ((VideoFunEntity) o).getV9LG4E6VR();
                        } else {
                            list = ((VideoChoiceEntity) o).getV9LG4B3A0();
                        }
                        mView.showVideoList(list, isLoadMore);
                    }
                }, new DefaultErrorConsumer(mView));
    }
}