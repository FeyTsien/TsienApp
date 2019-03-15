package com.hdnz.inanming.ui.fragment.me;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.me.AboutUsActivity;
import com.hdnz.inanming.ui.activity.me.AuthenticationActivity;
import com.hdnz.inanming.ui.activity.me.CollectActivity;
import com.hdnz.inanming.ui.activity.me.MyInfoActivity;
import com.hdnz.inanming.ui.activity.me.MyIntegralActivity;
import com.hdnz.inanming.ui.activity.me.MyLicenseListActivity;
import com.hdnz.inanming.ui.activity.me.MyReservationActivity;
import com.hdnz.inanming.ui.activity.setting.SettingActivity;
import com.hdnz.inanming.ui.activity.me.MyTransactionActivity;
import com.hdnz.inanming.utils.GlideUtils;
import com.hdnz.inanming.utils.UrlUtils;
import com.hdnz.inanming.webview.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    //    @BindView(R.id.iv_header)
//    ImageView mIvHeader;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.iv_header_img)
    ImageView mIvHeaderImg;
    @BindView(R.id.tv_my_name)
    TextView mTvMyName;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumberr;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setUserInfo();
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
            }

        });
    }

    /**
     * 刷新用户信息
     */
    private void setUserInfo() {

//        //以前的方式
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.test)
//                .error(R.drawable.empty);
//        Glide.with(this).load(R.mipmap.bg_home4).apply(requestOptions).into(mIvHeader);

        GlideApp.with(this)
                .load(GlideUtils.getGlideUrl(AppData.getValueStr(AppData.KEY_PORTRAIT_URL)))
                .placeholder(R.drawable.test)
                .error(R.drawable.empty)
                .into(mIvHeaderImg);
        mTvMyName.setText(!TextUtils.isEmpty(AppData.getValueStr(AppData.KEY_NICKNAME)) ? AppData.getValueStr(AppData.KEY_NICKNAME) : "无昵称");
        mTvPhoneNumberr.setText(AppData.getValueStr(AppData.KEY_PHONE_NUMBER));
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        //订阅EventBus,返回true.
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.MAIN_ME_A:
                setUserInfo();
                break;
        }
    }


    // TODO: 2018/11/2 我的资料
    @OnClick(R.id.ll_my_profile)
    void goToMyProfile() {
//        //此处为入口。照着写
        ActivityUtils.startActivity(new Intent(getActivity(), MyInfoActivity.class));
    }


    // TODO: 2018/11/2 我的收藏
    @OnClick(R.id.rl_collect)
    void goToCollect() {
        ActivityUtils.startActivity(new Intent(getActivity(), CollectActivity.class));

    }

    // TODO: 2018/11/2 我的积分
    @OnClick(R.id.rl_integral)
    void goToMyIntegral() {
        ActivityUtils.startActivity(new Intent(getActivity(), MyIntegralActivity.class));
    }

    // TODO: 2018/11/2 我的订单
    @OnClick(R.id.rl_my_order)
    void goToMyOrder() {
//        ActivityUtils.startActivity(new Intent(getActivity(), MyOrderActivity.class));
        WebViewActivity.goToWebView(getActivity(), UrlUtils.H5_SHOP_BASE_URL + UrlUtils.H5_SHOP_ORDER_LIST);
    }

    // TODO: 2018/11/2 实名认证
    @OnClick(R.id.rl_real_name_authentication)
    void goToRealNameAuthentication() {
        //此处为入口。照着写
        ActivityUtils.startActivity(new Intent(getActivity(), AuthenticationActivity.class));
    }

    // TODO: 2018/11/2 我的证照
    @OnClick(R.id.rl_my_license)
    void goToMyLicense() {
        ActivityUtils.startActivity(new Intent(getActivity(), MyLicenseListActivity.class));
    }

    // TODO: 2018/11/2 我的办理
    @OnClick(R.id.rl_my_transaction)
    void goToMytransaction() {
        ActivityUtils.startActivity(new Intent(getActivity(), MyTransactionActivity.class));
    }

    // TODO: 2018/11/2 我的预约
    @OnClick(R.id.rl_my_reservation)
    void goToMyReservation() {
        ActivityUtils.startActivity(new Intent(getActivity(), MyReservationActivity.class));
//        ActivityUtils.startActivity(new Intent(getActivity(), GovernmentMessagesActivity.class));
    }

    // TODO: 2018/11/2 帮助反馈
    @OnClick(R.id.rl_help_feedback)
    void goToHelpFeedback() {

//        ActivityUtils.startActivity(new Intent(getActivity(),帮助反馈.class));
    }

    // TODO: 2018/11/2 关于我们
    @OnClick(R.id.rl_about_us)
    void goToAboutUs() {
        ActivityUtils.startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    // TODO: 2018/11/2 设置
    @OnClick(R.id.iv_setup)
    void goToSetting() {
        ActivityUtils.startActivity(new Intent(getActivity(), SettingActivity.class));
    }
}
