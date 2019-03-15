package com.tsienlibrary.mvp.base;

import android.app.Service;
import android.content.Context;

import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;

import java.lang.reflect.ParameterizedType;


/**
 * MVPPlugin
 */

public abstract class MVPBaseService<V extends BaseView,T extends BasePresenterImpl<V>> extends Service implements BaseView {
    public T mPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
        mPresenter.detachView();
    }

    @Override
    public Context getContext(){
        return this;
    }

    public  <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


}
