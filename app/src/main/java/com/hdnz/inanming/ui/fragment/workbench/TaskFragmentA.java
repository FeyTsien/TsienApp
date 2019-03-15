package com.hdnz.inanming.ui.fragment.workbench;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.TaskBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventBusUtil;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.loadsir.callback.NotDataCallback;
import com.tsienlibrary.mvp.GsonManger;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * date:2017/6/7
 */

@SuppressLint("ValidFragment")
public class TaskFragmentA extends MVPFragment<MVPContract.View, MVPPresenter> {

    private int mRequestStatus;
    private int pageIndex;

    List<TaskBean.ListBean> mTasksList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    TaskFragmentA(int status) {
        mRequestStatus = status;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initData() {
        mTasksList = new ArrayList<>();
        pageIndex = 1;
    }

    @Override
    protected void initView() {

        recyclerViewAdapter = new RecyclerViewAdapter<TaskBean.ListBean>(mTasksList, R.layout.item_task) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_task_title, mTasksList.get(position).getType());
                TextView tvDaiBan = holder.getView(R.id.tv_dai_ban);
                if (mTasksList.get(position).getAgentflag() == 1) {
                    tvDaiBan.setVisibility(View.VISIBLE);
                } else {
                    tvDaiBan.setVisibility(View.GONE);
                }
                TextView tvPersonName = holder.getView(R.id.tv_person_name);
                if (mTasksList.get(position).getStatus() == 1) {
                    tvPersonName.setText("未分配");
                    tvPersonName.setTextColor(Color.RED);
                } else {
                    tvPersonName.setText(mTasksList.get(position).getApplyName());
                    tvPersonName.setTextColor(getResources().getColor(R.color.colorTextTitle));
                }
                TextView tvTime = holder.getView(R.id.tv_time);
                if (mTasksList.get(position).getOverdue() == 1) {
                    holder.setTextView(R.id.tv_time, mTasksList.get(position).getLastTime() + "　已逾期");
                    tvTime.setTextColor(Color.RED);
                } else {//未逾期
                    holder.setTextView(R.id.tv_time, mTasksList.get(position).getLastTime());
                    tvTime.setTextColor(getResources().getColor(R.color.colorTextTitle));
                }
                holder.setTextView(R.id.tv_address, mTasksList.get(position).getApplyAdr() + "　" + mTasksList.get(position).getApplyName());

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
//        mRecyclerView.addItemDecoration(itemDivider);

        mRecyclerView.setAdapter(recyclerViewAdapter);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载
                pageIndex++;
                request();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新
                pageIndex = 1;
                request();
                EventBusUtil.post(new Event<>(EventCode.MAIN_WORKBENCH_A, "刷新任务条数"));
            }
        });
        request();

    }


    /**
     * 启用懒加载
     *
     * @return
     */
    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void request() {
        super.request();

//        RequestBean requestBean = new RequestBean();
//        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
//        RequestBean.PageBean pageBean = new RequestBean.PageBean();
////        paramsBean.setStatus(mRequestStatus);
//        requestBean.setParams(paramsBean);
//        pageBean.setPageIndex(pageIndex);
//        pageBean.setPageSize(10);
//        requestBean.setPage(pageBean);

        Map paramsMap = new HashMap();
        paramsMap.put("status",mRequestStatus);
        Map pageMap = new HashMap();
        pageMap.put("pageIndex",pageIndex);
        pageMap.put("pageSize",10);
        Map map = new HashMap();
        map.put("params",paramsMap);
        map.put("page",pageMap);
        String jsonData = GsonManger.getGsonManger().toJson(map);

        mPresenter.request(UrlUtils.GET_TASKS_LIST, jsonData, TaskBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        TaskBean taskBean = (TaskBean) commonBean.getData();
        if (pageIndex <= 1) {
            mSmartRefreshLayout.finishRefresh();
            if (taskBean.getList().size() == 0) {
                //没有数据
                mBaseLoadService.showCallback(NotDataCallback.class);
                return;
            } else {
                //获取到最新数据
                mTasksList.clear();
                mTasksList.addAll(taskBean.getList());
            }
        } else {
            if (pageIndex >= taskBean.getTotalPage()) {
                //当前页和总页数相同
                //完成加载并标记没有更多数据
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                pageIndex = taskBean.getTotalPage();
            } else {
                //完成加载
                mSmartRefreshLayout.finishLoadMore();
            }
            mTasksList.addAll(taskBean.getList());
        }
        recyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }


}
