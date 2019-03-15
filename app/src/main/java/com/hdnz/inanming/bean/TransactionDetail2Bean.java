package com.hdnz.inanming.bean;

import java.util.List;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionDetail2Bean.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-20 16:21
 * Description:
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TransactionDetail2Bean {

    /**
     * data : {"status":"办理状态：已提交申请","order":true,"transationName":"居住证明","type":"公共服务 即办件","headerImg":"http://192.168.1.35:8722/img/1.png","name":"肖昕","sexAndAddress":"男 26岁 绿苑-33网格","idCard":"522121199305073214","phoneNum":"15519294256","verifyCodeUrl":"http://192.168.1.35:8722/img/3.png","verifyCode":"5532256","managedInfo":"沙蓝社区网格员-肖昕","phone":"13312345635","cutOffDate":"11-30 15:51 截止","materials":[{"viewType":6,"type":"1.身份证","check":true,"imgUrls":["http://192.168.1.35:8722/img/1.png"]},{"viewType":7,"type":"1.身份证","check":true,"imgUrls":null}]}
     * code : 200
     * message : sucess
     * status : true
     */

    private DataBean data;
    private int code;
    private String message;
    private boolean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * status : 办理状态：已提交申请
         * order : true
         * transationName : 居住证明
         * type : 公共服务 即办件
         * headerImg : http://192.168.1.35:8722/img/1.png
         * name : 肖昕
         * sexAndAddress : 男 26岁 绿苑-33网格
         * idCard : 522121199305073214
         * phoneNum : 15519294256
         * verifyCodeUrl : http://192.168.1.35:8722/img/3.png
         * verifyCode : 5532256
         * managedInfo : 沙蓝社区网格员-肖昕
         * phone : 13312345635
         * cutOffDate : 11-30 15:51 截止
         * materials : [{"viewType":6,"type":"1.身份证","check":true,"imgUrls":["http://192.168.1.35:8722/img/1.png"]},{"viewType":7,"type":"1.身份证","check":true,"imgUrls":null}]
         */

        private String status;
        private boolean order;
        private String transationName;
        private String type;
        private String headerImg;
        private String name;
        private String sexAndAddress;
        private String idCard;
        private String phoneNum;
        private String verifyCodeUrl;
        private String verifyCode;
        private String managedInfo;
        private String phone;
        private String cutOffDate;
        private List<MaterialsBean> materials;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isOrder() {
            return order;
        }

        public void setOrder(boolean order) {
            this.order = order;
        }

        public String getTransationName() {
            return transationName;
        }

        public void setTransationName(String transationName) {
            this.transationName = transationName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeaderImg(String headerImg) {
            this.headerImg = headerImg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSexAndAddress() {
            return sexAndAddress;
        }

        public void setSexAndAddress(String sexAndAddress) {
            this.sexAndAddress = sexAndAddress;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getVerifyCodeUrl() {
            return verifyCodeUrl;
        }

        public void setVerifyCodeUrl(String verifyCodeUrl) {
            this.verifyCodeUrl = verifyCodeUrl;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getManagedInfo() {
            return managedInfo;
        }

        public void setManagedInfo(String managedInfo) {
            this.managedInfo = managedInfo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCutOffDate() {
            return cutOffDate;
        }

        public void setCutOffDate(String cutOffDate) {
            this.cutOffDate = cutOffDate;
        }

        public List<MaterialsBean> getMaterials() {
            return materials;
        }

        public void setMaterials(List<MaterialsBean> materials) {
            this.materials = materials;
        }

        public static class MaterialsBean {
            /**
             * viewType : 6
             * type : 1.身份证
             * check : true
             * imgUrls : ["http://192.168.1.35:8722/img/1.png"]
             */

            private int viewType;
            private String type;
            private boolean check;
            private List<String> imgUrls;

            public int getViewType() {
                return viewType;
            }

            public void setViewType(int viewType) {
                this.viewType = viewType;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public List<String> getImgUrls() {
                return imgUrls;
            }

            public void setImgUrls(List<String> imgUrls) {
                this.imgUrls = imgUrls;
            }
        }
    }
}
