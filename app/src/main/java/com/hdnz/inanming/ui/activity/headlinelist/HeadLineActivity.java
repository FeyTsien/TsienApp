package com.hdnz.inanming.ui.activity.headlinelist;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.loadsir.callback.NotDataCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class HeadLineActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String ACTIVITY_TITLE = "activity_title";
    //页面顶部标题
    private String mTitle;
    //当前指定页
    private int pageIndex = 1;

    private List<HeadLineBean.RecordsBean> mDataList;

    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_line;
    }

    @Override
    protected void initData() {
        mTitle = getIntent().getStringExtra(ACTIVITY_TITLE);
        mDataList = new ArrayList<>();
        request();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, mTitle);

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                pageIndex++;
                request();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新
                pageIndex = 1;
                request();
            }
        });

        mAdapter = new RecyclerViewAdapter<HeadLineBean.RecordsBean>(mDataList, R.layout.item_headline) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_headline_title, mDataList.get(position).getTitle());
                holder.setTextView(R.id.tv_headline_writer, mDataList.get(position).getUsername());
                holder.setTextView(R.id.tv_headline_time, mDataList.get(position).getInitTime());
                GlideApp.with(HeadLineActivity.this)
                        .load(mDataList.get(position).getPicUrl())
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into((ImageView) holder.getView(R.id.iv_head_line_pic));
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(mLayout);
    }

    @Override
    protected void request() {
        RequestBean requestBean = new RequestBean();
        RequestBean.PageBean pageBean = new RequestBean.PageBean();
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(10);
        requestBean.setPage(pageBean);
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setType("3");
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.HEAD_LINE, jsonData, HeadLineBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        HeadLineBean headLineBean = (HeadLineBean) commonBean.getData();
        if (pageIndex <= 1) {
            mRefreshLayout.finishRefresh();
            if (headLineBean.getRecords().size() == 0) {
                //没有数据
                mBaseLoadService.showCallback(NotDataCallback.class);
                return;
            }
            mDataList.clear();
            mDataList.addAll(headLineBean.getRecords());
        } else {
            if (pageIndex >= headLineBean.getPages()) {
                //当前页和总页数相同
                //完成加载并标记没有更多数据
                mRefreshLayout.finishLoadMoreWithNoMoreData();
                pageIndex = headLineBean.getPages();
            } else {
                //完成加载
                mRefreshLayout.finishLoadMore();
            }
            mDataList.addAll(headLineBean.getRecords());
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }
}