package com.hdnz.inanming.utils;

import com.blankj.utilcode.util.BarUtils;
import com.hdnz.inanming.app.AppData;

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
//    //文件服务器 BaseUrl
//    public static final String BASEURL_FILE_CENTER = "http://47.92.165.143:8008/FileCenter/";

    //侯汭汎
    public static final String BASEURL_HRF = "http://192.168.3.128:8005/";
    //海洋
    public static final String BASEURL_HY = "http://192.168.3.85:8011/";

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
    //退出
    public static final String LOG_OUT = "hdnz-ucenter/api/v1/user_account/sign";
    //修改手机号
    public static final String UPDATE_PHONE_NUMBER = "hdnz-ucenter/api/v1/user_account/updatephonenum";
    //修改用户信息
    public static final String UPDATE_USER_INFO = "hdnz-ucenter/api/v1/user_account/updateUserAccount";
    //新增地址
    public static final String ADD_ADDRESS = "hdnz-ucenter/api/v1/useraddr/add";
    //查询地址列表
    public static final String GET_ADDRESS_LIST = "hdnz-ucenter/api/v1/tbUserAddr/findByToken";
    //修改地址信息
    public static final String UPDATE_ADDRESS = "hdnz-ucenter/api/v1/tbUserAddr/update";
    //删除地址
    public static final String DEL_ADDRESS = "hdnz-ucenter/api/v1/tbUserAddr/deletedUserAssr";
    //设置默认地址
    public static final String SET_DEFAULT_ADDRESS = "hdnz-ucenter/api/v1/tbUserAddr/updateStatus";
    //实名认证状态查询
    public static final String AUTHENTICATION_STATUS = "hdnz-ucenter/api/v1/authentication/select";
    //实名认证 提交身份证
    public static final String AUTHENTICATION_ADD = "hdnz-ucenter/api/v1/authentication/add";
    //获取证照类型列表
    public static final String GET_LICENSE_TYPE_LIST = "hdnz-ucenter/api/v1/paperstype/select";
    //获取证照信息
    public static final String GET_LICENSE_INFO = "hdnz-ucenter/api/v1/papers/select";

    //============积分======================
    //三种积分的总分列表
    public static final String GET_SUM_INTEGRAL_LIST = "integral/api/integral-gold/verify/getClassIntegralGold";
    //积分列表
    public static final String GET_INTEGRAL_LIST = "integral/api/integral-gold/verify/getGoldOrIntegralList";

    //============政务======================
    //政务办事-获取主题、部门列表
    public static final String GOV_AFFAIRS_OFFICE = "GovCenter/api/classify/getByPid";
    //业务办理、办事指南
    public static final String GOV_AFFAIRS_INFO_LIST = "GovCenter/api/item/getOne";
    //获取机构列表
    public static final String GET_ORGAN_LIST = "GovCenter/api/item/getList";
    //查看事项详情
    public static final String GET_ITEM_DETAILS = "GovCenter/api/item/getDetailMessage";
    //获取各种任务的数量
    public static final String GET_TASKS_COUNT = "GovCenter/api/item/listTypeNumber";
    //获取某一类型任务列表
    public static final String GET_TASKS_LIST = "GovCenter/api/item/listReviewMessage";


    //政务预约列表
    public static final String GET_RESERVATION_LIST = "PortalCenter/api/appointment/list";
    //政务预约——预约业务列表
    public static final String GET_BUSINES_LIST = "ResNumCenter/api/reservation/interior/1.0.0/init?implName=civil&methodName=brchNoCanRes";
    //提交预约
    public static final String SUBMIT_RESERVATION = "ResNumCenter/api/reservation/interior/1.0.0/init?implName=civil&methodName=saveRes&whetherOrNot=1";
    //我的预约
    public static final String GET_MY_RESERVATION_LIST = "ResNumCenter/api/reservation/interior/1.0.0/init?implName=civil&methodName=myReservation&startDate=2000-01-01&endDate=2030-01-01";
    //取消预约
    public static final String CANCEL_RESERVATION = "ResNumCenter/api/reservation/interior/1.0.0/init?implName=civil&methodName=cancelRes";


    //============消息======================
    //获取消息列表
    public static final String GET_MESSAGE_LIST = "MsgCenter/api/msg/getMsgListByUserId";


    //============图片上传======================
    //图片上传-多
    public static final String UPLOAD_IMAGES = "FileCenter/api/file/batchupload";
    //图片上传-单
    public static final String UPLOAD_IMAGE = "FileCenter/api/file/upload";

    //============社区======================
    //新闻头条
    public static final String HEAD_LINE = "community/api/community-posts/verify/pageSearch";
    //文章收藏列表
    public static final String COLLECT_ARTICLES = "community/api/community-collect/verify/pagingQueryCollect";

    //============电商======================
    //商铺列表
    public static final String STORE_LIST = "AiNanMingAppService/store/storelist";
    //商铺收藏列表
    public static final String COLLECT_SHOPS = "AiNanMingAppService/api/storefavorite/list";


}
