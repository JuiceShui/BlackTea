package com.shui.blacktea.ui.chat.child;

import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentTestBinding;
import com.shui.blacktea.ui.BaseFragment;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class DiscoverFragment extends BaseFragment {
    @Inject
    FragmentTestBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
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
        mBinding.tvName.setText(getClass().getSimpleName());
    }
}
