/*
 * Created by lsy on 2017/7/29.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: 本应用使用
 */
public class ShareManager {

    private static String TAG = "share ";

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView, String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        Log.d(TAG, "实际高度:" + h);
        Log.d(TAG, " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }

    /**
     * 截图listview
     **/
    public static Bitmap getListViewBitmap(RecyclerView rv, String picpath) {
        Bitmap longImage = Bitmap.createBitmap(rv.getMeasuredWidth(),
                rv.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, rv.getMeasuredHeight(), paint);
        rv.draw(canvas);
        FileOutputStream out = null;
        try {
            File f = new File(picpath);
            out = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                longImage.compress(Bitmap.CompressFormat.PNG, 10, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return longImage;
    }

//    public  void share() {
//        Observable.create(new ObservableOnSubscribe<Uri>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Uri> e) throws Exception {
//
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Uri>() {
//                    @Override
//                    public void accept(@NonNull Uri uri) throws Exception {
//                        Intent intent2=new Intent(Intent.ACTION_SEND);
//                        Uri uri=Uri.fromFile(new File("/storage/emulated/0/pictures/1474533294366.jpg"));
//                        intent2.putExtra(Intent.EXTRA_STREAM,uri);
//                        intent2.setType("image/*");
//                        startActivity(Intent.createChooser(intent2,"share"));
//                    }
//                });
//    }


}
