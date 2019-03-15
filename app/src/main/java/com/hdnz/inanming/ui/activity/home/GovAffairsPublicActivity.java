package com.hdnz.inanming.ui.activity.home;

import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.home.govaffairspublic.GAPInfoActivity;
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
 *     time   : 2019/01/15
 *     desc   :
 * </pre>
 */
public class GovAffairsPublicActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private List<AppBean.DataBean> mAppDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gov_affairs_public;
    }

    @Override
    protected void initData() {
        mAppDataList = new ArrayList<>();
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
//        request();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.government_affairs_public);

        mAdapter = new RecyclerViewAdapter<AppBean.DataBean>(mAppDataList, R.layout.item_gap) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.text_view, mAppDataList.get(position).getMsg());
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ActivityUtils.startActivity(GAPInfoActivity.class);
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter,false));
        transaction.commit();

    }

//    @Override
//    protected boolean isLoadSir() {
//        return true;
//    }
//
//    @Override
//    protected void initLoadSir(Object target) {
//        super.initLoadSir(mLayout);
//    }

    @Override
    protected void request() {
        String jsonData = "{}";
        mPresenter.request(UrlUtils.TEST, jsonData, AppBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
    }

    @Override
    public void requestFail(String requestUrl, String msg,int code) {
        super.requestFail(requestUrl, msg,code);
    }
}
