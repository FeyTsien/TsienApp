package com.tsienlibrary.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
import com.tsienlibrary.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Fey Tesin on 2017/07/05.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    protected LoadService mBaseLoadService;

    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();
    public static BaseActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置底部导航栏颜色
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        synchronized (mActivities) {
            mActivities.add(this);
        }

        if (isRegisteredEventBus()) {
            EventBusUtil.register(this);
        }
        //设置沉浸式状态栏
        setStatus(false, true);
        //以下顺序很重要，没有特殊情况，请不要改变
        initPresenter();
        if (isLoadSir()) {
            initLoadSir(this);
        }
        initData();
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (isRegisteredEventBus()) {
            EventBusUtil.unregister(this);
        }
    }


    /**
     * TODO:界面文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * TODO：设置沉浸式状态栏
     *
     * @param isBlack true = 白色。状态栏 字体颜色
     */
    protected void setStatus(boolean isfitsSystemWindows, boolean isBlack) {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, isfitsSystemWindows);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        if (isBlack) {
            //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
            //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this, 0x55000000);
            }
        }
    }

    /**
     * TODO:初始化共用Presenter
     */
    protected void initPresenter() {
    }

    /**
     * TODO:初始化数据
     */
    protected abstract void initData();

    /**
     * TODO:初始化View
     */
    protected abstract void initView();


    //TODO：================  LoadSir 相关设置  ======================================

    /**
     * TODO:是否需要使用LoadSir
     *
     * @return true是使用，默认不使用
     */
    protected boolean isLoadSir() {
        return false;
    }

    /**
     * TODO: 初始化LoadSir
     */
    protected void initLoadSir(Object target) {
        //子类重写此方法传入的super.initLoadSir(target)
        // ,target必须在此之前初始化，否则报控件的空指针错误
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new ErrorCallback())
                .addCallback(new Error404Callback())
                .addCallback(new LottieEmptyCallback())
                .addCallback(new NetworkFailureCallback())
                .addCallback(new WebErrorCallback())
                .addCallback(new NotDataCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        mBaseLoadService = loadSir.register(target, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onNetReload(v);
            }
        });
    }

    /**
     * 点击后重写执行此处方法
     *
     * @param v
     */
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

    //TODO：=============== ToolBar 相关设置 ====================================================

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, CharSequence title) {
        setToolBar(toolbar, textView, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param resid
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, int resid) {
        CharSequence title = getResources().getText(resid);
        setToolBar(toolbar, textView, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, TextView textView, CharSequence title, boolean enable) {
        textView.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//不显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     */
    /**
     * 子类可以直接用（暂时没有使用）
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        setToolBar(toolbar, title, true);
    }

    /**
     * 子类可以直接用（暂时没有使用）
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, boolean enable) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //============================================================================================================

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //================  进度条  ====================
    ProgressDialog progressDialog;

    //加载进度的progressDialog
    public void showProgressDialog(String title) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(title + "...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void updateDialogTitle(String title){
        if(progressDialog!=null){
            progressDialog.setMessage(title + "...");
        }
    }
    protected void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
