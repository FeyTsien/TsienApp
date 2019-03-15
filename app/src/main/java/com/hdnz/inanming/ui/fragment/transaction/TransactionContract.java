package com.hdnz.inanming.ui.fragment.transaction;


import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.mvp.base.BasePresenter;
import com.tsienlibrary.mvp.base.BaseView;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionContract.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-14 18:55
 * Description: 我的办理fragment契约类
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TransactionContract {
    /**
     * 视图接口
     *
     * @param <T>
     */
    interface View<T> extends BaseView {
        /**
         * 请求成功后调用
         * @param t
         * @param isFirstLoad
         */
        void requestSuccess(T t, Boolean isFirstLoad);

        /**
         * 请求失败后调用
         * @param msg
         */
        void requestFail(String msg);

        /**
         * 所有权限已授予
         * @param attachView
         * @param position
         */
        void permissionsAreGranted(android.view.View attachView, int position);

        /**
         * 至少有一个人拒绝了“永远不要访问”的许可,需要去设置
         */
        void goToSettings();
    }

    /**
     * 请求业务接口
     */
    interface Presenter extends BasePresenter<View> {
        /**
         * 获取所以办理业务数据
         *
         * @param url
         * @param start
         * @param end
         * @param josn
         * @param isFirstLoad
         */
        void getVocationalWorkList(String url, Integer start, Integer end, String josn, Boolean isFirstLoad);

        /**
         * 获取网格员基本信息数据
         *
         * @param url
         * @param josn
         */
        void getGridPersional(String url, String josn);

        /**
         * 提交评论
         *
         * @param url
         * @param josn
         */
        void submitComment(String url, String josn);

        /**
         * 查看审核详情
         *
         * @param url
         * @param josn
         */
        void seeCheckDetail(String url, String josn);

        /**
         * 设置应用权限(请求存储和拍照权限)
         *
         * @param rxPermissions
         * @param permissions
         */
        void setPermissions(RxPermissions rxPermissions, android.view.View attachView, int position, String... permissions);
    }
}
