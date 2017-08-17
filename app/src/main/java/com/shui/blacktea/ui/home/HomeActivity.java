package com.shui.blacktea.ui.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.shui.blacktea.R;
import com.shui.blacktea.config.AppCache;
import com.shui.blacktea.config.Constants;
import com.shui.blacktea.databinding.ActivityHomeBinding;
import com.shui.blacktea.databinding.NavHeaderHomeBinding;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.ui.chat.ChatFragment;
import com.shui.blacktea.ui.collection.CollectionFragment;
import com.shui.blacktea.ui.download.DownLoadFragment;
import com.shui.blacktea.ui.img.ImgFragment;
import com.shui.blacktea.ui.music.MusicFragment;
import com.shui.blacktea.ui.music.constants.Extras;
import com.shui.blacktea.ui.music.service.PlayService;
import com.shui.blacktea.ui.news.NewsFragment;
import com.shui.blacktea.ui.setting.SettingFragment;
import com.shui.blacktea.ui.user.UserFragment;
import com.shui.blacktea.ui.video.VideoFragment;
import com.shui.blacktea.utils.GlideImageLoader;
import com.shui.blacktea.utils.MusicLoaderUtils;
import com.shui.blacktea.utils.SharedPreferenceUtils;
import com.shui.blacktea.widget.CircleImageView;

