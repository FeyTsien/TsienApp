package com.tsienlibrary.mvp.base;

/**
 * MVPPlugin
 */

public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);

    void detachView();
}
