package com.hdnz.inanming.ui.fragment.transaction;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.mvp.base.BasePresenterImpl;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionPresenter.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-14 18:56
 * Description: 我的办理fragment业务处理类
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TransactionPresenter extends BasePresenterImpl<TransactionContract.View>
        implements TransactionContract.Presenter {
    @Override
    public void getVocationalWorkList(String url, Integer start, Integer end, String josn, Boolean isFirstLoad) {
        //判断缓存数据,避免再次请求数据
    }

    @Override
    public void getGridPersional(String url, String josn) {

    }

    @Override
    public void submitComment(String url, String josn) {

    }

    @Override
    public void seeCheckDetail(String url, String josn) {

    }

    @Override
    public void setPermissions(RxPermissions rxPermissions, View attachView, int position, String... permissions) {
        rxPermissions
                .requestEachCombined(permissions)
                // will emit 1 Permission object
                .subscribe(permission -> {
                    if (permission.granted) {
                        // All permissions are granted !
                        if (mView != null) {
                            mView.permissionsAreGranted(attachView, position);
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        ToastUtils.showShort("用户拒绝：" + permission.name + "权限");
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        LogUtils.e("xiaoxin  permission: " + permission.toString());
                        StringBuilder message = new StringBuilder();
                        message.append("部分权限未授权，评价功能不能正常使用，您是否跳转到设置界面重新授权？");
                        AlertDialog.Builder builder = new AlertDialog.Builder(attachView.getContext());
                        builder.setTitle("提示")
                                .setIcon(R.mipmap.ic_logo)
                                .setMessage(message.toString())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (mView != null) {
                                            mView.goToSettings();
                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create()
                                .show();
                    }
                });
    }
}
