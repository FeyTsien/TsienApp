package com.hdnz.inanming.ui.activity.me;

import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.content.ContentFragment;
import com.tsienlibrary.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class MyOrderActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tb_navigation)
    TabLayout mTab;
    @BindView(R.id.vp_fragment_container)
    CustomViewPager mViewPager;
    /**
     *  @field  tab标题
     */
    private List<String> titles;
    /**
     *  @field  fragmentList
     */
    private List<Fragment> mFragmentList;
    private List<String> alls ;
    private List<String> obligations ;
    private List<String> evaluates ;
    private RecyclerViewAdapter<String> allOrdersAdapter;
    private RecyclerViewAdapter<String> obligationsAdapter;
    private RecyclerViewAdapter<String> evaluatesAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initData() {
        //1
        titles = new ArrayList<>();
        titles.add("全部订单");
        titles.add("待付款");
        titles.add("待评价");

        //所有订单数据
        alls = new ArrayList<>();
        //待付款订单
        obligations = new ArrayList<>();
        //待评价
        evaluates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            alls.add(i + "");
            obligations.add(i + "");
            evaluates.add(i + "");
        }

        initRecyclerViewAdapter();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ContentFragment.newInstance(allOrdersAdapter));
        mFragmentList.add(ContentFragment.newInstance(obligationsAdapter));
        mFragmentList.add(ContentFragment.newInstance(evaluatesAdapter));
    }

    @Override
    protected void initView() {
        //2
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.my_order));

        //tab选择监听
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, titles);
        mViewPager.setAdapter(myPagerAdapter);

        //创建tab
        for (int i = 0; i < titles.size(); i++) {
            TabLayout.Tab tab = mTab.newTab();
            tab.setText(titles.get(i));
        }

        //联动viewpager
        mTab.setupWithViewPager(mViewPager);
    }

    /**
     * 根据不同布局，创建adapter
     */
    private void initRecyclerViewAdapter() {
        allOrdersAdapter = new RecyclerViewAdapter<String>(alls, R.layout.item_order_type1) {
            @Override
            public void bindView(MyViewHolder holder, int position) {

            }
        };

        obligationsAdapter = new RecyclerViewAdapter<String>(obligations, R.layout.item_order_type1) {
            @Override
            public void bindView(MyViewHolder holder, int position) {

            }
        };

        evaluatesAdapter = new RecyclerViewAdapter<String>(evaluates, R.layout.item_order_type1) {
            @Override
            public void bindView(MyViewHolder holder, int position) {

            }
        };
    }
}
