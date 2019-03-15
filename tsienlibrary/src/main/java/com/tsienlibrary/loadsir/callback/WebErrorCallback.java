package com.tsienlibrary.loadsir.callback;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.tsienlibrary.R;

/**
 * Description:TODO
 * Create Time:2017/9/3 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class WebErrorCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_web_error;
    }

    @Override
    protected void onViewCreate(final Context context, View view) {
        super.onViewCreate(context, view);
        (view.findViewById(R.id.tv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity((Activity) context);
            }
        });
    }
//
//    @Override
//    protected boolean onReloadEvent(final Context context, View view) {
//        //整个View的点击监听，子view的点击监听如果写在这里，第一次也先执行最外层监听
//        ToastUtils.showLong("点击了");
//        return true;
//    }
}
