package com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * date:2017/6/7
 */


public class GAITransactionsFragmentR extends MVPFragment<MVPContract.View, MVPPresenter> {

    //当前指定页
    private int pageIndex = 1;

    List<String> mThemesList;
    List<String> mThemeItemesList;
    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initData() {
        mThemesList = new ArrayList<>();
        mThemeItemesList = new ArrayList<>();
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");
        mThemesList.add("个人社会保障卡");

    }

    @Override
    protected void initView() {

        mAdapter = new RecyclerViewAdapter<String>(mThemesList, R.layout.item_gai_transaction) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mThemesList.get(position));
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                ToastUtils.showShort("一级菜单" + pos + "");
            }
        });


        //下拉刷新
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

        request();
    }

//
//    @Override
//    protected boolean isLoadSir() {
//        return true;
//    }


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
        mPresenter.request(UrlUtils.COLLECT_SHOPS, jsonData, HeadLineBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
//
//        //获取到指定list，发送给WokebenchFragment,进行刷新
//        HeadLineBean headLineBean = (HeadLineBean) commonBean.getData();
//        if (pageIndex <= 1) {
//            mSmartRefreshLayout.finishRefresh();
//            if (headLineBean.getRecords().size() == 0) {
//                //没有数据
//                mBaseLoadService.showCallback(NotDataCallback.class);
//                return;
//            }
//            mThemesList.clear();
//            mThemesList.addAll(headLineBean.getRecords());
//        } else {
//            if (pageIndex >= headLineBean.getPages()) {
//                //当前页和总页数相同
//                //完成加载并标记没有更多数据
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
//                pageIndex = headLineBean.getPages();
//            } else {
//                //完成加载
//                mSmartRefreshLayout.finishLoadMore();
//            }
//            mThemesList.addAll(headLineBean.getRecords());
//        }
//        mAdapter.notifyDataSetChanged();
//        mBaseLoadService.showSuccess();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {

    }

    /**
     * fragment静态传值
     */
    public static GAITransactionsFragmentR newInstance() {
        GAITransactionsFragmentR fragment = new GAITransactionsFragmentR();
        return fragment;
    }

}
