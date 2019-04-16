package com.tsien.bean.request;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/12/17
 *     desc   :请求接口的入参bean
 * </pre>
 */
public class RequestBean {

    private PageBean page;
    private ParamsBean params;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class PageBean {
        private int pageIndex;//当前第几页
        private int pageSize;//每页记录条数

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class ParamsBean {

        private String loginType;
        private String userName;
        private String smsCode;
        private String password;
        private String phoneNumber;
        private String type;
        private String smsType;
        private String memberId;
        private String recommendtype;
        private String id;
        private String profilePhotoUrl;
        private String nickName;
        private String signatory;
        private String classifyPId;
        private String dept;
        private String cardtypeId;
        private String currentPosition;
        private String itemId;
        private String orgId;
        private String status;
        private String profile;


        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String logintype) {
            this.loginType = logintype;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(String smsCode) {
            this.smsCode = smsCode;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSmsType() {
            return smsType;
        }

        public void setSmsType(String smsType) {
            this.smsType = smsType;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getRecommendtype() {
            return recommendtype;
        }

        public void setRecommendtype(String recommendtype) {
            this.recommendtype = recommendtype;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProfilePhotoUrl() {
            return profilePhotoUrl;
        }

        public void setProfilePhotoUrl(String profilePhotoUrl) {
            this.profilePhotoUrl = profilePhotoUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSignatory() {
            return signatory;
        }

        public void setSignatory(String signatory) {
            this.signatory = signatory;
        }

        public String getClassifyPId() {
            return classifyPId;
        }

        public void setClassifyPId(String classifyPId) {
            this.classifyPId = classifyPId;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getCardtypeId() {
            return cardtypeId;
        }


        public void setCardtypeId(String cardtypeId) {
            this.cardtypeId = cardtypeId;
        }

        public String getCurrentPosition() {
            return currentPosition;
        }

        public void setCurrentPosition(String currentPosition) {
            this.currentPosition = currentPosition;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }
}
