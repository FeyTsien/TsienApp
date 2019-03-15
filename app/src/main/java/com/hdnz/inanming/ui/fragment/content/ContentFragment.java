package com.hdnz.inanming.ui.fragment.content;

import android.annotation.SuppressLint;

import com.hdnz.inanming.R;
import com.tsienlibrary.bean.CommonBean;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * date:2017/6/7
 */


@SuppressLint("ValidFragment")
public class ContentFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private String mUrl;
    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public ContentFragment() {
    }

    public ContentFragment(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    public ContentFragment(RecyclerViewAdapter adapter, String url) {
        this.mAdapter = adapter;
        this.mUrl = url;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initData() {
//        String jsonData = "";
//        mPresenter.request(mUrl, jsonData);
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

        mRecyclerView.setAdapter(mAdapter);


        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

    }


    /**
     * fragment静态传值
     */
    public static ContentFragment newInstance(RecyclerViewAdapter adapter) {
        ContentFragment fragment = new ContentFragment(adapter);
        return fragment;
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl,commonBean);
        //获取到指定list，发送给WokebenchFragment,进行刷新
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl,msg,code);
    }
}
