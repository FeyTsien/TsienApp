package com.hdnz.inanming.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.service.LocationService;
import com.hdnz.inanming.ui.activity.home.govaffairsbooking.gabbusiness.GABBookingInfoActivity;
import com.hdnz.inanming.ui.activity.login.LoginActivity;
import com.hdnz.inanming.ui.activity.main.MainActivity;
import com.hdnz.inanming.ui.popupwindow.GABBookingInfoPopup;
import com.hdnz.inanming.ui.popupwindow.ServerListPopup;
import com.hdnz.inanming.utils.UrlUtils;

import androidx.annotation.Nullable;


public class  SplashActivity extends Activity {

    private boolean isShowLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    protected void initData() {

        isShowLog = true;

        LogUtils.Config config = LogUtils.getConfig();
        //logSwitch为false关闭日志
        config.setLogSwitch(isShowLog);

    }

    protected void initView() {
        if (isShowLog) {
            ServerListPopup.getInstance(this, map -> {
                AppData.setValueStr(AppData.KEY_BASE_URL,(String) map.get(ServerListPopup.KEY_SELECT_URL));
                start();
            }).setAllowDismissWhenTouchOutside(false)
                    .setBackPressEnable(false)
                    .showPopupWindow();
        } else {
            AppData.setValueStr(AppData.KEY_BASE_URL,UrlUtils.BASEURL);
            start();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 0);
    }

    private void start() {
        // 如果是第一次启动，则先进入功能引导页
//        if (AppData.getFirstOpen()==1) {
//            CommonUtils.startActivity(this, WelcomeG uideActivity.class);
//            finish();
//        }else {
        if (AppData.isValueBool(AppData.KEY_IS_LOGIN_ED)) {
            ActivityUtils.startActivity(this, MainActivity.class);
//            ActivityUtils.startActivity(this, MainActivity1.class);
//            ActivityUtils.startActivity(this, MainActivity2.class);
//        ActivityUtils.startActivity(this, DownloadTestActivity.class);
        } else {
//            ActivityUtils.startActivity(this, MainActivity.class);
            ActivityUtils.startActivity(this, LoginActivity.class);
        }
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
