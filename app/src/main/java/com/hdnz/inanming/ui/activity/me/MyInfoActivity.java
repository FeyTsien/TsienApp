package com.hdnz.inanming.ui.activity.me;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.ImageBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.me.information.MyAddressListActivity;
import com.hdnz.inanming.ui.activity.setting.alter.AlterPhoneActivity;
import com.hdnz.inanming.utils.GlideUtils;
import com.hdnz.inanming.utils.UrlUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.utils.OpenFileUtils;

import java.io.File;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGABaseAdapterUtil;
import cn.bingoogolapple.photopicker.imageloader.BGAImage;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.widget.QuickPopup;

public class MyInfoActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    //从图片库选择图片
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    //调用相机拍照
    private static final int REQUEST_CODE_TAKE_PHOTO = 2;
    //裁剪图片
    private static final int REQUEST_CODE_CROP = 3;

    private String protraitId;
    private String nickname;
    private String signature;

    private QuickPopup mQuickPopup;//PopupWindow
    private BGAPhotoHelper mPhotoHelper;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.iv_my_portrait)
    ImageView mIvMyPortrait;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_signature)
    EditText etSignature;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initData() {
        protraitId = AppData.getValueStr(AppData.KEY_PORTRAIT_URL);
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(OpenFileUtils.getImagePath(this));
        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.mine_edit_title));
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.save);


        GlideApp.with(this)
                .load(GlideUtils.getGlideUrl(protraitId))
                .placeholder(R.drawable.test)
                .error(R.drawable.empty)
                .into(mIvMyPortrait);
        etNickname.setText(AppData.getValueStr(AppData.KEY_NICKNAME));
        etSignature.setText(AppData.getValueStr(AppData.KEY_SIGNATORY));
        tvPhoneNumber.setText(AppData.getValueStr(AppData.KEY_PHONE_NUMBER));
        //将光标移至输入框文字末尾
        etNickname.setSelection(etNickname.getText().toString().length());
        etSignature.setSelection(etSignature.getText().toString().length());

    }

    /**
     * 点击事件（右上角保存、我的头像、手机号码、我的地址）
     *
     * @param view
     */
    @OnClick({R.id.tv_right_menu, R.id.iv_my_portrait, R.id.iv_camera, R.id.rl_phone_number, R.id.rl_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                saveUserInfo();
                break;
            case R.id.iv_my_portrait:
//                //我的头像，大图
//                mIvMyPortrait.setDrawingCacheEnabled(true);
//                Bitmap bitmap = Bitmap.createBitmap(mIvMyPortrait.getDrawingCache());
//                mIvMyPortrait.setDrawingCacheEnabled(false);
//                Intent intent = new Intent(this, MyPortraitActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("bitmap", bitmap);
//                intent.putExtra(MyPortraitActivity.KEY_PORTRAIT_URL, bundle);
//                startActivity(intent);
                break;
            case R.id.iv_camera:
                RxPermissions rxPermissions = new RxPermissions(this);
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                mPresenter.setPermissions(rxPermissions, permissions);
                break;
            case R.id.rl_phone_number:
                ActivityUtils.startActivity(new Intent(this, AlterPhoneActivity.class));
                break;
            case R.id.rl_address:
                ActivityUtils.startActivity(new Intent(this, MyAddressListActivity.class));
                break;
        }
    }


    /**
     * 保存用户信息
     */
    private void saveUserInfo() {
        nickname = etNickname.getText().toString();
        signature = etSignature.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.showLong("昵称不能为空");
            return;
        }
        showProgressDialog("保存");
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setId(AppData.getValueStr(AppData.KEY_USER_ID));
        paramsBean.setProfilePhotoUrl(protraitId);
        paramsBean.setNickName(nickname);
        paramsBean.setSignatory(signature);
        requestBean.setParams(paramsBean);
        String jsonData = new Gson().toJson(requestBean);
        request(UrlUtils.UPDATE_USER_INFO, jsonData, Object.class);
    }

    @Override
    protected void request(String requestUrl, String jsonData, Class clazz) {
        super.request(requestUrl, jsonData, clazz);
        mPresenter.request(requestUrl, jsonData, clazz);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        if (TextUtils.equals(requestUrl, UrlUtils.UPDATE_USER_INFO)) {
            //保存用户信息成功
            AppData.setValueStr(AppData.KEY_PORTRAIT_URL, protraitId);
            AppData.setValueStr(AppData.KEY_NICKNAME, nickname);
            AppData.setValueStr(AppData.KEY_SIGNATORY, signature);
            EventBusUtil.post(new Event<>(EventCode.MAIN_ME_A, "刷新"));
            finish();
        } else if (TextUtils.equals(requestUrl, UrlUtils.UPLOAD_IMAGES)) {
            //上传图片成功
            BGAImage.display(mIvMyPortrait, R.mipmap.bga_pp_ic_holder_light, mPhotoHelper.getCropFilePath(), BGABaseAdapterUtil.dp2px(200));
            ImageBean imageBean = (ImageBean) commonBean.getData();
            protraitId = imageBean.getFileIds().get(0);
        }
        dismissDialog();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
        if (TextUtils.equals(requestUrl, UrlUtils.UPDATE_USER_INFO)) {
            ToastUtils.showShort("保存用户信息报错：" + msg);
        } else if (TextUtils.equals(requestUrl, UrlUtils.UPLOAD_IMAGES)) {
            ToastUtils.showShort("上传图片报错：" + msg);
        }
        dismissDialog();
    }


    /**
     * mPresenter.setPermissions
     * 获取系统权限成功，可继续执行
     */
    @Override
    public void permissionsAreGranted(int type) {

        @SuppressLint("ObjectAnimatorBinding")
        ObjectAnimator down = ObjectAnimator.ofFloat(mQuickPopup, "translationY", -100, 0);
        down.setDuration(2000);
        mQuickPopup = QuickPopupBuilder.with(getContext())
                .contentView(R.layout.popup_select_photo)
                .config(new QuickPopupConfig()
                        .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                        .withClick(R.id.tv_camera, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                takePhoto();
                            }
                        }).withClick(R.id.tv_photos, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                choosePhoto();
                            }
                        }).withClick(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mQuickPopup.dismiss();
                            }
                        })
                ).show();
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
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
                //从图库选择图片成功
                try {
                    //准备执行裁剪图片
                    startActivityForResult(mPhotoHelper.getCropIntent(mPhotoHelper.getFilePathFromUri(data.getData()), 360, 360), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                //调用相机，拍摄照片成功
                try {
                    //准备执行裁剪图片
                    String path = mPhotoHelper.getCameraFilePath();
                    startActivityForResult(mPhotoHelper.getCropIntent(path, 360, 360), REQUEST_CODE_CROP);
                } catch (Exception e) {
                    mPhotoHelper.deleteCameraFile();
                    mPhotoHelper.deleteCropFile();
                    BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CODE_CROP) {
                //裁剪成功
                mPhotoHelper.deleteCameraFile();
                //上传图片
                mPresenter.uploadFiles(UrlUtils.UPLOAD_IMAGES, ImageBean.class, new File(mPhotoHelper.getCropFilePath()));
                mQuickPopup.dismiss();//关闭Popup
            }
        } else {
            if (requestCode == REQUEST_CODE_CROP) {
                mPhotoHelper.deleteCameraFile();//删除拍摄的照片
                mPhotoHelper.deleteCropFile();//删除裁剪的照片
            }
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (mPhotoHelper != null) {
            mPhotoHelper.deleteCameraFile();//删除拍摄的照片
            mPhotoHelper.deleteCropFile();//删除裁剪的照片
        }
    }
}
