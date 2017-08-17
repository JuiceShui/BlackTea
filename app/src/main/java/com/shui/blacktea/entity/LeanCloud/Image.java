package com.shui.blacktea.entity.LeanCloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/17.
 */
@AVClassName("Image")
public class Image extends AVObject {
    public Image() {
        super();
    }

    List likedUsers = new ArrayList();
    List images = new ArrayList();

    public AVUser getPublisher() {
        return (AVUser) super.getAVUser("publisher");
    }

    public void setPublisher(AVUser user) {
        super.put("publisher", user);
    }

    public String getCaption() {
        return getString("caption");
    }

    public void setCaption(String caption) {
        put("caption", caption);
    }

    public String getTakenAt() {
        return super.getCreatedAt().toString();
    }

    public AVFile getRawImage() {
        return super.getAVFile("imageFile");
    }

    public void setRawImage(AVFile file) {
        super.put("imageFile", file);
    }

    @SuppressWarnings("unchecked")
    public List getComments() {
        return (List) getList("comments");
    }

    public void addComment(Comment com) {
        addUnique("comments", com);
    }

    public AVRelation getLiker() {
        AVRelation relation = getRelation("likes");
        return relation;
    }

    public void removeLiker(AVUser user) {
        AVRelation users = getLiker();
        users.remove(user);
        this.saveInBackground();
    }

    public void addLiker(AVUser user) {
        AVRelation users = getLiker();
        users.add(user);
        this.saveInBackground();
    }

    public void setLikedUsers(List usr) {
        if (null == usr) return;
        this.likedUsers = usr;
    }

    public List getLikedUsers() {
        return this.likedUsers;
    }

    public int getLikerCount() {
        return this.likedUsers.size();
    }

    public void setImages(List usr) {
        if (null == usr) return;
        this.images = usr;
    }

    public List getImages() {
        return this.images;
    }

    public String getImage() {
        return getString("image");
    }

    public void setImage(String image) {
        put("image", image);
    }
}
