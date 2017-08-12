package com.shui.blacktea.ui.music.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.BaiduMusicApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.retrofit.DefaultErrorConsumer;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboard;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardListEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongPlayEntity;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.music.contract.OnlineMusicListContract;
import com.shui.blacktea.utils.FileUtils;
import com.yeeyuntech.framework.utils.RxUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by JuiceShui on 2017/8/11.
 * To strive,to seek,to find,and not to give up
 */

public class OnlineMusicListPresenter extends BaseRxPresenter<OnlineMusicListContract.View>
        implements OnlineMusicListContract.Presenter {
    private int mOffset = 0;

    @Inject
    public OnlineMusicListPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getData(int cate, final boolean isLoadMore) {
        if (isLoadMore) {
            mOffset += BaiduMusicApi.DEFAULT_LIST_SIZE_15;
        } else {
            mOffset = 0;
        }
        Disposable disposable = mRetrofitHelper.getBillboard(cate, BaiduMusicApi.DEFAULT_LIST_SIZE_15, mOffset)
                .compose(RxUtils.<BaiduSongBillboardListEntity>applySchedulers())
                .subscribe(new Consumer<BaiduSongBillboardListEntity>() {
                    @Override
                    public void accept(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        List<BaiduSongBillboardEntity> list = baiduSongBillboardListEntity.getSong_list();
                        BaiduSongBillboard billboard = baiduSongBillboardListEntity.getBillboard();
                        mView.showData(list, billboard, isLoadMore);
                    }
                }, new DefaultErrorConsumer(mView));
        addSubscribe(disposable);
    }

    @Override
    public void getMusic(final String songId) {
        Disposable disposable = mRetrofitHelper.playSong(songId)
                .compose(RxUtils.<BaiduSongPlayEntity>applySchedulers())
                .subscribe(new Consumer<BaiduSongPlayEntity>() {
                    @Override
                    public void accept(@NonNull BaiduSongPlayEntity baiduSongPlayEntity) throws Exception {
                        BaiduSongPlayEntity.SonginfoBean song = baiduSongPlayEntity.getSonginfo();
                        BaiduSongPlayEntity.BitrateBean bitrate = baiduSongPlayEntity.getBitrate();
                        MusicEntity musicEntity = new MusicEntity();
                        musicEntity.setType(MusicEntity.TYPE_ONLINE);
                        musicEntity.setTitle(song.getTitle());
                        musicEntity.setArtist(song.getAuthor());
                        musicEntity.setCoverPath(song.getPic_big());
                        musicEntity.setAlbum(song.getAlbum_title());
                        musicEntity.setDuration(bitrate.getFile_duration() * 1000);
                        musicEntity.setPath(bitrate.getFile_link());
                        mView.playOnlineMusic(musicEntity);
                    }
                }, new DefaultErrorConsumer(mView));
        addSubscribe(disposable);
    }

    @Override
    public void downloadSong(String songId) {
        final String[] name = new String[1];
        Disposable disposable = mRetrofitHelper.playSong(songId)
                .subscribeOn(Schedulers.io())
                .map(new Function<BaiduSongPlayEntity, String>() {
                    @Override
                    public String apply(@NonNull BaiduSongPlayEntity baiduSongPlayEntity) throws Exception {
                        name[0] = baiduSongPlayEntity.getSonginfo().getTitle();
                        name[1] = baiduSongPlayEntity.getSonginfo().getAuthor();
                        return baiduSongPlayEntity.getBitrate().getFile_link();
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<String, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(@NonNull String s) throws Exception {
                        return mRetrofitHelper.download(s);
                    }
                }).subscribeOn(Schedulers.io()).map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(@NonNull ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                }).subscribeOn(Schedulers.io()).doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(@NonNull InputStream inputStream) throws Exception {
                        File file = new File(FileUtils.getMusicDir(), FileUtils.getFileName(name[1], name[0]));
                        FileUtils.writeFile(inputStream, file);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<InputStream>() {
                    @Override
                    public void accept(@NonNull InputStream inputStream) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                    }
                });
    }
}