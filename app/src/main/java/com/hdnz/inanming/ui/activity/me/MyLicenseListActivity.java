package com.hdnz.inanming.ui.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.LicenseTypeBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.me.license.LicenseInfoActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.loadsir.callback.NotDataCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * Description: 【我的证照列表】
 */
public class MyLicenseListActivity extends MVPActivity<MVPContract.View, MVPPresenter> implements MVPContract.View {

    List<LicenseTypeBean.PaperstypeBean> mLicenseList;

    RecyclerViewAdapter mRecyclerViewAdapter;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_license_list;
    }

    @Override
    protected void initData() {
        mLicenseList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, R.string.my_license);
        tvRightMenu.setText("新增");
        tvRightMenu.setVisibility(View.VISIBLE);
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            request();
        });


        mRecyclerViewAdapter = new RecyclerViewAdapter<LicenseTypeBean.PaperstypeBean>(mLicenseList, R.layout.item_gao_department) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mLicenseList.get(position).getName());
            }
        };

        mRecyclerViewAdapter.setOnItemClickListener(pos -> {
            Intent intent = new Intent(MyLicenseListActivity.this, LicenseInfoActivity.class);
            intent.putExtra(LicenseInfoActivity.KEY_LICENSE_TITLE, mLicenseList.get(pos).getName());
            intent.putExtra(LicenseInfoActivity.KEY_LICENSE_TYPE_ID, mLicenseList.get(pos).getCardtypeId());
            startActivity(intent);

        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mRecyclerViewAdapter, false));
        transaction.commit();

        request();

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
        mPresenter.request(UrlUtils.GET_LICENSE_TYPE_LIST, "", LicenseTypeBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        refreshLayout.finishRefresh();
        LicenseTypeBean licenseBean = (LicenseTypeBean) commonBean.getData();
        if (licenseBean.getPaperstypelist().size() > 0) {
            mLicenseList.clear();
            mLicenseList.addAll(licenseBean.getPaperstypelist());
            mRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            mBaseLoadService.showCallback(NotDataCallback.class);
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
        refreshLayout.finishRefresh();
    }

}
