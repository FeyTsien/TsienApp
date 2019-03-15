package com.tsienlibrary.download;

/**
 * Description: 下载进度回调
 * Created by jia on 2017/11/30.
 * 人之所以能，是相信能
 */

public interface JsDownloadListener {
    void onStartDownload(long maxLength);

    void onProgress(long currentLength);

    void onFail(String errorInfo);
}
