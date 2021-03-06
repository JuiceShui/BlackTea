package com.shui.blacktea.ui.news;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shui.blacktea.R;
import com.shui.blacktea.config.Constants;
import com.shui.blacktea.data.API.YYApi;
import com.shui.blacktea.databinding.FragmentNewsBinding;
import com.shui.blacktea.entity.WeiBoEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.home.HomeActivity;
import com.shui.blacktea.ui.news.contract.NewsContract;
import com.shui.blacktea.ui.news.presenter.NewsPresenter;
import com.shui.blacktea.utils.CircularAnim;
import com.shui.blacktea.utils.RandomNumberUtil;
import com.shui.blacktea.utils.StatusBarUtils;
import com.yeeyuntech.framework.ui.IYYPresenter;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View, View.OnClickListener {
    @Inject
    FragmentNewsBinding mBinding;
    @Inject
    NewsPresenter mPresenter;
    private NewsAdapter mAdapter;
    private List<WeiBoEntity> mData = new ArrayList<>();
    private List<WeiBoEntity> mHeaderList = new ArrayList<>();
    private List<String> mHeaderTitleList = new ArrayList<>();
    private boolean isFirstOrRefresh = true;
    private Banner mBanner;
    private int mLastType = YYApi.TYPE_WEIBO_ENTERTAINMENT;
    private int mCurrentType = YYApi.TYPE_WEIBO_ENTERTAINMENT;
    private boolean isOverLayVisible = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
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
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {
        ((SupportActivity) mActivity).setSupportActionBar(mBinding.toolbar);
        setHasOptionsMenu(true);
        ((HomeActivity) mActivity).setToolbar(mBinding.toolbar);
        mBinding.toolbar.setTitle("新闻");
        mAdapter = new NewsAdapter(R.layout.item_news, mData);
        mBinding.recycler.addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recycler.setAdapter(mAdapter);
        //mBinding.refreshLayout.autoRefresh();
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                isFirstOrRefresh = true;
                mHeaderList.clear();
                mPresenter.getNewsList(mCurrentType, false);
            }
        });
        mBinding.refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isFirstOrRefresh = false;
                mPresenter.getNewsList(mCurrentType, true);
            }
        });
        View header = LayoutInflater.from(mActivity).inflate(R.layout.item_news_header, mBinding.recycler, false);
        mBanner = (Banner) header;
        mAdapter.addHeaderView(mBanner);

        //状态栏透明和间距处理
        StatusBarUtils.immersive(mActivity);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.toolbar);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.recycler);
        StatusBarUtils.setMargin(mActivity, mBinding.header);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.blurView);
        mBinding.recycler.addOnScrollListener(new RecyclerViewScrollDetector() {

            @Override
            void onScrollUp() {
                //System.out.println("onScrollUp");
                mBinding.fabMenu.animate().translationY(mBinding.fabMenu.getHeight()).setDuration(200);
                mBinding.fabMenu.collapse();
            }

            @Override
            void onScrollDown() {
                mBinding.fabMenu.animate().translationY(0).setDuration(200);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WeiBoEntity bean = mData.get(position);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("pic", bean.getImg());
                intent.putExtra("title", bean.getName());
                intent.putExtra("link", bean.getUrl());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.news_iv_thumb), Constants.TRANSITION_NAME);
                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public void getData() {
        mPresenter.getNewsList(mCurrentType, false);
    }

    @Override
    public void showNewsResult(List<WeiBoEntity> list, boolean isLoadMore) {
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
        if (list.size() < YYApi.PAGE_SIZE || list == null) {
            showToast("数据全部加载完毕");
            mBinding.refreshLayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
        }
        if (isFirstOrRefresh && mData.size() > 5) {
            int headerNumber = RandomNumberUtil.getRandom(2, 5);
            mHeaderList.clear();
            mHeaderTitleList.clear();
            for (int i = 0; i < headerNumber; i++) {
                mHeaderList.add(mData.get(i + 2));
                mHeaderTitleList.add(mData.get(i + 2).getDesc());
            }
            isFirstOrRefresh = false;
            mBanner.setBannerTitles(mHeaderTitleList);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.setImages(mHeaderList);
            mBanner.start();
        }
        //展开动画
        if (!isLoadMore && isOverLayVisible) {
            CircularAnim.hide(mBinding.llOverLay).triggerView(mBinding.fabFun).go(new CircularAnim.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    if (mBinding.fabMenu.isExpanded()) {
                        mBinding.fabMenu.collapse();
                    }
                    isOverLayVisible = false;
                }
            });
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.fab_entertainment:
                mCurrentType = YYApi.TYPE_WEIBO_ENTERTAINMENT;
                break;
            case R.id.fab_fun:
                mCurrentType = YYApi.TYPE_WEIBO_FUN;
                break;
            case R.id.fab_pet:
                mCurrentType = YYApi.TYPE_WEIBO_PET;
                break;
            case R.id.fab_sport:
                mCurrentType = YYApi.TYPE_WEIBO_SPORT;
                break;
        }
        fabClickAnim(v);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(((WeiBoEntity) path).getImg()).into(imageView);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
        private int mScrollThreshold = 5;

        abstract void onScrollUp();

        abstract void onScrollDown();

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
            if (isSignificantDelta) {
                if (dy > 0) {
                    onScrollUp();
                } else {
                    onScrollDown();
                }
            }
        }
    }

    private void fabClickAnim(final View v) {
        if (v.getId() != R.id.fab_more) {
            CircularAnim.show(mBinding.llOverLay).triggerView(mBinding.getRoot().findViewById(v.getId())).go(new CircularAnim.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    isOverLayVisible = true;
                    //开始请求
                    if (!(mLastType == mCurrentType)) {
                        mLastType = mCurrentType;
                        mPresenter.getNewsList(mCurrentType, false);
                    } else {
                        mBinding.recycler.scrollToPosition(0);
                        CircularAnim.hide(mBinding.llOverLay).triggerView(mBinding.getRoot().findViewById(v.getId())).go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                if (mBinding.fabMenu.isExpanded()) {
                                    mBinding.fabMenu.collapse();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            CircularAnim.fullActivity(mActivity, mBinding.getRoot().findViewById(v.getId()))
                    .colorOrImageRes(R.color.colorPrimary)
                    .go(new CircularAnim.OnAnimationEndListener() {
                        @Override
                        public void onAnimationEnd() {
                            startActivity(new Intent(mActivity, NewsCateSelectActivity.class));
                        }
                    });
        }
    }
}
