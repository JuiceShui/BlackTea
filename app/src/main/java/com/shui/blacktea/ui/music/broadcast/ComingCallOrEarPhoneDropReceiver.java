package com.shui.blacktea.ui.music.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shui.blacktea.ui.music.constants.Actions;
import com.shui.blacktea.ui.music.service.PlayService;

/**
 * Description:来电，耳机拔出
 * Created by Juice_ on 2017/8/8.
 */

public class ComingCallOrEarPhoneDropReceiver extends BroadcastReceiver implements Actions {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayService.startCommand(context, Actions.ACTION_MEDIA_PLAY_PAUSE);
    }
}
