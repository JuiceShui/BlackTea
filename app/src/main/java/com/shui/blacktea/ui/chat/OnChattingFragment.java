package com.shui.blacktea.ui.chat;

import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentOnChattingBinding;
import com.shui.blacktea.ui.BaseFragment;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/19.
 */

public class OnChattingFragment extends BaseFragment {
    @Inject
    FragmentOnChattingBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_on_chatting;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    protected View initBinding() {
        return mBinding.getRoot();
    }

    @Override
    public void getData() {
        //List convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();

    }
}
