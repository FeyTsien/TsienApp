package com.tsien.mvp.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonSyntaxException;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.model.HttpManager;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.mvp.base.BasePresenterImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/27
 *     desc   :
 * </pre>
 */
public class MVPPresenter extends BasePresenterImpl<MVPContract.View> implements MVPContract.Presenter {

    private final String TAG = "MVPPresenter";

    private Observer<ResponseBody> getObserver(String requestUrl, Class clazz, int type) {
       return new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i("onSubscribe==========");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                LogUtils.i("onNext==========");
                String jsons = "";
                try {
                    jsons = responseBody.string();
                    LogUtils.i("myRequest——"+TAG,"Json数据："+jsons);
                    if (type == 1) {
                        CommonBeanList commonBeanList = CommonBeanList.fromJson(jsons, clazz);
                        if (commonBeanList.getCode() == 200) {
                            if (mView != null) {
                                mView.requestListSuccess(requestUrl, commonBeanList);
                            }
                        } else {
                            if (mView != null) {
                                mView.requestFail(requestUrl, "CODE:" + commonBeanList.getCode() + " MSG:" + commonBeanList.getMsg(), commonBeanList.getCode());
                            }
                        }
                    } else {
                        CommonBean commonBean = CommonBean.fromJson(jsons, clazz);
                        if (commonBean.getCode() == 200) {
                            if (mView != null) {
                                mView.requestSuccess(requestUrl, commonBean);
                            }
                        } else {
                            if (mView != null) {
                                mView.requestFail(requestUrl, "CODE:" + commonBean.getCode() + " MSG:" + commonBean.getMsg(), commonBean.getCode());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    LogUtils.e("myRequest——"+TAG,"json数据和模板数据不统一");
                    try {
                        //json数据结构发生变化
                        JSONObject jsonObject = new JSONObject(jsons);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code != 200) {
                            mView.requestFail(requestUrl, "CODE:" + code + " MSG:" + msg, code);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }


            @Override
            public void onError(Throwable e) {
                LogUtils.i("myRequest——"+TAG,"onError:" + requestUrl + "===" + e.getMessage());
                //建议以下代码后期要做封装，优化
                if (e instanceof HttpException) {
                    //网络错误
                    HttpException exception = (HttpException) e;
                    if (mView != null) {
                        mView.requestFail(requestUrl, exception.getMessage(), exception.code());
                    }
                } else if (e instanceof SSLHandshakeException) {
                    //"证书验证失败"
                    if (mView != null) {
                        mView.requestFail(requestUrl, e.getMessage(), 0);
                    }
                } else if (e instanceof ConnectException) {
                    //"连接失败"
                    if (mView != null) {
                        mView.requestFail(requestUrl, e.getMessage(), 0);
                    }
                } else {
                    //未知错误
                    ToastUtils.showLong("未知错误：" + e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                LogUtils.i("onComplete==========");
            }
        };
    }

    private void request(String requestUrl, String jsonData, Class clazz, int type) {
        if (!mView.isConnectedNet()) {
            //请求接口前判断是否可以正常访问接口
            return;
        }

        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, type);
        HttpManager.getHttpManager().postJson(requestUrl, jsonData, observer);

    }

    private void requestWithGet(String requestUrl, Class clazz, int type) {

        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, type);
        HttpManager.getHttpManager().getMethod(requestUrl,observer);
    }

    @Override
    public void request(String requestUrl, String jsonData, Class clazz) {
        LogUtils.i("myRequest——"+TAG,requestUrl,jsonData);
        if (!mView.isConnectedNet()) {
            return;
        }
        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, 0);
        HttpManager.getHttpManager().postJson(requestUrl, jsonData, observer);
    }

    @Override
    public void requestList(String requestUrl, String jsonData, Class clazz) {
        LogUtils.i("myRequest——"+TAG,requestUrl,jsonData);
        if (!mView.isConnectedNet()) {
            return;
        }
        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, 1);
        HttpManager.getHttpManager().postJson(requestUrl, jsonData, observer);
    }

    @Override
    public void requestWithGet(String requestUrl, Class clazz) {
        LogUtils.i("myRequest——"+TAG,requestUrl);
        if (!mView.isConnectedNet()) {
            return;
        }
        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, 0);
        HttpManager.getHttpManager().getMethod(requestUrl,observer);
    }

    @Override
    public void requestWithGetList(String requestUrl, Class clazz) {
        LogUtils.i("myRequest——"+TAG,requestUrl);
        if (!mView.isConnectedNet()) {
            return;
        }
        Observer<ResponseBody> observer = getObserver(requestUrl, clazz, 1);
        HttpManager.getHttpManager().getMethod(requestUrl,observer);
    }

    @Override
    public void uploadFiles(String requestUrl, Class clazz, File... files) {

        if (!mView.isConnectedNet()) {
            //请求接口前判断是否可以正常访问接口
            return;
        }

        HttpManager.getHttpManager().uploadFiles(requestUrl, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.i("onSubscribe==========");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                LogUtils.i("onNext==========");
                try {
                    String jsons = responseBody.string();
                    CommonBean commonBean = CommonBean.fromJson(jsons, clazz);
                    if (commonBean.getCode() == 200) {
                        if (mView != null) {
                            mView.requestSuccess(requestUrl, commonBean);
                        }
                    } else {
                        if (mView != null) {
                            mView.requestFail(requestUrl, "CODE:" + commonBean.getCode() + " MSG:" + commonBean.getMsg(), commonBean.getCode());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                HttpException httpException = (HttpException) e;

                if (mView != null) {
                    mView.requestFail(requestUrl, e.getMessage(), httpException.code());
                }
                LogUtils.i("onError:" + requestUrl + "===" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.i("onComplete==========");
            }
        }, files);
    }

    @Override
    public void setPermissions(RxPermissions rxPermissions, String... permissions) {
        setPermissions(-1, rxPermissions, permissions);//不设置type,则默认-1
    }

    @SuppressLint("CheckResult")
    @Override
    public void setPermissions(final int type, RxPermissions rxPermissions, String... permissions) {

        rxPermissions
                .requestEachCombined(permissions)
                .subscribe(permission -> { // will emit 1 Permission object
                    if (permission.granted) {
                        // All permissions are granted !
//                        ToastUtils.showLong("所有请求的权限都被授予");
                        if (mView != null) {
                            mView.permissionsAreGranted(type);
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        ToastUtils.showLong("未经请求而被拒绝");
//                        if (mView != null) {
//                            mView.goToSettings();
//                        }
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        if (mView != null) {
                            mView.goToSettings();
                        }
                        ToastUtils.showLong("至少有一个人拒绝批准，但要求永远不再\n" +
                                "需要去设置");
                    }
                });


//        rxPermissions
//                .requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Permission>() {
//                               @Override
//                               public void accept(Permission permission) throws Exception {
//                                   if (permission.granted) {
//                                   } else if (permission.shouldShowRequestPermissionRationale) {
//                                       // Denied permission without ask never again
//                                   } else {
//                                       // Denied permission with ask never again
//                                       // Need to go to the settings
//                                   }
//                               }
//                           }, new Consumer<Throwable>() {
//                               @Override
//                               public void accept(Throwable t) {
//                                   LogUtils.e(TAG, "onError", t);
//                               }
//                           },
//                        new Action() {
//                            @Override
//                            public void run() {
//                                LogUtils.i(TAG, "OnComplete");
//                            }
//                        });
    }
}
