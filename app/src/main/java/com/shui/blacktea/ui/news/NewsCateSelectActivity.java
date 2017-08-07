package com.shui.blacktea.ui.news;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityNewsSelectCateBinding;
import com.shui.blacktea.entity.NewsCateEntity;
import com.shui.blacktea.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class NewsCateSelectActivity extends BaseActivity {
    private NewsCateAdapter mAdapter;
    private List<NewsCateEntity> mData = new ArrayList<>();
    @Inject
    ActivityNewsSelectCateBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_select_cate;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    protected void initBinding() {
        super.initBinding();
    }

    @Override
    public void initViews() {
        mBinding.recyclerCate.setLayoutManager(new LinearLayoutManager(mActivity));
        initData();
        mAdapter = new NewsCateAdapter(R.layout.item_news_cate, mData);
        mBinding.recyclerCate.setAdapter(mAdapter);
        setToolBar(mBinding.toolbar, "选择列别");
        mBinding.toolbar.setTitleTextColor(Color.WHITE);
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            NewsCateEntity entity = new NewsCateEntity();
            entity.setName("贾克斯");
            entity.setSelect(false);
            entity.setType(1);
            entity.setIcon(R.drawable.ic_fab_pet);
            mData.add(entity);
        }
    }
}
