package com.tsien.bean.result;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class ResultBean {

    private List<OrgListBean> orgList;

    public List<OrgListBean> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<OrgListBean> orgList) {
        this.orgList = orgList;
    }

    public static class OrgListBean implements IPickerViewData {
        /**
         * id : 314827487521935361
         * name : 南明区
         */

        private String id;
        private String name;

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

        @Override
        public String getPickerViewText() {
            return this.name;
        }
    }
}
