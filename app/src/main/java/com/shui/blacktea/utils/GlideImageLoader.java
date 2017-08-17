package com.shui.blacktea.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.shui.blacktea.R;

import java.io.File;

/**
 * Description:
 * Created by Juice_ on 2017/8/16.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .override(width, height)
                .centerCrop()//
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
