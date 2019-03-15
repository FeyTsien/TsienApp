package com.hdnz.inanming.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.certification.idCard.IdCardAuthenticationActivity;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;

import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class AuthenticationActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    @BindString(R.string.real_name_authentication)
    String mTitle;

    @BindView(R.id.frame_layout)
    FrameLayout mLayout;
    @BindView(R.id.rl_check)
    RelativeLayout rlCheck;
    @BindView(R.id.tv_check_fail)
    TextView tvCheckFail;
    @BindView(R.id.btn_re_authentication)
    Button btnReAuthentication;
    @BindView(R.id.rl_check_fail)
    RelativeLayout rlCheckFail;
    @BindView(R.id.tv_check_sex)
    TextView tvCheckSex;
    @BindView(R.id.tv_check_nation)
    TextView tvCheckNation;
    @BindView(R.id.tv_check_birthday)
    TextView tvCheckBirthday;
    @BindView(R.id.tv_check_address)
    TextView tvCheckAddress;
    @BindView(R.id.tv_check_idcard)
    TextView tvCheckIdcard;
    @BindView(R.id.rl_check_success)
    LinearLayout rlCheckSuccess;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_normal)
    LinearLayout llNormal;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initData() {
        request();
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, mTitle);
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(mLayout);
    }

    @Override
    protected void request() {
        super.request();
        mPresenter.request(UrlUtils.AUTHENTICATION_STATUS, "", Map.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        Map map = (Map) commonBean.getData();
        if (map == null) {
            return;
        }
        String authStatus = (String) map.get("auditResult");
        AppData.setValueStr(AppData.KEY_AUTHENTICATION_STATUS, authStatus);

        llNormal.setVisibility(View.GONE);
        rlCheck.setVisibility(View.GONE);
        rlCheckSuccess.setVisibility(View.GONE);
        rlCheckFail.setVisibility(View.GONE);

        if (TextUtils.equals(authStatus, "0")) {
            llNormal.setVisibility(View.VISIBLE);
            mTitle = getResources().getString(R.string.real_name_authentication);
        } else if (TextUtils.equals(authStatus, "1")) {
            mTitle = "审核中";
            rlCheck.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(authStatus, "2")) {
            mTitle = "认证成功";
            rlCheckSuccess.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(authStatus, "3")) {
            mTitle = "认证失败";
            rlCheckFail.setVisibility(View.VISIBLE);
        }
        setToolBar(mToolbar, tvTitle, mTitle);
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }

    @OnClick({R.id.tv_right_menu, R.id.rl_alipay_normal, R.id.rl_idcard_normal, R.id.btn_re_authentication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                ToastUtils.showShort("点击");
                break;
            case R.id.rl_alipay_normal:
                ToastUtils.showShort("跳转到支付宝认证");
                break;
            case R.id.rl_idcard_normal:
                ToastUtils.showShort("跳转到身份证认证");
                ActivityUtils.startActivity(new Intent(this, IdCardAuthenticationActivity.class));
//                ActivityUtils.startActivity(new Intent(this, IdCardAuthenticationActivity.class));
                break;
            case R.id.btn_re_authentication:
                //重新审核
                ToastUtils.showShort("重新审核");
                //根据审核方法，跳转不同页面，现在默认跳转身份证审核页面
                Intent intent = new Intent(this, IdCardAuthenticationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("checkType", "身份证");
                //以及传递的数据，例如用户信息
                intent.putExtras(bundle);
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
