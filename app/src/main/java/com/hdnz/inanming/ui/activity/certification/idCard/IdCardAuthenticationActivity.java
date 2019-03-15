package com.hdnz.inanming.ui.activity.certification.idCard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.result.ImageBean;
import com.hdnz.inanming.bean.request.AddAuthBeanReq;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.utils.UrlUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.utils.OpenFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.widget.QuickPopup;

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
public class IdCardAuthenticationActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    //从图片库选择图片
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    //调用相机拍照
    private static final int REQUEST_CODE_TAKE_PHOTO = 2;
    //裁剪图片
    private static final int REQUEST_CODE_CROP = 3;
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    //@field OCR拍照完成的结果吗
    private static final int REQUEST_CODE_CAMERA = 102;

    private String pathSideFront;
    private String pathSideBack;
    private String pathHand;


    private BGAPhotoHelper mPhotoHelper;

    //PopupWindow
    private QuickPopup mQuickPopup;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rl_idcard_face_hide)
    RelativeLayout rlIdCardFaceHide;
    @BindView(R.id.iv_idcard_side_front)
    ImageView ivIdCardSideFront;
    @BindView(R.id.rl_idcard_face)
    RelativeLayout rlIdCardFace;

    @BindView(R.id.rl_idcard_reverse_hide)
    RelativeLayout rlIdCardReverseHide;
    @BindView(R.id.iv_idcard_side_back)
    ImageView ivIdCardSideBack;
    @BindView(R.id.rl_idcard_reverse)
    RelativeLayout rlIdCardReverse;

    @BindView(R.id.rl_idcard_hand_hide)
    RelativeLayout rlIdCardHandHide;
    @BindView(R.id.iv_hand_idcard)
    ImageView ivHandIdCard;
    @BindView(R.id.rl_idcard_hand)
    RelativeLayout rlIdCardHand;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id_card_authentication;
    }

    @Override
    protected void initData() {
        //正面照路径
        pathSideFront = OpenFileUtils.getImagePath(this) + "pic_id_card_side_front.png";
        //反面照路径
        pathSideBack = OpenFileUtils.getImagePath(this) + "pic_id_card_side_back.png";
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), OpenFileUtils.getImagePath(this));
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);

        //  初始化百度OCR本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                (errorCode, e) -> {
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

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.idcard_real_authentication));
        tvRightMenu.setVisibility(View.VISIBLE);
        //设置save文本
        tvRightMenu.setText(R.string.save);

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
                setPermissions(R.id.rl_idcard_face);
                break;
            case R.id.rl_idcard_reverse:
                setPermissions(R.id.rl_idcard_reverse);
                break;
            case R.id.rl_idcard_hand:
                setPermissions(R.id.rl_idcard_hand);
                break;
            case R.id.tv_right_menu:
                if (TextUtils.isEmpty(pathSideFront) || TextUtils.isEmpty(pathSideBack) || TextUtils.isEmpty(pathHand)) {
                    ToastUtils.showLong("请提交指定的三张图片");
                    return;
                }
                showProgressDialog("正在上传证件");
                //上传图片
                mPresenter.uploadFiles(UrlUtils.UPLOAD_IMAGES, ImageBean.class, new File[]{new File(pathSideFront), new File(pathSideBack), new File(pathHand)});
