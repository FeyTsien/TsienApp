package com.hdnz.inanming.bean.result;

import java.util.List;

public class IntegralBean {

    private List<TransferDataBean> transferData;

    public List<TransferDataBean> getTransferData() {
        return transferData;
    }

    public void setTransferData(List<TransferDataBean> transferData) {
        this.transferData = transferData;
    }

    public static class TransferDataBean {
        /**
         * name : 金币
         * count : 1270
         * profile : gold
         */

        private String name;
        private String count;
        private String profile;
        private String value;
        private long changeTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public long getChangeTime() {
            return changeTime;
        }

        public void setChangeTime(long changeTime) {
            this.changeTime = changeTime;
        }
    }
}
