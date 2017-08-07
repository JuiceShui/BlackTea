package com.shui.blacktea.ui.news;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityNewsDetialBinding;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.utils.StatusBarUtils;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/7.
 */

public class NewsDetailActivity extends BaseActivity {
    private String mLink;
    private String mPic;
    private String mTitle;
    @Inject
    ActivityNewsDetialBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detial;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    public void initParams() {
        mLink = getIntent().getStringExtra("link");
        mPic = getIntent().getStringExtra("pic");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    public void initViews() {
        setToolBar(mBinding.toolbar, mTitle);
        StatusBarUtils.immersive(this);
        StatusBarUtils.setPaddingSmart(this, mBinding.toolbar);
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(mLink);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
