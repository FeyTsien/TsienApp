package com.hdnz.inanming.mvp.model;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ProjectAPI {

    //返回值类型是被观察者
    @Headers({"datasource:857b97e33cda46d1adbcd409f085de49"})
    @GET
    Observable<ResponseBody> getMethod(@Header("token") String token,@Url String url);

    /**
     * 使用RxJava的Observable
     *
     * @param token
     * @param url
     * @param route
     * @return
     */
    //    @Headers({"Content-type:application/json;charset=UTF-8"})
    @Headers({"Content-Type:application/json"
            , "Accept:application/json"
            , "datasource:857b97e33cda46d1adbcd409f085de49"})
    @POST
    Observable<ResponseBody> postJsonObs(@Header("token") String token, @Url String url, @Body RequestBody route);

    //post方式请求(json)
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST
    Call<ResponseBody> postJsonCall(@Url String url, @Body RequestBody route);

    //post请求方式（字典表）
    @FormUrlEncoded
    @POST
    Observable<String> postMethod(@Url String url, @FieldMap Map<String, String> map);

    //下载
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //上传多个文件
    @Headers({"datasource:857b97e33cda46d1adbcd409f085de49"
            , "version:0"})
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Header("token") String token, @Url String url, @Part List<MultipartBody.Part> partList);

    // 上传单个文件，也可用上面的上传多个的方法传单个
    @Headers({"datasource:857b97e33cda46d1adbcd409f085de49"
            , "version:0"})
    @Multipart
    @POST
    Observable<ResponseBody> upLoadImg(@Header("token") String token, @Url String url, @Part MultipartBody.Part part);

}
