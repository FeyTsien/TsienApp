package com.tsienlibrary.eventbus;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/30
 *     desc   :
 * </pre>
 */
public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
