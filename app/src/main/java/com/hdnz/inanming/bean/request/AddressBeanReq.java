package com.hdnz.inanming.bean.request;

public class AddressBeanReq {


    /**
     * params : {"name":"测试222","phoneNumber":"13000009999","number":"11111","province":"1","city":"1","county":"1","shequ":"222","juweihui":"9","wg":"1","ld":"2","sanjuhu":"mock","dy":"3","lc":"5","mph":"7","addr":"阜通东大街"}
     */

    private ParamsBean params;

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {


        /**
         * name : 测试222
         * phoneNumber : 13000009999
         * number : 11111
         * province : 1
         * city : 1
         * county : 1
         * sq : 222
         * jwh : 9
         * wg : 1
         * ld : 2
         * sjh : mock
         * dy : 3
         * lc : 5
         * mph : 7
         * addr : 阜通东大街
         */

        private String id;
        private String name;
        private String phoneNumber;
        private String number;
        private String province;
        private String city;
        private String county;
        private String sq;
        private String jwh;
        private String wg;
        private String ld;
        private String sjh;
        private String dy;
        private String lc;
        private String mph;
        private String addr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getSq() {
            return sq;
        }

        public void setSq(String sq) {
            this.sq = sq;
        }

        public String getJwh() {
            return jwh;
        }

        public void setJwh(String jwh) {
            this.jwh = jwh;
        }

        public String getWg() {
            return wg;
        }

        public void setWg(String wg) {
            this.wg = wg;
        }

        public String getLd() {
            return ld;
        }

        public void setLd(String ld) {
            this.ld = ld;
        }

        public String getSjh() {
            return sjh;
        }

        public void setSjh(String sjh) {
            this.sjh = sjh;
        }

        public String getDy() {
            return dy;
        }

        public void setDy(String dy) {
            this.dy = dy;
        }

        public String getLc() {
            return lc;
        }

        public void setLc(String lc) {
            this.lc = lc;
        }

        public String getMph() {
            return mph;
        }

        public void setMph(String mph) {
            this.mph = mph;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}
