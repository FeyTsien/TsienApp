package com.hdnz.inanming.bean.request;

import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/12/17
 *     desc   :添加认证 的请求接口的入参bean
 * </pre>
 */
public class AddAuthBeanReq {

    /**
     * params : {"realName":"张三00","sex":"21","nation":"汉1","birthdate":"2018-09-19","location":"北京朝阳区阜通1","address":"北京朝阳区阜通1","cardNo":"111111","name":"身份证","cardtypeId":"cardtype20190117100","number":"99998876","papersList":[{"card":"d:oooo","name":"mock","type":"666"}]}
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
         * realName : 张三00
         * sex : 21
         * nation : 汉1
         * birthdate : 2018-09-19
         * location : 北京朝阳区阜通1
         * address : 北京朝阳区阜通1
         * cardNo : 111111
         * name : 身份证
         * cardtypeId : cardtype20190117100
         * number : 99998876
         * papersList : [{"card":"d:oooo","name":"mock","type":"666"}]
         */

        private String realName;
        private String sex;
        private String nation;
        private String birthdate;
        private String location;
        private String address;
        private String cardNo;
        private String name;
        private String cardtypeId;
        private String number;
        private List<PapersBean> papersList;

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

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardtypeId() {
            return cardtypeId;
        }

        public void setCardtypeId(String cardtypeId) {
            this.cardtypeId = cardtypeId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<PapersBean> getPapersList() {
            return papersList;
        }

        public void setPapersList(List<PapersBean> papersList) {
            this.papersList = papersList;
        }

        public static class PapersBean {
            /**
             * card : d:oooo
             * name : mock
             * type : 666
             */

            private String card;
            private String description;
            private String type;

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
