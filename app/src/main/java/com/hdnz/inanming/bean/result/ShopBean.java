package com.hdnz.inanming.bean.result;

import java.util.List;

public class ShopBean {

    /**
     * total : 2
     * pages : 1
     * pageSize : 10
     * rows : [{"id":"309063311076388864","createddate":null,"lastmodifieddate":null,"version":null,"memberId":null,"storeId":null,"address":"花果园","lng":"106.693546","lat":"26.575206","email":null,"enddate":null,"isenabled":null,"keyword":null,"logo":"http://47.92.165.143:8011/shop++/upload/image/201901/1e9a847b-39cd-489b-9771-893a252e3133.jpg","pic":"http://47.92.165.143:8011/shop++/upload/image/201901/49b2a735-d942-4ae7-93c5-29093b355a23.jpg","mobile":"15676226564","name":"谢老五铁牛","phone":null,"status":null,"type":null,"zipcode":null,"businessId":null,"storecategoryId":"9951","storecategoryName":"优惠买单","storerankId":null,"opentime":null,"closetime":null,"consumerPer":null,"introduction":null,"qrcode":null,"pricepics":null,"recommendpics":null,"tradeareaid":"4","zhifubao":null,"storecategoryrankId":"314185107023376384","storecategoryrankName":"西单购物街","storecategoryrankOrders":"1","promotions":[{"id":null,"createddate":null,"lastmodifieddate":null,"version":null,"orders":null,"begindate":null,"enddate":null,"image":null,"iscouponallowed":null,"isenabled":null,"name":null,"promotionpluginid":null,"storeId":null,"storeName":null,"introduction":null,"promotionDefaultAttribute":null,"discounvalue":"90.00","discounttype":"2","discounttypeDesc":"百分比减免"}],"promotionMemberRanks":null,"currentPosition":null,"storeDistance":"无法定位","storeType":null,"storeStatus":null,"businessStatus":"24小时营业","storeRerecommend":false}]
     * pageNum : 1
     */

    private String total;
    private int pages;
    private String pageSize;
    private String pageNum;
    private List<RowsBean> rows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 309063311076388864
         * createddate : null
         * lastmodifieddate : null
         * version : null
         * memberId : null
         * storeId : null
         * address : 花果园
         * lng : 106.693546
         * lat : 26.575206
         * email : null
         * enddate : null
         * isenabled : null
         * keyword : null
         * logo : http://47.92.165.143:8011/shop++/upload/image/201901/1e9a847b-39cd-489b-9771-893a252e3133.jpg
         * pic : http://47.92.165.143:8011/shop++/upload/image/201901/49b2a735-d942-4ae7-93c5-29093b355a23.jpg
         * mobile : 15676226564
         * name : 谢老五铁牛
         * phone : null
         * status : null
         * type : null
         * zipcode : null
         * businessId : null
         * storecategoryId : 9951
         * storecategoryName : 优惠买单
         * storerankId : null
         * opentime : null
         * closetime : null
         * consumerPer : null
         * introduction : null
         * qrcode : null
         * pricepics : null
         * recommendpics : null
         * tradeareaid : 4
         * zhifubao : null
         * storecategoryrankId : 314185107023376384
         * storecategoryrankName : 西单购物街
         * storecategoryrankOrders : 1
         * promotions : [{"id":null,"createddate":null,"lastmodifieddate":null,"version":null,"orders":null,"begindate":null,"enddate":null,"image":null,"iscouponallowed":null,"isenabled":null,"name":null,"promotionpluginid":null,"storeId":null,"storeName":null,"introduction":null,"promotionDefaultAttribute":null,"discounvalue":"90.00","discounttype":"2","discounttypeDesc":"百分比减免"}]
         * promotionMemberRanks : null
         * currentPosition : null
         * storeDistance : 无法定位
         * storeType : null
         * storeStatus : null
         * businessStatus : 24小时营业
         * storeRerecommend : false
         */

