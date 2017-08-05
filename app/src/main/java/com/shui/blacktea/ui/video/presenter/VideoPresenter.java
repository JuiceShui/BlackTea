package com.shui.blacktea.ui.video.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.VideoApi;
import com.shui.blacktea.data.API.YYApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.response.YYResponse;
import com.shui.blacktea.entity.VideoEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.video.contract.VideoContract;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        Disposable disposable = mRetrofitHelper.getVideo(YYApi.TYPE_VIDEO, "", mPage)
                .subscribeOn(Schedulers.io())
                .map(new Function<YYResponse<VideoEntity>, YYResponse<VideoEntity>>() {
                    @Override
                    public YYResponse<VideoEntity> apply(@NonNull YYResponse<VideoEntity> videoEntityYYResponse) throws Exception {
                        for (VideoEntity entity : videoEntityYYResponse.getShowapi_res_body().getPagebean().getContentlist()) {
                            entity.setVideoLength(Long.parseLong(getRingDuring(entity.getVideo_uri())));
                            if (entity.getProfile_image().contains("mini")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(entity.getProfile_image().substring(0, entity.getProfile_image().length() - 9)).append(".jpg");
                                entity.setProfile_image(sb.toString());
                            }
                        }
                        return videoEntityYYResponse;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<YYResponse<VideoEntity>>() {
                    @Override
                    public void accept(@NonNull YYResponse<VideoEntity> videoEntityYYResponse) throws Exception {
                        mView.showVideoList(videoEntityYYResponse.getShowapi_res_body().getPagebean().getContentlist(), isLoadMore);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                    }
                });
        addSubscribe(disposable);
    }

    private String getRingDuring(String mUri) {
        String duration = null;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();

        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        } catch (Exception ex) {
        } finally {
            mmr.release();
        }
        //Log.e("tag", "duration " + duration);
        return duration;
    }
}