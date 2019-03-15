

package com.hdnz.inanming.ui.activity.certification.idCard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.utils.FileUtil;
import com.hdnz.inanming.utils.PopWindowHelper;
import com.hdnz.inanming.utils.TempFileHelper;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    IdCardAuthenticationActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-08 16:09
 * Description: 身份证实名认证activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class IdCardAuthenticationActivityC extends MVPActivity<MVPContract.View, MVPPresenter>
        implements MVPContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rl_idcard_face_hide)
    RelativeLayout rlIdcardFaceHide;
    @BindView(R.id.iv_idcard_side_front)
    ImageView ivIdcardFaceShow;
    @BindView(R.id.rl_idcard_face)
    RelativeLayout rlIdcardFace;
    @BindView(R.id.rl_idcard_reverse_hide)
    RelativeLayout rlIdcardReverseHide;
    @BindView(R.id.iv_idcard_side_back)
    ImageView ivIdcardReverseShow;
    @BindView(R.id.rl_idcard_reverse)
    RelativeLayout rlIdcardReverse;
    @BindView(R.id.rl_idcard_hand_hide)
    RelativeLayout rlIdcardHandHide;
    @BindView(R.id.iv_hand_idcard)
    ImageView ivIdcardHandShow;
    @BindView(R.id.rl_idcard_hand)
    RelativeLayout rlIdcardHand;
    /**
     * @field 弹出层菜单
     */
    private PopupWindow popupWindow;
    /**
     * 百度OCR 本地质量错误代码
     */
    private int mErrorCode;
    /**
     * @field 身份证正面
     */
    private static final int CAMERA_ID_CARD_FRONT = 1;
    /**
     * @field 身份证反面
     */
    private static final int CAMERA_ID_CARD_BACK = 2;
    /**
     * @field 手持身份证拍照
     */
    private static final int CAMERA_ID_CARD_HANDLE = 3;
    /**
     * @field 选择图片，正面请求吗
     */
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    /**
     * @field 选择图片，反面请求吗
     */
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    /**
     * @field 选择图片，手持照请求吗
     */
    private static final int REQUEST_CODE_PICK_IMAGE_HANDLE = 203;
    /**
     * @field 裁剪图片，正面请求吗
     */
    private static final int REQUEST_CODE_CROP_IMAGE_FRONT = 204;
    /**
     * @field 裁剪图片，反面请求吗
     */
    private static final int REQUEST_CODE_CROP_IMAGE_BACK = 205;
    /**
     * @field 裁剪图片，手持照请求吗
     */
    private static final int REQUEST_CODE_CROP_IMAGE_HANDLE = 206;
    /**
     * @field OCR拍照完成的结果吗
     */
    private static final int REQUEST_CODE_CAMERA = 102;
    /**
     * @field 第三方拍照、图库选择helper
     */
    private BGAPhotoHelper mPhotoHelper;
    /**
     * @field 创建临时文件helper
     */
    private TempFileHelper tempFileHelper;
    private Map<String, Uri> imgUriMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_authentication;
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        //拍照、选择图库帮助类
        //拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "inanmingTempImage");
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
        tempFileHelper = new TempFileHelper(takePhotoDir);
        //创建保存图片文件的map
        imgUriMap = new HashMap<>();

        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.idcard_real_authentication));
        tvRightMenu.setVisibility(View.VISIBLE);
        //设置save文本
        tvRightMenu.setText(R.string.save);

        //  初始化百度OCR本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                (errorCode, e) -> {
                    mErrorCode = errorCode;
                    String msg;
                    switch (errorCode) {
                        case CameraView.NATIVE_SOLOAD_FAIL:
                            msg = "加载so失败，请确保apk中存在ui部分的so";
                            break;
                        case CameraView.NATIVE_AUTH_FAIL:
                            msg = "授权本地质量控制，token获取失败";
                            break;
                        case CameraView.NATIVE_INIT_FAIL:
                            msg = "授权本地质量控制，模型加载失败";
                            break;
                        default:
                            msg = String.valueOf(errorCode);
                    }
                    LogUtils.e("本地质量控制初始化错误，错误原因： " + msg);
                });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BGAPhotoHelper.onSaveInstanceState(mPhotoHelper, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BGAPhotoHelper.onRestoreInstanceState(mPhotoHelper, savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理选择照片请求吗
        handlePickerPhotosResult(requestCode, resultCode, data);
        //处理裁剪图片请求吗
        handleCropPhotosResult(requestCode, resultCode, data);
        //处理拍照请求吗
        handleTakePhotosResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }

    @OnClick({R.id.rl_idcard_face, R.id.rl_idcard_reverse, R.id.rl_idcard_hand, R.id.tv_right_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_idcard_face:
                ToastUtils.showShort("拍摄正面");
                showPopWindow(view, CAMERA_ID_CARD_FRONT);
                break;
            case R.id.rl_idcard_reverse:
                ToastUtils.showShort("拍摄反面");
                showPopWindow(view, CAMERA_ID_CARD_BACK);
                break;
            case R.id.rl_idcard_hand:
                ToastUtils.showShort("拍摄手持照");
                showPopWindow(view, CAMERA_ID_CARD_HANDLE);
                break;
            case R.id.tv_right_menu:
                ToastUtils.showShort("保存：" + imgUriMap.size());
                break;
        }
    }

    /***************************************  以下是私有方法  **************************************/

    /**
     * 打开弹窗
     *
     * @param attachView
     * @param type
     */
    private void showPopWindow(View attachView, int type) {
        PopWindowHelper.getInstance()
                .setAttachView(attachView)
                .setLayoutId(R.layout.pop_camera_select_layout)
                .setPopActivity(this)
                .setPopWindowListener(new PopWindowHelper.PopWindowListener() {
                    @Override
                    public void popWindowView(View popView, View attachView, PopupWindow popupWindow, String message) {
                        TextView tvCamera = (TextView) popView.findViewById(R.id.tv_camera);
                        TextView tvPickPhoto = (TextView) popView.findViewById(R.id.tv_pick_photo);
                        TextView tvCancel = (TextView) popView.findViewById(R.id.tv_cancel);
                        //拍照
                        tvCamera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showLong("拍照");
                                try {
                                    takePhotos(v, type);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                popupWindow.dismiss();
                            }
                        });
                        //选择照片
                        tvPickPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showLong("选择照片");
                                selectPhotos(v, type);
                                popupWindow.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showLong("取消");
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                .showPopWindow();
    }

    /**
     * 调用百度ocr进行自动拍照、识别
     *
     * @param clickView
     * @param type
     */
    private void takePhotos(View clickView, int type) throws IOException {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                tempFileHelper.createCameraFile().getAbsolutePath());
        //根据错误代码，判断是否使用自动拍照
        if (mErrorCode == CameraView.NATIVE_AUTH_INIT_SUCCESS) {
            intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                    true);
            // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
            // 请手动使用CameraNativeHelper初始化和释放模型
            // 推荐这样做，可以避免一些activity切换导致的不必要的异常
            intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                    true);
        }
        //判断拍照类型
        if (type == CAMERA_ID_CARD_FRONT) {
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        } else if (type == CAMERA_ID_CARD_BACK) {
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        } else if (type == CAMERA_ID_CARD_HANDLE) {
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        } else {

        }
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 选择图库照片
     *
     * @param clickView
     * @param type
     */
    private void selectPhotos(View clickView, int type) {
        if (type == CAMERA_ID_CARD_FRONT) {
            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_PICK_IMAGE_FRONT);
        } else if (type == CAMERA_ID_CARD_BACK) {
            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_PICK_IMAGE_BACK);
        } else {
            startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_PICK_IMAGE_HANDLE);
        }
    }

    /**
     * 通过OCR识别身份证方法
     *
     * @param idCardSide
     * @param filePath
     */
    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    LogUtils.e("onResult: " + result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtils.e("onError: " + error.toString());
            }
        });
    }

    /**
     * 拍照、选择图片完，展示图片
     *
     * @param uri
     * @param iv
     * @param type
     */
    private void showImage(Uri uri, ImageView iv, int type) {
        iv.setVisibility(View.VISIBLE);
        GlideApp.with(this)
                .load(uri)
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(iv);
        imgUriMap.put("" + type, uri);
    }

    /**
     * 调用裁剪方法
     *
     * @param sourceUri
     * @param storeUri
     * @param requestCode
     */
    private void cropImage(Uri sourceUri, Uri storeUri, int requestCode) {
        //设置裁剪功能参数
        UCrop.Options options = new UCrop.Options();
        //设置png格式图片
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //设置压缩比
        options.setCompressionQuality(80);
        //开启底部导航控件
        options.setHideBottomControls(false);
        //设置是否自由裁剪，可拖动裁剪框
        options.setFreeStyleCropEnabled(true);
        UCrop.of(sourceUri, storeUri)
                //设置比率选择
                .useSourceImageAspectRatio()
                //和上面设置比率选择互斥
                //.withAspectRatio(1, 1)
                .withMaxResultSize(800, 600)
                .withOptions(options)
                .start(this, requestCode);
    }

    /**
     * 处理选择照片，请求吗
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handlePickerPhotosResult(int requestCode, int resultCode, Intent data) {
        //选择照片——正面
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = FileUtil.getRealPathFromURI(this, uri);
            try {
                //裁剪图片
                cropImage(uri, Uri.fromFile(tempFileHelper.createCropFile()), REQUEST_CODE_CROP_IMAGE_FRONT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //选择照片——反面
        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = FileUtil.getRealPathFromURI(this, uri);
            try {
                //裁剪图片
                cropImage(uri, Uri.fromFile(tempFileHelper.createCropFile()), REQUEST_CODE_CROP_IMAGE_BACK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //选择照片——手持照
        if (requestCode == REQUEST_CODE_PICK_IMAGE_HANDLE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = FileUtil.getRealPathFromURI(this, uri);
            try {
                //裁剪图片
                cropImage(uri, Uri.fromFile(tempFileHelper.createCropFile()), REQUEST_CODE_CROP_IMAGE_HANDLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理裁剪照片，请求吗
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleCropPhotosResult(int requestCode, int resultCode, Intent data) {
        //裁剪正面
        if (requestCode == REQUEST_CODE_CROP_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            //获取裁剪图片的Uri
            Uri resultUri = UCrop.getOutput(data);
            //获取裁剪图片的字符串路径
            String cropFilePath = tempFileHelper.getCropFilePath();
            showImage(resultUri, ivIdcardFaceShow, CAMERA_ID_CARD_FRONT);
        }

        //裁剪反面
        if (requestCode == REQUEST_CODE_CROP_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
            //获取裁剪图片的Uri
            Uri resultUri = UCrop.getOutput(data);
            //获取裁剪图片的字符串路径
            String cropFilePath = tempFileHelper.getCropFilePath();
            showImage(resultUri, ivIdcardReverseShow, CAMERA_ID_CARD_BACK);
        }

        //裁剪手持照
        if (requestCode == REQUEST_CODE_CROP_IMAGE_HANDLE && resultCode == Activity.RESULT_OK) {
            //获取裁剪图片的Uri
            Uri resultUri = UCrop.getOutput(data);
            //获取裁剪图片的字符串路径
            String cropFilePath = tempFileHelper.getCropFilePath();
            showImage(resultUri, ivIdcardHandShow, CAMERA_ID_CARD_HANDLE);
        }

        //裁剪出现异常
        if (resultCode == Activity.RESULT_OK && resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            if (cropError != null) {
                ToastUtils.showShort("裁剪图片异常!");
                LogUtils.e("handleCropPhotosResult handleCropError: ", cropError);
            } else {
                ToastUtils.showShort("裁剪图片异常!");
            }
        }
    }

    /**
     * 处理百度OCR拍照，请求吗
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleTakePhotosResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    String filePath = data.getStringExtra(CameraActivity.KEY_OUTPUT_FILE_PATH);
                    String cameraFilePath = tempFileHelper.getCameraFilePath();
                    File file = new File(cameraFilePath);
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        showImage(FileUtil.createFileUri(file), ivIdcardFaceShow, CAMERA_ID_CARD_FRONT);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        showImage(FileUtil.createFileUri(file), ivIdcardReverseShow, CAMERA_ID_CARD_BACK);
                    } else if (CameraActivity.CONTENT_TYPE_GENERAL.equals(contentType)) {
                        showImage(FileUtil.createFileUri(file), ivIdcardHandShow, CAMERA_ID_CARD_HANDLE);
                    }
                }
            }
        }
    }
}