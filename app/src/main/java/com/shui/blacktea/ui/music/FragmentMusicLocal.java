package com.shui.blacktea.ui.music;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shui.blacktea.R;
import com.shui.blacktea.config.AppCache;
import com.shui.blacktea.databinding.FragmentLocalMusicBinding;
import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.music.imp.ILocalMusic;
import com.shui.blacktea.utils.ShowHideUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class FragmentMusicLocal extends BaseFragment implements ILocalMusic, BaseQuickAdapter.OnItemClickListener {
    @Inject
    FragmentLocalMusicBinding mBinding;
    private LocalMusicAdapter mLocalMusicAdapter;
    private List<MusicEntity> mMusics = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_local_music;
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
    public void initParams() {
        mLocalMusicAdapter = new LocalMusicAdapter(R.layout.item_music_local_list, mMusics);
        mBinding.rvLocalMusic.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.rvLocalMusic.setAdapter(mLocalMusicAdapter);
    }

    @Override
    public void initViews() {
        List<MusicEntity> list = AppCache.getInstance().getMusicList();
        System.out.println(list);
        System.out.println(list.toString());
        mMusics.addAll(list);
        updateView();
    }

    @Override
    public void initListeners() {
        mLocalMusicAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemPlay() {
        if (isAdded()) {
            updateView();
            if (getPlayService().getPlayingMusic().getType() == MusicEntity.TYPE_LOCAL) {
                mBinding.rvLocalMusic.smoothScrollToPosition(getPlayService().getPlayingPosition());
            }
        }
    }

    @Override
    public void onMusicListUpdate() {
        if (isAdded()) {
            updateView();
        }
    }

    @Override
    public void shareMusic(MusicEntity musicEntity) {
        File file = new File(musicEntity.getPath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, "分享"));
    }

    @Override
    public void updateView() {
        if (mMusics == null || mMusics.size() == 0) {
            ShowHideUtils.showHide(mBinding.rvLocalMusic, mBinding.tvEmpty, true);
        } else {
            ShowHideUtils.showHide(mBinding.rvLocalMusic, mBinding.tvEmpty, false);
        }
        mLocalMusicAdapter.updatePlayingPosition(getPlayService());
        mLocalMusicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        getPlayService().play(position);
    }
}
