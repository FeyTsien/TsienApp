package com.hdnz.inanming.mobShare;

import java.util.Arrays;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    MobShareParm
 * Author:      肖昕
 * Date:        2018-10-9 11:09
 * Description:
 * Version:     v1.0
 * History:     历史信息
 */
public class MobShareParm {
    /**
     * @field   分享地址
     */
    private String address;
    /**
     * @field   分享标题
     */
    private String title;
    /**
     * @field   分享标题地址
     */
    private String titleUrl;
    /**
     * @field   分享文本类容
     */
    private String text;
    /**
     * @field   分享图片，本地SDCard路径
     */
    private String imagePath;
    /**
     * @field   分享图片，网络地址
     */
    private String imageUrl;
    /**
     * @field   url仅在微信（包括好友和朋友圈）中使用
     */
    private String url;
    /**
     * @field   分享本地视频，微信：分享应用apk文件及其他文件
     */
    private String filePath;
    /**
     * @field   评论类容，在人人网、Linked-in、KakaoStory用
     */
    private String comment;
    /**
     * @field   分享此内容的网站名称，仅在QQ空间使用
     */
    private String site;
    /**
     * @field   分享此内容的网站地址，仅在QQ空间使用
     */
    private String siteUrl;
    /**
     * @field   发生地点的名称，在FourSquare用到
     */
    private String venueName;
    /**
     * @field   类容描述，在FourSquare用到
     */
    private String venueDescription;
    /**
     * @field   纬度，可选字段
     */
    private Float latitude;
    /**
     * @field   经度，可选字段
     */
    private Float longitude;
    /**
     * @field   分享平台名称，可选字段
     */
    private String platform;
    /**
     * @field   分享类型，比如：文本、图文、链接、音频、视频等
     */
    private Integer shareType;
    /**
     * @field   分享音乐
     */
    private String musicUrl;
    /**
     * @field   分享视频
     */
    private String videoUrl;
    /**
     * @field   分享多图
     */
    private String[] imageArray;
    /**
     * @field   分享小程序页面路径
     */
    private String wxPath;
    /**
     * @field   分享小程序原始ID
     */
    private String wxUserName;
    /**
     * @field   应用信息脚本，只用在微信分享应用的时候
     */
    private String extInfo;
    /**
     *  @field  有道云笔记
     */
    private String notebook;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getWxPath() {
        return wxPath;
    }

    public void setWxPath(String wxPath) {
        this.wxPath = wxPath;
    }

    public String getWxUserName() {
        return wxUserName;
    }

    public void setWxUserName(String wxUserName) {
        this.wxUserName = wxUserName;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    @Override
    public String toString() {
        return "MobShareParm{" +
                "address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", text='" + text + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                ", comment='" + comment + '\'' +
                ", site='" + site + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", venueName='" + venueName + '\'' +
                ", venueDescription='" + venueDescription + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", platform='" + platform + '\'' +
                ", shareType='" + shareType + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", imageArray=" + Arrays.toString(imageArray) +
                ", wxPath='" + wxPath + '\'' +
                ", wxUserName='" + wxUserName + '\'' +
                ", extInfo='" + extInfo + '\'' +
                '}';
    }
}
