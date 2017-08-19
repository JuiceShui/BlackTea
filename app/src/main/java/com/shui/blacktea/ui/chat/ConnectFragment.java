package com.shui.blacktea.ui.chat;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentContractBinding;
import com.shui.blacktea.entity.UserEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.chat.contract.ConnectContract;
import com.shui.blacktea.ui.chat.presenter.ConnectPresenter;
import com.yeeyuntech.framework.ui.IYYPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/19.
 */

public class ConnectFragment extends BaseFragment implements ConnectContract.View, BaseQuickAdapter.OnItemClickListener {
    @Inject
    FragmentContractBinding mBinding;
    @Inject
    ConnectPresenter mPresenter;
    private List<UserEntity> mConnects = new ArrayList<>();
    private ConnectAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_contract;
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
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {
        mAdapter = new ConnectAdapter(R.layout.item_chat_connect, mConnects);
        mBinding.rvContract.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvContract.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void getData() {
        mPresenter.getUserLists();
    }

    @Override
    public void showUserLists(List<UserEntity> list) {
        System.out.println("UserEntity" + list.toString());
        if (list != null) {
            mConnects.clear();
            mConnects.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        Intent intent = new Intent(mActivity, OnChattingActivity.class);
        intent.putExtra("target", mConnects.get(position).getUserName());
        intent.putExtra("objectId", mConnects.get(position).getObjectId());
        startActivity(intent);
    }
}
