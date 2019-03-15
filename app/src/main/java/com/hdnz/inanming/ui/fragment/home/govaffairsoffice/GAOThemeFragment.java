package com.hdnz.inanming.ui.fragment.home.govaffairsoffice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.GAOBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.GovAffairInfoActivity;
import com.hdnz.inanming.ui.adapter.ListViewAdapter;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * date:2017/6/7
 */


public class GAOThemeFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    //当前指定页
    private int pageIndex = 1;

    List<GAOBean.ClassifyBean> mThemesList;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ListViewAdapter mListViewAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content_listview_no_more;
    }

    @Override
    protected void initData() {
        mThemesList = new ArrayList<>();

    }

    @Override
    protected void initView() {

        //No.1
//        initThemesAdapter();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        //No.2
        mListViewAdapter = getListViewAdapter();
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort("listView一级菜单" + position + "");
                Intent intent = new Intent(getActivity(), GovAffairInfoActivity.class);
                intent.putExtra(GovAffairInfoActivity.KEY_NAME, mThemesList.get(position).getName());
                intent.putExtra(GovAffairInfoActivity.KEY_ID, mThemesList.get(position).getId());
                intent.putExtra(GovAffairInfoActivity.KEY_DEPT_CLASSIFY, GovAffairInfoActivity.VALUE_CLASSIFY);
                startActivity(intent);
            }
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

    private ListViewAdapter getListViewAdapter() {
        return new ListViewAdapter<GAOBean.ClassifyBean>(mThemesList, R.layout.item_gao_theme) {
            @Override
            public void bindView(MyViewHolder holder, GAOBean.ClassifyBean classifyBean) {
                TextView textView = holder.getView(R.id.tv_title);
                TextView textView2 = holder.getView(R.id.tv_describe);
                textView.setText(classifyBean.getName());
                textView2.setText(classifyBean.getRemark());
                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                if (classifyBean.getLevel().size() > 0) {//如果含有二级菜单则展示这些
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;//禁止滑动
                        }
                    };
                    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
//                    gridLayoutManager.setAutoMeasureEnabled(true);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(getThemeItemAdapter(classifyBean.getLevel()));
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }

            }
        };
    }

    /**
     * 初始化一级列表的adapter
     */
    private void initThemesAdapter() {

        mRecyclerViewAdapter = new RecyclerViewAdapter<GAOBean.ClassifyBean>(mThemesList, R.layout.item_gao_theme) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                int size = 1;
                holder.setTextView(R.id.tv_title, mThemesList.get(position).getName());
                holder.setTextView(R.id.tv_describe, mThemesList.get(position).getRemark());
                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                if (mThemesList.get(position).getLevel().size() > 0) {//如果含有二级菜单则展示这些
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
//                    gridLayoutManager.setAutoMeasureEnabled(true);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(getThemeItemAdapter(mThemesList.get(position).getLevel()));
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }

                holder.getView(R.id.rl_theme).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("二级菜单" + position + "");
//                        ActivityUtils.startActivity(GovAffairInfoActivity.class);
                    }
                });
            }
        };
    }

    /**
     * 初始化二级列表的adapter
     *
     * @return
     */
    private RecyclerViewAdapter getThemeItemAdapter(List<GAOBean.ClassifyBean.LevelBean> themeItemesList) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter<GAOBean.ClassifyBean.LevelBean>(themeItemesList, R.layout.item_gao_theme_item) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_item, themeItemesList.get(position).getName());
            }
        };
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ToastUtils.showShort("二级菜单" + pos + "");

                Intent intent = new Intent(getActivity(), GovAffairInfoActivity.class);
                intent.putExtra(GovAffairInfoActivity.KEY_NAME, themeItemesList.get(pos).getName());
                intent.putExtra(GovAffairInfoActivity.KEY_ID, themeItemesList.get(pos).getId());
                intent.putExtra(GovAffairInfoActivity.KEY_DEPT_CLASSIFY, GovAffairInfoActivity.VALUE_CLASSIFY);
                startActivity(intent);
            }
        });
        return recyclerViewAdapter;
    }

    @Override
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setClassifyPId("314167616942247936");//主题的Id:314167616942247936
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.GOV_AFFAIRS_OFFICE, jsonData, GAOBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        mSmartRefreshLayout.finishRefresh();
        GAOBean gaoThemeBean = (GAOBean) commonBean.getData();
        mThemesList.clear();
        mThemesList.addAll(gaoThemeBean.getClassifyList());
        mListViewAdapter.notifyDataSetChanged();
//        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }

    /**
     * fragment静态传值
     */
    public static GAOThemeFragment newInstance() {
        GAOThemeFragment fragment = new GAOThemeFragment();
        return fragment;
    }

}
