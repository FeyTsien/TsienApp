package com.hdnz.inanming.ui.activity.me;

import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.fragment.me.collect.CollectArticlesFragment;
import com.hdnz.inanming.ui.fragment.me.collect.CollectShopsFragment;
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
 *     time   : 2018/12/04
 *     desc   : 【收藏】
 * </pre>
 */
public class CollectActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

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
        return R.layout.activity_collect;
    }

    @Override
    protected void initData() {

        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("商户");
        mTitleList.add("文章");

        mFragmentList.add(CollectShopsFragment.newInstance());
        mFragmentList.add(CollectArticlesFragment.newInstance());
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.collect);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setScanScroll(true);//可以滑动
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
