package com.hdnz.inanming.ui.activity.license.couple;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.utils.PopupWindowUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    CoupleIdCardActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-13 11:50
 * Description: 夫妇双方身份证照activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class CoupleIdCardActivity extends MVPActivity<MVPContract.View, MVPPresenter>
        implements MVPContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_camera_tip)
    TextView tvCameraTip;
    @BindView(R.id.tv_first_photo_desc)
    TextView tvFirstPhotoDesc;
    @BindView(R.id.rl_first_photo_hide)
    RelativeLayout rlFirstPhotoHide;
    @BindView(R.id.iv_first_photo_show)
    ImageView ivFirstPhotoShow;
    @BindView(R.id.rl_first_photo_content)
    RelativeLayout rlFirstPhotoContent;
    @BindView(R.id.tv_second_photo_desc)
    TextView tvSecondPhotoDesc;
    @BindView(R.id.rl_second_photo_hide)
    RelativeLayout rlSecondPhotoHide;
    @BindView(R.id.iv_second_photo_show)
    ImageView ivSecondPhotoShow;
    @BindView(R.id.rl_second_photo_content)
    RelativeLayout rlSecondPhotoContent;
    @BindView(R.id.tv_third_photo_desc)
    TextView tvThirdPhotoDesc;
    @BindView(R.id.rl_third_photo_hide)
    RelativeLayout rlThirdPhotoHide;
    @BindView(R.id.iv_third_photo_show)
    ImageView ivThirdPhotoShow;
    @BindView(R.id.rl_third_photo_content)
    RelativeLayout rlThirdPhotoContent;
    @BindView(R.id.tv_fourth_photo_desc)
    TextView tvFourthPhotoDesc;
    @BindView(R.id.rl_fourth_photo_hide)
    RelativeLayout rlFourthPhotoHide;
    @BindView(R.id.iv_fourth_photo_show)
    ImageView ivFourthPhotoShow;
    @BindView(R.id.rl_fourth_photo_content)
    RelativeLayout rlFourthPhotoContent;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_couple_id_card;
    }

    @Override
    protected void initData() {

    }
    /**
     * 初始化
     */
    @Override
    protected void initView() {
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.upload_couple_license_photo));
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.save);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(200);
            }
        });
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }

    @OnClick({R.id.tv_right_menu, R.id.rl_first_photo_content, R.id.rl_second_photo_content, R.id.rl_third_photo_content, R.id.rl_fourth_photo_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                ToastUtils.showShort( "保存", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_first_photo_content:
                new PopupWindowUtils(this).openPopupWindow(view, "");
                break;
            case R.id.rl_second_photo_content:
                new PopupWindowUtils(this).openPopupWindow(view, "");
                break;
            case R.id.rl_third_photo_content:
                new PopupWindowUtils(this).openPopupWindow(view, "");
                break;
            case R.id.rl_fourth_photo_content:
                new PopupWindowUtils(this).openPopupWindow(view, "");
                break;
        }
    }
}
