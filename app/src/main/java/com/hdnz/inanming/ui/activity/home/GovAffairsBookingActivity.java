package com.hdnz.inanming.ui.activity.home;

import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.bean.result.ReservationBeanA;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsbooking.GABBusinessActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.bean.CommonBeanList;
import com.tsienlibrary.mvp.GsonManger;
import com.tsienlibrary.utils.TextViewUitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2019/01/16
 *     desc   :【政务预约】
 * </pre>
 */
public class GovAffairsBookingActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    public static final String KEY_OFFICE_HALL = "office_hall";

    private List<ReservationBeanA> mDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gov_affairs_booking;
    }

    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.government_affairs_booking);

        mAdapter = new RecyclerViewAdapter<ReservationBeanA>(mDataList, R.layout.item_gab) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvDistance = holder.getView(R.id.tv_distance);
                TextView tvPeople = holder.getView(R.id.tv_people);
                TextViewUitls.setText(tvName, mDataList.get(position).getGovsector());
                TextViewUitls.setText(tvTime, mDataList.get(position).getAppointTime());
                TextViewUitls.setText(tvDistance, "没给距离km");
                String people = "当前有" + mDataList.get(position).getWaitPeopleTotalNum() + "人在等待办理（今日累计办理数量：" + mDataList.get(position).getBusineTodayTotalNum() + "）";
                TextViewUitls.setText(tvPeople, people);
            }
        };
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();


        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(GovAffairsBookingActivity.this,GABBusinessActivity.class);
                intent.putExtra(KEY_OFFICE_HALL,mDataList.get(pos).getGovsector());
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

        Map paramsMap = new HashMap();
        Map requestMap = new HashMap();
        paramsMap.put("currentPosition", AppData.getValueStr(AppData.KEY_LOCATION_LONGITUDE + "," + AppData.getValueStr(AppData.KEY_LOCATION_LATITUDE)));
        requestMap.put("params", paramsMap);
        String jsonData = GsonManger.getGsonManger().toJson(requestMap);
        mPresenter.requestList(UrlUtils.GET_RESERVATION_LIST, jsonData, ReservationBeanA.class);
        super.request();
    }

    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonList) {
        super.requestListSuccess(requestUrl, commonList);
        mDataList.clear();
        mDataList.addAll(commonList.getData());
        mAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
