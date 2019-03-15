package com.hdnz.inanming.ui.fragment.message;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.ui.activity.message.GovernmentMessagesActivity;
import com.hdnz.inanming.ui.activity.message.SystemMessagesActivity;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================
 * ===== 消息 =====
 * ================
 */

public class MessageFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private int pageIndex = 1;

    private List<AppBean.DataBean> mAppDataList;
    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        mAppDataList = new ArrayList<>();
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
    }

    @Override
    protected void initView() {

        mAdapter = new RecyclerViewAdapter<AppBean.DataBean>(mAppDataList, R.layout.item_message) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.text_view, mAppDataList.get(position).getMsg());
            }
        };

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                refreshLayout.finishLoadMore();
                pageIndex--;
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
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        requestBean.setParams(new RequestBean.ParamsBean());
        String jsonData = GsonManger.getGsonManger().toJson(requestBean);
        mPresenter.request(UrlUtils.GET_MESSAGE_LIST, jsonData, AppBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        mAdapter.notifyDataSetChanged();
        if(pageIndex>1){
            refreshLayout.finishLoadMore();
        }else {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
