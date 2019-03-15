package com.hdnz.inanming.ocr;

import android.content.Context;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.blankj.utilcode.util.LogUtils;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    BaiDuOcrHelper.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-12-05 15:34
 * Description: 百度OCR文字识别初始化类
 * Version:     V1.0.0
 * History:     历史信息
 */
public class BaiDuOcrHelper {
    /**
     * 声明volatile 保证编译器不做优化
     */
    private volatile static BaiDuOcrHelper instance;
    /**
     * 百度OCR token是否认证标识
     */
    private boolean hasGotToken;

    /**
     * 私有构造方法
     */
    private BaiDuOcrHelper() {
    }

    /**
     * 单例，提供给外部调用方法
     *
     * @return
     */
    public static BaiDuOcrHelper getInstance() {
        //采用加锁、双重判空检测
        if (null == instance) {
            synchronized (BaiDuOcrHelper.class) {
                if (null == instance) {
                    instance = new BaiDuOcrHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 百度OCR，以license文件方式初始化
     */
    public void initAccessToken(Context context) {
        OCR.getInstance(context).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                LogUtils.e("ocr licence方式获取token失败: " + error.getMessage());
            }
        }, context);
    }

    /**
     * 每次调用百度OCR，例如文字识别、身份证识别、银行卡、行驶证、车牌号等，需检测Token是否认证通过
     *
     * @return
     */
    public boolean checkTokenStatus(Context context) {
        if (!hasGotToken) {
            Toast.makeText(context, "百度OCR token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }
}
