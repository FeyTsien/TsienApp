package com.hdnz.inanming.mvp.presenter;


import com.blankj.utilcode.util.LogUtils;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.hdnz.inanming.mvp.contract.DownloadContract;
import com.tsienlibrary.download.DownloadUtils;
import com.tsienlibrary.download.JsDownloadListener;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/19
 *     desc   :
 * </pre>
 */
public class DownloadPresenter extends BasePresenterImpl<DownloadContract.View> implements DownloadContract.Presenter {

    private String TAG = "DownloadPresenter";

    @Override
    public void downloadFile(String url, File file) {

        final DownloadUtils downloadUtils = new DownloadUtils(url, new JsDownloadListener() {
            @Override
            public void onStartDownload(long maxLength) {
                mView.setMax(maxLength);
            }

            @Override
            public void onProgress(long currentLength) {
                mView.downLoading(currentLength);
            }

            @Override
            public void onFail(String errorInfo) {
                mView.showError(errorInfo);
            }
        });

        downloadUtils.download(url, file, new Observer<InputStream>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(InputStream inputStream) {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError:" + e);
                mView.showError("onError:" + e);
            }

            @Override
            public void onComplete() {
                mView.downloadSuccess();
            }
        });

    }

}