        private String id;
        private Object createddate;
        private Object lastmodifieddate;
        private Object version;
        private Object memberId;
        private Object storeId;
        private String address;
        private String lng;
        private String lat;
        private Object email;
        private Object enddate;
        private Object isenabled;
        private Object keyword;
        private String logo;
        private String pic;
        private String mobile;
        private String name;
        private Object phone;
        private Object status;
        private Object type;
        private Object zipcode;
        private Object businessId;
        private String storecategoryId;
        private String storecategoryName;
        private Object storerankId;
        private Object opentime;
        private Object closetime;
        private String consumerPer;
        private Object introduction;
        private Object qrcode;
        private String pricepics;
        private String recommendpics;
        private String tradeareaid;
        private Object zhifubao;
        private String storecategoryrankId;
        private String storecategoryrankName;
        private String storecategoryrankOrders;
        private Object promotionMemberRanks;
        private Object currentPosition;
        private String storeDistance;
        private Object storeType;
        private Object storeStatus;
        private String businessStatus;
        private boolean storeRerecommend;
        private List<PromotionsBean> promotions;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCreateddate() {
            return createddate;
        }

        public void setCreateddate(Object createddate) {
            this.createddate = createddate;
        }

        public Object getLastmodifieddate() {
            return lastmodifieddate;
        }

