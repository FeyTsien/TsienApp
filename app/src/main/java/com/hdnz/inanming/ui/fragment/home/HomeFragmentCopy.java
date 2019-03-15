package com.hdnz.inanming.ui.fragment.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.ui.activity.home.ScanActivity;
import com.hdnz.inanming.ui.activity.home.SearchActivity;
import com.hdnz.inanming.webview.WebViewActivity;
import com.hdnz.inanming.webview.X5WebView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tsienlibrary.bean.CommonBean;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
public class HomeFragmentCopy extends MVPFragment<MVPContract.View, MVPPresenter> {
    private String TAG = getClass().getSimpleName();
    private RxPermissions rxPermissions;

    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.twoLevel_header)
    TwoLevelHeader mTwoLevelHeader;
    @BindView(R.id.webview)
    X5WebView mWebView;

    public static HomeFragmentCopy newInstance() {
        return new HomeFragmentCopy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initView() {

//        Glide.with(this).load(R.mipmap.bg_home2).into(mIvBg);
        initWebView();
        mWebView.loadUrl("https://m.baidu.com");
        mWebView.setOnScrollChangedCallback(new X5WebView.OnScrollChangedCallback() {
            public void onScroll(int l, int t) {
                //Log.d(TAG, "We Scrolled etc..." + l + " t =" + t);
//                if(t<=0){
//                    mWebView.scrollTo(0,1);
//                }
                if (t == 0) {//webView在顶部
                    mSmartRefreshLayout.setEnableRefresh(true);
                } else {//webView不是顶部
                    mSmartRefreshLayout.setEnableRefresh(false);
                }
            }
        });
//        mWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//        });

        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                mWebView.loadUrl("https://m.baidu.com");
            }
        });
    }

    private void initWebView() {
        //不显示滑动小方块
        IX5WebViewExtension ix5 = mWebView.getX5WebViewExtension();
        if (ix5 != null) {
            ix5.setScrollBarFadingEnabled(false);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (TextUtils.isEmpty(url)) {
                    return false;
                }
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);
                return true;
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

    @OnClick({R.id.iv_bg, R.id.iv_scan, R.id.tv_search})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.iv_bg:
                mTwoLevelHeader.finishTwoLevel();
                ToastUtils.showLong("点击了");
                break;
            case R.id.iv_scan:
                ToastUtils.showLong("点击了iv_scan");
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};
                mPresenter.setPermissions(rxPermissions, permissions);
                break;
            case R.id.tv_search:
                //搜索
                ActivityUtils.startActivity(SearchActivity.class);
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
        Intent intent = new Intent(getActivity(), ScanActivity.class);
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
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

}