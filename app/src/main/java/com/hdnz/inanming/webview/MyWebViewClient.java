package com.hdnz.inanming.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.core.LoadService;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tsienlibrary.loadsir.callback.LoadingCallback;
import com.tsienlibrary.loadsir.callback.WebErrorCallback;

import androidx.appcompat.app.AlertDialog;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/13
 *     desc   :
 * </pre>
 */
public class MyWebViewClient extends WebViewClient {

    private Context mContext;
    private LoadService mLoadService;

    public MyWebViewClient(Context context) {
        mContext = context;
    }

    public MyWebViewClient(Context context, LoadService loadService) {
        mContext = context;
        mLoadService = loadService;
    }

    /**
     * 拦截 url 跳转,在里边添加点击链接跳转或者操作
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //注：以下return false 是继续执行X5Webview里的view.loadUrl(url);

        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.startsWith("http:") || url.startsWith("https:")) {
            if (openAlipay(view, url)) {//拦截阿里支付
                /*判断是否成功拦截,若成功拦截，则无需继续加载该URL；否则继续加载*/
                return true; //true,则不继续向下执行
            }
            // 可能有提示下载Apk文件
            if (url.contains(".apk")) {
                handleOtherwise(url);
                return true;
            }
            return false;
        }

        handleOtherwise(url);
        return true;
    }

    /**
     * 支付宝支付SDK调用（手机网站支付转native支付使用的接口）
     *
     * @param view
     * @param url
     */
    private boolean openAlipay(WebView view, String url) {
        /**
         * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
         */
        final PayTask task = new PayTask((Activity) mContext);
        boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
            @Override
            public void onPayResult(final H5PayResultModel result) {
                final String url = result.getReturnUrl();
                if (!TextUtils.isEmpty(url)) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.loadUrl(url);
                        }
                    });
                }
            }
        });

        return isIntercepted;

    }

    /**
     * 在开始加载网页时会回调
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }


    /**
     * 在结束加载网页时会回调
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        // html加载完成之后，添加监听图片的点击js函数
        super.onPageFinished(view, url);
        if (mLoadService != null) {
            if (mLoadService.getCurrentCallback() == LoadingCallback.class) {
                mLoadService.showSuccess();
            }
        }
    }

    /**
     * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        ToastUtils.showLong("onReceivedError:" + "加载错误");
        if (mLoadService != null) {
            mLoadService.showCallback(WebErrorCallback.class);
        }
    }

    /**
     * 当接收到https错误时，会回调此函数，在其中可以做错误处理
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        ToastUtils.showLong("onReceivedSslError:" + "https错误");
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return null;
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    /**
     * 网页里可能唤起其他的app
     */
    private void handleOtherwise(String url) {
        String appPackageName = "";
//        // 支付宝支付
//        if (url.contains("alipays")) {
//            appPackageName = "com.eg.android.AlipayGphone";
//
//            // 微信支付
//        } else if (url.contains("weixin://wap/pay")) {
//            appPackageName = "com.tencent.mm";
//
//            // 京东产品详情
//        } else if (url.contains("openapp.jdmobile")) {
//            appPackageName = "com.jingdong.app.mall";
//        } else {
        startActivity(url);
//        }
//        if (AppUtils.isAppInstalled(appPackageName)) {
//            startActivity(url);
//        }
    }

    private void startActivity(String url) {
        try {
            // 用于DeepLink测试
            if (url.startsWith("will://")) {
                Uri uri = Uri.parse(url);
                LogUtils.e("---------scheme", uri.getScheme() + "；host: " + uri.getHost() + "；Id: " + uri.getPathSegments().get(0));
            }
            showIntentDialog(mContext, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showIntentDialog(Context context, String url) {

        String appName = "";
        Intent intent1 = new Intent();
        intent1.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent1.setData(uri);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("提示")
                .setMessage("是否跳转到" + appName)
                .setCancelable(false)
                .setPositiveButton("确定", (dialog, which) -> ActivityUtils.startActivity(intent1))
                .setNegativeButton("取消", (dialog, which) -> {
                }).create().show();
    }
}
