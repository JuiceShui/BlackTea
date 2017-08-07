package com.shui.blacktea.ui.news;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.NewsCateEntity;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class NewsCateAdapter extends BaseQuickAdapter<NewsCateEntity, BaseViewHolder> {
    public NewsCateAdapter(@LayoutRes int layoutResId, @Nullable List<NewsCateEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsCateEntity item) {
        helper.setText(R.id.tv_name, item.getName())
                .setImageResource(R.id.iv_thumb, item.getIcon());
    }
}
