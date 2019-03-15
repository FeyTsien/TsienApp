package com.hdnz.inanming.bean;

/**
 * Copyright (C), 2017-2019, 华电南自（贵州）科技有限公司
 * FileName:    MobSharePlatformBean.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2019-01-10 15:00
 * Description:
 * Version:     V1.0.0
 * History:     历史信息
 */
public class MobSharePlatformBean {
    /**
     *  @field  平台名字
     */
    private String platformName;
    /**
     *  @field  平台图标
     */
    private int platformIcon;

    public MobSharePlatformBean() {
    }

    public MobSharePlatformBean(String platformName, int platformIcon) {
        this.platformName = platformName;
        this.platformIcon = platformIcon;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public int getPlatformIcon() {
        return platformIcon;
    }

    public void setPlatformIcon(int platformIcon) {
        this.platformIcon = platformIcon;
    }

    @Override
    public String toString() {
        return "MobSharePlatformBean{" +
                "platformName='" + platformName + '\'' +
                ", platformIcon=" + platformIcon +
                '}';
    }
}