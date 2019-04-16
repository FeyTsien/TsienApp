package com.tsien.ui.activity.login;


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
import com.tsien.R;
import com.tsien.app.AppData;
import com.tsien.bean.request.RequestBean;
import com.tsien.bean.result.UserBean;
import com.tsien.jpush.AliasTagUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsien.mvp.view.MVPActivity;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.ui.activity.main.MainActivity;
import com.tsien.utils.UrlUtils;
import com.tsienlibrary.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class LoginPwdLoginActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static String TAG = "LoginActivity";
    private String mPhoneNumber;
    private String mPassword;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_menu)
    TextView mTvRightMenu;
    @BindView(R.id.et_phone_number)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_errors)
    TextView mTvErrors;

//    /**
//     * 返回
//     */
//    @OnClick(R.id.iv_back)
//    public void goBack() {
//        finish();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_password;
    }


    @Override
    protected void initData() {
        mPhoneNumber = getIntent().getStringExtra(LoginActivity.PHONE_NUMBER);
    }


    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.password_login);
        mTvRightMenu.setText(R.string.log_up);
        mTvRightMenu.setVisibility(View.VISIBLE);
        mEtPhone.setText(mPhoneNumber);
    }


    /**
     * TODO:点击事件监听
     *
     * @param view
     */
    @OnClick({R.id.tv_right_menu, R.id.tv_code_login, R.id.tv_forget_password, R.id.btn_login})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                //跳到注册
                ActivityUtils.startActivity(new Intent(this, LogUpActivity.class));
                break;
            case R.id.tv_code_login:
                //切换到验证码登录
                Intent intent1 = new Intent(this, LoginActivity.class);
                intent1.putExtra(LoginActivity.PHONE_NUMBER, mEtPhone.getText().toString());
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_forget_password:
                //忘记密码
                Intent intent2 = new Intent(this, ForgetPasswordActivity.class);
                intent2.putExtra(LoginActivity.PHONE_NUMBER, mEtPhone.getText().toString());
                startActivity(intent2);
                break;
            case R.id.btn_login:
                //登录
                mPhoneNumber = mEtPhone.getText().toString();
                mPassword = MD5Util.getMD5(mEtPassword.getText().toString());
                request();
                break;
        }
    }

    @Override
    protected void request() {
        if (!RegexUtils.isMobileSimple(mPhoneNumber)) {
            ToastUtils.showShort("手机号格式不正确");
            return;
        }
        showProgressDialog("登录中");
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setUserName("");
        paramsBean.setPhoneNumber(mPhoneNumber);
        paramsBean.setLoginType(LoginCode.LOGIN_TYPE_PWD);
        paramsBean.setPassword(mPassword);
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.LOGIN, jsonData, UserBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        UserBean userBean = (UserBean) commonBean.getData();

        //注册极光推送的别名
        setAlias(userBean.getUserAccountEntity().getId());
        //保存最新登录信息
        AppData.saveLogin(userBean);
        AppData.setValueStr(AppData.KEY_PASSWORD, mPassword);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
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