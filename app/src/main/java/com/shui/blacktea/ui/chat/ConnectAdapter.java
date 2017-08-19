package com.shui.blacktea.ui.chat;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.UserEntity;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/19.
 */

public class ConnectAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    public ConnectAdapter(@LayoutRes int layoutResId, @Nullable List<UserEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        helper.setText(R.id.tv_connect_signature, item.getSignature())
                .setText(R.id.tv_connect_user, item.getUserName());
        Glide.with(mContext).load(item.getAvatar()).asBitmap().into((ImageView) helper.getView(R.id.iv_connect_avatar));
    }
}
