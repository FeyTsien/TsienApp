package com.hdnz.inanming.bean.result;

import java.util.List;

public class ReservationBeanA {

    /**
     * id : 1103954811688673282
     * govsector : 北京市朝阳区民政局
     * logo :
     * code : civil
     * beginTime : 1552006800000
     * lng :
     * lat :
     * endTime : 1552039200000
     * appointTime :
     * location : 北京市朝阳区工人体育场东路20号
     * registering : 1
     * createUserId :
     * createTime : 1552038313000
     * updateUserId :
     * updateTime : 1552038313000
     * flag : 0
     * busineTodayTotalNum : 0
     * waitPeopleTotalNum : 0
     * presentCallMaxNum : 0
     * businessOutlets : [{"businessId":5,"businessName":"结婚登记","waitPeople":0,"presentCall":0,"busineTodayNum":0},{"businessId":6,"businessName":"离婚登记","waitPeople":0,"presentCall":0,"busineTodayNum":0}]
     */

    private long id;
    private String govsector;
    private String logo;
    private String code;
    private long beginTime;
    private String lng;
    private String lat;
    private long endTime;
    private String appointTime;
    private String location;
    private int registering;
    private String createUserId;
    private long createTime;
    private String updateUserId;
    private long updateTime;
    private int flag;
    private int busineTodayTotalNum;
    private int waitPeopleTotalNum;
    private int presentCallMaxNum;
    private List<BusinessOutletsBean> businessOutlets;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGovsector() {
        return govsector;
    }

    public void setGovsector(String govsector) {
        this.govsector = govsector;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRegistering() {
        return registering;
    }

    public void setRegistering(int registering) {
        this.registering = registering;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getBusineTodayTotalNum() {
        return busineTodayTotalNum;
    }

    public void setBusineTodayTotalNum(int busineTodayTotalNum) {
        this.busineTodayTotalNum = busineTodayTotalNum;
    }

    public int getWaitPeopleTotalNum() {
        return waitPeopleTotalNum;
    }

    public void setWaitPeopleTotalNum(int waitPeopleTotalNum) {
        this.waitPeopleTotalNum = waitPeopleTotalNum;
    }

    public int getPresentCallMaxNum() {
        return presentCallMaxNum;
    }

    public void setPresentCallMaxNum(int presentCallMaxNum) {
        this.presentCallMaxNum = presentCallMaxNum;
    }

    public List<BusinessOutletsBean> getBusinessOutlets() {
        return businessOutlets;
    }

    public void setBusinessOutlets(List<BusinessOutletsBean> businessOutlets) {
        this.businessOutlets = businessOutlets;
    }

    public static class BusinessOutletsBean {
        /**
         * businessId : 5
         * businessName : 结婚登记
         * waitPeople : 0
         * presentCall : 0
         * busineTodayNum : 0
         */

        private int businessId;
        private String businessName;
        private int waitPeople;
        private int presentCall;
        private int busineTodayNum;

        public int getBusinessId() {
            return businessId;
        }

        public void setBusinessId(int businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public int getWaitPeople() {
            return waitPeople;
        }

        public void setWaitPeople(int waitPeople) {
            this.waitPeople = waitPeople;
        }

        public int getPresentCall() {
            return presentCall;
        }

        public void setPresentCall(int presentCall) {
            this.presentCall = presentCall;
        }

        public int getBusineTodayNum() {
            return busineTodayNum;
        }

        public void setBusineTodayNum(int busineTodayNum) {
            this.busineTodayNum = busineTodayNum;
        }
    }
}
