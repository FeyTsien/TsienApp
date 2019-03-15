package com.hdnz.inanming.ui.fragment.home.govaffairsoffice;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.GAOBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.GovAffairInfoActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

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
public class GAODepartmentsFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    List<GAOBean.ClassifyBean> mDepartmentsList;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content_no_more;
    }

    @Override
    protected void initData() {
        mDepartmentsList = new ArrayList<>();
    }

    @Override
    protected void initView() {

        mRecyclerViewAdapter = new RecyclerViewAdapter<GAOBean.ClassifyBean>(mDepartmentsList, R.layout.item_gao_department) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mDepartmentsList.get(position).getName());
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.setOnItemClickListener(pos -> {
            Intent intent = new Intent(getActivity(), GovAffairInfoActivity.class);
            intent.putExtra(GovAffairInfoActivity.KEY_NAME, mDepartmentsList.get(pos).getName());
            intent.putExtra(GovAffairInfoActivity.KEY_ID, mDepartmentsList.get(pos).getId());
            intent.putExtra(GovAffairInfoActivity.KEY_DEPT_CLASSIFY, GovAffairInfoActivity.VALUE_DEPT);
            startActivity(intent);
        });

        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
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
        Map paramsMap = new HashMap();
        paramsMap.put("classifyPId", "314167616942247937");
        Map requestMap = new HashMap();
        requestMap.put("params", paramsMap);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestMap);
        mPresenter.request(UrlUtils.GOV_AFFAIRS_OFFICE, jsonData, GAOBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        mSmartRefreshLayout.finishRefresh();
        GAOBean gaoThemeBean = (GAOBean) commonBean.getData();
        mDepartmentsList.clear();
        mDepartmentsList.addAll(gaoThemeBean.getClassifyList());
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }

    /**
     * fragment静态传值
     */
    public static GAODepartmentsFragment newInstance() {
        GAODepartmentsFragment fragment = new GAODepartmentsFragment();
        return fragment;
    }

}
