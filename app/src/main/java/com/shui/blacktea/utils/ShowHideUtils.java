package com.shui.blacktea.utils;

import android.view.View;

/**
 * Description:
 * Created by Juice_ on 2017/8/10.
 */

public class ShowHideUtils {
    public static void showHide(View content, View empty, boolean isEmpty) {
        if (isEmpty) {
            content.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
    }
}
