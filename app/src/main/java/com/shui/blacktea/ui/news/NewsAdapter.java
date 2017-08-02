package com.shui.blacktea.ui.news;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.NewsEntity;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsEntity, BaseViewHolder> {

    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<NewsEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity item) {
        helper.setText(R.id.news_tv_title, item.getTitle())
                .setText(R.id.news_tv_time, item.getCtime())
                .setText(R.id.news_tv_describe, item.getDescription())
                .setText(R.id.news_tv_actor, "来源于天行");
        Glide.with(mContext).load(item.getPicUrl()).into((ImageView) helper.getView(R.id.news_iv_thumb));
    }
    /**
     *   public class QuickAdapter extends BaseQuickAdapter<Movie, BaseViewHolder> {
     public QuickAdapter() {
     super(R.layout.item_news);
     }

     @Override
     protected void convert(BaseViewHolder viewHolder, Movie item) {
     viewHolder.setText(R.id.news_tv_title, item.filmName)
     .setText(R.id.news_tv_actor, item.actors)
     .setText(R.id.news_tv_time, item.grade)
     .setText(R.id.news_tv_describe, item.shortinfo);
     Glide.with(mContext).load(item.picaddr).into((ImageView) viewHolder.getView(R.id.news_iv_thumb));
     }
     }
     */
}
