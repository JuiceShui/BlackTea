package com.shui.blacktea.ui.music;

import android.view.View;
import android.widget.LinearLayout;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentMusicPlayingBinding;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.music.service.OnPlayEventListener;
import com.yeeyuntech.framework.utils.DisplayUtils;

import javax.inject.Inject;

/**
 * Description:正在播放 音乐界面
 * Created by Juice_ on 2017/8/8.
 */

public class FragmentPlayingMusic extends BaseFragment implements OnPlayEventListener, View.OnClickListener {
    @Inject
    FragmentMusicPlayingBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_playing;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    protected View initBinding() {
        mBinding.setClick(this);
        return mBinding.getRoot();
    }

    @Override
    public void initParams() {
        //处理状态栏的高度问题   ???????   因为设置fitystemWindowsS无效   ？？？？？？？
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBinding.llHeader.getLayoutParams();
        params.topMargin = DisplayUtils.getStatusBarHeight(mActivity);
        mBinding.llHeader.setLayoutParams(params);
        //----------------
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPlayerResume() {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onChange(MusicEntity musicEntity) {

    }

    @Override
    public void onTimer(int remain) {

    }

    @Override
    public void onMusicListUpdate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                ((MusicFragment) getParentFragment()).onBackPressedSupport();
                break;
            case R.id.iv_mode://列表模式
                break;
            case R.id.iv_prev://上一曲
                break;
            case R.id.iv_play://播放，暂停
                break;
            case R.id.iv_next://下一曲
                break;
        }
    }
}
