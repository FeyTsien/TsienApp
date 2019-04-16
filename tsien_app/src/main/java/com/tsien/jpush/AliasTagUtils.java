package com.tsien.jpush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

public class AliasTagUtils {
    private String TAG = "AliasTagUtils";

    public static final int ACTION_SET_ALIAS = 1;
    public static final int ACTION_GET_ALIAS = 2;
    public static final int ACTION_DELETE_ALIAS = 3;
    public static final int ACTION_ADD_TAGS = 4;
    public static final int ACTION_SET_TAGS = 5;
    public static final int ACTION_DELETE_TAGS = 6;
    public static final int ACTION_CHECK_TAG = 7;
    public static final int ACTION_GET_ALL_TAGS = 8;
    public static final int ACTION_CLEAN_TAGS = 9;
    public static final int ACTION_SET_MOBILE_NUMBER = 10;

    public static final int DELAY_SEND_ACTION = 1;

    public static int sequence = 1;

    @SuppressLint("StaticFieldLeak")
    private static AliasTagUtils mInstance;
    private Context mContext;

    private AliasTagUtils() {
    }

    public static AliasTagUtils getInstance() {
        if (mInstance == null) {
            synchronized (AliasTagUtils.class) {
                mInstance = new AliasTagUtils();
            }
        }
        return mInstance;
    }

    private SparseArray<Object> setActionCache = new SparseArray<Object>();

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == DELAY_SEND_ACTION) {
                if (msg.obj != null && msg.obj instanceof TagAliasBean) {
                    LogUtils.i(TAG, "on delay time");
                    sequence++;
                    TagAliasBean tagAliasBean = (TagAliasBean) msg.obj;
                    setActionCache.put(sequence, tagAliasBean);
                    if (mContext != null) {
                        handleAction(mContext, sequence, tagAliasBean);
                    } else {
                        LogUtils.e(TAG, "#unexcepted - context was null");
                    }
                } else {
                    LogUtils.w(TAG, "#unexcepted - msg obj was incorrect");
                }
            }
        }
    };

    private void init(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public void handleAction(Context context, int sequence, TagAliasBean tagAliasBean) {
        init(context);
        if (tagAliasBean == null) {
            LogUtils.w(TAG, "tagAliasBean was null");
            return;
        }
        setActionCache.put(sequence, tagAliasBean);
        switch (tagAliasBean.getAction()) {
            case ACTION_SET_ALIAS:
                JPushInterface.setAlias(mContext, sequence, tagAliasBean.getAlias());
                break;
            case ACTION_GET_ALIAS:
                JPushInterface.getAlias(mContext, sequence);
                break;
            case ACTION_DELETE_ALIAS:
                JPushInterface.deleteAlias(mContext, sequence);
                break;
            case ACTION_ADD_TAGS:
                JPushInterface.addTags(mContext, sequence, tagAliasBean.getTags());
                break;
            case ACTION_SET_TAGS:
                JPushInterface.setTags(mContext, sequence, tagAliasBean.getTags());
                break;
            case ACTION_DELETE_TAGS:
                JPushInterface.deleteTags(mContext, sequence, tagAliasBean.getTags());
                break;
            case ACTION_CHECK_TAG:
                //一次只能check一个tag
                String tag = (String) tagAliasBean.getTags().toArray()[0];
                JPushInterface.checkTagBindState(mContext, sequence, tag);
                break;
            case ACTION_GET_ALL_TAGS:
                JPushInterface.getAllTags(mContext, sequence);
                break;
            case ACTION_CLEAN_TAGS:
                JPushInterface.cleanTags(context, sequence);
                break;
            case ACTION_SET_MOBILE_NUMBER:
                JPushInterface.setMobileNumber(context, sequence, tagAliasBean.getMobileNumber());
                break;
        }
    }

    private boolean RetryActionIfNeeded(int errorCode, TagAliasBean tagAliasBean) {
        if (!NetworkUtils.isConnected()) {
            LogUtils.w(TAG, "no network");
            return false;
        }
        //返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
        if (errorCode == 6002 || errorCode == 6014) {
            LogUtils.d(TAG, "need retry");
            if (tagAliasBean != null) {
                Message message = new Message();
                message.what = DELAY_SEND_ACTION;
                message.obj = tagAliasBean;
                mHandler.sendMessageDelayed(message, 1000 * 60);
                return true;
            }
        }
        return false;
    }

    void onOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        init(context);
        TagAliasBean tagAliasBean = (TagAliasBean) setActionCache.get(sequence);

        if (tagAliasBean == null) {
            ToastUtils.showShort("获取缓存记录失败");
            return;
        }

        if (jPushMessage.getErrorCode() == 0) {
            //成功
            LogUtils.i(TAG, "action - modify alias Success,sequence:" + sequence);
            setActionCache.remove(sequence);
        } else {
            if (jPushMessage.getErrorCode() == 6018) {
                //tag数量超过限制,需要先清除一部分再add
            } else if (jPushMessage.getErrorCode() == 6002) {
                //建议重试，一般出现在网络不佳、初始化尚未完成时。
            }

            if (!RetryActionIfNeeded(jPushMessage.getErrorCode(), tagAliasBean)) {
                LogUtils.i(TAG, "重试失败");
            }
        }
    }

    public static class TagAliasBean {
        int action;
        Set<String> tags;
        String alias;
        String mobileNumber;

        int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public Set<String> getTags() {
            return tags;
        }

        public void setTags(Set<String> tags) {
            this.tags = tags;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }
    }
}
