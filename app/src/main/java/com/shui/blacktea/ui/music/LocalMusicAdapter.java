package com.shui.blacktea.ui.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.utils.FileUtils;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class LocalMusicAdapter extends BaseQuickAdapter<MusicEntity, BaseViewHolder> {

    public LocalMusicAdapter(@LayoutRes int layoutResId, @Nullable List<MusicEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_name, item.getArtist())
                .setText(R.id.tv_artist, FileUtils.getArtistAndAlbum(item.getArtist(), item.getAlbum()));
        Glide.with(mContext).load(item.getCoverPath()).crossFade().into((ImageView) helper.getView(R.id.iv_cover));
    }
}
