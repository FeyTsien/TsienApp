package com.hdnz.inanming.ui.activity.message;

import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/12
 *     desc   :
 * </pre>
 */
public class GovernmentMessagesActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private List<AppBean.DataBean> mAppDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_listview;
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
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.government_affairs);
        mAdapter = new RecyclerViewAdapter<AppBean.DataBean>(mAppDataList, R.layout.item_headline) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.text_view, mAppDataList.get(position).getMsg());
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter,true));
        transaction.commit();
        mBaseLoadService.showSuccess();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(refreshLayout);
    }

    @Override
    protected void request() {
        super.request();
        String jsonData = "{}";
        mPresenter.request(UrlUtils.TEST, jsonData, AppBean.class);
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
//        if(没有数据){
//            mBaseLoadService.showCallback(NotDataCallback.class);
//        }
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }
}
