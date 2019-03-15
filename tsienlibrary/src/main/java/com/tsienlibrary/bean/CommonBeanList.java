package com.tsienlibrary.bean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

/**
 * 共用的Bean,用Gson解析泛型数据，其中data为Arrary类型
 *
 * @param <T> == data
 */
public class CommonBeanList<T> implements Serializable {

    private int code;
    private String msg;

    /**
     * 数据
     */
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static CommonBeanList fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonBeanList.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonBeanList.class, clazz);
        return gson.toJson(this, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}