package com.hdnz.inanming.ui.activity.me;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.bean.result.ReservationBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
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
 * ====================
 * ===== 我的预约 =====
 * ====================
 */
public class MyReservationActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    //获取我的预约列表Url
    private String getMyReservationListUrl;
    //取消预约Url
    private String cancelReservationUrl;

    private List<ReservationBean> mDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_listview;
    }

    @Override
    protected void initData() {

        mDataList = new ArrayList<>();
    }

    @Override
    protected void initView() {

        setToolBar(mToolbar, mTvTitle, R.string.my_book);

        mAdapter = new RecyclerViewAdapter<ReservationBean>(mDataList, R.layout.item_book) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_business_name, mDataList.get(position).getBusinessName());
                holder.setTextView(R.id.tv_take_number, mDataList.get(position).getReservationNo());
                holder.setTextView(R.id.tv_book_time, mDataList.get(position).getReservationDate() + " " + mDataList.get(position).getResTime());

                TextView tvIsTake = holder.getView(R.id.tv_is_take);
                TextView BtnCancel = holder.getView(R.id.btn_cancel);

                if (TextUtils.equals(mDataList.get(position).getAccessstate(), "0")) {
                    //未取消 else {
                    if (TextUtils.equals(mDataList.get(position).getTakeState(), "0")) {
                        //未取号
                        tvIsTake.setText(R.string.not_take_num);
                        tvIsTake.setTextColor(Color.RED);
                        BtnCancel.setText(R.string.book_cancel);
                        BtnCancel.setTextColor(getResources().getColor(R.color.btn_save_selector));
                        BtnCancel.setBackgroundResource(R.drawable.btn_save_shape_selector);
                    } else if (TextUtils.equals(mDataList.get(position).getTakeState(), "1")) {
                        //已取号,显示叫号码
                        tvIsTake.setText(R.string.yet_take_num);
                        tvIsTake.setTextColor(Color.GREEN);
                        BtnCancel.setText(mDataList.get(position).getCallNo());
                        BtnCancel.setTextColor(Color.BLACK);
                        BtnCancel.setBackgroundColor(Color.WHITE);
                    } else if (TextUtils.equals(mDataList.get(position).getTakeState(), "2")) {
                        //已过号
                        tvIsTake.setText(R.string.lose_efficacy_num);
                        tvIsTake.setTextColor(Color.GRAY);
                        BtnCancel.setText(R.string.expired);
                        BtnCancel.setTextColor(Color.GRAY);
                        BtnCancel.setBackgroundColor(Color.WHITE);
                    }
                } else {
                    //已取消
                    BtnCancel.setText(R.string.expired);
                    BtnCancel.setTextColor(Color.GRAY);
                    BtnCancel.setBackgroundColor(Color.WHITE);
                    tvIsTake.setText(R.string.not_take_num);//未取号
                    tvIsTake.setTextColor(Color.GRAY);
                }

                BtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(mDataList.get(position).getAccessstate(), "0")) {
                            //取消预约
                            cancelReservationUrl = UrlUtils.CANCEL_RESERVATION
                                    + "&custNo=" + AppData.getValueStr(AppData.KEY_USER_ID)
                                    + "&reservationNo=" + mDataList.get(position).getReservationNo()
                                    + "&&reservationDate=" + mDataList.get(position).getReservationDate();
                            mPresenter.requestWithGetList(cancelReservationUrl, String.class);
                        }
                    }
                });
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();//因为后台没有做分页的功能
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
        super.initLoadSir(smartRefreshLayout);
    }

    @Override
    protected void request() {
        super.request();
        getMyReservationListUrl = UrlUtils.GET_MY_RESERVATION_LIST + "&custNo=" + AppData.getValueStr(AppData.KEY_USER_ID);
        mPresenter.requestWithGetList(getMyReservationListUrl, ReservationBean.class);
    }

    @Override
    public void requestListSuccess(String requestUrl, CommonBeanList commonBeanList) {
        super.requestListSuccess(requestUrl, commonBeanList);
        if (TextUtils.equals(requestUrl, getMyReservationListUrl)) {
            mDataList.clear();
            mDataList.addAll(commonBeanList.getData());
            mAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishRefresh();
        } else if (TextUtils.equals(requestUrl,cancelReservationUrl)) {
            request();
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
