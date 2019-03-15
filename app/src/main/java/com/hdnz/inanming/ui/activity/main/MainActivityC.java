package com.hdnz.inanming.ui.activity.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.view.MenuItem;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.fragment.home.HomeFragment;
import com.hdnz.inanming.ui.fragment.me.MeFragment;
import com.hdnz.inanming.ui.fragment.message.MessageFragment;
import com.hdnz.inanming.ui.fragment.workbench.WorkbenchFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.BackHandlerHelper;
import com.tsienlibrary.utils.OpenFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivityC extends MVPActivity<MVPContract.View, MVPPresenter> {

    private static final String KEY_FRAGMENT_POSITION = "fragment_position";
    private static final int POSITION_HOME = 0;
    private static final int POSITION_WORKBENCH = 1;
    private static final int POSITION_MESSAGE = 2;
    private static final int POSITION_ME = 3;

    private RxPermissions rxPermissions;

    private List<Fragment> mFragmentList;
    private int mPosition;
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
                    switchFragment(mCurrentFragment, mFragmentList.get(POSITION_HOME), item.getTitle());
                    return true;
                case R.id.navigation_workbench:
                    setStatus(false, true);//状态栏字体黑色
                    switchFragment(mCurrentFragment, mFragmentList.get(POSITION_WORKBENCH), item.getTitle());
                    return true;
//                case R.id.navigation_center:
//                    //由 BottomNavigationView 上面的 imageview 处理点击事件
//                    return true;
                case R.id.navigation_message:
                    setStatus(false, true);//状态栏字体黑色
                    switchFragment(mCurrentFragment, mFragmentList.get(POSITION_MESSAGE), item.getTitle());
                    return true;
                case R.id.navigation_me:
                    setStatus(false, false);//状态栏字体黑色
                    switchFragment(mCurrentFragment, mFragmentList.get(POSITION_ME), item.getTitle());
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(KEY_FRAGMENT_POSITION, POSITION_HOME);
            startMain(mPosition);
            return;
        }
        setStatus(false, false);//状态栏字体白色
//        视频为了避免闪屏和透明问题，需要如下设置
//        a)网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

    }

    @Override
    protected boolean isRegisteredEventBus() {
        //订阅EventBus,返回true.
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.MAIN_A:
                break;
            case EventCode.MAIN_B:
                //下载返回的状态
                ToastUtils.showLong((String) event.getData());
                showInstallDialog();
                break;
        }
    }

    @Override
    protected void initData() {
        rxPermissions = new RxPermissions(this);
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mPresenter.setPermissions(rxPermissions, permissions);

        mPosition = getIntent().getIntExtra(KEY_FRAGMENT_POSITION, POSITION_HOME);
        mHomeFragment = HomeFragment.newInstance();
        mWorkbenchFragment = WorkbenchFragment.newInstance();
        mMessageFragment = MessageFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
    }


    @Override
    protected void initView() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mWorkbenchFragment);
        mFragmentList.add(mMessageFragment);
        mFragmentList.add(mMeFragment);
        addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(1), R.id.fragment);//提前添加工作台fragment，因为有点卡顿
        addFragmentToActivity(getSupportFragmentManager(), mFragmentList.get(mPosition), R.id.fragment);
        mCurrentFragment = mFragmentList.get(mPosition);

        BottomNavigationView navigation = findViewById(R.id.navigation);
//        navigation.setItemIconTintList(null);//加上此句话显示原图片颜色
        if (AppData.isValueBool(AppData.KEY_IS_WORKBENCH)) {
            BottomNavigationViewHelper.disableShiftMode(navigation);
        } else {
            BottomNavigationViewHelper.disableShiftMode(navigation, POSITION_WORKBENCH);
        }
        navigation.setSelectedItemId(navigation.getMenu().getItem(mPosition).getItemId());//设置默认选中页面
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        ImageView imageView = (ImageView) findViewById(R.id.navigation_center_image);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Center", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void switchFragment(Fragment from, Fragment to, CharSequence title) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().
                    beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    public void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        LogUtils.i("Maina","onRestoreInstanceState");
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存退出时，当前页面下标
        if (mCurrentFragment == mWorkbenchFragment) {
            outState.putInt(KEY_FRAGMENT_POSITION, POSITION_WORKBENCH);
        } else if (mCurrentFragment == mMessageFragment) {
            outState.putInt(KEY_FRAGMENT_POSITION, POSITION_MESSAGE);
        } else if (mCurrentFragment == mMeFragment) {
            outState.putInt(KEY_FRAGMENT_POSITION, POSITION_ME);
        } else {
            //只要不是后三页，默认都显示第一页
            outState.putInt(KEY_FRAGMENT_POSITION, POSITION_HOME);
        }
        super.onSaveInstanceState(outState);
    }

    private long lastBackPress;


    /**
     * 跳转到主界面
     *
     * @param position —— 主界面的四个模块下标
     */
    private void startMain(int position) {
        finish();
        Intent intent = new Intent(this, MainActivityC.class);
        intent.putExtra(KEY_FRAGMENT_POSITION, position);
        startActivity(intent);
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

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

    }


    @Override
    public void permissionsAreGranted(int type) {

        String jsonData = "";
//                mPresenter.request("url", jsonData);
    }

    @Override
    public void goToSettings() {
        //前往设置界面
        AppUtils.launchAppDetailsSettings();
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
                File file = new File(OpenFileUtils.getApkPath(MainActivityC.this), "INanMing.apk");
//                File file = new File(getApkPath(), "abc.jpg");
                OpenFileUtils.installApk(MainActivityC.this, file);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }


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
