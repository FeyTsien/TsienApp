package com.hdnz.inanming.ui.activity.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.tsienlibrary.ui.activity.BaseActivity;
import com.tsienlibrary.utils.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/12
 *     desc   :
 * </pre>
 */
public class ScanActivity extends BaseActivity {

    private CaptureFragment captureFragment;
    public boolean isOpen = false;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_menu)
    TextView mTvRightMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initData() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.scan);
        mTvRightMenu.setText(R.string.photo_albums);
        mTvRightMenu.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }

            }
        });
    }

    @OnClick({R.id.tv_right_menu})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
        }
    }

    private static final int REQUEST_IMAGE = 1002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * 选择系统图片并解析
         */
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                            bundle.putString(CodeUtils.RESULT_STRING, result);
                            resultIntent.putExtras(bundle);
                            ScanActivity.this.setResult(RESULT_OK, resultIntent);
                            ScanActivity.this.finish();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                            bundle.putString(CodeUtils.RESULT_STRING, "");
                            resultIntent.putExtras(bundle);
                            ScanActivity.this.setResult(RESULT_OK, resultIntent);
                            ScanActivity.this.finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);
            ScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);
            ScanActivity.this.finish();
        }
    };
}
