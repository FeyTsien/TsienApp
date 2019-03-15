package com.hdnz.inanming.ui.activity.login;


import android.content.Intent;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.UserBean;
import com.hdnz.inanming.jpush.AliasTagUtils;
import com.tsienlibrary.bean.CommonBean;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.ui.activity.main.MainActivity;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.ui.widget.CountDownButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static String TAG = "LoginActivity";
    public static final String PHONE_NUMBER = "phone_number";

    private String mPhoneNumber;
    private String mSmsCode;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_menu)
    TextView mTvRightMenu;
    @BindView(R.id.et_phone_number)
    EditText mEtPhone;
    @BindView(R.id.et_verification_code)
    EditText mEtCode;
    @BindView(R.id.tv_errors)
    TextView mTvErrors;
    @BindView(R.id.btn_send_sms_code)
    CountDownButton mCdbCode;

//    /**
//     * 返回
//     */
//    @OnClick(R.id.iv_back)
//    public void goBack() {
//        finish();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_code;
    }

    @Override
    protected void initData() {
        //进入登录页就清除用户登录信息
        AppData.clearLogin();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.verification_code_login);
        mTvRightMenu.setText(R.string.log_up);
        mTvRightMenu.setVisibility(View.VISIBLE);
        mEtPhone.setText(mPhoneNumber);
    }

    /**
     * TODO:切换到注册
     */
    @OnClick(R.id.tv_right_menu)
    public void goToLogUp() {
        ActivityUtils.startActivity(new Intent(this, LogUpActivity.class));
    }

    /**
     * TODO:密码登录
     */
    @OnClick(R.id.tv_password_login)
    public void goToPwdLogin() {
        Intent intent = new Intent(this, LoginPwdLoginActivity.class);
        intent.putExtra(PHONE_NUMBER, mEtPhone.getText().toString());
        startActivity(intent);
        finish();
    }

    /**
     * TODO:点击监听（获取验证码、登录）
     */
    @OnClick({R.id.btn_send_sms_code, R.id.btn_login})
    void onClicks(View view) {
        mPhoneNumber = mEtPhone.getText().toString();
        if (!RegexUtils.isMobileSimple(mPhoneNumber)) {
            ToastUtils.showShort("手机号格式不正确");
            return;
        }

        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        Gson gson = new Gson();
        String jsonData;
        switch (view.getId()) {
            case R.id.btn_send_sms_code:
                //获取验证码
                showProgressDialog("发送验证码");
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setType(LoginCode.TYPE_LOGIN);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.GET_SMS_CODE, jsonData, UserBean.class);
                break;
            case R.id.btn_login:
                //登录
                showProgressDialog("登录中");
                mSmsCode = mEtCode.getText().toString();
                paramsBean.setUserName("");
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setSmsCode(mSmsCode);
                paramsBean.setSmsType(LoginCode.TYPE_LOGIN);
                paramsBean.setLoginType(LoginCode.LOGIN_TYPE_CODE);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.LOGIN, jsonData, UserBean.class);
                break;
        }
    }

    @Override
    protected void request(String requestUrl, String jsonData, Class clazz) {
        mPresenter.request(requestUrl, jsonData, clazz);
        super.request(requestUrl, jsonData, clazz);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        UserBean userBean = (UserBean) commonBean.getData();
        if (requestUrl.equals(UrlUtils.GET_SMS_CODE)) {
            //这里判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
            if (mCdbCode.isFinish()) {
                //发送验证码请求成功后调用
                mCdbCode.start();
                mEtCode.setText(userBean.getSmsCode());
            }
        } else if (requestUrl.equals(UrlUtils.LOGIN)) {
            //注册极光推送的别名
            setAlias(userBean.getUserAccountEntity().getId());
            LogUtils.i("jpush", userBean.getUserAccountEntity().getId());
            //保存最新登录信息
            AppData.saveLogin(userBean);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        dismissDialog();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
        mTvErrors.setVisibility(View.VISIBLE);
        mTvErrors.setText(msg);
        LogUtils.i(TAG, msg);
        dismissDialog();
    }

    private void setAlias(String userId) {
        AliasTagUtils.TagAliasBean aliasBean = new AliasTagUtils.TagAliasBean();
        aliasBean.setAlias(userId);
        aliasBean.setAction(AliasTagUtils.ACTION_SET_ALIAS);
        AliasTagUtils.sequence++;
        AliasTagUtils.getInstance().handleAction(this, AliasTagUtils.sequence, aliasBean);
    }
}