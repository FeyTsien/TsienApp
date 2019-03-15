package com.hdnz.inanming.ui.activity.transaction.detail;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionDetailActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-19 18:18
 * Description: 查看办理详情（activity）
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TransactionDetailActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_transaction_list)
    RecyclerView rvTransactionList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_error_page)
    RelativeLayout rlErrorPage;
    @BindView(R.id.rl_empty_page)
    RelativeLayout rlEmptyPage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.view_details));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvTransactionList.setLayoutManager(linearLayoutManager);
        rvTransactionList.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.me_divider), 1, 1));
        refreshLayout.autoRefresh();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData(0);
            }
        });
    }

    /**
     * 请求数据
     *
     * @param type
     */
    private void requestData(int type) {
    }
//
//    @Override
//    public void requestSuccess(TransactionDetailBean transactionDetailBean) {
//        rlErrorPage.setVisibility(View.GONE);
//        if (null != transactionDetailBean) {
//            List<TransactionDetailBean.DataBean> datas = transactionDetailBean.getData();
//            if (null != datas && datas.size() > 0) {
//                rvTransactionList.setVisibility(View.VISIBLE);
//                rlEmptyPage.setVisibility(View.GONE);
//                TransactionDetailAdapter adapter = new TransactionDetailAdapter(this, datas);
//                rvTransactionList.setAdapter(adapter);
//            }else {
//                rvTransactionList.setVisibility(View.GONE);
//                rlEmptyPage.setVisibility(View.VISIBLE);
//            }
//        } else {
//            rvTransactionList.setVisibility(View.GONE);
//            rlEmptyPage.setVisibility(View.VISIBLE);
//        }
//        refreshLayout.finishRefresh(200);
//    }
//
//    @Override
//    public void requestFail(String msg) {
//        rlErrorPage.setVisibility(View.VISIBLE);
//        rvTransactionList.setVisibility(View.GONE);
//        rlEmptyPage.setVisibility(View.GONE);
//        refreshLayout.finishRefresh(200);
//    }
}
