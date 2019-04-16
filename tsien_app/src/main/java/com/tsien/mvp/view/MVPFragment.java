package com.tsien.mvp.view;


import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.ui.activity.login.LoginActivity;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.loadsir.callback.Error404Callback;
import com.tsienlibrary.loadsir.callback.ErrorCallback;
import com.tsienlibrary.loadsir.callback.NetworkFailureCallback;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.mvp.base.MVPBaseFragment;

/**
 * MVP的View回调实现类
 *
 * @param <V>
 * @param <T>
 */
public abstract class MVPFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends MVPBaseFragment<V, T> implements MVPContract.View {

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

    /**
     * TODO:请求接口，一般页面刷新请求可以直接调用此方法，具体是否使用这个方法，自己看需求考量
     */
    @Override
    protected void request() {
        super.request();
        isUseLoadSir = true;//只有使用request()请求接口，才使用LoadSir，以便用于点击刷新
    }

    /**
     * TODO:接口请求成功，返回的json中data是对象的用这个
     * @param requestUrl
     * @param commonBean
     */
    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        if (mBaseLoadService != null) {
            mBaseLoadService.showSuccess();
        }
        isUseLoadSir = false;
    }

    /**
     * TODO:接口请求成功，返回的json中data是数组的用这个
     * @param requestUrl
     * @param commonBeanList
     */
    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonBeanList) {
        if (mBaseLoadService != null) {
            mBaseLoadService.showSuccess();
        }
        isUseLoadSir = false;
    }

    /**
     * TODO:接口请求失败
     * @param requestUrl
     * @param msg
     * @param code
     */
    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        if (code == 0) {
            ToastUtils.showLong("错误提示：" + msg);
        } else if (code == 404) {
            if (mBaseLoadService != null && isUseLoadSir) {
                mBaseLoadService.showCallback(Error404Callback.class);
                ToastUtils.showLong("LoadSir错误提示：" + msg);
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
                ToastUtils.showLong("LoadSir错误提示：" + msg);
            } else {
                ToastUtils.showLong("错误提示：" + msg);
            }
        }
        isUseLoadSir = false;
    }

    /**
     * TODO:权限已申请
     * @param type
     */
    @Override
    public void permissionsAreGranted(int type) {
        //使用动态权限注册成功时，子类应该重写此方法
    }

    /**
     * TODO:前往设置
     */
    @Override
    public void goToSettings() {
        //使用动态权限注册失败时，前往系统设置，看具体需求可重写此方法
        AppUtils.launchAppDetailsSettings();
    }

}
