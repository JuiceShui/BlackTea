package com.shui.blacktea.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.shui.blacktea.R;
import com.shui.blacktea.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 加载本地音乐列表
 * Created by Juice_ on 2017/8/9.
 */

public class MusicLoaderUtils {
    public static List<MusicEntity> scanMusic(Context context) {
        List<MusicEntity> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor == null) {
            return null;
        }
        long filterSize = SharedPreferenceUtils.getFilterSize();
        long filterTime = SharedPreferenceUtils.getFilterTime();
        while (cursor.moveToNext()) {
            MusicEntity entity = new MusicEntity();
            long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long fileTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            if (fileSize < filterSize || fileTime < filterTime) {
                continue;
            }
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String unknow = context.getString(R.string.unknown);
            artist = TextUtils.isEmpty(artist) || artist.toLowerCase().contains("unknow") ? unknow : artist;
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            //String coverPath = getAlbumArt(context, albumId);
            String coverPath = getCoverPath(context, albumId);
            entity.setType(MusicEntity.TYPE_LOCAL);
            entity.setAlbum(album);
            entity.setArtist(artist);
            entity.setCoverPath(coverPath);
            entity.setDuration(fileTime);
            entity.setFileName(fileName);
            entity.setFileSize(fileSize);
            entity.setId(id);
            entity.setTitle(title);
            entity.setPath(path);
            list.add(entity);
        }
        cursor.close();
        return list;
    }

    //获取封面
    private static String getCoverPath(Context context, long albumId) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/audio/albums/" + albumId),
                new String[]{"album_art"}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext() && cursor.getColumnCount() > 0) {
                path = cursor.getString(0);
            }
            cursor.close();
        }
        return path;
    }

    private static String getAlbumArt(Context context, long album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Long.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur != null) {
            if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
                cur.moveToNext();
                album_art = cur.getString(0);
            }
        }
        assert cur != null;
        cur.close();
        cur = null;
        return album_art;
    }
}
