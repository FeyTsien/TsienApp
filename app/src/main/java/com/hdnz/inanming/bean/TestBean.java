package com.hdnz.inanming.bean;

import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/24
 *     desc   :
 * </pre>
 */
public class TestBean {


    private List<AddressBean> address;

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        /**
         * name : 中华人民共和国
         * adcode : 100000
         * citycode :
         */

        private String name;
        private int adcode;
        private int citycode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAdcode() {
            return adcode;
        }

        public void setAdcode(int adcode) {
            this.adcode = adcode;
        }

        public int getCitycode() {
            return citycode;
        }

        public void setCitycode(int citycode) {
            this.citycode = citycode;
        }
    }
}
