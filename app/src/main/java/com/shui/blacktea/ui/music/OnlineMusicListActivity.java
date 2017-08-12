package com.shui.blacktea.ui.music;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shui.blacktea.R;
import com.shui.blacktea.config.AppCache;
import com.shui.blacktea.data.API.BaiduMusicApi;
import com.shui.blacktea.databinding.ActivityOnlineMusicListBinding;
import com.shui.blacktea.databinding.DialogMusicInfoBinding;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboard;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardEntity;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.ui.music.contract.OnlineMusicListContract;
import com.shui.blacktea.ui.music.presenter.OnlineMusicListPresenter;
import com.shui.blacktea.utils.ImageUtils;
import com.yeeyuntech.framework.ui.IYYPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/11.
 */

public class OnlineMusicListActivity extends BaseActivity implements OnlineMusicListContract.View, BaseQuickAdapter.OnItemClickListener, OnlineMusicListAdapter.onMoreClickListener, View.OnClickListener {
    @Inject
    ActivityOnlineMusicListBinding mBinding;
    @Inject
    OnlineMusicListPresenter mPresenter;
    private OnlineMusicListAdapter mAdapter;
    private List<BaiduSongBillboardEntity> mData = new ArrayList<>();
    private int mCate;
    private Dialog mMusicInfoDialog;
    DialogMusicInfoBinding mDialogMusicInfoBinding;
    private BaiduSongBillboardEntity mCurrentMusic = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_online_music_list;
    }

    @Override
    public void initParams() {
        mCate = getIntent().getIntExtra("cate", 1);
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {
        mDialogMusicInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.dialog_music_info, null, false);
        mDialogMusicInfoBinding.setClick(this);
        mAdapter = new OnlineMusicListAdapter(R.layout.item_music_local_list, mData);
        mBinding.recyclerCate.setAdapter(mAdapter);
        mBinding.recyclerCate.setLayoutManager(new LinearLayoutManager(mActivity));
        setToolBar(mBinding.toolbar, "");
        mBinding.srfRecycler.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getData(mCate, true);
            }
        });
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnMoreClickListener(this);
    }

    @Override
    public void getData() {
        mPresenter.getData(mCate, false);
    }

    @Override
    public void showData(List<BaiduSongBillboardEntity> songList, BaiduSongBillboard billboardInfo, boolean isLoadMore) {
        Glide.with(mActivity).load(billboardInfo.getPic_s210()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBinding.ivCover.setImageBitmap(resource);
                mBinding.ivHeaderBg.setImageBitmap(ImageUtils.blur(resource));
            }
        });
        setToolBar(mBinding.toolbar, billboardInfo.getName());
        mBinding.tvTitle.setText(billboardInfo.getName());
        mBinding.tvUpdateDate.setText(billboardInfo.getUpdate_date());
        mBinding.tvComment.setText(billboardInfo.getComment());
        if (songList.size() < BaiduMusicApi.DEFAULT_LIST_SIZE_15) {
            showToast("数据全部加载完毕");
            mBinding.srfRecycler.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
        }
        if (mBinding.srfRecycler.isLoading()) {
            mBinding.srfRecycler.finishLoadmore();
        }
        if (songList != null) {
            if (isLoadMore) {
                mData.addAll(songList);
            } else {
                mData.clear();
                mData.addAll(songList);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void playOnlineMusic(MusicEntity musicEntity) {
        AppCache.getInstance().getPlayService().play(musicEntity);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mPresenter.getMusic(mData.get(position).getSong_id());
    }

    @Override
    public void onMoreClick(int position) {
        mCurrentMusic = mData.get(position);
        mDialogMusicInfoBinding.tvMusicInfoAlbum.setText(getString(R.string.music_info, "专辑：", mCurrentMusic.getAlbum_title()));
        mDialogMusicInfoBinding.tvMusicInfoSinger.setText(mCurrentMusic.getAuthor());
        mDialogMusicInfoBinding.tvMusicInfoTitle.setText(getString(R.string.music_info, "歌曲：", mCurrentMusic.getTitle()));
        if (mMusicInfoDialog == null) {
            mMusicInfoDialog = new Dialog(mActivity, R.style.BottomDialog);
            mMusicInfoDialog.setContentView(mDialogMusicInfoBinding.getRoot());
            Window bottomDialogWindow = mMusicInfoDialog.getWindow();
            //getSoftInputMode(commentBinding.etContent, this);
            if (bottomDialogWindow != null) {
                WindowManager.LayoutParams layoutParams = bottomDialogWindow.getAttributes();
                layoutParams.gravity = Gravity.BOTTOM;
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                bottomDialogWindow.getDecorView().setPadding(0, 0, 0, 0);
                bottomDialogWindow.setAttributes(layoutParams);
            }
        }
        mMusicInfoDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_music_info_download:
                System.out.println("click");
                mPresenter.downloadSong(mCurrentMusic.getSong_id());
                break;
        }
    }
}
