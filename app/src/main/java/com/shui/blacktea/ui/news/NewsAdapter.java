package com.shui.blacktea.ui.news;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.WeiBoEntity;
import com.yeeyuntech.framework.utils.DateUtils;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class NewsAdapter extends BaseQuickAdapter<WeiBoEntity, BaseViewHolder> {

    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<WeiBoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeiBoEntity item) {
        helper.setText(R.id.news_tv_title, item.getDesc())
                .setText(R.id.news_tv_time, DateUtils.formatyMd(Long.parseLong(item.getDate() + "")))
                .setText(R.id.news_tv_describe, item.getName())
                .setText(R.id.news_tv_actor, item.getNewinfo());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.news_iv_thumb));
    }
}
