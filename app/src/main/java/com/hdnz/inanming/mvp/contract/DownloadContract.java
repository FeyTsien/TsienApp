package com.hdnz.inanming.mvp.contract;


import com.tsienlibrary.mvp.base.BasePresenter;
import com.tsienlibrary.mvp.base.BaseView;

import java.io.File;

public interface DownloadContract {
    interface View extends BaseView {
        void setMax(long max);

        void downLoading(long progress);

        void downloadSuccess();

        void showError(String s);


    }

    interface Presenter extends BasePresenter<View> {
        void downloadFile(String url, File file);
    }
}