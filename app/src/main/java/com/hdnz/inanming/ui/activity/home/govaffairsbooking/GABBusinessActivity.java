package com.hdnz.inanming.ui.activity.home.govaffairsbooking;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.ReservationBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.home.GovAffairsBookingActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsbooking.gabbusiness.GABBookingInfoActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBeanList;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/16
 *     desc   : 政务预约—业务列表
 * </pre>
 */
public class GABBusinessActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String KEY_BUSINESS_ID="business_id";

    private String mTitle;
    private List<ReservationBean> mDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gab_business;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra(GovAffairsBookingActivity.KEY_OFFICE_HALL);
        mDataList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, mTitle);

        mAdapter = new RecyclerViewAdapter<ReservationBean>(mDataList, R.layout.item_gab_business) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mDataList.get(position).getBusinessName());
                holder.setTextView(R.id.tv_now_num, mDataList.get(position).getPresentCall());
                holder.setTextView(R.id.tv_wait_people, mDataList.get(position).getWaitPeople());
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();


        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(GABBusinessActivity.this,GABBookingInfoActivity.class);
                intent.putExtra(GovAffairsBookingActivity.KEY_OFFICE_HALL,mTitle);
                intent.putExtra(KEY_BUSINESS_ID,mDataList.get(pos).getBusinessId());
                ActivityUtils.startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMoreWithNoMoreData();//没有分页
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                request();
            }
        });
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
        mPresenter.requestWithGetList(UrlUtils.GET_BUSINES_LIST, ReservationBean.class);
    }

    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonBeanList) {
        super.requestListSuccess(requestUrl, commonBeanList);
        mDataList.clear();
        mDataList.addAll(commonBeanList.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
