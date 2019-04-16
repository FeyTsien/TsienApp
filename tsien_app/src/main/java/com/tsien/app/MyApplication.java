package com.tsien.app;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
        /**扫描初始化*/
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**极光初始化*/
    private void initJPush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
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
}
