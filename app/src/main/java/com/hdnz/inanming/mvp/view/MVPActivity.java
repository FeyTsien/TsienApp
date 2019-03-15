package com.hdnz.inanming.mvp.view;


import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.ui.activity.login.LoginActivity;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.loadsir.callback.Error404Callback;
import com.tsienlibrary.loadsir.callback.ErrorCallback;
import com.tsienlibrary.loadsir.callback.NetworkFailureCallback;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.mvp.base.MVPBaseActivity;

public abstract class MVPActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseActivity<V, T> implements MVPContract.View {

    private boolean isUseLoadSir = false;

    /**
     * TODO:请求接口前判断是否可以正常访问接口
     *
     * @return
     */
    @Override
    public boolean isConnectedNet() {
        if (!NetworkUtils.isConnected()) {
            //网络故障
            if (mBaseLoadService != null) {
                mBaseLoadService.showCallback(NetworkFailureCallback.class);
            } else {
                ToastUtils.showShort("网络故障");
            }
            return false;
        }
        return true;
    }

    @Override
    protected void request() {
        super.request();
        isUseLoadSir = true;//只有使用request()请求接口，才使用LoadSir，以便用于点击刷新
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        dismissDialog();
        if (mBaseLoadService != null) {
            mBaseLoadService.showSuccess();
        }
        isUseLoadSir = false;
    }

    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonBeanList) {
        dismissDialog();
        if (mBaseLoadService != null) {
            mBaseLoadService.showSuccess();
        }
        isUseLoadSir = false;
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        dismissDialog();
        if (code == 0) {
            ToastUtils.showLong("错误提示：" + msg);
        } else if (code == 404) {
            if (mBaseLoadService != null && isUseLoadSir) {
                mBaseLoadService.showCallback(Error404Callback.class);
                ToastUtils.showLong("错误提示：" + msg);
            } else {
                ToastUtils.showLong("错误提示：" + msg);
            }
        } else if (code == 999) {
            ToastUtils.showLong("错误提示：token失效，重新登录 " + msg);
            ActivityUtils.startActivity(LoginActivity.class);
            ActivityUtils.finishAllActivities();
        } else {
            if (mBaseLoadService != null && isUseLoadSir) {
                mBaseLoadService.showCallback(ErrorCallback.class);
                ToastUtils.showLong("错误提示：" + msg);
            } else {
                ToastUtils.showLong("错误提示：" + msg);
            }
        }
        isUseLoadSir = false;
    }

    //    @Override
//    public void requestFail(String requestUrl, String msg) {
//        if (mBaseLoadService != null) {
//            mBaseLoadService.showCallback(ErrorCallback.class);
//        }
//    }

    @Override
    public void permissionsAreGranted(int type) {

    }

    @Override
    public void goToSettings() {
        //前往设置界面
        AppUtils.launchAppDetailsSettings();
    }

}
