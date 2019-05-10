package com.tsien.service;

import android.content.Intent;
import android.os.IBinder;

import com.tsien.R;
import com.tsien.mvp.contract.DownloadContract;
import com.tsien.mvp.presenter.DownloadPresenter;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsien.eventbus.EventCode;
import com.tsienlibrary.mvp.base.MVPBaseService;
import com.tsienlibrary.utils.NotificationUtil;
import com.tsienlibrary.utils.OpenFileUtils;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * Created by zs on 2016/7/8.
 */
public class DownLoadService extends MVPBaseService<DownloadContract.View, DownloadPresenter> implements DownloadContract.View {

    private int NOTIFY_ID = 1000;
    NotificationUtil notificationUtils;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downFile("down/tim.apk");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //下载apk操作
    public void downFile(final String url) {
        //初始化通知栏
        notificationUtils = new NotificationUtil(this, NOTIFY_ID, R.mipmap.ic_logo, "title", "content......");
        notificationUtils.notifyProgress((int) max, 0, "正在下载", 0 + "/" + max);
        File file = new File(OpenFileUtils.getApkPath(this), "INanMing.apk"); //获取文件路径
        mPresenter.downloadFile(url, file);
    }


    @Override
    public void setMax(long maxLength) {
        max = maxLength;
    }

    @Override
    public void downLoading(long currentLength) {
//        notificationUtils.notifyProgress((int) max, i, "正在下载", i + "/" + max);
        progress = (int) (currentLength * 100 / max);
        notificationUtils.notifyProgress(100, progress, "正在下载", progress + "%");
    }

    @Override
    public void downloadSuccess() {

        notificationUtils.cancel(NOTIFY_ID);
        // 发送消息到MainActivity弹框提交是否安装
//        EventBus.getDefault().post(new MainActivity.MessageEvent("下载完成"));
        EventBusUtil.post(new Event<>(EventCode.MAIN_B, "下载完成"));
        stopSelf();
    }

    @Override
    public void showError(String s) {

//        EventBus.getDefault().post(new MainActivity.MessageEvent("下载失败：" + s));
        EventBusUtil.post(new Event<>(EventCode.MAIN_B, "下载失败：" + s));
    }


    private long max;
    private int progress;
}