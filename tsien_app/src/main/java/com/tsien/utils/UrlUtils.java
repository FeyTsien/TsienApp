package com.tsien.utils;

import com.blankj.utilcode.util.BarUtils;
import com.tsien.app.AppData;

/**
 * Created by Administrator on 2017/4/10.
 */

public class UrlUtils {

    //TODO:============================  H5 URL  ===================================================
    //首页主连接
    public static final String H5_HOME_BASE_URL = "http://47.92.165.143:8007/";//http://192.168.3.237:8007/     http://47.92.165.143:8007/
    //电商首页连接
    public static final String H5_SHOP_BASE_URL = "http://47.92.165.143:8010/";//http://192.168.3.234:8080/    http://47.92.165.143:8010/

    //首页子连接(参数有：token、statusHeight)
    public static final String H5_HOME = "?token=" + AppData.getValueStr(AppData.KEY_TOKEN) + "&statusHeight=" + BarUtils.getStatusBarHeight();
    //我的订单(参数有：token、userId、statusHeight)
    public static final String H5_SHOP_ORDER_LIST = "orderList?token=" + AppData.getValueStr(AppData.KEY_TOKEN) + "&userId=" + AppData.getValueStr(AppData.KEY_USER_ID) + "&statusHeight=" + BarUtils.getStatusBarHeight();

    //TODO:==========================  服务器 BASE URL  ============================================


    //用户中心 BaseUrl
    public static final String BASEURL = "http://47.92.165.143:8002/";// 服务器->http://47.92.165.143:8002/ 猴子->http://192.168.3.128:8005/   海洋->http://192.168.3.85:8011/
    //    //社区 BaseUrl
//    public static final String BASEURL_COMMUNITY = "http://47.92.165.143:8002/";//http://192.168.3.49:8004/   http://47.92.165.143:8002/
    //电商 BaseUrl
    public static final String BASEURL_STORE = "http://47.92.219.176:8009/";
    //TODO:============================  接口名  ===================================================

    public static final String TEST = "test";
    //============用户中心==================
    //获取验证码
    public static final String GET_SMS_CODE = "hdnz-ucenter/api/v1/user_account/selectSms";
    //注册
    public static final String LOG_UP = "hdnz-ucenter/api/v1/user_account/register";
    //登录
    public static final String LOGIN = "hdnz-ucenter/api/v1/user_account/login";
    //忘记密码
    public static final String FORGET_PWD = "hdnz-ucenter/api/v1/user_account/updatepwd";
    //============消息======================
    //获取消息列表
    public static final String GET_MESSAGE_LIST = "MsgCenter/api/msg/getMsgListByUserId";

}
