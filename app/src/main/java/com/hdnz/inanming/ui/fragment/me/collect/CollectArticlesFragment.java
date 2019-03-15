package com.hdnz.inanming.ui.fragment.me.collect;

import android.widget.ImageView;

import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.loadsir.callback.NotDataCallback;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * date:2017/6/7
 */


public class CollectArticlesFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    //当前指定页
    private int pageIndex = 1;

    List<HeadLineBean.RecordsBean> mArticleList;
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
        mArticleList = new ArrayList<>();
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
//        mRecyclerView.addItemDecoration(itemDivider);

        mAdapter = new RecyclerViewAdapter<HeadLineBean.RecordsBean>(mArticleList, R.layout.item_article) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mArticleList.get(position).getTitle());
                holder.setTextView(R.id.tv_writer_name, mArticleList.get(position).getUsername());
                holder.setTextView(R.id.tv_comments, mArticleList.get(position).getReplyCount());
                holder.setTextView(R.id.tv_like, mArticleList.get(position).getUpCount());

                GlideApp.with(getActivity())
                        .load(mArticleList.get(position).getPicUrl())
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into((ImageView) holder.getView(R.id.iv_pic));
                GlideApp.with(getActivity())
                        .load(mArticleList.get(position).getAuthHead())
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into((ImageView) holder.getView(R.id.iv_writer_portrait));
            }
        };

        mRecyclerView.setAdapter(mAdapter);


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

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void request() {
        RequestBean requestBean = new RequestBean();
        RequestBean.PageBean pageBean = new RequestBean.PageBean();
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(10);
        requestBean.setPage(pageBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.COLLECT_ARTICLES, jsonData, HeadLineBean.class);
        super.request();
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl,commonBean);
        //获取到指定list，发送给WokebenchFragment,进行刷新
        HeadLineBean headLineBean = (HeadLineBean) commonBean.getData();
        if (pageIndex <= 1) {
            mSmartRefreshLayout.finishRefresh();
            if (headLineBean.getRecords().size() == 0) {
                //没有数据
                mBaseLoadService.showCallback(NotDataCallback.class);
                return;
            }
            mArticleList.clear();
            mArticleList.addAll(headLineBean.getRecords());
        } else {
            if (pageIndex >= headLineBean.getPages()) {
                //当前页和总页数相同
                //完成加载并标记没有更多数据
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                pageIndex = headLineBean.getPages();
            } else {
                //完成加载
                mSmartRefreshLayout.finishLoadMore();
            }
            mArticleList.addAll(headLineBean.getRecords());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl,msg,code);
    }

    /**
     * fragment静态传值
     */
    public static CollectArticlesFragment newInstance() {
        CollectArticlesFragment fragment = new CollectArticlesFragment();
        return fragment;
    }

    /**
     * 启用懒加载
     * @return
     */
    @Override
    protected boolean isLazyLoad() {
        return true;
    }

}
