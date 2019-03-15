package com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.bean.result.GAIBean;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.ResultBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.GovAffairInfoActivity;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.govaffairinfo.ItemdetailsActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.hdnz.inanming.webview.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * date:2017/6/7
 */


@SuppressLint("ValidFragment")
public class GAIGuidancesFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private static String deptOrClassifyPId;
    private static String mSelectValue;

    private List<GAIBean.GovitemsBean> mGovitemsList;
    private List<ResultBean.OrgListBean> mOrgList;
    private String selectItemId;
    private String selectItemTitle;

    private RecyclerViewAdapter mAdapter;

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
        mGovitemsList = new ArrayList<>();
        mOrgList = new ArrayList<>();
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

        mAdapter = new RecyclerViewAdapter<GAIBean.GovitemsBean>(mGovitemsList, R.layout.item_gao_department) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mGovitemsList.get(position).getName());
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (mGovitemsList.get(pos).getType() == 1) {
                    selectItemTitle = mGovitemsList.get(pos).getName();
                    selectItemId = mGovitemsList.get(pos).getId();
                    getOrganList(selectItemId);
                } else {
                    //找李阳
                    WebViewActivity.goToWebView(getActivity(), "http://www.baidu.com");

                }
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

    @Override
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        if (TextUtils.equals(mSelectValue, GovAffairInfoActivity.VALUE_CLASSIFY)) {
            paramsBean.setClassifyPId(deptOrClassifyPId);
        } else {
            paramsBean.setDept(deptOrClassifyPId);
        }
        paramsBean.setType("2");
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.GOV_AFFAIRS_INFO_LIST, jsonData, GAIBean.class);
    }

    /**
     * 根据指定Id获取结构列表
     *
     * @param itemId
     */
    private void getOrganList(String itemId) {
        RequestBean requestBean = new RequestBean();
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setItemId(itemId);
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.GET_ORGAN_LIST, jsonData, ResultBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);

        if (TextUtils.equals(requestUrl, UrlUtils.GOV_AFFAIRS_INFO_LIST)) {
            mSmartRefreshLayout.finishRefresh();
            //获取到指定list，发送给WokebenchFragment,进行刷新
            GAIBean gaiBean = (GAIBean) commonBean.getData();
            mGovitemsList.clear();
            mGovitemsList.addAll(gaiBean.getGovitems());
            mAdapter.notifyDataSetChanged();

        } else if (TextUtils.equals(requestUrl, UrlUtils.GET_ORGAN_LIST)) {
            ResultBean resultBean = (ResultBean) commonBean.getData();
            mOrgList.clear();
            mOrgList.addAll(resultBean.getOrgList());
            showPickerView();
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }

    /**
     * 弹出选择器
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Intent intent = new Intent(getActivity(), ItemdetailsActivity.class);
                intent.putExtra(ItemdetailsActivity.KEY_TITLE, selectItemTitle);
                intent.putExtra(ItemdetailsActivity.KEY_ID, selectItemId);
                intent.putExtra(ItemdetailsActivity.KEY_ORG_ID, mOrgList.get(options1).getId());
                startActivity(intent);
            }
        })
                .setTitleText("选择机构")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setTextXOffset(1, 1, 1)//不偏移则文字会被压缩
                .setSelectOptions(0)//默认选中项
                .build();

        pvOptions.setPicker(mOrgList);//一级选择器
        pvOptions.show();
    }


    /**
     * fragment静态传值
     */
    public static GAIGuidancesFragment newInstance(String id, String selectValue) {
        GAIGuidancesFragment fragment = new GAIGuidancesFragment();
        deptOrClassifyPId = id;
        mSelectValue = selectValue;
        return fragment;
    }

}
