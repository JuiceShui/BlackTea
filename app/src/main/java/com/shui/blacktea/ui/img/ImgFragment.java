package com.shui.blacktea.ui.img;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.shui.blacktea.App;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentImgBinding;
import com.shui.blacktea.entity.MakerExtra;
import com.shui.blacktea.ui.BaseFragment;
import com.yeeyuntech.framework.utils.ToastUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class ImgFragment extends BaseFragment implements AMap.OnMapClickListener, AMap.OnCameraChangeListener, View.OnClickListener, AMap.OnMarkerClickListener {
    private static final int IMAGE_PICKER = 1;
    @Inject
    FragmentImgBinding mBinding;
    private AMap mMap;
    private TextureMapView mMapView;
    private MyLocationStyle myLocationStyle;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationListener mLocationListener;
    private AMapLocationClientOption mLocationOption;
    private LatLng mCurrentPosition;
    private UiSettings mMapUiSetting;
    private boolean isShareIconShowing = true;
    private boolean isFirstPic = true;
    private List<MakerExtra> mMakersInfo = new ArrayList<>();
    private Marker mCurrentMarker = null;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_img;
    }

    @Override
    protected View initBinding() {
        mBinding.setClick(this);
        return mBinding.getRoot();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (TextureMapView) getView().findViewById(R.id.map_view);
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
            mMap = mMapView.getMap();
        }
    }

    @Override
    public void initViews() {
        if (mMapView == null) {
            mMapView = mBinding.mapView;
            if (mMap == null) {
                mMap = mMapView.getMap();
            }
        }
        mLocationListener = new MyLocationListener();
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setInterval(1000);
        mLocationOption.setOnceLocation(false);
        mLocationClient = new AMapLocationClient(App.getInstance());
        mLocationClient.setLocationListener(mLocationListener);
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000);//定位间隔
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.showMyLocation(true);
        mMap.setMyLocationStyle(myLocationStyle);
        mMapUiSetting = mMap.getUiSettings();
        mMapUiSetting.setMyLocationButtonEnabled(true);
        mMapUiSetting.setCompassEnabled(true);
        mMapUiSetting.setScaleControlsEnabled(true);
        mMapUiSetting.setLogoPosition(AMapOptions.LOGO_MARGIN_RIGHT);
        mMap.setMyLocationEnabled(true);
        mLocationClient.setLocationOption(mLocationOption);
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new InfoAdapter());
    }

    @Override
    public void initListeners() {
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    public void getData() {
        mLocationClient.startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
        mLocationClient.stopLocation();
    }

    //点击map的回调
    @Override
    public void onMapClick(LatLng latLng) {
        if (mCurrentMarker != null) {
            mCurrentMarker.hideInfoWindow();
        }
    }

    //地图移动事件
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (isShareIconShowing) {
            mBinding.shareImage.setVisibility(View.GONE);
            isShareIconShowing = !isShareIconShowing;
        }
    }

    //地图移动事件结束
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (!isShareIconShowing) {
            mBinding.shareImage.setVisibility(View.VISIBLE);
            isShareIconShowing = !isShareIconShowing;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_image:
                //开始分享图片
                Intent intent = new Intent(mActivity, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AVObject imageList = new AVObject("ImageList");
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                isFirstPic = true;
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    ImageItem item = images.get(i);
                    try {
                        final AVFile file = AVFile.withAbsoluteLocalPath(item.name, item.path);
                        file.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                //mImageUrls.add(file.getUrl());
                                AVObject image = new AVObject("Image");
                                image.put("belong", imageList);
                                image.put("url", file.getUrl());
                                image.saveInBackground();
                                if (isFirstPic) {
                                    imageList.put("thumb", file.getUrl());
                                    isFirstPic = false;
                                }
                                //AVRelation<AVObject> imageRelation = imageList.getRelation("images");
                                //imageRelation.add(image);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                imageList.put("belong", AVUser.getCurrentUser());
                imageList.put("latitude", mCurrentPosition.latitude);
                imageList.put("longitude", mCurrentPosition.longitude);
                imageList.put("description", "xxxxxxxxx");
                imageList.put("name", "xxxx");
                imageList.saveInBackground();
                //AVRelation<AVObject> relation = AVUser.getCurrentUser().getRelation("images");
                //relation.add(imageList);
                AVUser.getCurrentUser().saveInBackground();
            } else {
                ToastUtils.showToast("没有数据");
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getObject() != null) {
            mCurrentMarker = marker;
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return true;
        }
        return false;
    }

    /**
     * 定位的回调
     */
    private class MyLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (mLocationListener != null && aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (mCurrentPosition == null) {
                        mCurrentPosition = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(mCurrentPosition, 10, 0, 0)));
                        getLocationImage();
                    } else {
                        mCurrentPosition = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                    }
                } else {
                    ToastUtils.showToast("定位失败");
                }
            }
        }
    }

    private void getLocationImage() {
        AVQuery<AVObject> locationQuery = new AVQuery<>("ImageList");
        locationQuery.whereGreaterThan("latitude", mCurrentPosition.latitude - 0.01);
        locationQuery.whereGreaterThan("longitude", mCurrentPosition.longitude - 0.01);
        locationQuery.whereLessThan("latitude", mCurrentPosition.latitude + 0.01);
        locationQuery.whereLessThan("longitude", mCurrentPosition.longitude + 0.01);
        locationQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject item : list) {
                    //
                    final MakerExtra extra = new MakerExtra();
                    extra.setDate(item.getString("createdAt"));
                    extra.setDescription(item.getString("description"));
                    extra.setUrl(item.getString("thumb"));
                    extra.setLatitude(item.getDouble("latitude"));
                    extra.setLongitude(item.getDouble("longitude"));
                    extra.setName(item.getString("name"));
                    //extra.setAuthor();
                    item.fetchInBackground("belong", new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            AVObject user = avObject.getAVObject("belong");
                            extra.setAuthor(user.getString("username"));
                            mMakersInfo.add(extra);
                        }
                    });
                    AVQuery<AVObject> listQuery = new AVQuery<AVObject>("Image");
                    listQuery.whereEqualTo("belong", item.getString("objectId"));
                    final List<String> itemImageList = new ArrayList<String>();
                    listQuery.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            for (AVObject itemImage : list) {
                                itemImageList.add(itemImage.getString("url"));
                            }
                            extra.setListUrl(itemImageList);
                        }
                    });
                }
            }
        });
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        //System.out.println(mMakersInfo.toString());
                        for (MakerExtra item : mMakersInfo) {
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(new LatLng(item.getLatitude(), item.getLongitude()))
                                    .title(item.getName())
                                    .snippet(item.getDescription());
                            mMap.addMarker(markerOptions).setObject(item);
                        }
                    }
                });
    }
}
