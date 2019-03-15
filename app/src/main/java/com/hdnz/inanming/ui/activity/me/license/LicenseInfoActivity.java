package com.hdnz.inanming.ui.activity.me.license;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.LicenseBean;
import com.hdnz.inanming.bean.result.LicenseTypeBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.me.MyLicenseListActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.GlideUtils;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class LicenseInfoActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    public static final String KEY_LICENSE_TITLE = "license_title";
    public static final String KEY_LICENSE_TYPE_ID = "license_type_id";

    private String mTitle;
    private String mLicenseTypeId;
    List<LicenseBean.PapersBean> mPapersList;

    RecyclerViewAdapter mRecyclerViewAdapter;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_license_info;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra(KEY_LICENSE_TITLE);
        mLicenseTypeId = getIntent().getStringExtra(KEY_LICENSE_TYPE_ID);
        mPapersList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, mTitle);
        tvRightMenu.setText("新增");
        tvRightMenu.setVisibility(View.VISIBLE);
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            request();
        });


        mRecyclerViewAdapter = new RecyclerViewAdapter<LicenseBean.PapersBean>(mPapersList, R.layout.item_license_info) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_name, mPapersList.get(position).getDescription());
                ImageView ivLicense = holder.getView(R.id.iv_license);
                GlideApp.with(LicenseInfoActivity.this)
                        .load(GlideUtils.getGlideUrl(mPapersList.get(position).getCard()))
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into(ivLicense);
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mRecyclerViewAdapter, false));
        transaction.commit();

        request();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(refreshLayout);
    }

    @Override
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setCardtypeId(mLicenseTypeId);
        requestBean.setParams(paramsBean);
        String jsonData = GsonManger.getGsonManger().toJson(requestBean);
        mPresenter.request(UrlUtils.GET_LICENSE_INFO, jsonData, LicenseBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        refreshLayout.finishRefresh();
        LicenseBean licenseBean = (LicenseBean) commonBean.getData();
        mPapersList.clear();
        mPapersList.addAll(licenseBean.getPapersList());
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
        refreshLayout.finishRefresh();
    }

}