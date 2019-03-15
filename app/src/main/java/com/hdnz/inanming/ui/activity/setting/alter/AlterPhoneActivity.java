package com.hdnz.inanming.ui.activity.setting.alter;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.UserBean;
import com.hdnz.inanming.ui.activity.login.LoginCode;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.CountDownButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    SettingActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-06 10:35
 * Description: 修改手机号activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class AlterPhoneActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    private String mPhoneNumber;
    private String mCode;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone_info)
    TextView tvPhoneInfo;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_send_sms_code)
    CountDownButton cdbCode;
    @BindView(R.id.btn_alter_confirm)
    Button btnAlterConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_phone;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, R.string.setting_alter_phone_number);
    }


    @OnClick({R.id.btn_send_sms_code, R.id.btn_alter_confirm})
    void onClicks(View view) {
        mPhoneNumber = etPhone.getText().toString();
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
                showProgressDialog("发送验证码");
                if (mPhoneNumber.equals(AppData.getValueStr(AppData.KEY_PHONE_NUMBER))) {
                    ToastUtils.showLong("输入的手机号码不能与当前手机号码相同");
                    return;
                }
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setType(LoginCode.TYPE_CHANGE_PHONE_NUMBER);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.GET_SMS_CODE, jsonData, UserBean.class);
                break;
            case R.id.btn_alter_confirm:
                showProgressDialog("重置手机号中");
                mCode = etSmsCode.getText().toString();
//                paramsBean.setUserName(mPhoneNumber);
                paramsBean.setPhoneNumber(mPhoneNumber);
                paramsBean.setSmsCode(mCode);
                paramsBean.setSmsType(LoginCode.TYPE_CHANGE_PHONE_NUMBER);
                requestBean.setParams(paramsBean);
                jsonData = gson.toJson(requestBean);
                request(UrlUtils.UPDATE_PHONE_NUMBER, jsonData, UserBean.class);
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
            if (cdbCode.isFinish()) {
                //发送验证码请求成功后调用
                cdbCode.start();
                etSmsCode.setText(userBean.getSmsCode());
            }
        } else if (requestUrl.equals(UrlUtils.UPDATE_PHONE_NUMBER)) {
            ToastUtils.showShort("修改手机号成功");
            AppData.setValueStr(AppData.KEY_PHONE_NUMBER,mPhoneNumber);
            finish();
        }
        dismissDialog();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        ToastUtils.showLong(msg);
        dismissDialog();
    }
}
