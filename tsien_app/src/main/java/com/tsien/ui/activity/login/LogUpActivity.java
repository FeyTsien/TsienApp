package com.tsien.ui.activity.login;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tsien.R;
import com.tsien.app.AppData;
import com.tsien.bean.request.RequestBean;
import com.tsien.bean.result.UserBean;
import com.tsienlibrary.bean.CommonBean;
import com.tsien.mvp.view.MVPActivity;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.ui.activity.main.MainActivity;
import com.tsien.utils.UrlUtils;
import com.tsienlibrary.ui.widget.CountDownButton;
import com.tsienlibrary.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

public class LogUpActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mCode;
    private String mPassword;

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
    @BindView(R.id.et_password)
    EditText mEtPassword;
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
        return R.layout.activity_log_up;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.log_up);
        mTvRightMenu.setText(R.string.log_in);
        mTvRightMenu.setVisibility(View.VISIBLE);
    }

    /**
     * TODO:切换到登录
     */
    @OnClick(R.id.tv_right_menu)
    void goToLogin() {
        finish();
    }

    /**
     * TODO:点击监听
     */
    @OnClick({R.id.btn_send_sms_code, R.id.btn_submit})
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
                paramsBean.setType(LoginCode.TYPE_LOG_UP);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.GET_SMS_CODE, jsonData, UserBean.class);
                break;
            case R.id.btn_submit:
                //提交注册
                showProgressDialog("注册中");
                mCode = mEtCode.getText().toString();
                mPassword = MD5Util.getMD5(mEtPassword.getText().toString());
                paramsBean.setUserName(mPhoneNumber);
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setSmsCode(mCode);
                paramsBean.setSmsType(LoginCode.TYPE_LOG_UP);
                paramsBean.setPassword(mPassword);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.LOG_UP, jsonData, UserBean.class);
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
        } else if (requestUrl.equals(UrlUtils.LOG_UP)) {
            ToastUtils.showShort("注册成功");
            //保存最新登录信息
            AppData.saveLogin(userBean);
            AppData.setValueStr(AppData.KEY_PASSWORD, mPassword);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            for (AppCompatActivity activity : mActivities) {
                //同时关闭注册页、登录页
                activity.finish();
            }
        }

        dismissDialog();

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
        dismissDialog();
        mTvErrors.setVisibility(View.VISIBLE);
        mTvErrors.setText(msg);
        LogUtils.i(TAG, msg);
    }
}