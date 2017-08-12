package com.shui.blacktea.ui.music;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentMusicPlayingBinding;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.music.service.OnPlayEventListener;
import com.shui.blacktea.utils.ImageUtils;
import com.shui.blacktea.utils.SystemUtils;
import com.yeeyuntech.framework.utils.DisplayUtils;

import javax.inject.Inject;

/**
 * Description:正在播放 音乐界面
 * Created by Juice_ on 2017/8/8.
 */

public class FragmentPlayingMusic extends BaseFragment implements OnPlayEventListener, View.OnClickListener {
    @Inject
    FragmentMusicPlayingBinding mBinding;
    private int mLastProgress;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_playing;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    protected View initBinding() {
        mBinding.setClick(this);
        System.out.println("playingCreate");
        return mBinding.getRoot();
    }

    @Override
    public void initParams() {
        //处理状态栏的高度问题   ???????   因为设置fitystemWindowsS无效   ？？？？？？？
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBinding.llHeader.getLayoutParams();
        params.topMargin = DisplayUtils.getStatusBarHeight(mActivity);
        mBinding.llHeader.setLayoutParams(params);
        //----------------
    }

    @Override
    public void onPublish(int progress) {
        if (isAdded()) {
            mBinding.sbProgress.setProgress(progress);
            if (progress - mLastProgress >= 1000) {
                mBinding.tvCurrentTime.setText(formatTime(progress));
                mLastProgress = progress;
            }
        }
    }

    @Override
    public void onPlayerPause() {
        if (isAdded()) {
            mBinding.ivPlay.setSelected(false);
        }
    }

    @Override
    public void onPlayerResume() {
        if (isAdded()) {
            mBinding.ivPlay.setSelected(true);
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if (isAdded()) {
            mBinding.sbProgress.setSecondaryProgress(mBinding.sbProgress.getMax() * 100 / percent);
        }
    }

    @Override
    public void onChange(MusicEntity musicEntity) {
        if (isAdded()) {
            onPlay(musicEntity);
        }
    }

    private void onPlay(MusicEntity musicEntity) {
        if (musicEntity == null) {
            return;
        }
        mBinding.tvArtist.setText(musicEntity.getArtist());
        mBinding.tvTitle.setText(musicEntity.getTitle());
        mBinding.sbProgress.setProgress(0);
        mBinding.sbProgress.setSecondaryProgress(0);
        mBinding.sbProgress.setMax((int) musicEntity.getDuration());
        mLastProgress = 0;
        mBinding.tvCurrentTime.setText(R.string.play_time_start);
        mBinding.tvTotalTime.setText(formatTime(musicEntity.getDuration()));
        if (musicEntity.getCoverPath() != null && !musicEntity.getCoverPath().equals("")) {
            Glide.with(mActivity).load(musicEntity.getCoverPath()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mBinding.ivPlayPageBg.setImageBitmap(ImageUtils.blur(resource));
                }
            });
        }
        //TODO  设置背景  歌词
        if (getPlayService().isPlaying() || getPlayService().isPreparing()) {
            mBinding.ivPlay.setSelected(true);
        } else {
            mBinding.ivPlay.setSelected(false);
        }
    }

    @Override
    public void onTimer(int remain) {

    }

    @Override
    public void onMusicListUpdate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                ((MusicFragment) getParentFragment()).onBackPressedSupport();
                break;
            case R.id.iv_mode://列表模式
                break;
            case R.id.iv_prev://上一曲
                prev();
                break;
            case R.id.iv_play://播放，暂停
                play();
                break;
            case R.id.iv_next://下一曲
                next();
                break;
        }
    }

    private void play() {
        getPlayService().playPause();
    }

    private void next() {
        getPlayService().next();
    }

    private void prev() {
        getPlayService().prev();
    }

    private String formatTime(long time) {
        return SystemUtils.formatTime("mm:ss", time);
    }
}
