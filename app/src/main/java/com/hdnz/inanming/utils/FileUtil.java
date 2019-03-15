package com.hdnz.inanming.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.photopicker.util.BGAPhotoFileProvider;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    FileUtil.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-12-06 9:59
 * Description: 百度ocr 保存的文件路径
 * Version:     V1.0.0
 * History:     历史信息
 */
public class FileUtil {

    private static final SimpleDateFormat PHOTO_NAME_POSTFIX_SDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm_ss", Locale.getDefault());

    /**
     * 获取ocr保存的文件
     *
     * @param context
     * @return
     */
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "picz.jpg");
        return file;
    }


    /**
     * 根据文件创建 Uri
     *
     * @param file
     * @return
     */
    public static Uri createFileUri(File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String authority = BGABaseAdapterUtil.getApp().getApplicationInfo().packageName + ".bga_photo_picker.file_provider";
            return BGAPhotoFileProvider.getUriForFile(BGABaseAdapterUtil.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 处理获取返回的照片地址
     *
     * @param context
     * @param contentURI
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
