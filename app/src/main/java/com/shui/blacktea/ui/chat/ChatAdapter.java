package com.shui.blacktea.ui.chat;

import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.IMMessage;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/18.
 */

public class ChatAdapter extends BaseMultiItemQuickAdapter<IMMessage, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatAdapter(List<IMMessage> data) {
        super(data);
        addItemType(IMMessage.TYPE_MESSAGE_TEXT, R.layout.item_chat_text);
        addItemType(IMMessage.TYPE_MESSAGE_IMAGE, R.layout.item_chat_img);
        addItemType(IMMessage.TYPE_MESSAGE_AUDIO, R.layout.item_chat_audio);
        addItemType(IMMessage.TYPE_MESSAGE_VIDEO, R.layout.item_chat_img);//暂时没有
        addItemType(IMMessage.TYPE_MESSAGE_FILE, R.layout.item_chat_img);//暂时没有
        addItemType(IMMessage.TYPE_MESSAGE_LOCATION, R.layout.item_chat_img);//暂时没有
    }

    @Override
    protected void convert(BaseViewHolder helper, IMMessage item) {
        switch (helper.getItemViewType()) {
            case IMMessage.TYPE_MESSAGE_TEXT:
                AVIMTextMessage textMessage = (AVIMTextMessage) item.getMessage();
                if (item.getMessageType() == IMMessage.TYPE_COMMING) {
                    helper.getView(R.id.ll_chat_post).setVisibility(View.GONE);
                    helper.getView(R.id.ll_chat_coming).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_comming_message, textMessage.getText());
                    Glide.with(mContext).load(item.getAvatar()).asBitmap().into((ImageView) helper.getView(R.id.iv_coming_avatar));
                } else {
                    helper.getView(R.id.ll_chat_coming).setVisibility(View.GONE);
                    helper.getView(R.id.ll_chat_post).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_post_message, textMessage.getText());
                    Glide.with(mContext).load(item.getAvatar()).asBitmap().into((ImageView) helper.getView(R.id.iv_post_avatar));
                }
                break;
            case IMMessage.TYPE_MESSAGE_IMAGE:
                AVIMImageMessage imgMessage = (AVIMImageMessage) item.getMessage();
                String url = imgMessage.getFileUrl();
                if (item.getMessageType() == IMMessage.TYPE_COMMING) {
                    //String fromClientId = imgMessage.getFrom();
                    //String messageId = imgMessage.getMessageId();
                    helper.getView(R.id.ll_chat_post).setVisibility(View.GONE);
                    helper.getView(R.id.ll_chat_coming).setVisibility(View.VISIBLE);
                    //helper.setText(R.id.tv_comming_message, textMessage.getText());
                    Glide.with(mContext).load(url).asBitmap().into((ImageView) helper.getView(R.id.iv_comming_pic));
                    Glide.with(mContext).load(item.getAvatar()).asBitmap().into((ImageView) helper.getView(R.id.iv_coming_avatar));
                } else {
                    helper.getView(R.id.ll_chat_coming).setVisibility(View.GONE);
                    helper.getView(R.id.ll_chat_post).setVisibility(View.VISIBLE);
                    //helper.setText(R.id.tv_post_message, textMessage.getText());
                    Glide.with(mContext).load(url).asBitmap().into((ImageView) helper.getView(R.id.iv_post_pic));
                    Glide.with(mContext).load(item.getAvatar()).asBitmap().into((ImageView) helper.getView(R.id.iv_post_avatar));
                }
                break;
            case IMMessage.TYPE_MESSAGE_AUDIO:
                break;
            case IMMessage.TYPE_MESSAGE_VIDEO:
            case IMMessage.TYPE_MESSAGE_FILE:
            case IMMessage.TYPE_MESSAGE_LOCATION:
                break;

        }
    }
}
