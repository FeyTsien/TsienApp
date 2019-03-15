package com.hdnz.inanming.bean.result;

public class ReservationBean {


    /**
     * businessId : 7
     * businessName : VIP业务
     * waitPeople :
     * presentCall :
     * busineTodayNum :
     */

    private String businessId;
    private String busineId;
    private String businessName;
    private String waitPeople;
    private String presentCall;
    private String busineTodayNum;
    private String brchNoName;
    private String reservationNo;
    private String reservationDate;
    private String resTime;
    private String takeState;//取号状态，0未取号，1已取号，2已过号
    private String accessstate;//预约状态，0未取消；1已取消
    private String callNo;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        if(businessName==null){
            businessName = "";
        }
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getWaitPeople() {
        if(waitPeople==null){
            waitPeople = "";
        }
        return waitPeople;
    }

    public void setWaitPeople(String waitPeople) {
        this.waitPeople = waitPeople;
    }

    public String getPresentCall() {
        if(presentCall==null){
            presentCall = "";
        }
        return presentCall;
    }

    public void setPresentCall(String presentCall) {
        this.presentCall = presentCall;
    }

    public String getBusineTodayNum() {
        if(busineTodayNum==null){
            busineTodayNum = "";
        }
        return busineTodayNum;
    }

    public void setBusineTodayNum(String busineTodayNum) {
        this.busineTodayNum = busineTodayNum;
    }

    public String getBusineId() {
        return busineId;
    }

    public void setBusineId(String busineId) {
        this.busineId = busineId;
    }

    public String getBrchNoName() {
        if(brchNoName==null){
            brchNoName="";
        }
        return brchNoName;
    }

    public void setBrchNoName(String brchNoName) {
        this.brchNoName = brchNoName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime;
    }

    public String getTakeState() {
        return takeState;
    }

    public void setTakeState(String takeState) {
        this.takeState = takeState;
    }

    public String getAccessstate() {
        return accessstate;
    }

    public void setAccessstate(String accessstate) {
        this.accessstate = accessstate;
    }

    public String getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }
}
