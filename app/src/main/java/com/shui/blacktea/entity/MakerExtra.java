package com.shui.blacktea.entity;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/17.
 */

public class MakerExtra {
    private String date;
    private String author;
    private String description;
    private String url;
    private List<String> listUrl;
    private double latitude;
    private double longitude;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getListUrl() {
        return listUrl;
    }

    public void setListUrl(List<String> listUrl) {
        this.listUrl = listUrl;
    }

    @Override
    public String toString() {
        return "MakerExtra{" +
                "date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", listUrl='" + listUrl + '\'' +
                '}';
    }
}
