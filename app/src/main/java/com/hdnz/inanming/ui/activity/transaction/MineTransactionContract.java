package com.hdnz.inanming.ui.activity.transaction;

import com.tsienlibrary.mvp.base.BasePresenter;
import com.tsienlibrary.mvp.base.BaseView;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionContract.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-16 18:55
 * Description: 我的办理activity契约类
 * Version:     V1.0.0
 * History:     历史信息
 */
public interface MineTransactionContract {
    /**
     * 视图接口
     *
     * @param <T>
     */
    interface View<T> extends BaseView {
        /**
         * 请求成功后调用
         *
         * @param t
         */
        void requestSuccess(T t);

        /**
         * 请求失败后调用
         *
         * @param msg
         */
        void requestFail(String msg);
    }

    /**
     * 请求业务接口
     */
    interface Presenter extends BasePresenter<View> {
        /**
         * 获取我的办理tab选项卡数据
         *
         * @param url
         * @param josn
         */
        void getTabTitles(String url, String josn);

        /**
         * 查看详情
         *
         * @param url
         * @param josn
         * @param type
         */
        void seeDetail(String url, String josn, String type);

        /**
         * 查看审核进度详情
         *
         * @param url
         * @param josn
         */
        void seeCheckProgressDetail(String url, String josn);
    }
}
