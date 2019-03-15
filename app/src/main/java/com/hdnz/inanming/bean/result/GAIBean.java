package com.hdnz.inanming.bean.result;

import java.util.List;

public class GAIBean {


    private List<GovitemsBean> govitems;

    public List<GovitemsBean> getGovitems() {
        return govitems;
    }

    public void setGovitems(List<GovitemsBean> govitems) {
        this.govitems = govitems;
    }

    public static class GovitemsBean {
        /**
         * id : 414196210083041282
         * classifyId : 314196210083041280
         * dept : 314198418925162605
         * name : 出生证明办理
         * icon : 1
         * type : 1
         */

        private String id;
        private String classifyId;
        private String dept;
        private String name;
        private String icon;
        private int type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(String classifyId) {
            this.classifyId = classifyId;
        }

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
