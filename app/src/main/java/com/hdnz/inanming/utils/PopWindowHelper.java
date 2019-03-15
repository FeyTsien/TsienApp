package com.hdnz.inanming.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.hdnz.inanming.R;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    PopWindowHelper.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-12-05 16:11
 * Description: PopWindow帮助类
 * Version:     V1.0.0
 * History:     历史信息
 */
public class PopWindowHelper {
    /**
     * @field 声明volatile 保证编译器不做优化
     */
    private static volatile PopWindowHelper instance;
    /**
     * @field 依附的view，此参数必填
     */
    private View attachView;
    /**
     * @field 设置弹出的布局id，此参数必填
     */
    private Integer layoutId;
    /**
     * @field 回调监听，此参数必填
     */
    private PopWindowListener popWindowListener;
    /**
     * @field 设置是否点击外部区域，取消弹窗
     */
    private boolean focusable = true;
    /**
     * @field popWindow弹出层动画style
     */
    private Integer animStyle;
    /**
     * @field 依附的activity，此参数必填
     */
    private Activity mActivity;
    /**
     * @field 弹窗控件
     */
    private PopupWindow popupWindow;

    /**
     * 私有构造方法
     */
    private PopWindowHelper() {

    }

    /**
     * 单例，提供给外部调用方法
     *
     * @return
     */
    public static PopWindowHelper getInstance() {
        //采用加锁、双重判空检测
        if (null == instance) {
            synchronized (PopWindowHelper.class) {
                if (null == instance) {
                    instance = new PopWindowHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 设置依附的view
     *
     * @param attachView
     * @return
     */
    public PopWindowHelper setAttachView(View attachView) {
        this.attachView = attachView;
        return instance;
    }

    /**
     * 设置popwindow弹出的布局
     *
     * @param layoutId
     * @return
     */
    public PopWindowHelper setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return instance;
    }

    /**
     * 设置回调接口给调用者
     *
     * @param popWindowListener
     * @return
     */
    public PopWindowHelper setPopWindowListener(PopWindowListener popWindowListener) {
        this.popWindowListener = popWindowListener;
        return instance;
    }

    /**
     * 设置点击弹窗外隐藏自身
     *
     * @return
     */
    public PopWindowHelper setPopFocusable(boolean focusable) {
        this.focusable = focusable;
        return instance;
    }

    /**
     * 设置PopWindow弹出动画style
     *
     * @param animStyle
     * @return
     */
    public PopWindowHelper setPopAnimationStyle(int animStyle) {
        this.animStyle = animStyle;
        return instance;
    }

    /**
     * 设置当前activity
     * 目的是获取NavigationBar的高度：这里需要适配有些手机没有NavigationBar有些手机有，这里以存在NavigationBar来演示
     * 此参数必填
     *
     * @param activity
     * @return
     */
    public PopWindowHelper setPopActivity(Activity activity) {
        this.mActivity = activity;
        return instance;
    }

    /**
     * 显示popWindow
     *
     * @return
     */
    public PopWindowHelper showPopWindow() {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return instance;
        }
        if (null == popWindowListener) {
            LogUtils.e("popWindowListener 监听事件为空，弹窗功能无法使用");
            return instance;
        }
        StringBuilder builder = new StringBuilder();
        //设置PopupWindow的View
        if (null != layoutId && null != attachView && null != mActivity) {
            View view = LayoutInflater.from(mActivity).inflate(layoutId, null);
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            //设置背景,这个没什么效果，不添加会报错
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            //设置点击弹窗外隐藏自身
            popupWindow.setFocusable(focusable);
            popupWindow.setOutsideTouchable(focusable);
            //设置动画
            popupWindow.setAnimationStyle(null != animStyle ? animStyle : R.style.pop_shop_anim);
            //设置消失监听
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1);
                }
            });
            //设置PopupWindow的View点击事件
            if (null != popWindowListener) {
                popWindowListener.popWindowView(view, attachView, popupWindow, null);
            }
            //设置背景色
            setBackgroundAlpha(0.5f);
            //展示弹窗 获取NavigationBar的高度：这里需要适配有些手机没有NavigationBar有些手机有，这里以存在NavigationBar来演示
            popupWindow.showAtLocation(attachView, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(mActivity));
        }else {
            builder.append("popWindow弹窗布局或者依附的View或者依附的Activity为空...");
            popWindowListener.popWindowView(null, null, null, builder.toString());
        }
        return instance;
    }

    /**
     * 关闭弹窗
     *
     * @return
     */
    public PopWindowHelper hidePopWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
        return instance;
    }

    /**
     * 根据不同布局打开popWindow，参数配置麻烦，使用此方法
     *
     * @param attachView
     * @param layout
     */
    public void openPopWindowWithLayout(View attachView, int layout, PopWindowListener popWindowListener) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(mActivity).inflate(layout, null);
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
        popupWindow.showAtLocation(attachView, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(mActivity));
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1);
            }
        });
        //设置PopupWindow的View点击事件
        if (null != popWindowListener) {
            popWindowListener.popWindowView(view, attachView, popupWindow, null);
        }
        //设置背景色
        setBackgroundAlpha(0.5f);
    }


    /************************************** 以下私有方法和接口 ****************************************/

    /**
     * 设置屏幕背景透明效果
     *
     * @param alpha
     */
    private void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
        // 此方法用来设置浮动层，防止部分手机变暗无效
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 弹窗视图回调接口
     */
    public interface PopWindowListener {
        /**
         * 回调依附的view, popView， popupWindow, message(参数错误消息)
         *
         * @param popView
         * @param attachView
         * @param popupWindow
         * @param message
         */
        void popWindowView(View popView, View attachView, PopupWindow popupWindow, String message);
    }
}
