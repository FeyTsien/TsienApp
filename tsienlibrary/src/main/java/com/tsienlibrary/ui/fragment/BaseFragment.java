
/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tsienlibrary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.loadsir.callback.Error404Callback;
import com.tsienlibrary.loadsir.callback.ErrorCallback;
import com.tsienlibrary.loadsir.callback.LoadingCallback;
import com.tsienlibrary.loadsir.callback.LottieEmptyCallback;
import com.tsienlibrary.loadsir.callback.NetworkFailureCallback;
import com.tsienlibrary.loadsir.callback.NotDataCallback;
import com.tsienlibrary.loadsir.callback.WebErrorCallback;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.FragmentBackHandler;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends LazyBaseFragment implements FragmentBackHandler {

    protected Unbinder unbinder;
    protected LoadService mBaseLoadService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = View.inflate(getActivity(), getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);

        if (isRegisteredEventBus()) {
            EventBusUtil.register(this);
        }
        if (isLoadSir()) {
            return initLoadSir(rootView);
        } else {
            return rootView;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isLazyLoad()) {//不使用懒加载的时候，则继续往下执行一些初始化
            loadNet();//展示的第一个状态页（如果不使用默认界面，可以在子类重写此方法更换界面）
            initData();//数据初始化
            initView();//组件初始化
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    //TODO：===============  fragment懒加载相关  ==============================================

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     * 第一次懒加载的时候
     */
    @Override
    protected void lazyLoad() {
        loadNet();//展示的第一个状态页（如果不使用默认界面，可以在子类重写此方法更换界面）
        initData();//数据初始化
        initView();//组件初始化
    }

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    @Override
    protected void stopLoad() {
        //终止加载的操作
        //子类可以重新此方法终止加载
    }

    //TODO：===============  LoadSir 加载状态页面  ==============================================
    protected boolean isLoadSir() {//是否需要使用LoadSir
        return false;
    }

    //初始化LoadSir
    private View initLoadSir(View rootView) {
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())
                .addCallback(new Error404Callback())
                .addCallback(new LottieEmptyCallback())
                .addCallback(new NetworkFailureCallback())
                .addCallback(new WebErrorCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new NotDataCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        mBaseLoadService = loadSir.register(rootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onNetReload(v);
            }
        });
        return mBaseLoadService.getLoadLayout();
    }

    protected void loadNet() {

    }

    protected void onNetReload(View v) {
        mBaseLoadService.showCallback(LoadingCallback.class);
        request();
    }

    //TODO：=============== 请求 相关设置（包括接口请求，web的url请求）  ======================================

    /**
     * TODO:请求数据（一个类中只有一个接口请求使用）
     */
    protected void request() {

    }

    /**
     * TODO:请求数据（一个类中有多个接口请求使用）
     */
    protected void request(String requestUrl, String jsonData, Class clazz) {

    }

    //TODO：=============== EventBus 相关设置==============================================

    /**
     * TODO:是否订阅事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * TODO:接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * TODO:接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    //TODO：===============  返回按钮监听重写  ==============================================

    /**
     * TODO:返回按钮监听重写
     *
     * @return
     */
    @Override
    public boolean onBackPressed() {
        return interceptBackPressed();
//        return interceptBackPressed()|| (getBackHandleViewPager() == null
//                ? BackHandlerHelper.handleBackPress(this)
//                : BackHandlerHelper.handleBackPress(getBackHandleViewPager()));
    }

    public boolean interceptBackPressed() {
        return false;
    }

//    /**
//     * 2.1 版本已经不在需要单独对ViewPager处理
//     *
//     * @deprecated
//     */
//    @Deprecated
//    public ViewPager getBackHandleViewPager() {
//        return null;
//    }


}
