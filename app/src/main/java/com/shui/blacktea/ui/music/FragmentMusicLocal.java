package com.shui.blacktea.ui.music;

import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentLocalMusicBinding;
import com.shui.blacktea.ui.BaseFragment;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class FragmentMusicLocal extends BaseFragment {
    @Inject
    FragmentLocalMusicBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected View initBinding() {
        return mBinding.getRoot();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    public void initViews() {
        mBinding.rvLocalMusic.setVisibility(View.GONE);
        mBinding.tvEmpty.setVisibility(View.VISIBLE);
    }
}
