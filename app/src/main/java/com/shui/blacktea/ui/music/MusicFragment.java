package com.shui.blacktea.ui.music;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.common.FragmentAdapter;
import com.shui.blacktea.databinding.FragmentMusicBinding;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class MusicFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    public FragmentMusicBinding mBinding;
    private FragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

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
            case R.id.iv_menu:
                ((HomeActivity) mActivity).toggleDrawer();
                break;
            case R.id.tv_local_music:
                mBinding.tvLocalMusic.setSelected(true);
                mBinding.tvOnlineMusic.setSelected(false);
                mBinding.viewpager.setCurrentItem(0, true);
                break;
            case R.id.tv_online_music:
                mBinding.tvLocalMusic.setSelected(false);
                mBinding.tvOnlineMusic.setSelected(true);
                mBinding.viewpager.setCurrentItem(1, true);
                break;
        }
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
}
