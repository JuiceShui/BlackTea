package com.shui.blacktea.utils;

import android.content.Context;
import android.text.TextUtils;

import com.shui.blacktea.App;
import com.shui.blacktea.R;
import com.shui.blacktea.config.Constants;
import com.shui.blacktea.entity.MusicEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileUtils {
    private static final String MP3 = ".mp3";
    private static final String LRC = ".lrc";

    private static String getAppDir() {
        return Constants.PATH_EXTERNAL_CARD;
    }

    public static String getMusicDir() {
        String dir = getAppDir() + "/Music/";
        return mkdirs(dir);
    }

    public static String getLrcDir() {
        String dir = getAppDir() + "/Lyric/";
        return mkdirs(dir);
    }

    public static String getAlbumDir() {
        String dir = getAppDir() + "/Album/";
        return mkdirs(dir);
    }

    public static String getLogDir() {
        String dir = getAppDir() + "/Log/";
        return mkdirs(dir);
    }

    public static String getSplashDir(Context context) {
        String dir = context.getFilesDir() + "/splash/";
        return mkdirs(dir);
    }

    public static String getRelativeMusicDir() {
        String dir = "blackTea/Music/";
        return mkdirs(dir);
    }

    /**
     * 获取歌词路径<br>
     * 先从已下载文件夹中查找，如果不存在，则从歌曲文件所在文件夹查找。
     *
     * @return 如果存在返回路径，否则返回null
     */
    public static String getLrcFilePath(MusicEntity music) {
        if (music == null) {
            return null;
        }

        String lrcFilePath = getLrcDir() + getLrcFileName(music.getArtist(), music.getTitle());
        if (!exists(lrcFilePath)) {
            lrcFilePath = music.getPath().replace(MP3, LRC);
            if (!exists(lrcFilePath)) {
                lrcFilePath = null;
            }
        }
        return lrcFilePath;
    }

    /**
     * 获取封面图片路径<br>
     *
     * @return 如果存在返回路径，否则返回null
     */
    public static String getAlbumFilePath(MusicEntity music) {
        if (music == null) {
            return null;
        }

        String albumFilePath = music.getCoverPath();
        if (TextUtils.isEmpty(albumFilePath) || !exists(albumFilePath)) {
            albumFilePath = getAlbumDir() + getAlbumFileName(music.getArtist(), music.getTitle());
            if (!exists(albumFilePath)) {
                albumFilePath = null;
            }
        }
        return albumFilePath;
    }

    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    private static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String getMp3FileName(String artist, String title) {
        return getFileName(artist, title) + MP3;
    }

    public static String getLrcFileName(String artist, String title) {
        return getFileName(artist, title) + LRC;
    }

    public static String getAlbumFileName(String artist, String title) {
        return getFileName(artist, title);
    }

    public static String getFileName(String artist, String title) {
        artist = stringFilter(artist);
        title = stringFilter(title);
        if (TextUtils.isEmpty(artist)) {
            artist = App.getInstance().getString(R.string.unknown);
        }
        if (TextUtils.isEmpty(title)) {
            title = App.getInstance().getString(R.string.unknown);
        }
        return artist + " - " + title;
    }

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

    /**
     * 过滤特殊字符(\/:*?"<>|)
     */
    private static String stringFilter(String str) {
        if (str == null) {
            return null;
        }
        String regEx = "[\\/:*?\"<>|]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static float b2mb(int b) {
        String mb = String.format(Locale.getDefault(), "%.2f", (float) b / 1024 / 1024);
        return Float.valueOf(mb);
    }

    public static void saveLrcFile(String path, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        System.out.println("下载完成");
        out.flush();
        out.close();
        in.close();

    }
}
