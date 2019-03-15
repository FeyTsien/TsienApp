package com.hdnz.inanming.bean.result;

import java.util.List;

public class LicenseTypeBean {


    private List<PaperstypeBean> paperstypelist;

    public List<PaperstypeBean> getPaperstypelist() {
        return paperstypelist;
    }

    public void setPaperstypelist(List<PaperstypeBean> paperstypelist) {
        this.paperstypelist = paperstypelist;
    }

    public static class PaperstypeBean {
        /**
         * id : 478697d811074d9990bbd2e8b800088
         * userId : c5fab0e01f0d45a9af6f5f3f51252ed3
         * name : 护照
         * munber : 123123
         * cardtypeId : 123
         * states : 1
         */

        private String id;
        private String userId;
        private String name;
        private String number;
        private String cardtypeId;
        private String states;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCardtypeId() {
            return cardtypeId;
        }

        public void setCardtypeId(String cardtypeId) {
            this.cardtypeId = cardtypeId;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }
    }
}
