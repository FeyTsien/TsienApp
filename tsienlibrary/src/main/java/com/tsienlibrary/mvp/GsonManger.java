package com.tsienlibrary.mvp;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 */

public class GsonManger {
    public static GsonManger gsonManger;
    private final Gson gson;

    private GsonManger() {
        gson = new Gson();

    }

    public static GsonManger getGsonManger() {
        if (gsonManger == null) {
            gsonManger = new GsonManger();
        }
        return gsonManger;
    }

    public String toJson(List<String> list) {
        String str = gson.toJson(list);
        return str;
    }

    public String toJson(Object o) {
        String str = gson.toJson(o);
        return str;
    }

    /**
     * 将json数据转换成字典表格式
     *
     * @param s
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T gsonFromat(String s, Class<T> classOfT) {
        T t = gson.fromJson(s, classOfT);
        return t;
    }

    /**
     * 读取assets 中的json数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
