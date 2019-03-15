package com.tsienlibrary.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.AppUtils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/02
 *     desc   :
 * </pre>
 */
public class DialogUtils {

    public static void showInfoDialog(Context context, String message) {
        showInfoDialog(context, message, "提示", null, "知道了", null, null);
    }

    public static void showInfoDialog(Context context, String message,
                                      String titleStr, String negativeStr, String positiveStr,
                                      DialogInterface.OnClickListener negativeOnClickListener,
                                      DialogInterface.OnClickListener positiveOnClickListener) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        localBuilder.setPositiveButton(positiveStr, positiveOnClickListener);
        localBuilder.setNegativeButton(negativeStr, negativeOnClickListener);
        localBuilder.setCancelable(false);
        localBuilder.show();
    }

    /**
     * TODO： 如果用户开启了[不保留活动]，则弹出此提示框
     *
     * @param context
     */
    public static void showAlwaysFinishDialog(Context context) {

        int alwaysFinish = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            Dialog dialog = new AlertDialog.Builder(context)
                    .setMessage(
                            "由于您已开启 [不保留活动] ，导致" + AppUtils.getAppName() + "部分功能无法正常使用。我们建议您点击左下方 [设置] 按钮，在 [开发者选项] 中关闭 [不保留活动] 功能。")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("设置", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                            startActivity(intent);
                        }
                    }).create();
            dialog.show();
        }
    }

}
