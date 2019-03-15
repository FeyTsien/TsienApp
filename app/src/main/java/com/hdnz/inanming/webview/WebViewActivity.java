package com.hdnz.inanming.webview;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.ui.activity.BaseActivity;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.utils.TbsLog;
import com.tsienlibrary.statusbar.StatusBarUtil;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */

    private X5WebView mWebView;

    private static final String TAG = "SdkDemo";

    private ValueCallback<Uri> uploadFile;

    private URL mIntentUrl;

    @BindView(R.id.webView_layout)
    FrameLayout mViewParent;
    @BindView(R.id.relative_layout)
    RelativeLayout mRelativeLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void setStatus(boolean isfitsSystemWindows, boolean isBlack) {
        super.setStatus(false, isBlack);
//        视频为了避免闪屏和透明问题，需要如下设置
//        a)网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            try {
                mIntentUrl = new URL(intent.getData().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void initView() {
        mWebView = new X5WebView(this, null);

//        mWebView.setWebViewClient(new MyWebViewClient(this));
        mWebView.setWebViewClient(new MyWebViewClient(this, mBaseLoadService));
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());  //设置应用缓存目录
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath()); //设置数据库缓存路径
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());//设置定位的数据库路径
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mWebView.setHorizontalScrollBarEnabled(false); //横向不显示滑动小方块
        mWebView.setVerticalScrollBarEnabled(false);//竖向不显示滑动小方块
        // 下面方法去掉
        IX5WebViewExtension ix5 = mWebView.getX5WebViewExtension();
        if (ix5 != null) {
            ix5.setScrollBarFadingEnabled(false);
        }
        mWebView.addJavascriptInterface(new X5WebViewJS(this, mWebView), "INanMing");//与js进行交互

//        mWebView.setWebChromeClient(chromeClient);

//        if (mIntentUrl == null) {
//            mWebView.loadUrl(mHomeUrl);
//        } else {
//        mWebView.loadUrl(mIntentUrl.toString());
//        }
        if (mIntentUrl != null) {
            mWebView.loadUrl(mIntentUrl.toString());
            if (mBaseLoadService != null) {
                mBaseLoadService.showSuccess();
            }
        }
        long time = System.currentTimeMillis();
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(mViewParent);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);

        switch (event.getCode()) {
            case EventCode.ME_ADDRESS_B:
                mWebView.loadUrl("javascript:refresh(0)");
                break;
        }
    }

    @Override
    protected void request() {
        super.request();
//        mBaseLoadService.showSuccess();
        mWebView.onResume();
        //刷新当前页
        mWebView.reload();
        ToastUtils.showLong("刷新了");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
                + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    //返回
    @OnClick(R.id.iv_go_back)
    void goBack() {
        onBackPressed();
    }

    //关闭
    @OnClick(R.id.iv_finish)
    void goFinish() {
        this.finish();
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
//            moveTaskToBack(true);//返回，但不finish，仍然保活
//            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    public static void goToWebView(Context context, String url) {

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
