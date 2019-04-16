package com.tsien.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.tsien.ui.activity.login.LoginActivity;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.eventbus.EventCode;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * 注册方法，提供给js调用
 */
public class WebViewJS {
    private Context mContext;
    private WebView mWebView;

    public WebViewJS(Context context, WebView webView) {
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

}
