package com.yeeyuntech.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by adu on 2017/5/20.
 * Bitmap相关工具类
 */

public class BitmapUtils {

    /**
     * 由图片的base64编码获取bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap getBitmap(String base64) {
        Bitmap bitmap = null;
        String[] arr = base64.split(",");
        if (arr.length > 1 && !TextUtils.isEmpty(arr[1])) {
            byte[] decodedString = Base64.decode(arr[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bitmap;
    }


    /**
     * 对bitmap进行base64编码
     *
     * @param bitmap
     * @return
     */
    public static String base64Encode(Bitmap bitmap) {
        //convert to byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] bytes = out.toByteArray();
        //base64 encode
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        return new String(encode);
    }


    /**
     * 由本地资源生成base64编码
     *
     * @param context
     * @param resId
     * @return
     */
    public static String base64Encode(Context context, @DrawableRes int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return base64Encode(bitmap);
    }
}
