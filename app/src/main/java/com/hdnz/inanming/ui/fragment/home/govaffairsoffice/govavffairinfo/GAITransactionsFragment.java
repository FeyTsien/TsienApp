package com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.result.GAIBean;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.ResultBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.activity.home.govaffairsoffice.GovAffairInfoActivity;
import com.hdnz.inanming.ui.adapter.ListViewAdapter;
import com.hdnz.inanming.utils.GlideUtils;
import com.hdnz.inanming.utils.UrlUtils;
import com.hdnz.inanming.webview.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * date:2017/6/7
 */


public class GAITransactionsFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private static String deptOrClassifyPId;
    private static String mSelectValue;

    private List<GAIBean.GovitemsBean> mGovitemsList;
    private List<ResultBean.OrgListBean> mOrgList;
    private String selectItemId;

    private ListViewAdapter mListViewAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content_listview_no_more;
    }

    @Override
    protected void initData() {
        mGovitemsList = new ArrayList<>();
        mOrgList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mListViewAdapter = new ListViewAdapter<GAIBean.GovitemsBean>(mGovitemsList, R.layout.item_gai_transaction) {
            @Override
            public void bindView(MyViewHolder holder, GAIBean.GovitemsBean govitemsBean) {
                TextView textView = holder.getView(R.id.tv_title);
                textView.setText(govitemsBean.getName());
                GlideApp.with(getActivity())
                        .load(GlideUtils.getGlideUrl(govitemsBean.getIcon()))
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into((ImageView) holder.getView(R.id.riv_bg));
            }
        };
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort("listView一级菜单" + position + "");
                getOrganList(mGovitemsList.get(position).getId());
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
        paramsBean.setType("1");
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
        selectItemId = itemId;
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
            mListViewAdapter.notifyDataSetChanged();
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
                WebViewActivity.goToWebView(getActivity(), UrlUtils.H5_HOME_BASE_URL+"govEveDetail?token=" + AppData.getValueStr(AppData.KEY_TOKEN) + "&itemId=" + selectItemId + "&orgId=" + mOrgList.get(options1).getId()+"&applyUser="+AppData.getValueStr(AppData.KEY_USER_ID));

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
    public static GAITransactionsFragment newInstance(String id,String selectValue) {
        GAITransactionsFragment fragment = new GAITransactionsFragment();
        deptOrClassifyPId = id;
        mSelectValue = selectValue;
        return fragment;
    }

}
