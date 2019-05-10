package com.tsienlibrary.download;

/**
 * Description: 下载进度回调
 */

public interface JsDownloadListener {
    void onStartDownload(long maxLength);

    void onProgress(long currentLength);

    void onFail(String errorInfo);
}
