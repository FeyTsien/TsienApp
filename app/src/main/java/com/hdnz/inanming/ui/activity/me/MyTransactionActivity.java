package com.hdnz.inanming.ui.activity.me;

import android.content.Intent;

import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.fragment.transaction.TransactionFragment;
import com.tsienlibrary.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 我的办理
 */
public class MyTransactionActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tb_navigation)
    TabLayout mTabLayout;
    @BindView(R.id.vp_fragment_container)
    CustomViewPager mViewPager;

    private List<String> mTitles;
    private List<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_transaction;
    }


    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        //初始化标题
        mTitles.add("全部业务");
        mTitles.add("办理进行");
        mTitles.add("办理完成");
        mTitles.add("办理失败");
        mFragments.add(TransactionFragment.newInstance("1"));
        mFragments.add(TransactionFragment.newInstance("2"));
        mFragments.add(TransactionFragment.newInstance("3"));
        mFragments.add(TransactionFragment.newInstance("4"));
    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.my_transaction));

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setScanScroll(true);//禁止滑动
        mViewPager.setOffscreenPageLimit(0);//预加载几页数据

        for (int i = 0; i < mTitles.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(mTitles.get(i));
            mTabLayout.addTab(tab, i);
        }
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.i("xiaoxin", "onPageScrolled:  " + position);
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 55f, false);
                LogUtils.e("xiaoxin", "onPageSelected:  " + position);
                //此处判断选择了哪一页
                if (position == 0) {
//                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
