package com.hdnz.inanming.webview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

//自定义x5WebView
public class X5WebView extends WebView {
    private static final String TAG = "x5webview";
    ProgressView mProgressview; //自定义WebView加载进度条
    TextView title;
    Context context;

    public X5WebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initWebViewSettings();//初始化setting配置
        this.setWebViewClient(webViewClient);
        this.setWebChromeClient(chromeClient);
        this.setDownloadListener(downloadListener);
        initProgressBar();//初始化进度条
        this.getView().setClickable(true);
    }

    //setting配置
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);//允许js调用
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setAllowFileAccess(true);//在File域下，能够执行任意的JavaScript代码，同源策略跨域访问能够对私有目录文件进行访问等
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);//控制页面的布局(使所有列的宽度不超过屏幕宽度)
        webSetting.setSupportZoom(true);//支持页面缩放
        webSetting.setBuiltInZoomControls(true);//进行控制缩放
        webSetting.setAllowContentAccess(true);//是否允许在WebView中访问内容URL（Content Url），默认允许
        webSetting.setUseWideViewPort(true);//设置缩放密度
        webSetting.setSupportMultipleWindows(false);//设置WebView是否支持多窗口,如果为true需要实现onCreateWindow(WebView, boolean, boolean, Message)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            webSetting.setMixedContentMode(webSetting.getMixedContentMode());//设置安全的来源
        }
        webSetting.setAppCacheEnabled(true);//设置应用缓存
        webSetting.setDomStorageEnabled(true);//DOM存储API是否可用
        webSetting.setGeolocationEnabled(true);//定位是否可用
        webSetting.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面，
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);//设置应用缓存内容的最大值
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);//设置是否支持插件
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);//重写使用缓存的方式
        webSetting.setAllowUniversalAccessFromFileURLs(true);//是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
        webSetting.setAllowFileAccessFromFileURLs(true);//是否允许运行在一个URL环境
    }

    //进度条
    private void initProgressBar() {
        mProgressview = new ProgressView(context);
        mProgressview.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
        mProgressview.setDefaultColor(getResources().getColor(R.color.colorTheme));
        addView(mProgressview);
    }

    //客户端配置
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //这里直接加载url
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //处理客户端与WebView同步，具体细节问题请看最上面传送门
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String endCookie = cookieManager.getCookie(url);
            LogUtils.i(TAG, "onPageFinished: endCookie : " + endCookie);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();//同步cookie
            } else {
                CookieManager.getInstance().flush();
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            //网页问题报错的时候执行
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            if (sslError.getPrimaryError() == android.net.http.SslError.SSL_INVALID) {// 校验过程遇到了bug
                //这里直接忽略ssl证书的检测出错问题，选择继续执行页面
                sslErrorHandler.proceed();
            } else {
                //不是证书问题时候则停止执行加载页面
                sslErrorHandler.cancel();
            }
        }
    };

    //x5浏览器配置可视频播放、文件下载
    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                   JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;

        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view,
                                     IX5WebChromeClient.CustomViewCallback customViewCallback) {
            FrameLayout normalView = null;
            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
            viewGroup.removeView(normalView);
            viewGroup.addView(view);
            myVideoView = view;
            myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgressview.setProgress(i);
        }

        @Override
        public boolean onJsAlert(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2,
                                 com.tencent.smtt.export.external.interfaces.JsResult arg3) {
            /**
             * 这里写入你自定义的window alert
             */
            return super.onJsAlert(null, arg1, arg2, arg3);
        }
    };
    //下载监听器
    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String arg0, String arg1, String arg2,
                                    String arg3, long arg4) {
            new AlertDialog.Builder(context)
                    .setTitle("allow to download？")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    ToastUtils.showLong("fake message: i'll download...");
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                    ToastUtils.showShort("fake message: refuse download..");
                                }
                            })
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // TODO Auto-generated method stub

                                    ToastUtils.showShort("fake message: refuse download..");
                                }
                            }).show();
        }
    };

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        canvas.restore();
        return ret;
    }


    //======================================下面代码是为了解决X5WebView 与 下拉刷新 滑动冲突问题==================================//
    private OnScrollChangedCallback mOnScrollChangedCallback;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }


    public interface OnScrollChangedCallback {
        void onScroll(int l, int t);
    }
}
