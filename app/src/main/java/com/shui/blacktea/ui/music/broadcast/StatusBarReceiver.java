package com.shui.blacktea.ui.music.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.shui.blacktea.ui.music.constants.Actions;
import com.shui.blacktea.ui.music.service.PlayService;

/**
 * Description: notification的通知
 * Created by Juice_ on 2017/8/8.
 */

public class StatusBarReceiver extends BroadcastReceiver implements Actions {
    public static final String ACTION_STATUS_BAR = "com.shui.blacktea.STATUS_BAR_ACTIONS";
    public static final String EXTRA = "extra";
    public static final String EXTRA_NEXT = "next";
    public static final String EXTRA_PLAY_PAUSE = "play_pause";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {//为空
            return;
        }
        String extra = intent.getStringExtra(EXTRA);
        if (TextUtils.equals(extra, EXTRA_NEXT)) {
            PlayService.startCommand(context, Actions.ACTION_MEDIA_NEXT);
        } else if (TextUtils.equals(EXTRA_PLAY_PAUSE, extra)) {
            PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
        }
    }
}
