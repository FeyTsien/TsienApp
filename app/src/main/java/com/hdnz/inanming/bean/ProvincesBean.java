package com.hdnz.inanming.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/24
 *     desc   :
 * </pre>
 */
public class ProvincesBean {


    private List<ProvinceBean> province;

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }


    public static class ProvinceBean implements IPickerViewData {
        /**
         * code : 1
         * name : 北京市
         * cityList : [{"code":1,"name":"市辖区","areaList":[{"code":1,"name":"东城区"},{"code":2,"name":"西城区"},{"code":3,"name":"朝阳区"},{"code":4,"name":"丰台区"},{"code":5,"name":"石景山区"},{"code":6,"name":"海淀区"},{"code":7,"name":"门头沟区"},{"code":8,"name":"房山区"},{"code":9,"name":"通州区"},{"code":10,"name":"顺义区"},{"code":11,"name":"昌平区"},{"code":12,"name":"大兴区"},{"code":13,"name":"怀柔区"},{"code":14,"name":"平谷区"}]},{"code":2,"name":"市辖县","areaList":[{"code":15,"name":"密云县"},{"code":16,"name":"延庆县"}]}]
         */

        private int adcode;
        private int pos;
        private String name;
        private List<CityListBean> cityList;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getAdcode() {
            return adcode;
        }

        public void setAdcode(int adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        @Override
        public String getPickerViewText() {
            return this.name;
        }

        public static class CityListBean implements IPickerViewData {
            /**
             * code : 1
             * name : 市辖区
             * areaList : [{"code":1,"name":"东城区"},{"code":2,"name":"西城区"},{"code":3,"name":"朝阳区"},{"code":4,"name":"丰台区"},{"code":5,"name":"石景山区"},{"code":6,"name":"海淀区"},{"code":7,"name":"门头沟区"},{"code":8,"name":"房山区"},{"code":9,"name":"通州区"},{"code":10,"name":"顺义区"},{"code":11,"name":"昌平区"},{"code":12,"name":"大兴区"},{"code":13,"name":"怀柔区"},{"code":14,"name":"平谷区"}]
             */

            private int adcode;
            private int pos;
            private String name;
            private List<AreaListBean> areaList;

            public int getPos() {
                return pos;
            }

            public void setPos(int pos) {
                this.pos = pos;
            }

            public int getAdcode() {
                return adcode;
            }

            public void setAdcode(int adcode) {
                this.adcode = adcode;
            }


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<AreaListBean> getAreaList() {
                return areaList;
            }

            public void setAreaList(List<AreaListBean> areaList) {
                this.areaList = areaList;
            }

            @Override
            public String getPickerViewText() {
                return this.name;
            }

            public static class AreaListBean implements IPickerViewData {
                /**
                 * code : 1
                 * name : 东城区
                 */

                private int adcode;
                private int pos;
                private String name;

                public int getPos() {
                    return pos;
                }

                public void setPos(int pos) {
                    this.pos = pos;
                }

                public int getAdcode() {
                    return adcode;
                }

                public void setAdcode(int adcode) {
                    this.adcode = adcode;
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
    }
}
