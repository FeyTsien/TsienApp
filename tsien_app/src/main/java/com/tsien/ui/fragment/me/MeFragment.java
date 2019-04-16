package com.tsien.ui.fragment.me;


import com.tsien.R;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.mvp.view.MVPFragment;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;

/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }

    /**
     * 刷新用户信息
     */
    private void setUserInfo() {
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        //订阅EventBus,返回true.
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.MAIN_ME_A:
                setUserInfo();
                break;
        }
    }

}
