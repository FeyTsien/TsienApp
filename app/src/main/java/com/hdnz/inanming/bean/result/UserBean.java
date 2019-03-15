package com.hdnz.inanming.bean.result;

import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/12/17
 *     desc   :
 * </pre>
 */
public class UserBean {


    /**
     * userAccountEntity : {"id":"d826585cb2814ceaae4817929ef849ef","userName":"123456_1234","email":"mock","phoneNumber":"123456","profilePhotoUrl":"mock","nickName":"mock","signatory":"mock","systemCode":"mock"}
     * tbAdhibitionEntityList : [{"code":"10013","name":"消息","icon":"tuan","url":"http//"},{"code":"10011","name":"首页","icon":"tuan","url":"http//"}]
     * token : 7e00d592c3f748c8aac5a0873b2668f5
     */

    private UserAccountEntityBean userAccountEntity;
    private List<TbAdhibitionEntityListBean> tbAdhibitionEntityList;
    private String token;
    private String tbUserTagEntity;
    private String smsCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTbUserTagEntity() {
        return tbUserTagEntity;
    }

    public void setTbUserTagEntity(String tbUserTagEntity) {
        this.tbUserTagEntity = tbUserTagEntity;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public UserAccountEntityBean getUserAccountEntity() {
        return userAccountEntity;
    }

    public void setUserAccountEntity(UserAccountEntityBean userAccountEntity) {
        this.userAccountEntity = userAccountEntity;
    }


    public List<TbAdhibitionEntityListBean> getTbAdhibitionEntityList() {
        return tbAdhibitionEntityList;
    }

    public void setTbAdhibitionEntityList(List<TbAdhibitionEntityListBean> tbAdhibitionEntityList) {
        this.tbAdhibitionEntityList = tbAdhibitionEntityList;
    }

    public static class UserAccountEntityBean {


        /**
         * id : 8a54ac0e60e143b88f9fa581a160b40d
         * userName : 18500000000_1234
         * password : 739c04087749fc26b9ee735c5c09f785
         * email :
         * phoneNumber : 18500000000
         * profilePhotoUrl : 317060498766172160
         * nickName : 啦啦啦
         * signatory :
         * systemCode :
         * realName :
         * sex : 1
         * cardNo : 130826199812243612
         * nation :
         * birthdate :
         * location :
         * address :
         * auditResult : 2
         * deleted : 0
         * createDatetime : 2019-01-14 09:58:11
         * createUser :
         * updateDatetime : 2019-02-28 14:09:21
         * updateUser :
         */

        private String id;
        private String userName;
        private String password;
        private String email;
        private String phoneNumber;
        private String profilePhotoUrl;
        private String nickName;
        private String signatory;
        private String systemCode;
        private String realName;
        private String sex;
        private String cardNo;
        private String nation;
        private String birthdate;
        private String location;
        private String address;
        private String auditResult;
        private int deleted;
        private String createDatetime;
        private String createUser;
        private String updateDatetime;
        private String updateUser;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAuditResult() {
            return auditResult;
        }

        public void setAuditResult(String auditResult) {
            this.auditResult = auditResult;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getCreateDatetime() {
            return createDatetime;
        }

        public void setCreateDatetime(String createDatetime) {
            this.createDatetime = createDatetime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(String updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }

    public static class TbAdhibitionEntityListBean {
        /**
         * code : 10013
         * name : 消息
         * icon : tuan
         * url : http//
         */

        private String adhCode;
        private String name;
        private String icon;
        private String url;

        public String getAdhCode() {
            return adhCode;
        }

        public void setAdhCode(String adhCode) {
            this.adhCode = adhCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
