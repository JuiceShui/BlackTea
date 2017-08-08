package com.shui.blacktea.ui.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.music.Notifier;
import com.shui.blacktea.ui.music.broadcast.ComingCallOrEarPhoneDropReceiver;
import com.shui.blacktea.ui.music.constants.Actions;
import com.shui.blacktea.ui.music.constants.PlayMode;
import com.shui.blacktea.utils.SharedPreferenceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description:音乐service
 * Created by Juice_ on 2017/8/8.
 */

public class PlayService extends Service implements Actions, PlayServiceMethod, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener, PlayMode {
    private static final long TIME_UPDATE = 100L;
    private static final int STATE_IDLE = 0;//空闲
    private static final int STATE_PREPARING = 1;//准备
    private static final int STATE_PLAYING = 2;//播放中
    private static final int STATE_PAUSE = 3;//暂停中
    private OnPlayEventListener mPlayListener;
    private MediaPlayer mPlayer;
    private AudioManager mAudioManager;
    private MusicEntity mPlayingMusic;//当前播放的音乐
    private int mPlayingPosition;//当前播放的本地音乐标号
    private int mPlayingState = STATE_IDLE;//当前状态
    private final IntentFilter mComingCallOrEarPhoneDropFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private ComingCallOrEarPhoneDropReceiver mComingCallOrEarPhoneDropReceiver = new ComingCallOrEarPhoneDropReceiver();
    private List<MusicEntity> mMusicList = new ArrayList<>();//音乐列表

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mPlayer.setOnCompletionListener(this);
        Notifier.init(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_MEDIA_PLAY_PAUSE:
                    playPause();
                    break;
                case ACTION_MEDIA_NEXT:
                    next();
                    break;
                case ACTION_MEDIA_PREVIOUS:
                    prev();
                    break;
            }
        }
        return START_NOT_STICKY;
    }


    /**
     * 播放对应音乐
     *
     * @param musicEntity
     */
    @Override
    public void play(MusicEntity musicEntity) {
        mPlayingMusic = musicEntity;//赋值当前播放的音乐
        mPlayer.reset();
        try {
            mPlayer.setDataSource(musicEntity.getPath());
            mPlayer.prepareAsync();
            mPlayingState = STATE_PREPARING;//更改当前状态
            mPlayer.setOnPreparedListener(mPreparedListener);//准备的监听
            mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);//缓冲的监听
            if (mPlayListener != null) {
                mPlayListener.onChange(musicEntity);//改变当前音乐
            }
            Notifier.play(musicEntity);//更新到notification
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据position播放本地音乐
     *
     * @param position
     */
    @Override
    public void play(int position) {
        if (mMusicList == null) {
            return;
        }
        if (position <= 0) {
            position = mMusicList.size() - 1;
        } else if (position >= mMusicList.size()) {
            position = 0;
        }
        mPlayingPosition = position;
        MusicEntity musicEntity = mMusicList.get(mPlayingPosition);
        SharedPreferenceUtils.setCurrentSongId(musicEntity.getId());
        play(musicEntity);
    }

    @Override
    public void playPause() {
        if (isPreparing()) {
            stop();
        } else if (isPlaying()) {
            pause();
        } else if (isPausing()) {
            resume();
        } else {
            play(getPlayingPosition());
        }
    }

    @Override
    public void stop() {
        if (isIdle()) {
            return;
        }
        pause();
        mPlayer.reset();
        mPlayingState = STATE_IDLE;
    }

    @Override
    public void next() {
        if (mMusicList == null) {
            return;
        }
        int PlayMode = SharedPreferenceUtils.getPlayMode();
        switch (PlayMode) {
            case MODE_RANDOM:
                mPlayingPosition = new Random().nextInt(mMusicList.size());
                break;
            case MODE_SINGLE:
                break;
            case MODE_LOOP:
                mPlayingPosition += 1;
                break;
        }
        play(mPlayingPosition);
    }

    @Override
    public void prev() {
        if (mMusicList == null) {
            return;
        }
        int PlayMode = SharedPreferenceUtils.getPlayMode();
        switch (PlayMode) {
            case MODE_RANDOM:
                mPlayingPosition = new Random().nextInt(mMusicList.size());
                break;
            case MODE_SINGLE:
                break;
            case MODE_LOOP:
                mPlayingPosition -= 1;
                break;
        }
        play(mPlayingPosition);
    }

    /**
     * 退出
     */
    @Override
    public void quit() {
        stop();
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        Notifier.cancel();
        stopSelf();
    }

    @Override
    public void seekTo(int second) {
        if (isPausing() || isPlaying()) {
            mPlayer.seekTo(second);
            if (mPlayListener != null) {
                mPlayListener.onPublish(second);
            }
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pause();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    public OnPlayEventListener getPlayListener() {
        return mPlayListener;
    }

    public void setPlayListener(OnPlayEventListener playListener) {
        this.mPlayListener = playListener;
    }

    /**
     * 加载的监听
     */
    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (isPreparing()) {
                start();
            }
        }
    };
    /**
     * 缓冲的监听
     */
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mPlayListener != null) {
                mPlayListener.onBufferingUpdate(percent);
            }
        }
    };

    /**
     * 开始播放
     */
    private boolean start() {
        mPlayer.start();
        if (mPlayer.isPlaying())//如果开始播放了
        {
            mPlayingState = STATE_PLAYING;//更改状态为playing
            doInterval(false);//通知更新
            Notifier.play(mPlayingMusic);
            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK);
            registerReceiver(mComingCallOrEarPhoneDropReceiver, mComingCallOrEarPhoneDropFilter);
        }
        return mPlayer.isPlaying();
    }

    /**
     * 暂停播放
     */
    private void pause() {
        if (!isPlaying()) {//如果当前不是播放着的状态
            return;
        }
        mPlayer.pause();
        mPlayingState = STATE_PAUSE;
        Notifier.pause(mPlayingMusic);
        doInterval(true);
        mAudioManager.abandonAudioFocus(this);
        unregisterReceiver(mComingCallOrEarPhoneDropReceiver);
        if (mPlayListener != null) {
            mPlayListener.onPlayerPause();
        }
    }

    /**
     * 重新播放
     */
    private void resume() {
        if (!isPausing()) {
            return;
        }
        if (start()) {
            if (mPlayListener != null) {
                mPlayListener.onPlayerPause();
            }
        }
    }

    /**
     * 获取正在播放的音乐序号
     *
     * @return
     */
    private int getPlayingPosition() {
        return mPlayingPosition;
    }

    public boolean isPlaying() {
        return mPlayingState == STATE_PLAYING;
    }

    public boolean isPausing() {
        return mPlayingState == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return mPlayingState == STATE_PREPARING;
    }

    public boolean isIdle() {
        return mPlayingState == STATE_IDLE;
    }

    private void doInterval(boolean isCancel) {
        Disposable disposable = null;
        if (!isCancel) {
            disposable = Observable.interval(TIME_UPDATE, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                            if (mPlayListener != null && isPlaying()) {
                                mPlayListener.onPublish(mPlayer.getCurrentPosition());
                            }
                        }
                    });
        } else {
            disposable.dispose();
        }
    }
}
