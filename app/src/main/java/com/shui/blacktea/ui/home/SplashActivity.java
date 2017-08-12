package com.shui.blacktea.ui.home;

import android.content.Intent;

import com.bumptech.glide.Glide;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivitySplashBinding;
import com.shui.blacktea.entity.SplashImgEntity;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.ui.home.contract.SplashContract;
import com.shui.blacktea.ui.home.presenter.SplashPresenter;
import com.shui.blacktea.utils.SHA1;
import com.yeeyuntech.framework.ui.IYYPresenter;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/9.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject
    SplashPresenter mPresenter;
    @Inject
    ActivitySplashBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void getData() {
        mPresenter.getSplashImg();
    }

    @Override
    public void showSplashImg(SplashImgEntity entity) {
        SHA1.getSHA1(mActivity);
        Glide.with(this).load(entity.getShowapi_res_body().getData().getImg_1366()).into(mBinding.ivSplash);
        mBinding.tvDes.setText(entity.getShowapi_res_body().getData().getDescription());
        mBinding.tvSubtitle.setText(entity.getShowapi_res_body().getData().getSubtitle());
        mBinding.tvTitle.setText(entity.getShowapi_res_body().getData().getTitle());

    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
