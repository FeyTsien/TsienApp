package com.hdnz.inanming.app;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hdnz.inanming.bean.result.UserBean;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AppData {

    //BaseUrl
    public static final String KEY_BASE_URL = "base_url";
    //是否是第一次打开APP
    public static final String KEY_IS_FIRST_OPEN = "is_first_open";
    //是否是新版APP
    public static final String KEY_IS_NEW_APP = "is_new_app";
    //是否登录
    public static final String KEY_IS_LOGIN_ED = "is_logined";
    //Token
    public static final String KEY_TOKEN = "token";
    //UserId
    public static final String KEY_USER_ID = "user_id";
    //手机号
    public static final String KEY_PHONE_NUMBER = "phone_number";
    //身份证
    public static final String KEY_ID_CARD = "id_card";
    //邮箱
    public static final String KEY_EMAIL = "email";
    //密码
    public static final String KEY_PASSWORD = "password";
    //头像Url
    public static final String KEY_PORTRAIT_URL = "portrait_url";
    //昵称
    public static final String KEY_NICKNAME = "nickname";
    //个性签名
    public static final String KEY_SIGNATORY = "signatory";
    //Android设备Id
    public static final String KEY_ANDROID_ID = "android_id";
    //是否包含工作台
    public static final String KEY_IS_WORKBENCH = "is_workbench";
    //是否实名认证
    public static final String KEY_AUTHENTICATION_STATUS = "is_authentication_status";
    //定位——经度
    public static final String KEY_LOCATION_LONGITUDE = "location_longitude";
    //定位——纬度
    public static final String KEY_LOCATION_LATITUDE = "location_latitude";


    /**
     * 保存String 类型数据
     */
    public static void setValueStr(String key, String value) {
        SPUtils.getInstance().put(key, value);
    }

    /**
     * 获取String 类型数据
     */
    public static String getValueStr(String key) {
        return SPUtils.getInstance().getString(key);
    }


    /**
     * 保存 Boolean 类型数据
     *
     * @param key
     * @param isBool
     */
    public static void setValueBool(String key, boolean isBool) {
        SPUtils.getInstance().put(key, isBool);
    }

    /**
     * 查询 Boolean 类型数据
     *
     * @param key
     * @return
     */
    public static boolean isValueBool(String key) {
        return SPUtils.getInstance().getBoolean(key, false);
    }


    /**
     * 清除登录信息
     */
    public static void clearLogin() {
        AppData.setValueBool(AppData.KEY_IS_LOGIN_ED,false);
        AppData.setValueStr(AppData.KEY_TOKEN,"");
        AppData.setValueStr(AppData.KEY_USER_ID,"");
        AppData.setValueStr(AppData.KEY_PHONE_NUMBER,"");
        AppData.setValueStr(AppData.KEY_ID_CARD,"");
        AppData.setValueStr(AppData.KEY_PASSWORD,"");
        AppData.setValueStr(AppData.KEY_PORTRAIT_URL,"");
        AppData.setValueStr(AppData.KEY_NICKNAME,"");
        AppData.setValueStr(AppData.KEY_SIGNATORY,"");
        AppData.setValueBool(KEY_IS_WORKBENCH,false);
    }

    /**
     * 保存登录信息
     *
     * @param userBean
     */
    public static void saveLogin(UserBean userBean) {

        AppData.setValueBool(AppData.KEY_IS_LOGIN_ED,true);
        AppData.setValueStr(AppData.KEY_TOKEN,userBean.getToken());
        AppData.setValueStr(AppData.KEY_USER_ID,userBean.getUserAccountEntity().getId());
        AppData.setValueStr(AppData.KEY_PHONE_NUMBER,userBean.getUserAccountEntity().getPhoneNumber());
        AppData.setValueStr(AppData.KEY_ID_CARD,userBean.getUserAccountEntity().getCardNo());
//        AppData.setPassword(password);
        AppData.setValueStr(AppData.KEY_PORTRAIT_URL,userBean.getUserAccountEntity().getProfilePhotoUrl());
        AppData.setValueStr(AppData.KEY_NICKNAME,userBean.getUserAccountEntity().getNickName());
        AppData.setValueStr(AppData.KEY_SIGNATORY,userBean.getUserAccountEntity().getSignatory());
        if (userBean.getTbAdhibitionEntityList().size() > 3) {
            //如果数量大于3个，表明该用户有工作台
            AppData.setValueBool(KEY_IS_WORKBENCH,true);
        } else {
            AppData.setValueBool(KEY_IS_WORKBENCH,false);
        }
    }
}
