package com.shui.blacktea.entity;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class NewsEntity {

    /**
     * ctime : 2016-12-04 13:00
     * title : 格林：理解科尔吸食大麻 不过我从没吸过
     * description : NBA新闻
     * picUrl : http://www.51tyw.com/uploads/allimg/161204/1-161204120131.jpg
     * url : http://www.51tyw.com/nba/2421.html
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
