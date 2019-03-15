package com.hdnz.inanming.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TempFileHelper.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-12-06 11:36
 * Description:
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TempFileHelper {
    /**
     *  @field  时间格式化
     */
    private static final SimpleDateFormat PHOTO_NAME_POSTFIX_SDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm_ss", Locale.getDefault());
    private File mCameraFileDir;
    private String mCameraFilePath;
    private String mCropFilePath;

    public TempFileHelper() {
    }

    /**
     * @param cameraFileDir 拍照后图片保存的目录
     */
    public TempFileHelper(File cameraFileDir) {
        mCameraFileDir = cameraFileDir;
        if (!mCameraFileDir.exists()) {
            mCameraFileDir.mkdirs();
        }
    }


    /**
     * 创建用于保存拍照生成的图片文件
     *
     * @return
     * @throws IOException
     */
    public File createCameraFile() throws IOException {
        File captureFile = File.createTempFile(
                "Capture_" + PHOTO_NAME_POSTFIX_SDF.format(new Date()),
                ".jpg",
                mCameraFileDir);
        mCameraFilePath = captureFile.getAbsolutePath();
        return captureFile;
    }

    /**
     * 创建用于保存裁剪生成的图片文件
     *
     * @return
     * @throws IOException
     */
    public File createCropFile() throws IOException {
        File cropFile = File.createTempFile(
                "Crop_" + PHOTO_NAME_POSTFIX_SDF.format(new Date()),
                ".jpg",
                BGABaseAdapterUtil.getApp().getExternalCacheDir());
        mCropFilePath = cropFile.getAbsolutePath();
        return cropFile;
    }

    /**
     * 删除拍摄的照片
     */
    public void deleteCameraFile() {
        deleteFile(mCameraFilePath);
        mCameraFilePath = null;
    }

    /**
     * 删除裁剪的照片
     */
    public void deleteCropFile() {
        deleteFile(mCropFilePath);
        mCropFilePath = null;
    }

    private void deleteFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File photoFile = new File(filePath);
            photoFile.deleteOnExit();
        }
    }

    /**
     * 获取拍照路径
     *
     * @return
     */
    public String getCameraFilePath() {
        return mCameraFilePath;
    }

    /**
     * 获取裁剪图片路径
     *
     * @return
     */
    public String getCropFilePath() {
        return mCropFilePath;
    }
}
