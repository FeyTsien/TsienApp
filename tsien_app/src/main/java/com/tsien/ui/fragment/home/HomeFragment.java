package com.tsien.ui.fragment.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsien.R;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.mvp.view.MVPFragment;
import com.tsien.ui.activity.home.ScanActivity;
import com.tsien.utils.UrlUtils;
import com.tsien.webview.WebViewActivity;
import com.tsien.webview.WebViewJS;
import com.tsienlibrary.bean.CommonBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/05
 *     desc   :====== 首页 ====
 * </pre>
 */
public class HomeFragment extends MVPFragment<MVPContract.View, MVPPresenter> {
    private RxPermissions rxPermissions;
    private boolean webIsOnTop = true;          //判断webview内容是否滑动到顶部
    private boolean isOpenTwoLevel = false;     //判断是否开启二楼


    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.twoLevel_header)
    TwoLevelHeader mTwoLevelHeader;
    @BindView(R.id.webview)
    WebView mWebView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initView() {

        initWebView();
        String HomeUrl = UrlUtils.H5_HOME_BASE_URL + UrlUtils.H5_HOME;
//        String HomeUrl = UrlUtils.H5_SHOP_BASE_URL + UrlUtils.H5_HOME;
        mWebView.loadUrl(HomeUrl);
        mWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                if (i1 == 0) {//webView在顶部
                    webIsOnTop = true;
                } else {//webView不是顶部
                    webIsOnTop = false;
                }
            }
        });

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下
                        break;
                    case MotionEvent.ACTION_UP:
                        // 松开
                        if (webIsOnTop) {
                            //如果web回到顶部，按压松开后，打开刷新控件监听
                            mSmartRefreshLayout.setEnableRefresh(true);
                        } else {
                            //如果web没有回到顶部，按压松开后，依然关闭刷新控件监听
                            mSmartRefreshLayout.setEnableRefresh(false);
                        }
                    default:
                        break;
                }
                return false;
            }
        });

        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                mWebView.loadUrl(UrlUtils.H5_HOME_BASE_URL + UrlUtils.H5_HOME);
            }
        });

        //二楼头部
        mTwoLevelHeader.setEnablePullToCloseTwoLevel(false);//禁止在二极状态是上滑关闭状态回到初态
        mTwoLevelHeader.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                //开启二楼
                ToastUtils.showShort("二楼开启");
                isOpenTwoLevel = true;
//                GlideApp.with(HomeFragment.this).load(R.mipmap.bg_home2).placeholder(R.mipmap.xingkong).into(mIvBg);
                return true;
            }
        });
    }


    private void initWebView() {
        //不显示滑动小方块
        //添加与js进行交互的约定名
        mWebView.addJavascriptInterface(new WebViewJS(getContext(), mWebView), "INanMing");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (TextUtils.isEmpty(url)) {
                return false;
//                }
//                if (url.startsWith("http:") || url.startsWith("https:")) {
//                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                }
//                view.loadUrl(url);
//                return true;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //回调加载成功的函数
            }
        });
    }

    @OnClick({R.id.iv_bg, R.id.btn_goto, R.id.iv_scan, R.id.iv_search, R.id.btn_start_instant})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.iv_bg:
//                mTwoLevelHeader.finishTwoLevel();
//                ToastUtils.showLong("点击了");
                WebViewActivity.goToWebView(getContext(), "https://www.bilibili.com");
                break;
            case R.id.btn_goto:
                WebViewActivity.goToWebView(getContext(), "http://www.dangdang.com");
                break;
            case R.id.iv_scan:
                ToastUtils.showLong("点击了iv_scan");
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};
                mPresenter.setPermissions(rxPermissions, permissions);
                break;
            case R.id.btn_start_instant:
                startInstantApp();
                break;
        }
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {

    }

    @Override
    public void permissionsAreGranted(int type) {
        Intent intent = new Intent(getContext(), ScanActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void goToSettings() {
        //前往设置界面
        AppUtils.launchAppDetailsSettings();
    }

    private static final int REQUEST_CODE = 1001;
    private static final int REQUEST_IMAGE = 1002;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.showLong("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showLong("解析二维码失败");
                }
            }
        }

    }


    @Override
    public boolean onBackPressed() {
        if (isOpenTwoLevel) {
            mTwoLevelHeader.finishTwoLevel();
            isOpenTwoLevel = false;
            return true;
        }
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }


    void startInstantApp(){
        ToastUtils.showShort("启动Instant App");
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.setData(Uri.parse("https://tsien.com/"));
//        it.setPackage();
        startActivity(intent);
    }

}