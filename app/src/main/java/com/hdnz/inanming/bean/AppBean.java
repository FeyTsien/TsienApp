package com.hdnz.inanming.bean;

/**
 * Created by admin on 2018/7/2.
 */

public class AppBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String sex;
        private String phone;
        private String nickname;
        private String userid;
        private String password;
        private String headurl;

        private String roletype;

        private int messagetype;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getRoletype() {
            return roletype;
        }

        public void setRoletype(String roletype) {
            this.roletype = roletype;
        }

        public int getMessagetype() {
            return messagetype;
        }

        public void setMessagetype(int messagetype) {
            this.messagetype = messagetype;
        }

    }
}
