package com.hdnz.inanming.bean.result;

import java.util.List;

public class LicenseBean {


    private List<PapersBean> papersList;

    public List<PapersBean> getPapersList() {
        return papersList;
    }

    public void setPapersList(List<PapersBean> papersList) {
        this.papersList = papersList;
    }

    public static class PapersBean {
        /**
         * id : a5f8916e53d848a9a75157364986ac82
         * pId : e8c7e4f3ec1e42a6baf0c7bf9cbcdb14
         * name : mock
         * card : d:5555
         * type : 000000
         */

        private String id;
        private String pId;
        private String description;
        private String card;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
