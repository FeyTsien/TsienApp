package com.tsien.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import java.util.HashMap;
import java.util.Map;

import razerdp.basepopup.BasePopupWindow;

public abstract class MyBasePopupWindow extends BasePopupWindow {

    protected static TsienPopupWindowCallback mTsienPopupWindowCallback;

    public MyBasePopupWindow(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        View rootView = createPopupById(getLayouId());
        initData();
        initView(rootView);
        return rootView;
    }

    protected abstract int getLayouId();

    protected abstract void initData();

    protected abstract void initView(View rootView);

    protected void sendCallback(Map map) {
        mTsienPopupWindowCallback.getMap(map);
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
//        return getDefaultScaleAnimation(true);
//        return getDefaultAlphaAnimation(true);
        return getTranslateVerticalAnimation(1500, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
//        return getDefaultScaleAnimation(false);
//        return getDefaultAlphaAnimation(false);
        return getTranslateVerticalAnimation(0, 1500, 500);
    }

    public interface TsienPopupWindowCallback {
        void getMap(Map map);
    }

}
