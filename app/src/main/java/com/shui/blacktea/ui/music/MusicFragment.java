package com.shui.blacktea.ui.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.common.FragmentAdapter;
import com.shui.blacktea.config.AppCache;
import com.shui.blacktea.databinding.FragmentMusicBinding;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.home.HomeActivity;
import com.shui.blacktea.ui.music.constants.Extras;
import com.shui.blacktea.ui.music.service.EarPhoneControlReceiver;
import com.shui.blacktea.ui.music.service.OnPlayEventListener;
import com.shui.blacktea.ui.music.service.PlayService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class MusicFragment extends BaseFragment implements View.OnClickListener, OnPlayEventListener, Extras {
    @Inject
    public FragmentMusicBinding mBinding;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentPlayingMusic mFragmentPlaying;
    private boolean mIsPlayFragmentShowing = false;
    private AudioManager mAudioManager;
    private ComponentName mRemoteReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music;
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
    public void initParams() {
        mAudioManager = (AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
        mRemoteReceiver = new ComponentName(mActivity.getPackageName(), EarPhoneControlReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(mRemoteReceiver);
    }

    @Override
    public void initViews() {
        mFragmentList.add(new FragmentMusicLocal());
        mFragmentList.add(new FragmentMusicOnline());
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragmentList);
        mBinding.tvLocalMusic.setSelected(true);
        mBinding.viewpager.setAdapter(mFragmentAdapter);
        mBinding.viewpager.addOnPageChangeListener(new pageChangeListener());
        mBinding.viewpager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //菜单栏
            case R.id.iv_menu:
                ((HomeActivity) mActivity).toggleDrawer();
                break;
            //本地音乐
            case R.id.tv_local_music:
                mBinding.tvLocalMusic.setSelected(true);
                mBinding.tvOnlineMusic.setSelected(false);
                mBinding.viewpager.setCurrentItem(0, true);
                break;
            //在校音乐
            case R.id.tv_online_music:
                mBinding.tvLocalMusic.setSelected(false);
                mBinding.tvOnlineMusic.setSelected(true);
                mBinding.viewpager.setCurrentItem(1, true);
                break;
            //底部菜单容器
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            //底部播放
            case R.id.iv_play_bar_play:
                play();
                break;
            //底部下一曲
            case R.id.iv_play_bar_next:
                next();
                break;
            case R.id.iv_search:
                //TODO  搜索
                break;
        }
    }

    @Override
    public void onPublish(int progress) {
        mBinding.pbPlayBar.setProgress(progress);
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onPublish(progress);
        }
    }

    @Override
    public void onPlayerPause() {
        mBinding.ivPlayBarPlay.setSelected(false);
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onPlayerPause();
        }
    }

    @Override
    public void onPlayerResume() {
        mBinding.ivPlayBarPlay.setSelected(true);
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onPlayerResume();
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onBufferingUpdate(percent);
        }
    }

    @Override
    public void onChange(MusicEntity musicEntity) {
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onChange(musicEntity);
        }
    }

    @Override
    public void onTimer(int remain) {
        /*if (mFragmentPlaying != null) {
            mFragmentPlaying.onTimer(remain);
        }*/
    }

    @Override
    public void onMusicListUpdate() {
        if (mFragmentPlaying != null) {
            mFragmentPlaying.onMusicListUpdate();
        }
    }

    //返回按键
    @Override
    public boolean onBackPressedSupport() {
        if (mFragmentPlaying != null && mIsPlayFragmentShowing) {
            hidePlayingFragment();
            return true;
        }
        if (((HomeActivity) getActivity()).isDrawerOpen()) {
            ((HomeActivity) getActivity()).toggleDrawer();
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        parseIntent();
    }

    private void play() {
        getPlayService().playPause();
    }

    private void next() {
        getPlayService().next();
    }

    private class pageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mBinding.tvLocalMusic.setSelected(true);
                mBinding.tvOnlineMusic.setSelected(false);
            } else {
                mBinding.tvLocalMusic.setSelected(false);
                mBinding.tvOnlineMusic.setSelected(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //show 播放界面
    private void showPlayingFragment() {
        if (mIsPlayFragmentShowing) {
            return;
        }
        mBinding.flFragment.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mFragmentPlaying == null) {
            mFragmentPlaying = new FragmentPlayingMusic();
            fragmentTransaction.replace(R.id.fl_fragment, mFragmentPlaying);
            fragmentTransaction.show(mFragmentPlaying);
        } else {
            fragmentTransaction.show(mFragmentPlaying);
        }
        fragmentTransaction.commitAllowingStateLoss();
        mIsPlayFragmentShowing = true;
    }

    //hide 播放界面
    private void hidePlayingFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(0, R.anim.fragment_slide_down);
        fragmentTransaction.hide(mFragmentPlaying);
        fragmentTransaction.commitAllowingStateLoss();
        //mBinding.flFragment.setVisibility(View.GONE);
        mIsPlayFragmentShowing = false;
    }

    //获取PlayService
    private PlayService getPlayService() {
        return AppCache.getInstance().getPlayService();
    }

    //处理intent
    private void parseIntent() {
        Intent intent = mActivity.getIntent();
        if (intent.hasExtra(EXTRA_NOTIFICATION)) {
            showPlayingFragment();
            mActivity.setIntent(new Intent());
        }
    }
}
