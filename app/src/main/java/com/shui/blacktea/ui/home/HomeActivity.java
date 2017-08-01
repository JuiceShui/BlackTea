package com.shui.blacktea.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityHomeBinding;
import com.shui.blacktea.databinding.AppBarHomeBinding;
import com.shui.blacktea.databinding.ContentHomeBinding;
import com.shui.blacktea.databinding.NavHeaderHomeBinding;
import com.shui.blacktea.ui.BaseActivity;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Inject
    ActivityHomeBinding mBinding;
    private AppBarHomeBinding mAppbarHomeBinding;
    private NavHeaderHomeBinding mNavHeaderBinding;
    private ContentHomeBinding mContentHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
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
        mAppbarHomeBinding = DataBindingUtil.inflate(mInflater, R.layout.app_bar_home, null, false);
        mContentHomeBinding = DataBindingUtil.inflate(mInflater, R.layout.content_home, null, false);
        mNavHeaderBinding = DataBindingUtil.inflate(mInflater, R.layout.nav_header_home, null, false);
        setToolBar(mAppbarHomeBinding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, mAppbarHomeBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mBinding.navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void backAction() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.backAction();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
