package com.hdnz.inanming.bean.address;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class BlockBean {

    /**
     * addresq : 悠然居1栋 居委会-1号网格
     * addArr : [{"lcList":[{"mphList":[{"dataName":"101房间","pId":"5","id":"7","type":"5"},{"dataName":"102房间","pId":"5","id":"8","type":"5"}],"dataName":"1层","pId":"3","id":"5","type":"4"},{"mphList":[{"dataName":"202房间","pId":"6","id":"10","type":"5"},{"dataName":"201房间","pId":"6","id":"9","type":"5"}],"dataName":"2层","pId":"3","id":"6","type":"4"}],"dataName":"一单元","pId":"2","id":"3","type":"3"},{"lcList":[{"mphList":[{"dataName":"101房间","pId":"11","id":"13","type":"5"},{"dataName":"102房间","pId":"11","id":"14","type":"5"}],"dataName":"1层","pId":"4","id":"11","type":"4"},{"mphList":[{"dataName":"201房间","pId":"12","id":"15","type":"5"},{"dataName":"202房间","pId":"12","id":"16","type":"5"}],"dataName":"2层","pId":"4","id":"12","type":"4"}],"dataName":"二单元","pId":"2","id":"4","type":"3"}]
     */

    private String sjhId;
    private String ldId;
    private String jwhId;
    private String wgId;
    private String sjh;
    private String addresq;
    private String jwhName;
    private String wgName;
    private List<AddArrBean> addArr;

    public String getSjhId() {
        return sjhId;
    }

    public void setSjhId(String sjhId) {
        this.sjhId = sjhId;
    }

    public String getLdId() {
        return ldId;
    }

    public void setLdId(String ldId) {
        this.ldId = ldId;
    }

    public String getJwhId() {
        return jwhId;
    }

    public void setJwhId(String jwhId) {
        this.jwhId = jwhId;
    }

    public String getWgId() {
        return wgId;
    }

    public void setWgId(String wgId) {
        this.wgId = wgId;
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    public String getAddresq() {
        return addresq;
    }

    public void setAddresq(String addresq) {
        this.addresq = addresq;
    }

    public String getJwhName() {
        return jwhName;
    }

    public void setJwhName(String jwhName) {
        this.jwhName = jwhName;
    }

    public String getWgName() {
        return wgName;
    }

    public void setWgName(String wgName) {
        this.wgName = wgName;
    }

    public List<AddArrBean> getAddArr() {
        return addArr;
    }

    public void setAddArr(List<AddArrBean> addArr) {
        this.addArr = addArr;
    }

    public static class AddArrBean implements IPickerViewData {
        /**
         * lcList : [{"mphList":[{"dataName":"101房间","pId":"5","id":"7","type":"5"},{"dataName":"102房间","pId":"5","id":"8","type":"5"}],"dataName":"1层","pId":"3","id":"5","type":"4"},{"mphList":[{"dataName":"202房间","pId":"6","id":"10","type":"5"},{"dataName":"201房间","pId":"6","id":"9","type":"5"}],"dataName":"2层","pId":"3","id":"6","type":"4"}]
         * dataName : 一单元
         * pId : 2
         * id : 3
         * type : 3
         */

        private String dataName;
        private String pId;
        private String id;
        private String type;
        private List<LcListBean> lcList;

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<LcListBean> getLcList() {
            return lcList;
        }

        public void setLcList(List<LcListBean> lcList) {
            this.lcList = lcList;
        }

        @Override
        public String getPickerViewText() {
            return this.dataName;
        }

        public static class LcListBean implements IPickerViewData {
            /**
             * mphList : [{"dataName":"101房间","pId":"5","id":"7","type":"5"},{"dataName":"102房间","pId":"5","id":"8","type":"5"}]
             * dataName : 1层
             * pId : 3
             * id : 5
             * type : 4
             */

            private String dataName;
            private String pId;
            private String id;
            private String type;
            private List<MphListBean> mphList;

            public String getDataName() {
                return dataName;
            }

            public void setDataName(String dataName) {
                this.dataName = dataName;
            }

            public String getPId() {
                return pId;
            }

            public void setPId(String pId) {
                this.pId = pId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<MphListBean> getMphList() {
                return mphList;
            }

            public void setMphList(List<MphListBean> mphList) {
                this.mphList = mphList;
            }

            @Override
            public String getPickerViewText() {
                return this.dataName;
            }

            public static class MphListBean implements IPickerViewData {
                /**
                 * dataName : 101房间
                 * pId : 5
                 * id : 7
                 * type : 5
                 */

                private String dataName;
                private String pId;
                private String id;
                private String type;

                public String getDataName() {
                    return dataName;
                }

                public void setDataName(String dataName) {
                    this.dataName = dataName;
                }

                public String getPId() {
                    return pId;
                }

                public void setPId(String pId) {
                    this.pId = pId;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                @Override
                public String getPickerViewText() {
                    return this.dataName;
                }
            }
        }
    }
}
