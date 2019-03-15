package com.hdnz.inanming.ui.activity.home.govaffairsoffice;

import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.GAIGuidancesFragment;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.GAITransactionsFragment;
import com.tsienlibrary.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/14
 *     desc   :【政务详情】-主题、部门的详情
 * </pre>
 */
public class GovAffairInfoActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEPT_CLASSIFY = "dept_classify";
    public static final String VALUE_DEPT = "is_dept";
    public static final String VALUE_CLASSIFY = "is_classify";

    private String mTitle;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gov_affairs_office;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra(KEY_NAME);

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("业务办理");
        mTitleList.add("办事指南");

        mFragmentList.add(GAITransactionsFragment.newInstance(getIntent().getStringExtra(KEY_ID), getIntent().getStringExtra(KEY_DEPT_CLASSIFY)));
        mFragmentList.add(GAIGuidancesFragment.newInstance(getIntent().getStringExtra(KEY_ID), getIntent().getStringExtra(KEY_DEPT_CLASSIFY)));
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, mTitle);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setScanScroll(true);//禁止滑动
        mViewPager.setOffscreenPageLimit(0);//预加载几页数据
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
//        mTabLayout.removeAllTabs();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                LogUtils.i("Woke","onPageScrolled" + position);
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 55f, false);

                LogUtils.i("Woke", "onPageSelected" + position);
                //此处判断选择了哪一页，position
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
