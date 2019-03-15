/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tsienlibrary.mvp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by david on 16/8/19.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class RetrofitUtil {

    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static RetrofitUtil instance;
    private static Retrofit mRetrofit;

    private final static Object mRetrofitLock = new Object();

    /**
     * new OkHttpClient.Builder()
     * .connectTimeout(15, TimeUnit.SECONDS)
     * .readTimeout(300, TimeUnit.SECONDS)
     * .writeTimeout(300, TimeUnit.SECONDS)
     * .cache(new Cache(FileConstants.HTTP_CACHE_DIR, FileConstants.CACHE_SIZE))
     * .addInterceptor(interceptor)
     * //                .addInterceptor(new MockInterceptor())
     * .addInterceptor(new TokenInterceptor())
     * //                .addInterceptor(new RetryIntercepter(3))
     * .addInterceptor(logging)
     *
     * @return
     */

    private static Retrofit getRetrofit(String beseUrl) {
        if (sRetrofit == null) {
            synchronized (mRetrofitLock) {

                if (sRetrofit == null) {

                    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(
                                X509Certificate[] chain,
                                String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }};
                    try {
                        // Install the all-trusting trust manager
                        final SSLContext sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        clientBuilder.addInterceptor(httpLoggingInterceptor);
                        sOkHttpClient = clientBuilder
                                .sslSocketFactory(sslContext.getSocketFactory())
                                .hostnameVerifier(new HostnameVerifier() {
                                    @Override
                                    public boolean verify(String hostname, SSLSession session) {
                                        return true;
                                    }
                                })
                                .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                                .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
                                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
                                .retryOnConnectionFailure(true)//错误重连
                                .build();

                        sRetrofit = new Retrofit.Builder()
                                .client(sOkHttpClient)
                                .baseUrl(beseUrl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                    } catch (Exception e) {

                    }

                }
            }
        }
        return sRetrofit;
    }


    private static Retrofit getRetrofit2(String beseUrl) {
        synchronized (mRetrofitLock) {
            try {
                if (sOkHttpClient == null) {
                    // Install the all-trusting trust manager
                    OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    clientBuilder.addInterceptor(httpLoggingInterceptor);
                    sOkHttpClient = clientBuilder
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
                            .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时时间
                            .retryOnConnectionFailure(true)//错误重连
                            .build();
                }

                sRetrofit = new Retrofit.Builder()
                        .client(sOkHttpClient)
                        .baseUrl(beseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            } catch (Exception e) {

            }

        }
        return sRetrofit;
    }


    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }

    /**
     * BaseUrl不变的情况下用这个
     * @param tClass
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T getRetrofit(Class<T> tClass, String baseUrl) {
        return getRetrofit(baseUrl).create(tClass);
    }

    /**
     * BaseUrl不固定情况下用这个（如果选择使用这个，统一换）
     * @param tClass
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T getRetrofit2(Class<T> tClass, String baseUrl) {
        return getRetrofit2(baseUrl).create(tClass);
    }


//
//    public <T> T get2(Class<T> tClass) {
//        return getRetrofit2().create(tClass);
//    }


}
