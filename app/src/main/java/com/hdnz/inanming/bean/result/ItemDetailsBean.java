package com.hdnz.inanming.bean.result;

import java.util.List;

public class ItemDetailsBean {

    /**
     * datumList : [{"id":"313719334839390208","name":"mike2","path":"www.baidu.com","type":"1"}]
     * govitem : {"alias":"油榨社区服务中心","handleType":"出生证明","handleObj":"地市","handleLimit":"星期一至星期五上午9:00-17:00,法定节假日不对外办公"}
     */

    private GovitemBean govitem;
    private List<DatumListBean> datumList;

    public GovitemBean getGovitem() {
        return govitem;
    }

    public void setGovitem(GovitemBean govitem) {
        this.govitem = govitem;
    }

    public List<DatumListBean> getDatumList() {
        return datumList;
    }

    public void setDatumList(List<DatumListBean> datumList) {
        this.datumList = datumList;
    }

    public static class GovitemBean {
        /**
         * alias : 油榨社区服务中心
         * handleType : 出生证明
         * handleObj : 地市
         * handleLimit : 星期一至星期五上午9:00-17:00,法定节假日不对外办公
         */

        private String alias;
        private String handleType;
        private String handleObj;
        private String handleLimit;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getHandleType() {
            return handleType;
        }

        public void setHandleType(String handleType) {
            this.handleType = handleType;
        }

        public String getHandleObj() {
            return handleObj;
        }

        public void setHandleObj(String handleObj) {
            this.handleObj = handleObj;
        }

        public String getHandleLimit() {
            return handleLimit;
        }

        public void setHandleLimit(String handleLimit) {
            this.handleLimit = handleLimit;
        }
    }

    public static class DatumListBean {
        /**
         * id : 313719334839390208
         * name : mike2
         * path : www.baidu.com
         * type : 1
         */

        private String id;
        private String name;
        private String path;
        private String type;
        private String claims;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getClaims() {
            return claims;
        }

        public void setClaims(String claims) {
            this.claims = claims;
        }
    }
}
