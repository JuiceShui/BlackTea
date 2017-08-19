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

       /*
       //创建用户
        for (int i = 0; i < 9; i++) {
            AVUser testObject = new AVUser();
            testObject.setUsername("zed" + i * i);
            testObject.setPassword("yasuo" + i * i);
            testObject.setMobilePhoneNumber("184082439" + i + i);
            testObject.setEmail("shuicz00" + i + i + "@qq.com");
            testObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Log.d("saved", "success!");
                    } else {
                        System.out.println(e);
                    }
                }
            });
        }*/
        /*
       //创建好友关系
        final AVQuery<AVUser> userQuery = new AVQuery<>("_User");
        Observable.create(new ObservableOnSubscribe<List<AVUser>>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AVUser>> e) throws Exception {
                e.onNext(userQuery.find());
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new Consumer<List<AVUser>>() {
                    @Override
                    public void accept(@NonNull List<AVUser> users) throws Exception {
                        for (int i = 0; i < users.size(); i++) {
                            for (int j = 0; j < users.size(); j++) {
                                AVObject connect = new AVObject("Connect");
                                connect.put("home", users.get(i));
                                connect.put("host", users.get(j));
                                connect.save();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                    }
                });*/
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
