package com.tsienlibrary.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 共用的Bean,用Gson解析泛型数据，其中data为Object类型
 * @param <T> == data
 */
public class CommonBean<T> implements Serializable {


    private int code;
    private String msg;
    private String message;
    private T data;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static CommonBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(CommonBean.class, clazz);
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