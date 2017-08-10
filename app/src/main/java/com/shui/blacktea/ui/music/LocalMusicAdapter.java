package com.shui.blacktea.ui.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.music.service.PlayService;
import com.shui.blacktea.utils.FileUtils;
import com.shui.blacktea.utils.ShowHideUtils;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class LocalMusicAdapter extends BaseQuickAdapter<MusicEntity, BaseViewHolder> {
    private int mPlayingPosition = 1;

    public LocalMusicAdapter(@LayoutRes int layoutResId, @Nullable List<MusicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_artist, FileUtils.getArtistAndAlbum(item.getArtist(), item.getAlbum()));
        if (mPlayingPosition == -1) {
            ShowHideUtils.showHide(helper.getView(R.id.iv_cover), helper.getView(R.id.iv_playing), false);
        } else {
            if (item == mData.get(mPlayingPosition)) {
                ShowHideUtils.showHide(helper.getView(R.id.iv_cover), helper.getView(R.id.iv_playing), true);
            } else {
                ShowHideUtils.showHide(helper.getView(R.id.iv_cover), helper.getView(R.id.iv_playing), false);
            }
        }
        Glide.with(mContext).load(item.getCoverPath()).crossFade().placeholder(R.drawable.default_cover).into((ImageView) helper.getView(R.id.iv_cover));
    }

    public void updatePlayingPosition(PlayService playService) {
        if (playService.getPlayingMusic() != null && playService.getPlayingMusic().getType() == MusicEntity.TYPE_LOCAL) {
            mPlayingPosition = playService.getPlayingPosition();
        } else {
            mPlayingPosition = -1;
        }
    }
}
