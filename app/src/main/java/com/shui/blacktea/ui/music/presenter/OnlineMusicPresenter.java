package com.shui.blacktea.ui.music.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.API.BaiduMusicApi;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.data.retrofit.DefaultErrorConsumer;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardListEntity;
import com.shui.blacktea.entity.BaiduSong.SongBillboardEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.music.contract.OnlineMusicContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by JuiceShui on 2017/8/11.
 * To strive,to seek,to find,and not to give up
 */

/**
 * #主打榜单
 * 1.新歌榜
 * 2.热歌榜
 * #分类榜单
 * 20.华语金曲榜
 * 21.欧美金曲榜
 * 24.影视金曲榜
 * 23.情歌对唱榜
 * 25.网络歌曲榜
 * 22.经典老歌榜
 * 11.摇滚榜
 * #媒体榜单
 * 6.KTV热歌榜
 * 8.Billboard
 * 18.Hito中文榜
 * 7.叱咤歌曲榜
 */
public class OnlineMusicPresenter extends BaseRxPresenter<OnlineMusicContract.View>
        implements OnlineMusicContract.Presenter {
    private List<SongBillboardEntity> mData = new ArrayList<>();

    @Inject
    public OnlineMusicPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getData() {
        SongBillboardEntity mainBillBoard = new SongBillboardEntity();
        mainBillBoard.setType(SongBillboardEntity.TYPE_TITLE);
        mainBillBoard.setCover("主打榜单");
        mData.add(mainBillBoard);
        Disposable disposable = mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_NEW, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_NEW);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_HOT, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }//新歌
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_HOT);
                        mData.add(entity);
                        SongBillboardEntity cateBillBoard = new SongBillboardEntity();
                        cateBillBoard.setType(SongBillboardEntity.TYPE_TITLE);
                        cateBillBoard.setCover("分类榜单");
                        mData.add(cateBillBoard);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_CHINESE, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }//热歌
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_CHINESE);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_EUR, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_EUR);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_TV, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_TV);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_LOVER, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_LOVER);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_NET, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_NET);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_CLASSICAL, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_CLASSICAL);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_ROCK, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io())
                .flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_ROCK);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_POP, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_POP);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_JAZZ, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);

                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_JAZZ);
                        mData.add(entity);
                        SongBillboardEntity medaiBillBoard = new SongBillboardEntity();
                        medaiBillBoard.setType(SongBillboardEntity.TYPE_TITLE);
                        medaiBillBoard.setCover("媒体榜单");
                        mData.add(medaiBillBoard);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_KTV, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).flatMap(new Function<BaiduSongBillboardListEntity, ObservableSource<BaiduSongBillboardListEntity>>() {
                    @Override
                    public ObservableSource<BaiduSongBillboardListEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_KTV);
                        mData.add(entity);
                        return mRetrofitHelper.getBillboard(BaiduMusicApi.TYPE_BILLBOARD, BaiduMusicApi.DEFAULT_LIST_SIZE, BaiduMusicApi.DEFAULT_OFFSET);
                    }
                }).subscribeOn(Schedulers.io()).map(new Function<BaiduSongBillboardListEntity, List<SongBillboardEntity>>() {
                    @Override
                    public List<SongBillboardEntity> apply(@NonNull BaiduSongBillboardListEntity baiduSongBillboardListEntity) throws Exception {
                        SongBillboardEntity entity = getEntity(baiduSongBillboardListEntity);
                        entity.setCate(BaiduMusicApi.TYPE_BILLBOARD);
                        mData.add(entity);
                        return mData;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SongBillboardEntity>>() {
                    @Override
                    public void accept(@NonNull List<SongBillboardEntity> songBillboardEntities) throws Exception {
                        mView.showData(songBillboardEntities);
                    }
                }, new DefaultErrorConsumer(mView));
        addSubscribe(disposable);
    }

    private SongBillboardEntity getEntity(BaiduSongBillboardListEntity content) {
        SongBillboardEntity entity = new SongBillboardEntity();
        entity.setType(SongBillboardEntity.TYPE_BILLBOARD);
        entity.setCover(content.getBillboard().getPic_s210());
        if (content.getSong_list().size() == 1) {
            entity.setTopOne(content.getSong_list().get(0).getAlbum_title());
        } else if (content.getSong_list().size() == 2) {
            entity.setTopOne(content.getSong_list().get(0).getAlbum_title());
            entity.setTopTwo(content.getSong_list().get(1).getAlbum_title());
        } else if (content.getSong_list().size() == 3) {
            entity.setTopOne(content.getSong_list().get(0).getAlbum_title());
            entity.setTopTwo(content.getSong_list().get(1).getAlbum_title());
            entity.setTopThree(content.getSong_list().get(2).getAlbum_title());
        }
        return entity;
    }
}