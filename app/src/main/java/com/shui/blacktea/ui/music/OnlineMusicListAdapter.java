package com.shui.blacktea.ui.music;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardEntity;
import com.shui.blacktea.utils.FileUtils;
import com.shui.blacktea.utils.ShowHideUtils;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/11.
 */

public class OnlineMusicListAdapter extends BaseQuickAdapter<BaiduSongBillboardEntity, BaseViewHolder> {
    private onMoreClickListener mOnMoreClickListener;

    public OnlineMusicListAdapter(@LayoutRes int layoutResId, @Nullable List<BaiduSongBillboardEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, BaiduSongBillboardEntity item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_artist, FileUtils.getArtistAndAlbum(item.getArtist_name(), item.getAlbum_title()));
        Glide.with(mContext).load(item.getPic_small()).asBitmap().into((ImageView) helper.getView(R.id.iv_cover));
        ShowHideUtils.showHide(helper.getView(R.id.iv_cover), helper.getView(R.id.iv_playing), false);
        helper.getView(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMoreClickListener != null) {
                    mOnMoreClickListener.onMoreClick(helper.getAdapterPosition());
                }
            }
        });
    }

    public interface onMoreClickListener {
        void onMoreClick(int position);
    }

    public void setOnMoreClickListener(onMoreClickListener onMoreClickListener) {
        this.mOnMoreClickListener = onMoreClickListener;
    }
}
