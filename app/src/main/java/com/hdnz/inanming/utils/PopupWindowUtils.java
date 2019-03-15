package com.hdnz.inanming.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/20
 *     desc   :
 * </pre>
 */
public class PopupWindowUtils {

    private PopupWindow popupWindow;
    private Activity mActivity;
    private PopWindowListener popWindowListener;

    public PopupWindowUtils(Activity activity) {
        mActivity = activity;
    }

    /**
     * 打开popwindow 进行拍照选择
     *
     * @param v
     * @param type
     */
    public void openPopupWindow(View v, String type) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_camera_select_layout, null);
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
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(mActivity));
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1);
            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    /**
     * 根据不同布局打开popwindow
     *
     * @param attachView
     * @param layout
     */
    public void openPopupWindowByLayout(View attachView, int layout, PopWindowListener popWindowListener) {
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
            popWindowListener.popWindowView(view, attachView, popupWindow);
        }
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    /**
     * 查找控件，并设置点击事件
     *
     * @param view
     */
    private void setOnPopupViewClick(View view) {
        TextView tvCamera;
        TextView tvPickPhoto;
        TextView tvCancel;
        tvCamera = (TextView) view.findViewById(R.id.tv_camera);
        tvPickPhoto = (TextView) view.findViewById(R.id.tv_pick_photo);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        //拍照
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("拍照");
                popupWindow.dismiss();
            }
        });
        //选择照片
        tvPickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("选择照片");
                popupWindow.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("取消");
                popupWindow.dismiss();
            }
        });
    }

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
     *  弹窗视图回调接口
     */
    public interface PopWindowListener {
        /**
         * popwindow回调方法
         *
         * @param popView
         * @param attachView
         * @param popupWindow
         */
        void popWindowView(View popView, View attachView, PopupWindow popupWindow);
    }
}
