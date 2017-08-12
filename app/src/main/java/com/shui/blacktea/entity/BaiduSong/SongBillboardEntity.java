package com.shui.blacktea.entity.BaiduSong;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Description:  歌曲集合单
 * Created by Juice_ on 2017/8/10.
 */

public class SongBillboardEntity implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_BILLBOARD = 2;
    private int type;
    private String cover;
    private int cate;
    private String topOne;
    private String topTwo;
    private String topThree;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTopOne() {
        return topOne;
    }

    public void setTopOne(String topOne) {
        this.topOne = topOne;
    }

    public String getTopTwo() {
        return topTwo;
    }

    public void setTopTwo(String topTwo) {
        this.topTwo = topTwo;
    }

    public String getTopThree() {
        return topThree;
    }

    public void setTopThree(String topThree) {
        this.topThree = topThree;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
