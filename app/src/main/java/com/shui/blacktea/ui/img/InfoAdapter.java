package com.shui.blacktea.ui.img;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.bumptech.glide.Glide;
import com.shui.blacktea.App;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.LayoutInfoWindowBinding;
import com.shui.blacktea.entity.MakerExtra;

/**
 * Description:
 * Created by Juice_ on 2017/8/17.
 */

public class InfoAdapter implements AMap.InfoWindowAdapter {
    private Context mContext = App.getInstance();
   /* private LatLng latLng;
    private String snippet;
    private String title;
    private String url;*/

    @Override
    public View getInfoWindow(Marker marker) {
        /*latLng = marker.getPosition();
        snippet = marker.getSnippet();
        title = marker.getTitle();*/
        View view = initView(marker);
        return view;
    }

    private View initView(Marker maker) {
        MakerExtra extra = (MakerExtra) maker.getObject();
        LayoutInfoWindowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_info_window, null, false);
        binding.tvInfoDate.setText(extra.getDate());
        binding.tvInfoAuthor.setText(extra.getAuthor());
        binding.tvInfoDescription.setText(extra.getDescription());
        Glide.with(mContext).load(extra.getUrl()).asBitmap().into(binding.ivInfoThumb);
        return binding.getRoot();
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
