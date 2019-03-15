package com.hdnz.inanming.jpush;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 * */
public class MyJPushMessageReceiver extends JPushMessageReceiver {
    private final String TAG = "MyJPushMessageReceiver";

    @Override
    public void onTagOperatorResult(Context context,JPushMessage jPushMessage) {
        LogUtils.i(TAG,"onTagOperatorResult");
        AliasTagUtils.getInstance().onOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        LogUtils.i(TAG,"onCheckTagOperatorResult");
        AliasTagUtils.getInstance().onOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        LogUtils.i(TAG,"onAliasOperatorResult");
        AliasTagUtils.getInstance().onOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        LogUtils.i(TAG,"onMobileNumberOperatorResult");
        AliasTagUtils.getInstance().onOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}
