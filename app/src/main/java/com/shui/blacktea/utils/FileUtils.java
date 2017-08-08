package com.shui.blacktea.utils;

import android.text.TextUtils;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class FileUtils {
    /**
     * 获取作者和专辑名
     * @param artist
     * @param album
     * @return
     */
    public static String getArtistAndAlbum(String artist, String album) {
        if (TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return "";
        } else if (!TextUtils.isEmpty(artist) && TextUtils.isEmpty(album)) {
            return artist;
        } else if (TextUtils.isEmpty(artist) && !TextUtils.isEmpty(album)) {
            return album;
        } else {
            return artist + " - " + album;
        }
    }
}