import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportFragment;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Extras, View.OnClickListener {
    @Inject
    ActivityHomeBinding mBinding;
    private NewsFragment mNewsFragment;
    private VideoFragment mVideoFragment;
    private ImgFragment mImgFragment;
    private ChatFragment mChatFragment;
    private SettingFragment mSettingFragment;
    private CollectionFragment mCollectionFragment;
    private DownLoadFragment mDownLoadFragment;
    private MusicFragment mMusicFragment;
    private UserFragment mUserFragment;
    private MenuItem mLastMenuItem;
    private int mShowFragmentType = Constants.TYPE_NEWS;
    private int mHideFragmentType = Constants.TYPE_NEWS;
    private ServiceConnection mServiceConnection;
    private NavHeaderHomeBinding mHeaderBinding;
    private View mHeaderView;
    private CircleImageView mCiAvatar;
    private TextView mTVUserName;
    private AVUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)//第一次启动
        {

        } else {
            mShowFragmentType = SharedPreferenceUtils.getCurrentItem();
            mHideFragmentType = Constants.TYPE_NEWS;
            showHideFragment(getCurrentFragment(mShowFragmentType), getCurrentFragment(mHideFragmentType));
            mBinding.navView.getMenu().findItem(R.id.nav_news).setChecked(false);
            mHideFragmentType = mShowFragmentType;
        }
        /*List<MusicEntity> list = MusicLoaderUtils.scanMusic(this);
        AppCache.getInstance().setMusicList(list);*/
        checkPlayService();
        parseIntent();
    }

    @Override
    public void initParams() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(true);//单选多选
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
       // mUser = App.getUserInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    protected void initBinding() {
        super.initBinding();
        //mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.nav_header_home, null, false);
        //mHeaderBinding.setClick(this);
    }

    @Override
    public void initViews() {
        initFragments();
        mLastMenuItem = mBinding.navView.getMenu().findItem(R.id.nav_news);
        loadMultipleRootFragment(R.id.fl_container, 0, mNewsFragment, mVideoFragment, mImgFragment, mUserFragment,
                mChatFragment, mMusicFragment, mSettingFragment, mCollectionFragment, mDownLoadFragment);
        mBinding.navView.setNavigationItemSelectedListener(this);
        mBinding.navView.getMenu().findItem(R.id.nav_news).setChecked(true);
        mHeaderView = mBinding.navView.inflateHeaderView(R.layout.nav_header_home);
        mCiAvatar = (CircleImageView) mHeaderView.findViewById(R.id.nav_user_avatar);
        mTVUserName = (TextView) mHeaderView.findViewById(R.id.nav_user_name);
        mCiAvatar.setOnClickListener(this);
        /*if (mUser != null) {
            System.out.println(mUser.toString());
            System.out.println(mUser.getUsername());
            mTVUserName.setText(mUser.getUsername());
        }*/

    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void backAction() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.backAction();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_news:
                mShowFragmentType = Constants.TYPE_NEWS;
                break;
            case R.id.nav_video:
                mShowFragmentType = Constants.TYPE_VIDEO;
                break;
            case R.id.nav_chat:
                mShowFragmentType = Constants.TYPE_CHAT;
                break;
            case R.id.nav_collection:
                mShowFragmentType = Constants.TYPE_COLLECTION;
                break;
            case R.id.nav_download:
                mShowFragmentType = Constants.TYPE_DOWNLOAD;
                break;
            case R.id.nav_setting:
                mShowFragmentType = Constants.TYPE_SETTING;
                break;
            case R.id.nav_img:
                mShowFragmentType = Constants.TYPE_IMG;
                break;
            case R.id.nav_music:
                mShowFragmentType = Constants.TYPE_MUSIC;
                break;
        }
        if (mLastMenuItem != null) {
            mLastMenuItem.setChecked(false);
        }
        mLastMenuItem = item;
        SharedPreferenceUtils.setCurrentItem(mShowFragmentType);
        item.setChecked(true);
        showHideFragment(getCurrentFragment(mShowFragmentType), getCurrentFragment(mHideFragmentType));
        mHideFragmentType = mShowFragmentType;
        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        parseIntent();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTIFICATION)) {
            //showPlayingFragment();
            showHideFragment(mMusicFragment);
            mShowFragmentType = Constants.TYPE_MUSIC;
            mHideFragmentType = Constants.TYPE_MUSIC;
            setIntent(new Intent());
        }
    }

    private void initFragments() {
        mNewsFragment = new NewsFragment();
        mVideoFragment = new VideoFragment();
        mImgFragment = new ImgFragment();
        mChatFragment = new ChatFragment();
        mSettingFragment = new SettingFragment();
        mCollectionFragment = new CollectionFragment();
        mDownLoadFragment = new DownLoadFragment();
        mUserFragment = new UserFragment();
        mMusicFragment = new MusicFragment();
    }

    private SupportFragment getCurrentFragment(int current) {
        switch (current) {
            case Constants.TYPE_NEWS:
                return mNewsFragment;
            case Constants.TYPE_VIDEO:
                return mVideoFragment;
            case Constants.TYPE_IMG:
                return mImgFragment;
            case Constants.TYPE_CHAT:
                return mChatFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_COLLECTION:
                return mCollectionFragment;
            case Constants.TYPE_DOWNLOAD:
                return mDownLoadFragment;
            case Constants.TYPE_MUSIC:
                return mMusicFragment;
        }
        return mNewsFragment;
    }

    public boolean isDrawerOpen() {
        return mBinding.drawerLayout.isDrawerOpen(Gravity.START);
    }

    public void toggleDrawer() {
        if (!mBinding.drawerLayout.isDrawerOpen(R.id.drawer_layout)) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START, true);
        } else {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START, true);
        }
    }

    private void checkPlayService() {
        if (AppCache.getInstance().getPlayService() == null) {
            startService();
            /*Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                            bindService();
                        }
                    });*/
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 1000);
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mServiceConnection = new PlayServiceConnection();
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_user_avatar:
                System.out.println("click");
                boolean isLogin = SharedPreferenceUtils.isUserLogin();
                if (isLogin) {
                    //用户信息界面
                } else {
                    //登录界面
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private class PlayServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppCache.getInstance().setPlayService(playService);
            //PermissionReq.with(HomeActivity.this).permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).result(new PermissionResult() {
            // @Override
            // public void onGranted() {
            List<MusicEntity> list = MusicLoaderUtils.scanMusic(mActivity);
            AppCache.getInstance().setMusicList(list);
        }

        // @Override
        // public void onDenied() {
        //  ToastUtils.showToast(getString(R.string.no_permission, "读取储存", "扫描本地音乐"));
        //   playService.quit();
        //}
        //});

        //}

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConnection != null) {
            mActivity.unbindService(mServiceConnection);
        }
    }
}
