package com.shui.blacktea.ui.news;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shui.blacktea.R;
import com.shui.blacktea.data.API.TXApi;
import com.shui.blacktea.databinding.FragmentNewsBinding;
import com.shui.blacktea.entity.NewsEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.news.contract.NewsContract;
import com.shui.blacktea.ui.news.presenter.NewsPresenter;
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

public class NewsFragment extends BaseFragment implements NewsContract.View {
    @Inject
    FragmentNewsBinding mBinding;
    @Inject
    NewsPresenter mPresenter;
    private NewsAdapter mAdapter;
    private List<NewsEntity> mData = new ArrayList<>();
    private List<NewsEntity> mHeaderList = new ArrayList<>();
    private boolean isFirstOrRefresh = true;
    private Banner mBanner;
    private String mCate = TXApi.SOCIAL;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
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
                mPresenter.getNewsList(mCate, false);
            }
        });
        mBinding.refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isFirstOrRefresh = false;
                mPresenter.getNewsList(mCate, true);
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
    }

    @Override
    public void getData() {
        mPresenter.getNewsList(mCate, false);
    }

    @Override
    public void showNewsResult(List<NewsEntity> list, boolean isLoadMore) {
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
        if (list.size() < TXApi.PAGE_SIZE || list == null) {
            showToast("数据全部加载完毕");
            mBinding.refreshLayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
        }
        if (isFirstOrRefresh && mData.size() > 5) {
            int headerNumber = RandomNumberUtil.getRandom(2, 5);
            for (int i = 0; i < headerNumber; i++) {
                mHeaderList.add(mData.get(i + 2));
            }
            isFirstOrRefresh = false;
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.setImages(mHeaderList);
            mBanner.start();
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(((NewsEntity) path).getPicUrl()).into(imageView);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
