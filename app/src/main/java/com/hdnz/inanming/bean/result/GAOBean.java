package com.hdnz.inanming.bean.result;

import java.util.ArrayList;
import java.util.List;

public class GAOBean {


    private List<ClassifyBean> classifyList;

    public List<ClassifyBean> getClassifyList() {
        return classifyList;
    }

    public void setClassifyList(List<ClassifyBean> classifyList) {
        this.classifyList = classifyList;
    }

    public static class ClassifyBean {
        /**
         * bg :
         * name : 我出生了
         * icon :
         * remark : 生殖保健服务证、二孩生育证等12项服务办理
         * id : 314196210083041280
         * level : [{"bg":"","name":"户籍","icon":"","remark":"","id":"314197436380090369"},{"bg":"","name":"创业","icon":"","remark":"","id":"314197436380090370"},{"bg":"","name":"人力社保","icon":"","remark":"","id":"314197436380090371"},{"bg":"","name":"税务","icon":"","remark":"","id":"314197436380090372"}]
         */

        private String bg;
        private String name;
        private String icon;
        private String remark;
        private String id;
        private List<LevelBean> level;

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<LevelBean> getLevel() {
            if (level == null) {
                level = new ArrayList<>();
            }
            return level;
        }

        public void setLevel(List<LevelBean> level) {
            this.level = level;
        }

        public static class LevelBean {
            /**
             * bg :
             * name : 户籍
             * icon :
             * remark :
             * id : 314197436380090369
             */

            private String bg;
            private String name;
            private String icon;
            private String remark;
            private String id;

            public String getBg() {
                return bg;
            }

            public void setBg(String bg) {
                this.bg = bg;
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
