package com.shui.blacktea.ui.music;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentOnlineMusicBinding;
import com.shui.blacktea.entity.BaiduSong.SongBillboardEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.music.contract.OnlineMusicContract;
import com.shui.blacktea.ui.music.presenter.OnlineMusicPresenter;
import com.yeeyuntech.framework.ui.IYYPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class FragmentMusicOnline extends BaseFragment implements OnlineMusicContract.View, BaseQuickAdapter.OnItemClickListener {
    @Inject
    FragmentOnlineMusicBinding mBinding;
    @Inject
    OnlineMusicPresenter mPresenter;
    private OnlineMusicAdapter mAdapter;
    private List<SongBillboardEntity> mData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online_music;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View initBinding() {
        return mBinding.getRoot();
    }

    @Override
    public void initViews() {
        mAdapter = new OnlineMusicAdapter(mData);
        mBinding.rvLocalMusic.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvLocalMusic.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void getData() {
        mPresenter.getData();
    }

    @Override
    public void showData(List<SongBillboardEntity> list) {
        if (list != null) {
            mData.clear();
            mData.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(mActivity, OnlineMusicListActivity.class);
        intent.putExtra("cate", mData.get(position).getCate());
        startActivity(intent);
    }
}
