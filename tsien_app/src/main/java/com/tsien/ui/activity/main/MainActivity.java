package com.tsien.ui.activity.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tsien.R;
import com.tsien.app.AppData;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.mvp.view.MVPActivity;
import com.tsien.service.LocationService;
import com.tsien.ui.fragment.home.HomeFragment;
import com.tsien.ui.fragment.me.MeFragment;
import com.tsien.ui.fragment.message.MessageFragment;
import com.tsien.ui.fragment.workbench.WorkbenchFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.BackHandlerHelper;
import com.tsienlibrary.ui.widget.dialog.DialogUtils;
import com.tsienlibrary.utils.OpenFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final String KEY_HOME_FRAGMENT = "home_fragment";
    private static final String KEY_WOKEBENCH_FRAGMENT = "wokebench_fragment";
    private static final String KEY_MESSAGE_FRAGMENT = "message_fragment";
    private static final String KEY_ME_FRAGMENT = "me_fragment";

    private static final String KEY_FRAGMENT_POSITION = "fragment_position";
    private static final int POSITION_WORKBENCH = 1;

    private RxPermissions rxPermissions;

    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;
    private HomeFragment mHomeFragment;
    private WorkbenchFragment mWorkbenchFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setStatus(false, false);//状态栏字体白色
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance();//首页
                    }
                    switchFragment(mCurrentFragment, mHomeFragment, item.getTitle());
                    return true;
                case R.id.navigation_workbench:
                    setStatus(false, true);//状态栏字体黑色
                    if (mWorkbenchFragment == null) {
                        mWorkbenchFragment = WorkbenchFragment.newInstance();//工作台
                    }
                    switchFragment(mCurrentFragment, mWorkbenchFragment, item.getTitle());
                    return true;
//                case R.id.navigation_center:
//                    //由 BottomNavigationView 上面的 imageview 处理点击事件
//                    return true;
                case R.id.navigation_message:
                    setStatus(false, true);//状态栏字体黑色
                    if (mMessageFragment == null) {
                        mMessageFragment = MessageFragment.newInstance();//消息
                    }
                    switchFragment(mCurrentFragment, mMessageFragment, item.getTitle());
                    return true;
                case R.id.navigation_me:
                    setStatus(false, false);//状态栏字体黑色
                    if (mMeFragment == null) {
                        mMeFragment = MeFragment.newInstance();//我的
                    }
                    switchFragment(mCurrentFragment, mMeFragment, item.getTitle());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        mPresenter.setPermissions(rxPermissions, permissions);
        mFragmentList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setItemIconTintList(null);//加上此句话显示原图片颜色
        if (!AppData.isValueBool(AppData.KEY_IS_WORKBENCH)) {
            BottomNavigationViewHelper.disableShiftMode(navigation);
        } else {
            BottomNavigationViewHelper.disableShiftMode(navigation, POSITION_WORKBENCH);
        }

//        navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());//设置默认选中页面
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DialogUtils.showAlwaysFinishDialog(this); //如果用户开启了[不保留活动]，则弹出此提示框
        super.onCreate(savedInstanceState);//先执行BaseActivity的onCreate方法，再继续执行子类的onCreate方法。

        if (savedInstanceState != null) {
            initFragmentWithInstanceState(savedInstanceState);
        } else {
            initFragment();
        }

    }

    /**
     * 初始化部分Fragment
     */
    private void initFragment() {
        mHomeFragment = HomeFragment.newInstance();
        addToList(mHomeFragment);
//        mWorkbenchFragment = WorkbenchFragment.newInstance();
//        addToList(mWorkbenchFragment);
        mCurrentFragment = mHomeFragment;
        setStatus(false, false);//状态栏字体白色（默认第一屏显示首页，状态栏字体白色）
//        addFragmentToActivity(getSupportFragmentManager(), mWorkbenchFragment, R.id.fragment);//提前添加工作台fragment，解决加载工作台页面延迟问题
        addFragmentToActivity(getSupportFragmentManager(), mCurrentFragment, R.id.fragment);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mWorkbenchFragment);//提前添加工作台fragment，解决加载工作台页面延迟问题
    }

    public void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    /**
     * 获取到保存的fragment
     *
     * @param savedInstanceState
     */
    private void initFragmentWithInstanceState(Bundle savedInstanceState) {

        /*获取保存的fragment  没有的话返回null*/
        mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT);
        mHomeFragment = (HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_HOME_FRAGMENT);
        mWorkbenchFragment = (WorkbenchFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_WOKEBENCH_FRAGMENT);
        mMessageFragment = (MessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_MESSAGE_FRAGMENT);
        mMeFragment = (MeFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_ME_FRAGMENT);

