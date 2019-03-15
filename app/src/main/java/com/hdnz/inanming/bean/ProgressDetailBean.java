package com.hdnz.inanming.bean;

import java.util.List;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    ProgressDetailBean.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-21 15:19
 * Description: 审核进度详情
 * Version:     V1.0.0
 * History:     历史信息
 */
public class ProgressDetailBean {

    /**
     * data : [{"date":"11/12","time":"14:24","check":false,"status":"提交申请","description":"贵州省老年优待证。"},{"date":"11/13","time":"14:25","check":false,"status":"上门服务","description":"等待网格员上门。/ 网格员XX已上门。"},{"date":"11/14","time":"14:26","check":false,"status":"等待审核","description":"已提交到社区，请耐心等待。"},{"date":"11/15","time":"14:27","check":true,"status":"办理完成","description":"恭喜您办理成功。请于2017年11月16日下午2:00至沙南社区领取《居住证证明》文件。"}]
     * code : 200
     * message : sucess
     * status : true
     */

    private int code;
    private String message;
    private boolean status;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date : 11/12
         * time : 14:24
         * check : false
         * status : 提交申请
         * description : 贵州省老年优待证。
         */

        private String date;
        private String time;
        private boolean check;
        private String status;
        private String description;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
