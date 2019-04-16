package com.tsien.mvp.view;

import com.blankj.utilcode.util.AppUtils;
import com.tsien.mvp.contract.MVPContract;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.mvp.base.MVPBaseService;

public abstract class MVPService<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseService<V, T> implements MVPContract.View {

    @Override
    public boolean isConnectedNet() {
        return false;
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonBeanList) {

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {

    }

    @Override
    public void permissionsAreGranted(int type) {

    }

    @Override
    public void goToSettings() {
        //前往设置界面
        AppUtils.launchAppDetailsSettings();
    }
}
