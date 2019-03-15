package com.hdnz.inanming.ui.fragment.workbench;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.TaskBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.headlinelist.HeadLineActivity;
import com.hdnz.inanming.ui.activity.workbench.CreateTaskActivity;
import com.hdnz.inanming.ui.activity.workbench.TaskSetActivity;
import com.hdnz.inanming.ui.adapter.MyPagerAdapter;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/10/30
 *     desc   :====== 工作台 ====
 * </pre>
 */
public class WorkbenchFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

//    private String TAG = getClass().getSimpleName();

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    List<String> mWokebenchMenuList;
    private List<TextView> textViewList;


    private RecyclerViewAdapter mWokebenchMenuAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tl_task_center)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;




    public static WorkbenchFragment newInstance() {
        return new WorkbenchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wokebench;
    }

    @Override
    protected void initData() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mWokebenchMenuList = new ArrayList<>();
        textViewList = new ArrayList<>();

        mWokebenchMenuList.add("考勤打卡");
        mWokebenchMenuList.add("汇报");
        mWokebenchMenuList.add("文件");

        //添加标题
        mTitleList.add("待办任务");
        mTitleList.add("已逾期");
        mTitleList.add("已完成");
        mTitleList.add("全部");
        //添加Fragment
        mFragmentList.add(new TaskFragmentA(1));
        mFragmentList.add(new TaskFragmentA(2));
        mFragmentList.add(new TaskFragmentA(3));
        mFragmentList.add(new TaskFragmentA(4));
    }

    @Override
    protected void initView() {
        initRecyclerView();//这个是上面的菜单选项的recyclerView
        initViewPager();
        request();
    }

    /**
     * 工作台菜单表初始化
     */
    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mWokebenchMenuAdapter = new RecyclerViewAdapter<String>(mWokebenchMenuList, R.layout.item_wokebench_menu) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.text_view, mWokebenchMenuList.get(position));
            }
        };
        mRecyclerView.setAdapter(mWokebenchMenuAdapter);
    }



    /**
     * TODO:初始化ViewPager(任务中心的四个可切换fragment)
     */
    private void initViewPager() {

        LogUtils.i("WokeBenchF", "mViewPager" + mViewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setScanScroll(true);//是否禁止滑动
        mViewPager.setOffscreenPageLimit(3);//预加载几页数据
        mViewPager.setAdapter(myPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.removeAllTabs();

        for (int i = 0; i < mTitleList.size(); i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View inflate = View.inflate(getActivity(), R.layout.tab_task_center, null);
            ((TextView) inflate.findViewById(R.id.tv_tab_title)).setText(mTitleList.get(i));
            textViewList.add(inflate.findViewById(R.id.tv_count));
            tab.setCustomView(inflate);
            mTabLayout.addTab(tab, i);
        }

//        mTabLayout.setTabMode(TabLayout.SCROLL_AXIS_HORIZONTAL);//设置tab模式，当前为系统默认模式
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                LogUtils.i("Woke","onPageScrolled" + position);
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 55f, false);

                LogUtils.i("Woke", "onPageSelected" + position);
                //此处判断选择了哪一页，
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
    protected boolean isRegisteredEventBus() {
        return super.isRegisteredEventBus();
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case EventCode.MAIN_WORKBENCH_A:
                //TaskFragment下拉刷新的同时也执行此刷新操作
                request();
                break;
        }
    }

    @Override
    protected void request() {
        super.request();
        mPresenter.request(UrlUtils.GET_TASKS_COUNT, "", TaskBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        if (TextUtils.equals(requestUrl, UrlUtils.GET_TASKS_COUNT)) {
            TaskBean taskBean = (TaskBean) commonBean.getData();
            textViewList.get(0).setText(taskBean.getUpcomingTasks());
            textViewList.get(1).setText(taskBean.getOverdue());
            textViewList.get(2).setText(taskBean.getCarryOut());
            textViewList.get(3).setText(taskBean.getTotal());
        }

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }

    @OnClick({R.id.tv_headline_list, R.id.tv_create_task, R.id.tv_task_set})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                //刷新
                request();
                break;
            case R.id.tv_headline_list:
                //打开单位头条
                Intent intent = new Intent(getActivity(), HeadLineActivity.class);
                intent.putExtra(HeadLineActivity.ACTIVITY_TITLE, getResources().getString(R.string.unit_headline));
                startActivity(intent);
                break;
            case R.id.tv_create_task:
                //进入任务设置
                ActivityUtils.startActivity(CreateTaskActivity.class);
                break;
            case R.id.tv_task_set:
                //进入任务设置
                ActivityUtils.startActivity(TaskSetActivity.class);
                break;
        }
    }

}