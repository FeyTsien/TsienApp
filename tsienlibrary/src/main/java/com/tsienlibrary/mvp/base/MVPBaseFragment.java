package com.tsienlibrary.mvp.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.tsienlibrary.ui.fragment.BaseFragment;

import java.lang.reflect.ParameterizedType;

/**
 * MVPPlugin
 */

public abstract class MVPBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseFragment implements BaseView {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
