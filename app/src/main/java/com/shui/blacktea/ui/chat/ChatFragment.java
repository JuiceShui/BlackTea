package com.shui.blacktea.ui.chat;

import android.support.v4.app.Fragment;
import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.common.FragmentAdapter;
import com.shui.blacktea.databinding.FragmentChatBinding;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.chat.child.ChattingFragment;
import com.shui.blacktea.ui.chat.child.ContractFragment;
import com.shui.blacktea.ui.chat.child.DiscoverFragment;
import com.shui.blacktea.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class ChatFragment extends BaseFragment {
    @Inject
    FragmentChatBinding mBinding;
    private FragmentAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = new String[]{"会话", "朋友", "发现"};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected View initBinding() {
        return mBinding.getRoot();
    }

    @Override
    public void initParams() {
        mFragments.add(new ChattingFragment());
        mFragments.add(new ContractFragment());
        mFragments.add(new DiscoverFragment());
        mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    public void initViews() {
        StatusBarUtils.immersive(mActivity);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.tlChat);
        mBinding.vpChat.setAdapter(mAdapter);
        mBinding.tlChat.addTab(mBinding.tlChat.newTab().setText(mTitles[0]));
        mBinding.tlChat.addTab(mBinding.tlChat.newTab().setText(mTitles[1]));
        mBinding.tlChat.addTab(mBinding.tlChat.newTab().setText(mTitles[2]));
        mBinding.tlChat.getTabAt(0).setText(mTitles[0]);
        mBinding.tlChat.getTabAt(1).setText(mTitles[1]);
        mBinding.tlChat.getTabAt(2).setText(mTitles[2]);
        mBinding.tlChat.setupWithViewPager(mBinding.vpChat);

    }
}
