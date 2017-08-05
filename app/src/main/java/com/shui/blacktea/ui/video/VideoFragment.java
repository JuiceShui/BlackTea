package com.shui.blacktea.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shui.blacktea.R;
import com.shui.blacktea.data.API.VideoApi;
import com.shui.blacktea.databinding.FragmentVideoBinding;
import com.shui.blacktea.entity.VideoEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.home.HomeActivity;
import com.shui.blacktea.ui.video.contract.VideoContract;
import com.shui.blacktea.ui.video.presenter.VideoPresenter;
import com.shui.blacktea.utils.StatusBarUtils;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.yeeyuntech.framework.ui.IYYPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class VideoFragment extends BaseFragment implements VideoContract.View {
    @Inject
    FragmentVideoBinding mBinding;
    @Inject
    VideoPresenter mPresenter;
    private String mCurrentCate = VideoApi.VIDEO_ENTERTAINMENT_ID;
    private VideoAdapter mAdapter;
    private List<VideoEntity> mData = new ArrayList<>();
    private boolean pressedHome;
    private HomeKeyWatcher mHomeKeyWatcher;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
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
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {
        ((SupportActivity) mActivity).setSupportActionBar(mBinding.toolbar);
        setHasOptionsMenu(true);
        ((HomeActivity) mActivity).setToolbar(mBinding.toolbar);
        mBinding.toolbar.setTitle("视频");
        mAdapter = new VideoAdapter(R.layout.item_video, mData);
        mBinding.recycler.addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recycler.setAdapter(mAdapter);
        //mBinding.refreshLayout.autoRefresh();
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                mPresenter.getVideoList(mCurrentCate, false);
            }
        });
        mBinding.refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getVideoList(mCurrentCate, true);
            }
        });
        mBinding.recycler.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = mAdapter.getPlayer();
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
        //状态栏透明和间距处理
        StatusBarUtils.immersive(mActivity);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.toolbar);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.recycler);
        StatusBarUtils.setMargin(mActivity, mBinding.header);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.blurView);
    }

    @Override
    public void getData() {
        mPresenter.getVideoList(mCurrentCate, false);
    }

    @Override
    public void showVideoList(List<VideoEntity> list, boolean isLoadMore) {
        System.out.println(list.toString());
        if (!isLoadMore) {
            mData.clear();
            mBinding.refreshLayout.setLoadmoreFinished(false);
        }
        mData.addAll(list);
        //mAdapter.replaceData(list);
        mAdapter.notifyDataSetChanged();
        if (mBinding.refreshLayout.isRefreshing()) {
            mBinding.refreshLayout.finishRefresh();
        }
        if (mBinding.refreshLayout.isLoading()) {
            mBinding.refreshLayout.finishLoadmore();
        }
        if (list.size() < VideoApi.PAGE_SIZE || list == null) {
            showToast("数据全部加载完毕");
            mBinding.refreshLayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeKeyWatcher = new HomeKeyWatcher(getActivity());
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressedHome = true;
            }
        });
        pressedHome = false;
        mHomeKeyWatcher.startWatch();
    }

    @Override
    public void onStart() {
        mHomeKeyWatcher.startWatch();
        pressedHome = false;
        super.onStart();
        NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (pressedHome) {
            NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
        } else {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }
        super.onStop();
        mHomeKeyWatcher.stopWatch();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}
