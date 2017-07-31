package com.yeeyuntech.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.yeeyuntech.framework.YYApplication;


/**
 * Created by adu on 2017/5/20.
 * Toast相关工具类
 */

public class ToastUtils {

    private static Context context = YYApplication.getInstance();

    public static void showToast(int resID) {
        showToast(context, Toast.LENGTH_SHORT, resID);
    }

    public static void showToast(String text) {
        showToast(context, Toast.LENGTH_SHORT, text);
    }

    public static void showToast(Context ctx, int resID) {
        showToast(ctx, Toast.LENGTH_SHORT, resID);
    }

    public static void showToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_SHORT, text);
    }

    public static void showLongToast(int resID) {
        showToast(context, Toast.LENGTH_LONG, resID);
    }

    public static void showLongToast(Context ctx, int resID) {
        showToast(ctx, Toast.LENGTH_LONG, resID);
    }

    public static void showLongToast(String text) {
        showToast(context, Toast.LENGTH_LONG, text);
    }

    public static void showLongToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_LONG, text);
    }

    public static void showToast(Context ctx, int duration, int resID) {
        showToast(ctx, duration, ctx.getString(resID));
    }

    public static void showToast(Context ctx, int duration, String text) {
        Toast toast = Toast.makeText(ctx, text, duration);
//        View mNextView = toast.getView();
//        if (mNextView != null)
//            mNextView.setBackgroundResource(R.drawable.toast_frame);
        toast.show();
    }

    /**
     * 在UI线程运行弹出
     */
    public static void showToastOnUiThread(final Activity ctx, final String text) {
        if (ctx != null) {
            ctx.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    showToast(ctx, text);
                }
            });
        }
    }

}
