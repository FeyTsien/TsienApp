package com.tsienlibrary.download;

import androidx.annotation.NonNull;


import com.tsienlibrary.mvp.ProjectAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Description: 下载工具类
 * Created by jia on 2017/11/30.
 * 人之所以能，是相信能
 */
public class DownloadUtils {
    private static final String TAG = "DownloadUtils";
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit retrofit;
    private JsDownloadListener listener;

    public DownloadUtils(String baseUrl, JsDownloadListener listener) {
        this.listener = listener;
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sqdd.myapp.com/myapp/qqteam/tim/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 开始下载
     *
     * @param url
     * @param file
     * @param observer
     */
    public void download(@NonNull String url, final File file, Observer<InputStream> observer) {
        retrofit.create(ProjectAPI.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {

                        writeFile(inputStream, file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param file
     */
    private void writeFile(InputStream inputString, File file) {
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            listener.onFail("IOException");
        }

    }
}