package com.hdnz.inanming.ui.activity.setting;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.bean.result.UserBean;
import com.hdnz.inanming.ui.activity.login.LoginActivity;
import com.hdnz.inanming.ui.activity.setting.alter.AlterPasswordActivity;
import com.hdnz.inanming.ui.activity.setting.alter.AlterPhoneActivity;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    SettingActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-05 09:35
 * Description: 设置更换手机号activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class SettingActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_set_phone)
    RelativeLayout rlSetPhone;
    @BindView(R.id.rl_set_password)
    RelativeLayout rlSetPassword;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.setting));
        tvPhone.setText(AppData.getValueStr(AppData.KEY_PHONE_NUMBER));
    }

    @OnClick({R.id.rl_set_phone, R.id.rl_set_password, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_set_phone:
                ActivityUtils.startActivity(new Intent(this, AlterPhoneActivity.class));
                break;
            case R.id.rl_set_password:
                ActivityUtils.startActivity(new Intent(this, AlterPasswordActivity.class));
                break;
            case R.id.btn_login_out:
                request();
                break;
        }
    }

    @Override
    protected void request() {
        showProgressDialog("退出中");
        mPresenter.request(UrlUtils.LOG_OUT, "", UserBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        ActivityUtils.startActivity(LoginActivity.class);
        for (AppCompatActivity activity : mActivities) {
            //关闭所有页
            activity.finish();
        }
        dismissDialog();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        dismissDialog();
    }

}
