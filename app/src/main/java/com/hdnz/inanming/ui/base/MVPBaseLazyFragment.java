package com.hdnz.inanming.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hdnz.inanming.R;
import com.tsienlibrary.mvp.base.BasePresenterImpl;
import com.tsienlibrary.mvp.base.BaseView;
import com.tsienlibrary.ui.fragment.BaseFragment;
import com.hdnz.inanming.utils.NavigationBarUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    MVPBaseLazyFragment.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-16 09:39
 * Description: Fragment基类，封装了懒加载的实现
 * 1、Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义一个新的回调方法，当fragment可见状态改变时会触发的回调方法
 * Version:     V1.0.0
 * History:     历史信息
 */
public abstract class MVPBaseLazyFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseFragment implements BaseView {
    /**
     * @field 业务处理对象
     */
    public T mPresenter;
    /**
     * @field fragment是否可见
     */
    private boolean isFragmentVisible;
    /**
     * @field 是否重新使用view
     */
    private boolean isReuseView;
    /**
     * @field fragment是否第一次可见
     */
    private boolean isFirstVisible;
    /**
     * @field 复用的跟视图，给子类使用
     */
    protected View rootView;
    private PopupWindow popupWindow;

    /**
     * setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
     * 如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
     * 如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
     * 总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (null == rootView) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            lazyLoadData();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        initVariable();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**
         * 如果setUserVisibleHint()在rootView创建前调用时，那么
         * 就等到rootView创建完后才回调onFragmentVisibleChange(true)
         * 保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
         */
        if (null == rootView) {
            this.rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    lazyLoadData();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView && rootView != null ? rootView : view, savedInstanceState);
//        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        //初始化变量
        initVariable();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected abstract void lazyLoadData();

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible);

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuseView
     */
    protected void setReuseView(boolean isReuseView) {
        this.isReuseView = isReuseView;
    }

    /**
     * 判断当前fragment是否可见
     *
     * @return
     */
    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 实例化presenter
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
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

    /**
     * 根据不同布局，打开popwindow
     *
     * @param attchView
     * @param layoutId
     * @param position
     */
    protected void openPopupWindow(View attchView, int layoutId, int position) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.pop_shop_anim);
        //设置位置 获取NavigationBar的高度：这里需要适配有些手机没有NavigationBar有些手机有，这里以存在NavigationBar来演示
        popupWindow.showAtLocation(attchView, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(getActivity()));
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1);
            }
        });
        //设置PopupWindow的View点击事件
        findPopupView(view, attchView, position);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    /**
     * 查找控件，并设置点击事件，让子类复写方法
     *
     * @param popView
     */
    protected void findPopupView(View popView, View attchView, int position) {
    }

    /**
     * 获取popwindow，子类使用
     *
     * @return
     */
    protected PopupWindow getPopWindow() {
        return this.popupWindow;
    }

    /**
     * 关闭popwindow，子类使用
     */
    protected void closePopWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置屏幕背景透明效果
     *
     * @param alpha
     */
    protected void setBackgroundAlpha(float alpha) {
        Activity activity = getActivity();
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
        // 此方法用来设置浮动层，防止部分手机变暗无效
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置EditText的hint字体的大小
     *
     * @param editText
     * @param hintText
     * @param size     单位是sp
     */
    protected void setEditTextHintSize(EditText editText, String hintText, int size) {
        //定义hint的值
        SpannableString ss = new SpannableString(hintText);
        //设置字体大小 true表示单位是sp
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
