package com.hdnz.inanming.ui.activity.home;

import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.GAODepartmentsFragment;
import com.hdnz.inanming.ui.fragment.home.govaffairsoffice.GAOThemeFragment;
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
 *     desc   :【政务办事】
 * </pre>
 */
public class GovAffairsOfficeActivity extends MVPActivity<MVPContract.View, MVPPresenter>{

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

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("主题");
        mTitleList.add("部门");

        mFragmentList.add(GAOThemeFragment.newInstance());
        mFragmentList.add(GAODepartmentsFragment.newInstance());
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.government_affairs_office);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setScanScroll(true);//禁止滑动
//        mViewPager.setOffscreenPageLimit(0);//预加载几页数据
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
