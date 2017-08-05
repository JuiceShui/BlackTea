package com.shui.blacktea.ui.video;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.VideoEntity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/4.
 */

public class VideoAdapter extends BaseQuickAdapter<VideoEntity, BaseViewHolder> {
    private NiceVideoPlayer mPlayer;
    private TxVideoPlayerController mController;

    public VideoAdapter(@LayoutRes int layoutResId, @Nullable List<VideoEntity> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, VideoEntity item) {
        mPlayer = helper.getView(R.id.video_player);
        ViewGroup.LayoutParams params = mPlayer.getLayoutParams();
        params.width = helper.itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mController = new TxVideoPlayerController(mContext);
        mPlayer.setLayoutParams(params);
        mController.setTitle(item.getText());
        mController.setLenght(item.getVideoLength());
        Glide.with(mContext)
                .load(item.getProfile_image())
                //.placeholder(R.drawable.img_default)
                .crossFade()
                .into(mController.imageView());
        mPlayer.setController(mController);
        mPlayer.setUp(item.getVideo_uri(), null);
    }

    public NiceVideoPlayer getPlayer() {
        return mPlayer;
    }
}
