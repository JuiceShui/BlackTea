package com.shui.blacktea.ui.music;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.BaiduSong.SongBillboardEntity;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/11.
 */

public class OnlineMusicAdapter extends BaseMultiItemQuickAdapter<SongBillboardEntity, BaseViewHolder> {
    public OnlineMusicAdapter(@Nullable List<SongBillboardEntity> data) {
        super(data);
        addItemType(SongBillboardEntity.TYPE_TITLE, R.layout.item_music_cate);
        addItemType(SongBillboardEntity.TYPE_BILLBOARD, R.layout.item_music_online);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongBillboardEntity item) {
        switch (helper.getItemViewType()) {
            case SongBillboardEntity.TYPE_TITLE:
                helper.setText(R.id.tv_profile, item.getCover());
                break;
            case SongBillboardEntity.TYPE_BILLBOARD:
                helper.setText(R.id.tv_music_1, "1." + item.getTopOne())
                        .setText(R.id.tv_music_2, "2." + item.getTopTwo())
                        .setText(R.id.tv_music_3, item.getTopThree() == null ? "" : "3." + item.getTopThree());
                Glide.with(mContext).load(item.getCover()).placeholder(R.drawable.default_cover).crossFade().into((ImageView) helper.getView(R.id.iv_cover));
        }
    }
}