//                List<String> fileIds = new ArrayList<>();
//                fileIds.add("315969003040542720");
//                fileIds.add("315969003048931328");
//                fileIds.add("315969003053125632");
//                authSubmit(fileIds);
                ToastUtils.showShort("保存");
                break;
        }
    }


    /**
     * 提交身份证信息和图片Id
     *
     * @param fileIds
     */
    private void authSubmit(List<String> fileIds) {
        updateDialogTitle("提交认证信息");
        Gson gson = new Gson();
        AddAuthBeanReq requestAddAuth = new AddAuthBeanReq();
        AddAuthBeanReq.ParamsBean params = new AddAuthBeanReq.ParamsBean();
        List<AddAuthBeanReq.ParamsBean.PapersBean> papersList = new ArrayList<>();
        params.setRealName(realName);
        if (TextUtils.equals(sex, "男")) {
            params.setSex("1");
        } else {
            params.setSex("2");
        }
        params.setNation(nation);
        params.setBirthdate(birthday);
        params.setLocation(location);
        params.setAddress(address);
        params.setCardNo(cardNo);
        params.setName(name);
        params.setCardtypeId(cardtypeId);
        params.setNumber(number);
        for (int i = 0; i < fileIds.size(); i++) {
            AddAuthBeanReq.ParamsBean.PapersBean papersBean = new AddAuthBeanReq.ParamsBean.PapersBean();
            papersBean.setCard(fileIds.get(i));
            if (i == 0) {
                papersBean.setDescription("正面");
                papersBean.setType("cardtype20190117101");
            } else if (i == 1) {
                papersBean.setDescription("反面");
                papersBean.setType("cardtype20190117102");
            } else if (i == 2) {
                papersBean.setDescription("手持");
                papersBean.setType("cardtype20190117103");
            }
            papersList.add(papersBean);
        }
        params.setPapersList(papersList);
        requestAddAuth.setParams(params);
        String jsonData = gson.toJson(requestAddAuth);
        request(UrlUtils.AUTHENTICATION_ADD, jsonData, Object.class);
    }

    @Override
    protected void request(String requestUrl, String jsonData, Class clazz) {
        super.request(requestUrl, jsonData, clazz);
        mPresenter.request(requestUrl, jsonData, clazz);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        if (TextUtils.equals(requestUrl, UrlUtils.UPLOAD_IMAGES)) {
            //上传图片成功
            ImageBean imageBean = (ImageBean) commonBean.getData();
            if (imageBean == null) {
                ToastUtils.showLong("图片信息丢失");
                return;
            }
            authSubmit(imageBean.getFileIds());
        } else if (TextUtils.equals(requestUrl, UrlUtils.AUTHENTICATION_ADD)) {
            dismissDialog();
            finish();
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
        dismissDialog();
        ToastUtils.showLong("出现错误：" + msg);
    }

    /**
     * TODO:动态获取权限
     *
     * @param type
     */
    private void setPermissions(int type) {
        RxPermissions rxPermissions = new RxPermissions(this);
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        mPresenter.setPermissions(type, rxPermissions, permissions);
    }

    @Override
    public void permissionsAreGranted(int type) {
        switch (type) {
            case R.id.rl_idcard_face:
                //身份证正面照
                takePhotosCOR1(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
//                takePhotosCOR2(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                break;
            case R.id.rl_idcard_reverse:
                //身份证反面照
//                takePhotosCOR1(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                takePhotosCOR2(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                break;
            case R.id.rl_idcard_hand:
                //手持身份证拍照
                mQuickPopup = QuickPopupBuilder.with(getContext())
                        .contentView(R.layout.popup_select_photo)
                        .config(new QuickPopupConfig()
                                .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                .withClick(R.id.tv_camera, v -> takePhoto())
                                .withClick(R.id.tv_photos, v -> choosePhoto())
                                .withClick(R.id.tv_cancel, v -> mQuickPopup.dismiss())
                        ).show();
                break;
        }
    }

    /**
     * 手动执行拍照（百度OCR UI）
     *
     * @param type
     */
    private void takePhotosCOR1(String type) {
        Intent intent = new Intent(this, CameraActivity.class);
        if (TextUtils.equals(type, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT)) {
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, pathSideFront);
        } else {
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, pathSideBack);
        }
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, type);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 自动识别拍照（百度OCR UI）
     *
     * @param type
     */
    private void takePhotosCOR2(String type) {
        Intent intent = new Intent(this, CameraActivity.class);
        if (TextUtils.equals(type, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT)) {
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, pathSideFront);
        } else {
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, pathSideBack);
        }
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, type);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 从图片库选择图片
     */
    public void choosePhoto() {
        startActivityForResult(mPhotoHelper.getChooseSystemGalleryIntent(), REQUEST_CODE_CHOOSE_PHOTO);
    }

    /**
     * 调用相机拍照
     */
    public void takePhoto() {
        try {
            startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
        } catch (Exception e) {
            BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_take_photo);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        idcardSideFrontAndrBack(requestCode, resultCode, data);
        handIdcard(requestCode, resultCode, data);

    }

    /**
     * 选择身份证正反面的回调
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    private void idcardSideFrontAndrBack(int requestCode, int resultCode, Intent data) {

//        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            String filePath =  FileUtil.getRealPathFromURI(mContext, uri);
//            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
//        }
//
//        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            String filePath =  FileUtil.getRealPathFromURI(mContext, uri);
//            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
//        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CAMERA) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, pathSideFront, contentType);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, pathSideBack, contentType);
                    }
                }
            }
        }
    }

    /**
     * 选择手持身份证照片的回调
     *
     * @param resultCode
     * @param requestCode
     * @param data
     */
    private void handIdcard(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                //从图库选择图片成功
                try {
                    //准备执行裁剪图片
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(data.getData()), 360, 205), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                //调用相机，拍摄照片成功
                try {
                    //准备执行裁剪图片
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getCameraFilePath(), 340, 205), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCameraFile();
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
                //裁剪成功
                pathHand = mPhotoHelper.getCropFilePath();
                showImage(mPhotoHelper.getCropFilePath(), ivHandIdCard);
                mQuickPopup.dismiss();//关闭Popup
            }
        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();
                mPhotoHelper.deleteCropFile();
            }
        }
    }

    private String realName;
    private String sex;                //类型：String  必有字段  备注：性别 1：男 2：女
    private String nation;                //类型：String  必有字段  备注：民族
    private String birthday;            //类型：String  必有字段  备注：出生日期
    private String location;                //类型：String  必有字段  备注：户籍所在地
    private String address;                //类型：String  必有字段  备注：居住地址
    private String cardNo;                //类型：String  必有字段  备注：身份证号
    private String name;             //类型：String  必有字段  备注：证件名称
    private String cardtypeId;      //类型：String  必有字段  备注：证件类型id
    private String number;

    /**
     * 通过OCR识别身份证方法(需要联网)
     *
     * @param idCardSide
     * @param filePath
     */
    private void recIDCard(String idCardSide, String filePath, String contentType) {
        showProgressDialog("识别证件信息");
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
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        if (TextUtils.equals(result.getImageStatus(), "non_idcard")) {
                            ToastUtils.showLong("不是身份证");
                            return;
                        }
                        if (TextUtils.equals(result.getImageStatus(), "unkown")) {
                            ToastUtils.showLong("未知的身份证");
//                            return;
                        }
                        if (TextUtils.equals(result.getImageStatus(), "noamal")) {
                            ToastUtils.showLong("正常的身份证");
                        }
                        showImage(filePath, ivIdCardSideFront);
                        realName = result.getName() == null ? "" : result.getName().getWords();
                        sex = result.getGender() == null ? "" : result.getGender().getWords();
                        nation = result.getEthnic() == null ? "" : result.getEthnic().getWords();
                        birthday = result.getBirthday() == null ? "" : result.getBirthday().getWords();
                        location = result.getAddress() == null ? "" : result.getAddress().getWords();
                        address = "";
                        cardNo = result.getIdNumber() == null ? "" : result.getIdNumber().getWords();
                        name = "身份证";
                        cardtypeId = "cardtype20190117100";
                        number = cardNo;
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        result.getIssueAuthority().getWords(); //签发机关
                        result.getSignDate().getWords();//初始日期
                        result.getExpiryDate().getWords();//过期日期
                        showImage(filePath, ivIdCardSideBack);
                    }
                    LogUtils.i("OCR", result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                LogUtils.i("OCR", error.getMessage());
            }
        });
    }


    /**
     * 拍照、选择图片完，展示图片
     *
     * @param uri
     * @param iv
     */
    private void showImage(String uri, ImageView iv) {
        dismissDialog();
        iv.setVisibility(View.VISIBLE);
        GlideApp.with(this)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不读取本地磁盘的缓存
                .skipMemoryCache(true)
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(iv);
    }

}