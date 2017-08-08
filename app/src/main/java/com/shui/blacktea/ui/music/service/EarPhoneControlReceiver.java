package com.shui.blacktea.ui.music.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.shui.blacktea.ui.music.constants.Actions;

/**
 * Description:  耳机线控制播放
 * Created by Juice_ on 2017/8/8.
 */

public class EarPhoneControlReceiver extends BroadcastReceiver implements Actions {
    @Override
    public void onReceive(Context context, Intent intent) {
        KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null || event.getAction() != KeyEvent.ACTION_UP) {
            return;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                PlayService.startCommand(context, ACTION_MEDIA_PLAY_PAUSE);
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                PlayService.startCommand(context, ACTION_MEDIA_NEXT);
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                PlayService.startCommand(context, ACTION_MEDIA_PREVIOUS);
                break;
        }
    }
}
