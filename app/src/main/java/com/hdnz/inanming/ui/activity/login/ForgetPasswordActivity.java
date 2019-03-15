package com.hdnz.inanming.ui.activity.login;


import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.UserBean;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.CountDownButton;
import com.tsienlibrary.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mCode;
    private String mPassword;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.forget_password);
    }

    @OnClick({R.id.btn_send_sms_code, R.id.btn_submit})
    void onClicks(View view) {
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        Gson gson = new Gson();
        String jsonData;

        mPhoneNumber = mEtPhone.getText().toString();
        if (!RegexUtils.isMobileSimple(mPhoneNumber)) {
            ToastUtils.showShort("手机号格式不正确");
            return;
        }
        switch (view.getId()) {
            case R.id.btn_send_sms_code:
                //获取验证码
                showProgressDialog("发送验证码");
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setType(LoginCode.TYPE_FORGET_PASSWORD);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.GET_SMS_CODE, jsonData, UserBean.class);
                break;
            case R.id.btn_submit:
                //重置密码
                showProgressDialog("重置密码中");
                mCode = mEtCode.getText().toString();
                mPassword = MD5Util.getMD5(mEtPassword.getText().toString());
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setSmsCode(mCode);
                paramsBean.setSmsType(LoginCode.TYPE_FORGET_PASSWORD);
                paramsBean.setPassword(mPassword);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.FORGET_PWD, jsonData, UserBean.class);
                break;
        }
    }

    @Override
    protected void request(String requestUrl, String jsonData, Class clazz) {
        super.request(requestUrl, jsonData, clazz);
        mPresenter.request(requestUrl, jsonData, clazz);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        UserBean userBean = (UserBean) commonBean.getData();
        if (requestUrl.equals(UrlUtils.GET_SMS_CODE)) {
            //这里判断是否倒计时结束，避免在倒计时时多次点击导致重复请求接口
            if (mCdbCode.isFinish()) {
                //发送验证码请求成功后调用
                mCdbCode.start();
                mEtCode.setText(userBean.getSmsCode());
            }
        } else if (requestUrl.equals(UrlUtils.FORGET_PWD)) {
            ToastUtils.showShort("重置密码成功");
            finish();
        }

        dismissDialog();

    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        dismissDialog();
        mTvErrors.setVisibility(View.VISIBLE);
        mTvErrors.setText(msg);
        LogUtils.i(TAG, msg);
    }
}