        public void setLastmodifieddate(Object lastmodifieddate) {
            this.lastmodifieddate = lastmodifieddate;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public Object getMemberId() {
            return memberId;
        }

        public void setMemberId(Object memberId) {
            this.memberId = memberId;
        }

        public Object getStoreId() {
            return storeId;
        }

        public void setStoreId(Object storeId) {
            this.storeId = storeId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getEnddate() {
            return enddate;
        }

        public void setEnddate(Object enddate) {
            this.enddate = enddate;
        }

        public Object getIsenabled() {
            return isenabled;
        }

        public void setIsenabled(Object isenabled) {
            this.isenabled = isenabled;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getZipcode() {
            return zipcode;
        }

        public void setZipcode(Object zipcode) {
            this.zipcode = zipcode;
        }

        public Object getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Object businessId) {
            this.businessId = businessId;
        }

        public String getStorecategoryId() {
            return storecategoryId;
        }

        public void setStorecategoryId(String storecategoryId) {
            this.storecategoryId = storecategoryId;
        }

        public String getStorecategoryName() {
            return storecategoryName;
        }

        public void setStorecategoryName(String storecategoryName) {
            this.storecategoryName = storecategoryName;
        }

        public Object getStorerankId() {
            return storerankId;
        }

        public void setStorerankId(Object storerankId) {
            this.storerankId = storerankId;
        }

        public Object getOpentime() {
            return opentime;
        }

        public void setOpentime(Object opentime) {
            this.opentime = opentime;
        }

        public Object getClosetime() {
            return closetime;
        }

        public void setClosetime(Object closetime) {
            this.closetime = closetime;
        }

        public String getConsumerPer() {
            return consumerPer;
        }

        public void setConsumerPer(String consumerPer) {
            this.consumerPer = consumerPer;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
            this.introduction = introduction;
        }

        public Object getQrcode() {
            return qrcode;
        }

        public void setQrcode(Object qrcode) {
            this.qrcode = qrcode;
        }

        public String getPricepics() {
            return pricepics;
        }

        public void setPricepics(String pricepics) {
            this.pricepics = pricepics;
        }

        public String getRecommendpics() {
            return recommendpics;
        }

        public void setRecommendpics(String recommendpics) {
            this.recommendpics = recommendpics;
        }

        public String getTradeareaid() {
            return tradeareaid;
        }

        public void setTradeareaid(String tradeareaid) {
            this.tradeareaid = tradeareaid;
        }

        public Object getZhifubao() {
            return zhifubao;
        }

        public void setZhifubao(Object zhifubao) {
            this.zhifubao = zhifubao;
        }

        public String getStorecategoryrankId() {
            return storecategoryrankId;
        }

        public void setStorecategoryrankId(String storecategoryrankId) {
            this.storecategoryrankId = storecategoryrankId;
        }

        public String getStorecategoryrankName() {
            return storecategoryrankName;
        }

        public void setStorecategoryrankName(String storecategoryrankName) {
            this.storecategoryrankName = storecategoryrankName;
        }

        public String getStorecategoryrankOrders() {
            return storecategoryrankOrders;
        }

        public void setStorecategoryrankOrders(String storecategoryrankOrders) {
            this.storecategoryrankOrders = storecategoryrankOrders;
        }

        public Object getPromotionMemberRanks() {
            return promotionMemberRanks;
        }

        public void setPromotionMemberRanks(Object promotionMemberRanks) {
            this.promotionMemberRanks = promotionMemberRanks;
        }

        public Object getCurrentPosition() {
            return currentPosition;
        }

        public void setCurrentPosition(Object currentPosition) {
            this.currentPosition = currentPosition;
        }

        public String getStoreDistance() {
            return storeDistance;
        }

        public void setStoreDistance(String storeDistance) {
            this.storeDistance = storeDistance;
        }

        public Object getStoreType() {
            return storeType;
        }

        public void setStoreType(Object storeType) {
            this.storeType = storeType;
        }

        public Object getStoreStatus() {
            return storeStatus;
        }

        public void setStoreStatus(Object storeStatus) {
            this.storeStatus = storeStatus;
        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public boolean isStoreRerecommend() {
            return storeRerecommend;
        }

        public void setStoreRerecommend(boolean storeRerecommend) {
            this.storeRerecommend = storeRerecommend;
        }

        public List<PromotionsBean> getPromotions() {
            return promotions;
        }

        public void setPromotions(List<PromotionsBean> promotions) {
            this.promotions = promotions;
        }

        public static class PromotionsBean {
            /**
             * id : null
             * createddate : null
             * lastmodifieddate : null
             * version : null
             * orders : null
             * begindate : null
             * enddate : null
             * image : null
             * iscouponallowed : null
             * isenabled : null
             * name : null
             * promotionpluginid : null
             * storeId : null
             * storeName : null
             * introduction : null
             * promotionDefaultAttribute : null
             * discounvalue : 90.00
             * discounttype : 2
             * discounttypeDesc : 百分比减免
             */

            private Object id;
            private Object createddate;
            private Object lastmodifieddate;
            private Object version;
            private Object orders;
            private Object begindate;
            private Object enddate;
            private Object image;
            private Object iscouponallowed;
            private Object isenabled;
            private Object name;
            private Object promotionpluginid;
            private Object storeId;
            private Object storeName;
            private Object introduction;
            private Object promotionDefaultAttribute;
            private double discounvalue;
            private String discounttype;
            private String discounttypeDesc;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getCreateddate() {
                return createddate;
            }

            public void setCreateddate(Object createddate) {
                this.createddate = createddate;
            }

            public Object getLastmodifieddate() {
                return lastmodifieddate;
            }

            public void setLastmodifieddate(Object lastmodifieddate) {
                this.lastmodifieddate = lastmodifieddate;
            }

            public Object getVersion() {
                return version;
            }

            public void setVersion(Object version) {
                this.version = version;
            }

            public Object getOrders() {
                return orders;
            }

            public void setOrders(Object orders) {
                this.orders = orders;
            }

            public Object getBegindate() {
                return begindate;
            }

            public void setBegindate(Object begindate) {
                this.begindate = begindate;
            }

            public Object getEnddate() {
                return enddate;
            }

            public void setEnddate(Object enddate) {
                this.enddate = enddate;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public Object getIscouponallowed() {
                return iscouponallowed;
            }

            public void setIscouponallowed(Object iscouponallowed) {
                this.iscouponallowed = iscouponallowed;
            }

            public Object getIsenabled() {
                return isenabled;
            }

            public void setIsenabled(Object isenabled) {
                this.isenabled = isenabled;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getPromotionpluginid() {
                return promotionpluginid;
            }

            public void setPromotionpluginid(Object promotionpluginid) {
                this.promotionpluginid = promotionpluginid;
            }

            public Object getStoreId() {
                return storeId;
            }

            public void setStoreId(Object storeId) {
                this.storeId = storeId;
            }

            public Object getStoreName() {
                return storeName;
            }

            public void setStoreName(Object storeName) {
                this.storeName = storeName;
            }

            public Object getIntroduction() {
                return introduction;
            }

            public void setIntroduction(Object introduction) {
                this.introduction = introduction;
            }

            public Object getPromotionDefaultAttribute() {
                return promotionDefaultAttribute;
            }

            public void setPromotionDefaultAttribute(Object promotionDefaultAttribute) {
                this.promotionDefaultAttribute = promotionDefaultAttribute;
            }

            public double getDiscounvalue() {
                return discounvalue;
            }

            public void setDiscounvalue(double discounvalue) {
                this.discounvalue = discounvalue;
            }

            public String getDiscounttype() {
                return discounttype;
            }

            public void setDiscounttype(String discounttype) {
                this.discounttype = discounttype;
            }

            public String getDiscounttypeDesc() {
                return discounttypeDesc;
            }

            public void setDiscounttypeDesc(String discounttypeDesc) {
                this.discounttypeDesc = discounttypeDesc;
            }
        }
    }
}
