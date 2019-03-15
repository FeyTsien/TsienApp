package com.tsienlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;

import java.io.File;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/21
 *     desc   :
 * </pre>
 */
public class OpenFileUtils {


    //Apk文件路径
    public static String getApkPath(Context context) {
        String directoryPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir("apk").getAbsolutePath();
        } else {
            //没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + "apk";
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }


    //Apk文件路径
    public static String getImagePath(Context context) {
        String directoryPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir("image").getAbsolutePath();
        } else {
            //没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + "image";
        }
        File file = new File(directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }

    /**
     * 打开APK安装包
     *
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(context, "com.hdnz.inanming.fileprovider", file); //与manifest中定义的provider中的authorities="com.shawpoo.app.fileprovider"保持一致
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "image/*");
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
