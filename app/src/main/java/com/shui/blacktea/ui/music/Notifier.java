package com.shui.blacktea.ui.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shui.blacktea.R;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.home.HomeActivity;
import com.shui.blacktea.ui.music.broadcast.StatusBarReceiver;
import com.shui.blacktea.ui.music.constants.Extras;
import com.shui.blacktea.ui.music.service.PlayService;
import com.shui.blacktea.utils.FileUtils;

/**
 * Description: 状态栏控制器
 * Created by Juice_ on 2017/8/8.
 */

public class Notifier implements Extras {
    private static final int NOTIFICATION_ID = 0x222;
    private static PlayService mPlayService;
    private static NotificationManager mNotificationManager;

    //初始化
    public static void init(PlayService playService) {
        Notifier.mPlayService = playService;
        mNotificationManager = (NotificationManager) playService.getSystemService(Context.NOTIFICATION_SERVICE);//获取NotificationManager
    }

    /**
     * 设置播放
     *
     * @param musicEntity
     */
    public static void play(MusicEntity musicEntity) {
        mPlayService.startForeground(NOTIFICATION_ID, buildNotification(mPlayService, musicEntity, true));
    }

    /**
     * 暂停播放
     *
     * @param musicEntity
     */
    public static void pause(MusicEntity musicEntity) {
        mPlayService.stopForeground(false);
        mPlayService.startForeground(NOTIFICATION_ID, buildNotification(mPlayService, musicEntity, false));
    }

    /**
     * 取消notification
     */
    public static void cancel() {
        mNotificationManager.cancelAll();
    }

    /**
     * 创建notification
     *
     * @return
     */
    private static Notification buildNotification(Context context, MusicEntity music, boolean isPlaying) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_NOTIFICATION, true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置启动模式
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);//创建pendingInten
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)//绑定intent
                .setSmallIcon(R.drawable.ic_notification)//设置icon
                .setCustomContentView(getRemoteViews(context, music, isPlaying));//设置remoteViews
        return builder.build();
    }

    /**
     * 设置remoteViews
     *
     * @param context
     * @param musicEntity
     * @param isPlaying
     * @return
     */
    private static RemoteViews getRemoteViews(Context context, MusicEntity musicEntity, boolean isPlaying) {
        String title = musicEntity.getTitle();
        String subTitle = FileUtils.getArtistAndAlbum(musicEntity.getArtist(), musicEntity.getAlbum());
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layoyt_notificatin);
        //设置图标
        Glide.with(context).load(musicEntity.getCoverPath()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null) {
                    remoteViews.setImageViewBitmap(R.id.iv_icon, resource);
                } else {
                    remoteViews.setImageViewResource(R.id.iv_icon, R.drawable.ic_launcher);
                }
            }
        });
        /**
         * 播放  暂停
         */
        remoteViews.setTextViewText(R.id.tv_title, title);//设置标题
        remoteViews.setTextViewText(R.id.tv_subtitle, subTitle);//设置子标题
        Intent intentPlay = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);//设置intent的Action
        intentPlay.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_PLAY_PAUSE);//设置参数
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);//创建intent
        remoteViews.setImageViewResource(R.id.iv_play_pause, getPlayIconRes(isPlaying));//根据是否播放设置imgRes
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause, pendingIntentPlay);//绑定点击事件
        //下一曲
        Intent intentNext = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        intentNext.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_NEXT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, 1, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_next, getNextIconRes());
        remoteViews.setOnClickPendingIntent(R.id.iv_next, pendingIntentNext);
        return remoteViews;
    }

    /**
     * 获取play pause 图标
     *
     * @param isPlaying
     * @return
     */
    private static int getPlayIconRes(boolean isPlaying) {
        if (isPlaying) {
            return R.drawable.ic_status_bar_pause_dark_selector;
        } else {
            return R.drawable.ic_status_bar_play_dark_selector;
        }
    }

    /**
     * 获取下一曲图标
     *
     * @return
     */
    private static int getNextIconRes() {
        return R.drawable.ic_status_bar_next_dark_selector;
    }
}
