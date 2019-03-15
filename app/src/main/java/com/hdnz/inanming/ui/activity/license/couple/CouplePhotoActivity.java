package com.hdnz.inanming.ui.activity.license.couple;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * FileName:    CouplePhotoActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-13 15:09
 * Description: 夫妇合影照activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class CouplePhotoActivity extends MVPActivity<MVPContract.View, MVPPresenter>
        implements MVPContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_single_photo_desc)
    TextView tvSinglePhotoDesc;
    @BindView(R.id.rl_single_photo_hide)
    RelativeLayout rlSinglePhotoHide;
    @BindView(R.id.iv_single_photo_show)
    ImageView ivSinglePhotoShow;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_single_photo_content)
    RelativeLayout rlSinglePhotoContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_couple_photo;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.upload_couple_photo));
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.save);

        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }
        });
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
//        if (licenseImgBean.getCode() == 200) {
//            String imgUrl = licenseImgBean.getData().get(0);
//            rlSinglePhotoHide.setVisibility(View.GONE);
//            ivSinglePhotoShow.setVisibility(View.VISIBLE);
//            Glide.with(this).load(imgUrl).into(ivSinglePhotoShow);
//        }else {
//            rlSinglePhotoHide.setVisibility(View.VISIBLE);
//            ivSinglePhotoShow.setVisibility(View.GONE);
//        }
//        refreshLayout.finishRefresh(500);
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
        rlSinglePhotoHide.setVisibility(View.VISIBLE);
        ivSinglePhotoShow.setVisibility(View.GONE);
        refreshLayout.finishRefresh(500);
    }

    @OnClick({R.id.tv_right_menu, R.id.rl_single_photo_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_single_photo_content:
                new PopupWindowUtils(this).openPopupWindow(view, "");
                break;
        }
    }
}
