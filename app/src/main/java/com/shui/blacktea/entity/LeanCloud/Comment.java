package com.shui.blacktea.entity.LeanCloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Description:
 * Created by Juice_ on 2017/8/17.
 */

@AVClassName("Comment")
public class Comment extends AVObject {
    public Comment() {
        super();
    }
    public String getContent() {
        return getString("content");
    }
    public void setContent(String value) {
        put("content", value);
    }
    public void setCreator(AVUser user) {
        put("creator", user);
    }
    public AVUser getCreator() {
        return getAVUser("creator");
    }
}
