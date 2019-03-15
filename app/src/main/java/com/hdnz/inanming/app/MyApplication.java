package com.hdnz.inanming.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.hdnz.inanming.ocr.BaiDuOcrHelper;
import com.hdnz.inanming.webview.X5NetService;
import com.mob.MobSDK;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.HashMap;
import java.util.Map;

import androidx.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;


/**
 * @author :   FeyTsien
 * @date :   2017/8/15
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**极光初始化*/
        initJPush();
        /**X5ebView初始化*/
        preInitX5Core();
        /**扫描初始化*/
        ZXingLibrary.initDisplayOpinion(this);
        /**百度OCR 文字识别、身份证拍照初始化*/
        BaiDuOcrHelper.getInstance().initAccessToken(getApplicationContext());
        /**Mob分享初始化*/
        initMobShare();
    }

    /**极光初始化*/
    private void initJPush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    /**X5ebView初始化*/
    private void preInitX5Core() {
        Map map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(true);//允许使用流量下载
        //预加载x5内核
        Intent intent = new Intent(this, X5NetService.class);
        startService(intent);
    }

    /**
     * 解决java.lang.NoClassDefFoundError错误，方法数超过65536了
     * 5.0以下系统会出次问题
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * Mob分享初始化
     */
    private void initMobShare() {
        MobSDK.init(this);
    }
}
