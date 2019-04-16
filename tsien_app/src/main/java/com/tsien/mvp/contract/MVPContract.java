package com.tsien.mvp.contract;

import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.mvp.base.BasePresenter;
import com.tsienlibrary.mvp.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public interface MVPContract {

    interface View extends BaseView {


        //请求接口前判断是否可以正常访问接口
        boolean isConnectedNet();

        //请求成功后调用
        void requestSuccess(String requestUrl, CommonBean commonBean);

        //请求成功后调用
        void requestListSuccess(String requestUrl, CommonBeanList commonBeanList);

//        //请求失败后调用
//        void requestFail(String requestUrl, String msg);

        //请求失败后调用
        void requestFail(String requestUrl,String msg, int code);

        //所以权限已授予
        void permissionsAreGranted(int type);

        //至少有一个人拒绝了“永远不要再问”的许可,需要去设置
        void goToSettings();
    }


    interface Presenter extends BasePresenter<View> {


        /**
         * 请求接口
         *
         * @param requestUrl 接口地址
         * @param jsonData   请求的 json
         * @param clazz      返回的数据需要解析的bean
         */
        void request(String requestUrl, String jsonData, Class clazz);

        /**
         * 请求接口（返回值是List类型的）
         *
         * @param requestUrl 接口地址
         * @param jsonData   请求的 json
         * @param clazz      返回的数据需要解析的bean
         */
        void requestList(String requestUrl, String jsonData, Class clazz);

        /**
         * get请求接口（返回值是List类型的）
         *
         * @param requestUrl 接口地址
         * @param clazz      返回的数据需要解析的bean
         */
        void requestWithGet(String requestUrl,  Class clazz);
        /**
         * get请求接口（返回值是List类型的）
         *
         * @param requestUrl 接口地址
         * @param clazz      返回的数据需要解析的bean
         */
        void requestWithGetList(String requestUrl,  Class clazz);

        /**
         * 文件上传
         *
         * @param requestUrl 接口地址
         * @param files      上传的文件
         * @param clazz      返回的数据需要解析的bean
         */
        void uploadFiles(String requestUrl, Class clazz, File... files);

        /**
         * 设置应用权限
         *
         * @param rxPermissions
         * @param permissions
         */
        void setPermissions(RxPermissions rxPermissions, String... permissions);

        /**
         * 设置应用权限
         *
         * @param type
         * @param rxPermissions
         * @param permissions
         */
        void setPermissions(final int type, RxPermissions rxPermissions, String... permissions);

    }
}