//        //因为工作台的fragment包含了一个通用fragment，恢复时该fragment中的recyclerView的adapter一直为空，至今没有处理好，才出此下策，加载一个新得工作台fragment
//        if (mCurrentFragment == mWorkbenchFragment) {
//            FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
//            ft.remove(mWorkbenchFragment);
//            mWorkbenchFragment = WorkbenchFragment.newInstance();
//            mCurrentFragment = mWorkbenchFragment;
//            ft.add( R.id.fragment,mWorkbenchFragment);
//            ft.commit();
//        }

        addToList(mHomeFragment);
        addToList(mWorkbenchFragment);
        addToList(mMessageFragment);
        addToList(mMeFragment);

        if (mCurrentFragment == mHomeFragment || mCurrentFragment == mMeFragment) {
            setStatus(false, false);//状态栏字体（首页、我的=>白色）
        } else {
            setStatus(false, true);//状态栏字体（工作台、消息=>浅黑色）
        }
    }

    /**
     * 隐藏、展示 fragment
     *
     * @param from
     * @param to
     * @param title （menu 对应的标题）
     */
    public void switchFragment(Fragment from, Fragment to, CharSequence title) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().
                    beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment, to).commit();
                addToList(to);
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    /**
     * 将fragment加入对应的集合内
     *
     * @param fragment
     */
    private void addToList(Fragment fragment) {
        if (fragment != null) {
            mFragmentList.add(fragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mCurrentFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_CURRENT_FRAGMENT, mCurrentFragment);
        }
        if (mHomeFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_HOME_FRAGMENT, mHomeFragment);
        }
        if (mWorkbenchFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_WOKEBENCH_FRAGMENT, mWorkbenchFragment);
        }
        if (mMessageFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_MESSAGE_FRAGMENT, mMessageFragment);
        }
        if (mMeFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_ME_FRAGMENT, mMeFragment);
        }

        super.onSaveInstanceState(outState);
    }


    @Override
    protected boolean isRegisteredEventBus() {
        //订阅EventBus,返回true.
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.onReceiveEvent(event);
        switch (event.getCode()) {
            case EventCode.MAIN_A:
                ToastUtils.showShort("主界面收到了通知 A");
                break;
            case EventCode.MAIN_B:
                //下载返回的状态
                ToastUtils.showLong((String) event.getData());
                showInstallDialog();
                break;
        }
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);

        //返回的是 app相关的详细信息，然后跟现有版本做比较

        int nowVersion = AppUtils.getAppVersionCode();
        //目前没接口，后面用到放开此段代码
//        if (nowVersion == updateInfo.getVersion()) {
//            Toast.makeText(this, "已经是最新版本", Toast.LENGTH_SHORT).show();
//            LogUtils.d("版本号是", "onResponse: " + nowVersion);
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setIcon(android.R.drawable.ic_dialog_info);
//            builder.setTitle("请升级APP至版本" + updateInfo.getVersion());
//            builder.setMessage(updateInfo.getDescription());
//            builder.setCancelable(false);
//            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(MainActivity.this, DownLoadService.class);
//                    startService(intent);
//                }
//            });
//            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//            builder.create().show();
//        }
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg, code);

    }


    @Override
    public void permissionsAreGranted(int type) {

        //启动Service
        Intent intentLocation = new Intent(this, LocationService.class);
        startService(intentLocation);

        String jsonData = "";
//                mPresenter.request("url", jsonData);
    }

    /**
     * 下载完成弹出安装提示框
     */
    private void showInstallDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("下载完成");
        builder.setMessage("是否安装");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(OpenFileUtils.getApkPath(MainActivity.this), "INanMing.apk");
//                File file = new File(getApkPath(), "abc.jpg");
                OpenFileUtils.installApk(MainActivity.this, file);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


    private long lastBackPress;

    @Override
    public void onBackPressed() {

        if (!BackHandlerHelper.handleBackPress(this)) {
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                super.onBackPressed();
            } else {
                lastBackPress = System.currentTimeMillis();
                ToastUtils.showShort("再按一次退出");
            }
        }
    }

}
