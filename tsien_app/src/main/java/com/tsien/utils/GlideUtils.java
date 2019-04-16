package com.tsien.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.tsien.app.AppData;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/08
 *     desc   :
 * </pre>
 */
public class GlideUtils {
    //
//    public static GlideUrl getGlideUrl(String url) {
//
//        return new GlideUrl(url, new LazyHeaders.Builder()
//                .addHeader("token", AppData.getToken())//"faaf40300c17435191d97546748a45a9"
//                .addHeader("version", "")
//                .build());
//    }
//
    public static GlideUrl getGlideUrl(String picId) {

        return new GlideUrl("http://47.92.165.143:8008/FileCenter/api/file/fileByte/" + picId, new LazyHeaders.Builder()
                .addHeader("token", AppData.getValueStr(AppData.KEY_TOKEN))//"faaf40300c17435191d97546748a45a9"
                .addHeader("version", "")
                .build());
    }

    public static GlideUrl getGlideUrl(String picId, String token) {

        return new GlideUrl("http://47.92.165.143:8008/FileCenter/api/file/fileByte/" + picId, new LazyHeaders.Builder()
                .addHeader("token", token)//"faaf40300c17435191d97546748a45a9"
                .addHeader("version", "")
                .build());
    }

}
