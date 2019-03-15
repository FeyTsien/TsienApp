package com.hdnz.inanming.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.ui.activity.home.GovAffairsBookingActivity;
import com.hdnz.inanming.ui.activity.home.GovAffairsOfficeActivity;
import com.hdnz.inanming.ui.activity.home.GovAffairsPublicActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsbooking.gabbusiness.GABBookingInfoActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.GovAffairInfoActivity;
import com.hdnz.inanming.ui.activity.me.AuthenticationActivity;
import com.hdnz.inanming.ui.activity.me.information.address.MyAddressActivity;
import com.hdnz.inanming.ui.popupwindow.GABBookingInfoPopup;
import com.hdnz.inanming.ui.popupwindow.MobSharePopup;
import com.hdnz.inanming.ui.activity.login.LoginActivity;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.eventbus.EventCode;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * 注册方法，提供给js调用
 */
public class X5WebViewJS {
    private Context mContext;
    private X5WebView mWebView;

    public X5WebViewJS(Context context, X5WebView webView) {
        mContext = context;
        mWebView = webView;
    }

    /**
     * TODO:打开新连接，跳转到WebViewActivity
     */
    @JavascriptInterface
    public void goToUrl(String url) {
        //跳转到WebViewActivity
        WebViewActivity.goToWebView(mContext, url);
//        WebViewActivity.goToWebView(mContext,"https://www.baidu.com");
    }

    /**
     * TODO:关闭WebViewActivity
     */
    @JavascriptInterface
    public void finish() {
        ((Activity) mContext).finish();
    }


    /**
     * TODO:前往分享
     */
    @JavascriptInterface
    public void shareAction(String share) {
        MobSharePopup.getInstance(mContext, share).showPopupWindow();
    }

    /**
     * TODO:前往登录页
     */
    @JavascriptInterface
    public void goToLogin() {
        startActivity(LoginActivity.class);
    }

    /**
     * TODO:H5地图选择楼栋后返回的数据
     */
    @JavascriptInterface
    public void goAdder(String address) {
        ToastUtils.showLong(address);

        EventBusUtil.post(new Event<>(EventCode.ME_ADDRESS_A, address));
        ((Activity) mContext).finish();
    }

    /**
     * TODO:跳转到新增地址页面
     */
    @JavascriptInterface
    public void addAddresList() {
        startActivity(MyAddressActivity.class);
    }


    /**
     * TODO:前往政务办事
     */
    @JavascriptInterface
    public void affairs() {
        startActivity(GovAffairsOfficeActivity.class);
    }

    /**
     * TODO:前往政务办事
     */
    @JavascriptInterface
    public void businessManagement(String id, String title) {
        Intent intent = new Intent(mContext, GovAffairInfoActivity.class);
        intent.putExtra(GovAffairInfoActivity.KEY_NAME, title);
        intent.putExtra(GovAffairInfoActivity.KEY_ID, id);
        intent.putExtra(GovAffairInfoActivity.KEY_DEPT_CLASSIFY, GovAffairInfoActivity.VALUE_CLASSIFY);
        startActivity(intent);
    }

    /**
     * TODO:前往政务预约
     */
    @JavascriptInterface
    public void appointments() {
        startActivity(GovAffairsBookingActivity.class);
    }


    /**
     * TODO:前往政务公开
     */
    @JavascriptInterface
    public void openness() {
        startActivity(GovAffairsPublicActivity.class);
    }

    /**
     * TODO:前往实名认证
     */
    @JavascriptInterface
    public void goCertifi() {
        startActivity(AuthenticationActivity.class);
    }

    /**
     * TODO:打开时间选择器页面
     */
    @JavascriptInterface
    public void timeList() {
        GABBookingInfoPopup.getInstance(mContext, map -> {
                    new Handler(Looper.myLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String time = (String) map.get(GABBookingInfoPopup.KEY_DATE_TIME);
                            mWebView.loadUrl("javascript:selectedDateTimeJS(\"" + time + "\")");
                        }
                    });
                }
        ).showPopupWindow();
    }


    //====================================   其他（可忽略）   =============================================

    @JavascriptInterface //js调用Android方法
    public void onX5ButtonClicked() {
        //X5全屏模式
        enableX5FullscreenFunc();
    }

    @JavascriptInterface
    public void onCustomButtonClicked() {
        //恢复webkit初始模式
        disableX5FullscreenFunc();
    }

    @JavascriptInterface
    public void onLiteWndButtonClicked() {
        //开启小窗模式
        enableLiteWndFunc();
    }

    @JavascriptInterface
    public void onPageVideoClicked() {
        //页面内全屏播放模式
        enablePageVideoFunc();
    }


    /**
     * 开启X5全屏播放模式
     */
    private void enableX5FullscreenFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(mContext, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    /**
     * 恢复webkit初始状态
     */
    private void disableX5FullscreenFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(mContext, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    /**
     * 开启小窗模式
     */
    private void enableLiteWndFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(mContext, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    /**
     * 页面内全屏播放模式
     */
    private void enablePageVideoFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(mContext, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

}
