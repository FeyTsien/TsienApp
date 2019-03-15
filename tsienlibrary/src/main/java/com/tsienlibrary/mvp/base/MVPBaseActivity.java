package com.tsienlibrary.mvp.base;

import android.content.Context;

import com.tsienlibrary.ui.activity.BaseActivity;

import java.lang.reflect.ParameterizedType;


/**
 * MVPPlugin
 */

public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseActivity implements BaseView {
    public T mPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initPresenter();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void initPresenter() {
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
    }

    public <T> T getInstance(Object o, int i) {
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
