package com.hdnz.inanming.ui.activity.license.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.certification.idCard.IdCardAuthenticationActivity;
import com.hdnz.inanming.ui.activity.license.couple.CoupleIdCardActivity;
import com.hdnz.inanming.ui.activity.license.couple.CouplePhotoActivity;
import com.hdnz.inanming.ui.activity.license.couple.MarriageActivity;
import com.hdnz.inanming.ui.activity.license.householdRegister.HouseholdRegisterActivity;
import com.hdnz.inanming.ui.activity.license.inchPhoto.InchPhotoActivity;
import com.hdnz.inanming.ui.activity.license.register.RegistrationFormActivity;
import com.hdnz.inanming.ui.activity.license.student.StudentIdActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    LicenseDetailActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-12 10:09
 * Description: 用户证照详情activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class LicenseDetailActivity extends MVPActivity<MVPContract.View, MVPPresenter>
        implements MVPContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_license_img_list)
    RecyclerView rvLicenseImgList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_right_menu)
    ImageView ivRightMenu;
    @BindView(R.id.rl_empty_page)
    RelativeLayout rlEmptyPage;
    /**
     * @field 证照类型
     */
    private String licenseType;
    /**
     * @field 证照描述
     */
    private String licenseDesc;
    private RecyclerViewAdapter<String> imgsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_license_detail;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        licenseDesc = bundle.getString("licenseDesc");
        licenseType = bundle.getString("licenseType");
    }
    @Override
    protected void initView() {
        refreshLayout.autoRefresh();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(100);
            }
        });
        //设置title
        setToolBar(mToolbar, tvTitle, licenseDesc + "详情");
        //显示右边菜单
        tvRightMenu.setVisibility(View.VISIBLE);
        tvRightMenu.setText(R.string.edit);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvLicenseImgList.setLayoutManager(linearLayoutManager);
//        rvLicenseImgList.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.me_divider), 1, 1));
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
//        int code = licenseImgBean.getCode();
//        if (code == 200) {
//            rlEmptyPage.setVisibility(View.GONE);
//            rvLicenseImgList.setVisibility(View.VISIBLE);
//            final List<String> datas = licenseImgBean.getData();
//            imgsAdapter = new RecyclerViewAdapter(datas, R.layout.mine_license_detail_item) {
//                @Override
//                public void bindView(MyViewHolder holder, int position) {
//                    String imgUrl = datas.get(position);
//                    holder.setImageView(R.id.iv_cheak_img, imgUrl);
//                }
//            };
//
//            rvLicenseImgList.setAdapter(imgsAdapter);
//            imgsAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(int pos) {
//                    //图片预览
//                    ToastUtils.showShort("图片预览");
//                    Intent intent = new Intent(LicenseDetailActivity.this, BrowseImagesActivity.class);
//                    intent.putStringArrayListExtra("imgUrls", (ArrayList<String>) datas);
//                    intent.putExtra("pos", pos);
//                    ActivityUtils.startActivity(intent);
////                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
//                }
//            });
//        } else {
//            rlEmptyPage.setVisibility(View.VISIBLE);
//            rvLicenseImgList.setVisibility(View.GONE);
//        }
//        refreshLayout.finishRefresh(500);
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
        rlEmptyPage.setVisibility(View.VISIBLE);
        rvLicenseImgList.setVisibility(View.GONE);
        refreshLayout.finishRefresh(500);
    }

    @OnClick(R.id.tv_right_menu)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_menu:
                ToastUtils.showShort( "编辑");
                Intent intent = null;
                if ("0".equals(licenseType)) {
                    intent = new Intent(this, IdCardAuthenticationActivity.class);
                } else if ("1".equals(licenseType)) {
                    intent = new Intent(this, HouseholdRegisterActivity.class);
                } else if ("2".equals(licenseType)) {
                    intent = new Intent(this, StudentIdActivity.class);
                } else if ("3".equals(licenseType)) {
                    intent = new Intent(this, InchPhotoActivity.class);
                } else if ("4".equals(licenseType)) {
                    intent = new Intent(this, CoupleIdCardActivity.class);
                } else if ("5".equals(licenseType)) {
                    intent = new Intent(this, MarriageActivity.class);
                } else if ("6".equals(licenseType)) {
                    intent = new Intent(this, CouplePhotoActivity.class);
                } else if ("7".equals(licenseType)) {
                    intent = new Intent(this, RegistrationFormActivity.class);
                }
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
