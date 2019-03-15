package com.tsienlibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyBaseFragment extends Fragment {


    /**视图是否已经初初始化*/
    private boolean isInit = false;
    /**用来只允许第一次执行加载，之后变成false,不再执行加载*/
    private boolean isFrist = true;

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isLazyLoad()) {
            return;
        }

        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            if (isFrist) {
                isFrist = false;
                lazyLoad();
            }
        } else {
            if (!isFrist) {
                stopLoad();
            }
        }
    }

    /**
     * 是否使用懒加载，默认是false为不使用
     *
     * @return
     */
    protected boolean isLazyLoad() {
        return false;
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected abstract void stopLoad();


    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isFrist = true;
    }
}
