package com.hdnz.inanming.ui.activity.setting.alter;

import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.UserBean;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    SettingActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-05 09:35
 * Description: 设置更换密码activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class AlterPasswordActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private String mPassword;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvTitleMenu;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password_confirm)
    EditText etNewPasswordConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_password;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.setting_password));
        tvTitleMenu.setVisibility(View.VISIBLE);
        //设置save文本，为“完成”
        tvTitleMenu.setText(R.string.setting_finish);
    }

    @OnClick({R.id.tv_right_menu, R.id.tv_toggle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                request();
                break;
            case R.id.tv_toggle:
                //切换布局
                ToastUtils.showShort("验证码方式修改...");
                break;
        }
    }


    @Override
    protected void request() {
        if (!TextUtils.equals(AppData.getValueStr(AppData.KEY_PASSWORD), MD5Util.getMD5(etOldPassword.getText().toString()))) {
            ToastUtils.showShort("原始密码输入错入");
            return;
        }
        if (!TextUtils.equals(etNewPasswordConfirm.getText().toString(), etNewPassword.getText().toString())) {
            ToastUtils.showShort("两次新密码不一致，请重新输入");
            return;
        }
        showProgressDialog("重置密码中");
        mPassword = MD5Util.getMD5(etNewPassword.getText().toString());
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setPassword(mPassword);
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.FORGET_PWD, jsonData, UserBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        ToastUtils.showShort("修改密码成功");
        dismissDialog();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        dismissDialog();
    }

}
