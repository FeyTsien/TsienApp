package com.tsien.mvp.model;


import com.tsien.utils.UrlUtils;
import com.tsien.app.AppData;
import com.tsienlibrary.mvp.RetrofitUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * w网络请求的工具类
 */

public class HttpManager {

    private final String TAG = "HttpManager";

    public static HttpManager httpManager = new HttpManager();

    public static HttpManager getHttpManager() {
        return httpManager;
    }

    /**
     * TODO:Post方式请求(提交json)
     * 封装时，传递RxJava的Observer
     *
     * @param url
     */
    public void postJson(String url, String jsonData, Observer<ResponseBody> observer) {

        String baseUrl= AppData.getValueStr(AppData.KEY_BASE_URL);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonData);//datas是json字符串
        String token = AppData.getValueStr(AppData.KEY_TOKEN);
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getRetrofit2(ProjectAPI.class, baseUrl).postJsonObs(token, url, body);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


    /**
     * TODO:get方式请求
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void getMethod(String url, Observer<ResponseBody> observer) {
        String baseUrl =  AppData.getValueStr(AppData.KEY_BASE_URL);
        String token = AppData.getValueStr(AppData.KEY_TOKEN);
        //获取被观察者
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getRetrofit2(ProjectAPI.class, baseUrl).getMethod(token, url);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }


    /**
     * TODO:Post方式请求(提交json)
     * 封装时，传递RxJava的Observer
     *
     * @param url
     */
    public void uploadFiles(String url, Observer<ResponseBody> observer, File... files) {

        //多张图片
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
//                .addFormDataPart("appkey", Api.APPKEY)
//                .addFormDataPart("sign", Api.SIGN);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("uploadfile", file.getName(), imageBody);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getRetrofit(ProjectAPI.class, AppData.getValueStr(AppData.KEY_BASE_URL)).uploadFiles(AppData.getValueStr(AppData.KEY_TOKEN), url, parts);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    /**
     * TODO:Post方式请求(提交json)
     * 封装时，传递callback（不使用RxJava）
     *
     * @param url
     */
    public void postJson(String url, String jsonData, Callback<ResponseBody> callback) {

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonData);//datas是json字符串

        Call<ResponseBody> call = RetrofitUtil.getInstance().getRetrofit(ProjectAPI.class, AppData.getValueStr(AppData.KEY_BASE_URL)).postJsonCall(url, body);
        call.enqueue(callback);
    }

    /**
     * TODO:Post方式请求（提交表单）
     * 封装时，传递observer
     *
     * @param url
     * @param observer
     */
    public void postForm(String url, Observer<String> observer, Map map) {

        Observable<String> observable = RetrofitUtil.getInstance().getRetrofit(ProjectAPI.class, AppData.getValueStr(AppData.KEY_BASE_URL)).postMethod(url, map);
        //在子线程中执行请求，在主线程观察，将信息设置给观察者
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    /**
     * TODO:Post方式请求(提交json)
     * 封装时，传递RxJava的Observer
     *
     * @param url
     */
    public void download(String url, final File file, Observer<InputStream> observer) {


        RetrofitUtil.getInstance()
                .getRetrofit(ProjectAPI.class, UrlUtils.BASEURL)
                .download(url)
//                .compose(this.bindToLifecycle())
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
        } catch (IOException e) {
        }

    }
}
