package com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.Itemdetails;

import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.ItemDetailsBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;

import butterknife.BindView;

public class BasicInfoFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private static ItemDetailsBean.GovitemBean mGovitemBean;

    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_unit)
    TextView mTvUnit;
    @BindView(R.id.tv_object)
    TextView mTvObject;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvType.setText(mGovitemBean.getHandleType());
        mTvUnit.setText(mGovitemBean.getAlias());
        mTvObject.setText(mGovitemBean.getHandleObj());
        mTvTime.setText(mGovitemBean.getHandleLimit());
    }


    /**
     * fragment静态传值
     */
    public static BasicInfoFragment newInstance(ItemDetailsBean.GovitemBean govitemBean) {
        BasicInfoFragment fragment = new BasicInfoFragment();
        mGovitemBean = govitemBean;
        return fragment;
    }

}
