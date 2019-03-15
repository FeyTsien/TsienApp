package com.hdnz.inanming.ui.activity.home.govaffairsoffice.govaffairinfo;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.ItemDetailsBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.Itemdetails.AdmissionDocumentFragment;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.Itemdetails.BasicInfoFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.GsonManger;
import com.tsienlibrary.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class ItemdetailsActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String KEY_TITLE = "title";
    public static final String KEY_ID = "id";
    public static final String KEY_ORG_ID = "org_id";

    private String mId;
    private String mOrgId;

    private String mTitle;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_item_details;
    }

    @Override
    protected void initData() {

        mTitle = getIntent().getStringExtra(KEY_TITLE);
        mId = getIntent().getStringExtra(KEY_ID);
        mOrgId = getIntent().getStringExtra(KEY_ORG_ID);

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();

    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, mTitle);

        request();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(mLayout);
    }

    @Override
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setId(mId);
        paramsBean.setOrgId(mOrgId);
        requestBean.setParams(paramsBean);
        String jsonData = GsonManger.getGsonManger().toJson(requestBean);
        mPresenter.request(UrlUtils.GET_ITEM_DETAILS, jsonData, ItemDetailsBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);

        ItemDetailsBean itemDetailsBean = (ItemDetailsBean) commonBean.getData();
        if (itemDetailsBean.getGovitem() != null) {
            mTitleList.add("基本信息");
            mFragmentList.add(BasicInfoFragment.newInstance(itemDetailsBean.getGovitem()));
        }
        if (itemDetailsBean.getDatumList() != null) {
            mTitleList.add("申请材料");
            mFragmentList.add(AdmissionDocumentFragment.newInstance(itemDetailsBean.getDatumList()));
        }
        initViewPager();
    }

    private void initViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setScanScroll(true);//禁止滑动
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
//        mTabLayout.removeAllTabs();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
